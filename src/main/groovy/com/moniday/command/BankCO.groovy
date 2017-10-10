package com.moniday.command

import grails.validation.Validateable

class BankCO implements Validateable {
    String bankName
    String bankURL
    String bankFirebaseId

    static constraints = {
        bankName nullable: false, blank: false
        bankURL nullable: false, blank: false
        bankFirebaseId nullable: true, blank: false
    }
}