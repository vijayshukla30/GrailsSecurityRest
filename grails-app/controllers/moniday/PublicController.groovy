package moniday

import com.moniday.User
import com.moniday.command.UserCO
import com.moniday.firebase.FirebaseInitializer
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
                flash.msg = "You have been succesfully registerd. Please complete your profile."
                redirect(controller: "account", action: 'personalDetail', params: [uniqueId: user.uniqueId])
            }
        }
        render "FAILED"
    }

    def forgetPassword() {
        if (params.username) {
            String userName = params.username
            User user = User.findByUsername(userName)
            if (user) {
                String path = "${createLink(controller: 'public', action: 'resetPassword', absolute: true, params: [name: user?.username, id: user?.uniqueId])}"
                if (user.passwordForgotten) {
                    flash.info = "Password reset link already sent to your mail"
                } else {
                    emailService.sendPasswordResetMail(userName, path)
                    user.passwordForgotten = true
                    user.save(flush: true, failOnError: true)
                    flash.info = "Password reset mail is sent check your registered mail"
                }
                redirect(uri: "/")
            } else {
                flash.warn = "User with this emailId/Username does not exist"
                render(view: 'passwordReset')
            }
        } else {
            render(view: 'passwordReset')
        }
    }

    def resetPassword() {
        String userName = params.name
        User user = User.findByUsername(userName)
        if (user.passwordForgotten) {
            render(view: 'newPassword', model: [name: userName])
        } else {
            flash.info = "The password reset link has already been used"
            redirect(uri: "/")
        }
    }

    def saveResetPassword() {
        String userName = params.name
        String newPassword = params.password
        User user = User.findByUsername(userName)
        user.password = newPassword
        user.passwordForgotten = false
        user.save(flush: true)
        flash.info = "Password is reset.. Login to continue"
        redirect(uri: "/")
    }

    def mangoPay() {
        mangoPayService.serviceMethod()
        render "Tested MangoPay API"
    }

    def saveBanks() {
        FirebaseInitializer.saveBanks()
        render "Bank has been populated"
    }
}