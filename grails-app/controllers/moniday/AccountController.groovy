package moniday

import com.moniday.AppUtil
import com.moniday.User
import com.moniday.command.AccountDetailCO
import com.moniday.command.PersonalDetailCO
import grails.plugin.springsecurity.annotation.Secured

import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

@Secured(['ROLE_ADMIN', 'ROLE_USER', "ROLE_SUB_ADMIN"])
class AccountController {

    def springSecurityService
    def accountService
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
        println(params)
        println(params.dob_month)
        println(params.dob_year)
        println(params.dob_day)
        println(params.uniqueId)
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
        println uniqueId
        render(view: '', model: [uniqueId: uniqueId, accountDetail: new AccountDetailCO()])
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def saveAccountDetail() {

    }

}