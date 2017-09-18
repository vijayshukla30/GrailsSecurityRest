package com.moniday.command

import grails.validation.Validateable

class AccountDetailCO implements Validateable {
    String bankName
    String bankUsername
    String bankPassword
    List<SecurityDetailCO> securities

    static constraints = {
        bankName nullable: false, blank: false
        bankUsername nullable: false, blank: false
        bankPassword nullable: false, blank: false
    }
}