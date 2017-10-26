package moniday

import com.moniday.User
import com.moniday.command.UserCO
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class PublicController {

    def springSecurityService
    def accountService
    def scrapService
    def mangoPayService
    def fireBaseService
    def emailService

    def register() {
        render(view: 'register', model: [userCO: new UserCO()])
    }

    def saveRegisterUser(UserCO userCO) {
        if (userCO.validate()) {
            User user = accountService.saveUser(userCO)
            if (user) {
                springSecurityService.reauthenticate(userCO.username)
                emailService.sendRegistrationMail(userCO.username)
                redirect(controller: "account", action: 'personalDetail', params: [uniqueId: user.uniqueId])
            }
        }
        render "FAILED"
    }

    def forgetPassword() {
        render(view: '/account/scrappedBankDescription')
    }

    def mangoPay() {
        mangoPayService.serviceMethod()
        render "Tested MangoPay API"
    }

    def saveBanks() {
        fireBaseService.saveBanks()
        render "Bank has been populated"
    }
}