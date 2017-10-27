package moniday

import com.firebase.Bank
import com.moniday.Role
import com.moniday.ScrapBankJob
import com.moniday.User
import com.moniday.command.*
import com.moniday.firebase.FirebaseInitializer
import com.moniday.util.AppUtil
import grails.plugin.springsecurity.annotation.Secured

import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

@Secured(['ROLE_ADMIN', 'ROLE_USER', "ROLE_SUB_ADMIN"])
class AccountController {

    def springSecurityService
    def fireBaseService
    def scrapService
    def mangoPayService

    def index() {
        User user = springSecurityService.currentUser as User
        Set<Role> roles = user.getAuthorities()
        if (roles[0].getAuthority() == "ROLE_ADMIN") {
            render(view: 'admin', model: [user: user])
        } else if (roles[0].getAuthority() == "ROLE_SUB_ADMIN") {
            render("you are sub admin")
        } else if (roles[0].getAuthority() == "ROLE_USER") {
            render(view: 'index', model: [user: user])
        } else {
            flash.message = "Some error occured. Please try again"
            redirect(uri: "/")
        }
    }

    def logout() {
        render "logout"
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def personalDetail(String uniqueId) {
        if (!uniqueId) {
            User user = springSecurityService.currentUser as User
            uniqueId = user.uniqueId
        }
        if (uniqueId) {
            render(view: 'personalDetail', model: [uniqueId: uniqueId, personalDetailCO: new PersonalDetailCO()])
        } else {
            println "No UniqueId found at personal detail action"
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def savePersonalDetail(PersonalDetailCO personalDetailCO) {
        User user = User.findByUniqueId(params.uniqueId)
        LocalDate dobDate = LocalDate.of(params.dob_year as int, AppUtil.getMonth(params.dob_month), params.dob_day as int)
        LocalDate todayDate = LocalDate.now()
        Period period = Period.between(dobDate, todayDate)
        personalDetailCO.dateOfBirth = Date.from(dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        personalDetailCO.age = period.years
        boolean validPersonalDetail = personalDetailCO?.validate()
        if (user && validPersonalDetail) {
            fireBaseService.savePersonalDetail(personalDetailCO, user?.firebaseId)
            mangoPayService.createUser(user, personalDetailCO)
            mangoPayService.createWalletForUser(user)
            redirect(action: 'accountDetail', params: [uniqueId: params.uniqueId])
        } else if (!user) {
            render "Invalid User"
        } else {
            personalDetailCO.errors.allErrors.each {
                println(it)
            }
            render "Person Detail is not valid"
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def accountDetail(String uniqueId) {
        if (!uniqueId) {
            User user = springSecurityService.currentUser as User
            uniqueId = user.uniqueId
        }
        if (uniqueId) {
            List<Bank> banks = FirebaseInitializer.banks
            List<BankCO> bankCOS = []
            banks.each {
                bankCOS.add(new BankCO(bankName: it.bankName, bankURL: it.bankURL, bankFirebaseId: it.bankFirebaseId))
            }
            render(view: 'accountDetail', model: [uniqueId: uniqueId, accountDetail: new AccountDetailCO(), bankCOS: bankCOS])
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def saveAccountDetail(AccountDetailCO accountDetailCO) {
        User user = User.findByUniqueId(params.uniqueId)
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
                render "Account Detail is not valid"
            }
            redirect(controller: 'account', action: 'debitMandateDetail', params: [uniqueId: params.uniqueId])
        } else if (!user) {
            render "Invalid User"
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def securityDetail(String uniqueId) {
        if (!uniqueId) {
            User user = springSecurityService.currentUser as User
            uniqueId = user.uniqueId
        }
        println uniqueId
        if (uniqueId) {
            render(view: 'securityDetail', model: [uniqueId: uniqueId, securityDetail: new SecurityDetailCO()])
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def saveSecurityDetail() {
        User user = User.findByUniqueId(params.uniqueId)
        if (user) {
            fireBaseService.saveSecurityDetail(params.question, params.answer, user?.firebaseId)
            redirect(action: 'debitMandateDetail', params: [uniqueId: params.uniqueId])
        } else {
            render "//////"
        }
    }

    def debitMandateDetail(String uniqueId) {
        if (!uniqueId) {
            User user = springSecurityService.currentUser as User
            uniqueId = user.uniqueId
        }
        println uniqueId
        if (uniqueId) {
            render(view: 'debitMandateDetail', model: [uniqueId: uniqueId, debitMandateDetail: new DirectDebitMandateCO()])
        }
    }

    def saveDebitMandateDetail(DirectDebitMandateCO debitMandateCO) {
        User user = User.findByUniqueId(params.uniqueId)
        boolean validDebitDetail = debitMandateCO?.validate()
        if (user && validDebitDetail) {
            fireBaseService.saveDirectDebitMandateDetail(debitMandateCO, user?.firebaseId)
            mangoPayService.createMandateForUser(debitMandateCO, user)
            fireBaseService.updateUserForMangoPay(user)
            render "Direct Debit Information hase been saved"
        } else if (!user) {
            render "Invalid User"
        } else {
            debitMandateCO.errors.allErrors.each {
                println(it)
            }
            render "Accountt Detail is not valid"
        }
    }

    def mangoPayReturn() {
        println(":******************************************:")
        println request.properties
        println(":******************************************:")
        println params
        println(":******************************************:")
        println params.JSON
        println(":******************************************:")

        render 200
    }
}