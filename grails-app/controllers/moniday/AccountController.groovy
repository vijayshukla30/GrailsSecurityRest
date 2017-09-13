package moniday

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER', 'ROLE_ADMIN', 'ROLE_SUB_ADMIN'])
class AccountController {

    def index() {
        render "login"
    }

    def logout() {
        render "logout"
    }

    def personalDetail(String uniqueId) {
        render uniqueId
    }

    def savePersonalDetail() {

    }

    def accountDetail(String uniqueId) {

    }

    def saveAccountDetail() {

    }

}