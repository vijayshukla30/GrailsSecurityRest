package moniday

import com.moniday.User
import com.moniday.command.UserCO
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class PublicController {

    def fireBaseService

    def register() {
        render(view: 'register', model: [userCO: new UserCO()])
    }

    def saveRegisterUser(UserCO userCO) {
        println(userCO.properties)
        User user = new User(userCO)
        user.fireBaseUserId = "1"
        if (user.validate()) {
            user.save(flush: true)
        } else {
            user.errors.allErrors.each {
                println(it)
            }
        }
        redirect(controller: "account", action: 'personalDetail', params: [uniqueId: user.uniqueId])
    }

    def forgetPassword() {
        render fireBaseService.serviceMethod()
    }
}