package moniday

import com.moniday.AppUtil
import com.moniday.User
import com.moniday.command.AccountDetailCO
import com.moniday.command.DirectDebitMandateCO
import com.moniday.command.PersonalDetailCO
import com.moniday.command.SecurityDetailCO
import grails.plugin.springsecurity.annotation.Secured

import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

@Secured(['ROLE_ADMIN', 'ROLE_USER', "ROLE_SUB_ADMIN"])
class AccountController {

    def springSecurityService
    def fireBaseService

    def index() {
        User user = springSecurityService.currentUser as User
        render(view: 'index', model: [user: user])
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
        println uniqueId
        if (uniqueId) {
            render(view: 'personalDetail', model: [uniqueId: uniqueId, personalDetailCO: new PersonalDetailCO()])
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
            render(view: 'accountDetail', model: [uniqueId: uniqueId, accountDetail: new AccountDetailCO()])
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def saveAccountDetail(AccountDetailCO accountDetailCO) {
        User user = User.findByUniqueId(params.uniqueId)
        boolean validAccountDetail = accountDetailCO?.validate()
        if (user && validAccountDetail) {
            fireBaseService.saveAccountDetail(accountDetailCO, user?.firebaseId)
            redirect(action: 'securityDetail', params: [uniqueId: params.uniqueId])
        } else if (!user) {
            render "Invalid User"
        } else {
            accountDetailCO.errors.allErrors.each {
                println(it)
            }
            render "Accountt Detail is not valid"
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
}