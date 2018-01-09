package moniday

import com.firebase.Bank
import com.moniday.AdminSetting
import com.moniday.AuthenticationToken
import com.moniday.ScrapBankJob
import com.moniday.User
import com.moniday.command.AccountDetailCO
import com.moniday.command.PersonalDetailCO
import com.moniday.dto.DeductionDetailDTO
import com.moniday.dto.PersonDTO
import com.moniday.dto.RestAccountDTO
import com.moniday.enums.Country
import com.moniday.enums.Currency
import com.moniday.firebase.FirebaseInitializer
import com.moniday.util.AppUtil
import com.moniday.util.RestUtil
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

class RestController {

    static responseFormats = ['json']

    static allowedMethods = [index         : "GET", personalDetail: "GET", savePersonalDetail: "POST",
                             banks         : "GET", accountDetail: "POST", debitMendateDetail: "POST",
                             saveBank      : "POST", adminSettings: "GET", updateAdminSettings: "POST",
                             getLendingPageRecord: "GET", logout: "POST", getCountries: "GET", getCurrencies: "GET"]

    def springSecurityService
    def mangoPayService
    def fireBaseService


    @Secured(['ROLE_USER'])
    def personalDetail() {
        User user = springSecurityService.currentUser as User
        if (user) {
            render user as JSON
        } else {
            render(status: 404, "Invalid User")
        }
    }

    @Secured(['ROLE_USER'])
    def savePersonalDetail(PersonalDetailCO personalDetailCO) {
        User user = springSecurityService.currentUser as User
        String[] dateValue = personalDetailCO.dateOfBirth.split("-")
        LocalDate dobDate = LocalDate.of(dateValue[0] as int, AppUtil.getMonth(dateValue[1]), dateValue[2] as int)
        LocalDate todayDate = LocalDate.now()
        Period period = Period.between(dobDate, todayDate)
        personalDetailCO.dateOfBirth = AppUtil.createBankDateString(Date.from(dobDate.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant()))
        personalDetailCO.age = period.years
        boolean validPersonalDetail = personalDetailCO?.validate()
        if (user && validPersonalDetail) {
            FirebaseInitializer.savePersonalDetail(personalDetailCO, user?.firebaseId)
            mangoPayService.createUser(user, personalDetailCO)
            mangoPayService.createWalletForUser(user)
            render(status: 200, "success")
        } else if (!user) {
            render(status: 404, "Invalid User")
        } else {
            personalDetailCO.errors.allErrors.each {
                println(it)
            }
            render(status: 400, "Person Detail is not valid")
        }
    }

    @Secured(['ROLE_USER'])
    def banks() {
        User user = springSecurityService.currentUser as User
        String uniqueId = user.uniqueId
        if (uniqueId) {
            List<Bank> banks = FirebaseInitializer.banks
            render banks as JSON
        } else {
            render(status: 204, "No Banks Found")
        }
    }

    @Secured(['ROLE_ADMIN'])
    def saveBank() {
        def jsonObject = request.JSON
        String bankName = jsonObject.bankName
        String bankUrl = jsonObject.bankURL
        FirebaseInitializer.saveBanks(bankName, bankUrl)
        render(status: 200, "Bank Saved")
    }

    @Secured(['ROLE_USER'])
    def saveAccountDetail(AccountDetailCO accountDetailCO) {
        User user = springSecurityService.currentUser as User
        if (user) {
            List<Bank> banks = FirebaseInitializer.banks
            banks.each {
                if (params.bankNameId?.equals(it.bankName)) {
                    accountDetailCO.bankNameFirebase = it.bankFirebaseId
                }
            }
            if (accountDetailCO?.validate()) {
                fireBaseService.saveAccountDetail(accountDetailCO, user?.firebaseId)
                ScrapBankJob.triggerNow([username: user?.username])
            } else {
                accountDetailCO.errors.allErrors.each {
                    println(it)
                }
                render(status: 400, "Account Detail is not valid")
            }
            render(status: 200, "success")
        } else if (!user) {
            render(status: 503, "Invalid User")
        }
    }


    @Secured(['ROLE_USER'])
    def saveDebitMendateDetail() {}

    @Secured(['ROLE_ADMIN'])
    def adminSettings() {
        AdminSetting adminSetting = AdminSetting.get(1)
        render adminSetting as JSON
    }

    @Secured(['ROLE_ADMIN'])
    def updateAdminSettings(AdminSetting setting) {
        AdminSetting adminSetting = AdminSetting.get(1)
        bindData(adminSetting, setting)
        adminSetting.save(flush: true)
        render(status: 200, "Admin Setting updated")
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def getLendingPageRecord() {
/*        def jsonObject = request.JSON
        String uniqueId = jsonObject.uniqueId
        User user = User.findByUniqueId(uniqueId)*/
        User user = springSecurityService.currentUser as User
        RestAccountDTO restAccountDTO = new RestAccountDTO()
        if (user) {
            Map personalMap = FirebaseInitializer.getUserScrap(user?.firebaseId)
            PersonDTO personDTO = null
            if (personalMap) {
                Double investmentSum = 0
                personDTO = new PersonDTO(personalMap)
                restAccountDTO.accountValue = RestUtil.calculateAccountBalance(personDTO.accounts)
                restAccountDTO.deductionDetailDTOList = personDTO.getDeductionHistory()
                for (DeductionDetailDTO deductionDetailDTO : personDTO.getDeductionHistory()) {
                    investmentSum += deductionDetailDTO.amount
                }
                restAccountDTO.thirtyDaysInvestments = investmentSum
            }
            render(restAccountDTO as JSON)
        } else {
            render(status: 503, "Invalid User")
        }
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def getDeductionHistory() {
        /*def jsonObject = request.JSON
        String uniqueId = jsonObject.uniqueId
        User user = User.findByUniqueId(uniqueId)*/
        User user = springSecurityService.currentUser as User
        if (user) {
            Map personalMap = FirebaseInitializer.getUserScrap(user?.firebaseId)
            PersonDTO personDTO = null
            if (personalMap) {
                personDTO = new PersonDTO(personalMap)
                List<DeductionDetailDTO> deductionDetailDTOS = personDTO.getDeductionHistory()
                render(deductionDetailDTOS as JSON)
            } else {
                render(status: 503, "Invalid User")
            }
        } else {
            render(status: 503, "Invalid User")
        }
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def logout() {
        User user = springSecurityService.currentUser as User
        if (user) {
            def userName = user.username
            def token = AuthenticationToken.findByUsername(userName)
            token.delete()
            render(status: 200, "Successfully logged out")
        } else {
            render(status: 503, "Invalid User")
        }
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def getCountries() {
        List<Country> countries = Country.list()
        List response = []
        for (country in countries) {
//            println(country.key+" "+ Country.valueOf(country.key).toString())
            Map countryMap = [:]
            countryMap.put("country",Country.valueOf(country.key).toString())
            countryMap.put("code", country.key)
            response.add(countryMap)
        }
//        println(response as JSON)
        render response as JSON
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def getCurrencies() {
        List<Currency> currencies = Currency.list()
        List response = []
        for (currency in currencies) {
            Map currencyMap = [:]
            currencyMap.put("currency",Currency.valueOf(currency.key).toString())
            currencyMap.put("code", currency.key)
            response.add(currencyMap)
        }
        render response as JSON
    }

    def saveBankDetails(AccountDetailCO accountDetailCO) {
        User user = springSecurityService.currentUser as User
        if (user) {
            List<Bank> banks = FirebaseInitializer.banks
            String bankUrl = ""
            banks.each {
                if (params.bankNameId?.equals(it.bankName)) {
                    accountDetailCO.bankNameFirebase = it.bankFirebaseId
                    bankUrl = it.bankURL
                }
            }
            if (accountDetailCO?.validate()) {
                fireBaseService.saveAccountDetail(accountDetailCO, user?.firebaseId)
                ScrapBankJob.triggerNow([username: user?.username])
            } else {
                accountDetailCO.errors.allErrors.each {
                    println(it)
                }
                render(status: 503, "Account details are not valid")
            }
            render(status: 200, "success")
        } else if (!user) {
            render(status: 503, "Invalid User")
        }
    }

}
