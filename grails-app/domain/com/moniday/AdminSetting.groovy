package com.moniday

class AdminSetting {

    String mangoPayUrl
    String mangoPayClientId
    String mangoPayEmailId
    String mangoPayPassKey
    String sendgridUsername
    String sendgridPassword
    //email address using which email will be sent
    String sendgridEmail
//    String firebaseConfigurationPath
    String firebaseServerUrl
    Double minDeductionAmount = 0.0

    static constraints = {
        mangoPayUrl nullable: true, blank: true
        mangoPayClientId nullable: true, blank: true
        mangoPayEmailId nullable: true, blank: true
        mangoPayPassKey nullable: true, blank: true
        sendgridUsername nullable: true, blank: true
        sendgridPassword nullable: true, blank: true
        sendgridEmail nullable: true, blank: true
//        firebaseConfigurationPath nullable: true, blank: true
        firebaseServerUrl nullable: true, blank: true
        minDeductionAmount nullable: true, blank: true
    }
}
