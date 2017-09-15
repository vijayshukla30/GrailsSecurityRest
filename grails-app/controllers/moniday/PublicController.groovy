package moniday

import com.moniday.User
import com.moniday.command.UserCO
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class PublicController {

    def fireBaseService
    def springSecurityService
    def accountService

    def register() {
        render(view: 'register', model: [userCO: new UserCO()])
    }

    def saveRegisterUser(UserCO userCO) {
        if (userCO.validate()) {
            User user = accountService.saveUser(userCO)
            if (user) {
                fireBaseService.createUser(user, userCO.password)
                springSecurityService.reauthenticate(userCO.username)
                redirect(controller: "account", action: 'personalDetail', params: [uniqueId: user.uniqueId])
            }
        }
        render "FAILED"
    }

    def forgetPassword() {
        render fireBaseService.serviceMethod()
    }
}