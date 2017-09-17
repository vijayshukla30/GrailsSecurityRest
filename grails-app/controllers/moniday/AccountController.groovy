package moniday

import com.moniday.User
import com.moniday.command.PersonalDetailCO
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER', "ROLE_SUB_ADMIN"])
class AccountController {

    def springSecurityService

    def index() {
        User user = springSecurityService.currentUser as User
        render(view: 'index', model: [user: user])
    }

    def logout() {
        render "logout"
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def personalDetail(String uniqueId) {
        println uniqueId
        render(view: 'personalDetail', model: [uniqueId: uniqueId, personalDetailCO: new PersonalDetailCO()])
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def savePersonalDetail() {

    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def accountDetail(String uniqueId) {

    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def saveAccountDetail() {

    }

}