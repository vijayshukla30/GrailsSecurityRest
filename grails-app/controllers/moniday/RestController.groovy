package moniday

import com.firebase.Bank
import com.moniday.ScrapBankJob
import com.moniday.User
import com.moniday.command.AccountDetailCO
import com.moniday.command.PersonalDetailCO
import com.moniday.firebase.FirebaseInitializer
import com.moniday.util.AppUtil
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

class RestController {

    static allowedMethods = [index: "GET", personalDetail: "GET", savePersonalDetail: "POST",
                             banks: "GET", accountDetail: "POST", debitMendateDetail: "POST"]

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
        String[] dateValue = personalDetailCO.dateOfBirth.split("/")
        LocalDate dobDate = LocalDate.of(dateValue[2] as int, AppUtil.getMonth(dateValue[1]), dateValue[0] as int)
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

}
