package com.moniday

import grails.gsp.PageRenderer
import uk.co.desirableobjects.sendgrid.SendGridEmail
import uk.co.desirableobjects.sendgrid.SendGridEmailBuilder
import uk.co.desirableobjects.sendgrid.SendGridService

class EmailService {

    static transactional = false

    SendGridService sendGridService
    PageRenderer groovyPageRenderer
    String mailSender = 'puneetmungali93@gmail.com'

    def serviceMethod() {}

    def sendRegistrationMail(String toEmail) {
        String myTemplateString = groovyPageRenderer.render(template: "/emailtemplates/registerSuccess", model: [user: toEmail])
        SendGridEmail email = new SendGridEmailBuilder()
                .from(mailSender)
                .to(toEmail)
                .subject('Welcome to Moniday')
                .withHtml(myTemplateString)
                .build()
        sendGridService.send(email)
    }

    def sendPasswordResetMail(String toEmail, String resetLink) {
        String myTemplateString = groovyPageRenderer.render(template: "/emailtemplates/resetPassword", model: [user: toEmail, resetlink: resetLink])
        SendGridEmail email = new SendGridEmailBuilder()
                .from(mailSender)
                .to(toEmail)
                .subject('Moniday -Password Reset')
                .withHtml(myTemplateString)
                .build()
        sendGridService.send(email)
    }
}
