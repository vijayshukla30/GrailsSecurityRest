package com.moniday.command

import grails.validation.Validateable

class AdminSettingCO implements Validateable {

    String mangoPayUrl
    String mangoPayClientId
    String mangoPayEmailId
    String mangoPayPassKey
    String sendgridUsername
    String sendgridPassword
    String sendgridEmail
//    String firebaseConfigurationPath
    String firebaseServerUrl

    static constraints = {
        mangoPayUrl nullable: true, blank: false
        mangoPayClientId nullable: true, blank: false
        mangoPayEmailId nullable: true, blank: false
        mangoPayPassKey nullable: true, blank: false
        sendgridUsername nullable: true, blank: false
        sendgridPassword nullable: true, blank: false
        sendgridEmail nullable: true, blank: false
//        firebaseConfigurationPath nullable: true, blank: false
        firebaseServerUrl nullable: true, blank: false
    }
}
