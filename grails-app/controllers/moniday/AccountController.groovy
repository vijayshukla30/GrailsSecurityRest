package moniday

import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_FULLY_AUTHENTICATED'])
class AccountController {

    def index() {
        render "login"
    }

    def logout() {
        render "logout"
    }

    @Secured(['ROLE_ADMIN', 'ROLE_USER'])
    def personalDetail(String uniqueId) {
        render uniqueId
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