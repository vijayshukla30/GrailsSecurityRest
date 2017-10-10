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

    def register() {
        render(view: 'register', model: [userCO: new UserCO()])
    }

    def saveRegisterUser(UserCO userCO) {
        if (userCO.validate()) {
            User user = accountService.saveUser(userCO)
            if (user) {
                springSecurityService.reauthenticate(userCO.username)
                redirect(controller: "account", action: 'personalDetail', params: [uniqueId: user.uniqueId])
            }
        }
        render "FAILED"
    }

    def forgetPassword() {
//        PersonDTO personDTO = scrapService.scrapCAPCA()
//        render new JsonBuilder(personDTO).toPrettyString()
        fireBaseService.saveBanks()

        render "DONE"
    }

    def mangoPay() {
        mangoPayService.serviceMethod()
        render "Tested MangoPay API"
    }
}