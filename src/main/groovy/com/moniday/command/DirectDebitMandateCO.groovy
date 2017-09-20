package com.moniday.command

import com.moniday.enums.Country
import grails.validation.Validateable

class DirectDebitMandateCO implements Validateable {
    String owner
    String addressLine1
    String city
    String region
    String postCode
    Country country
    String iban
    String bic
    static constraints = {
        owner nullable: false, blank: false
        addressLine1 nullable: false, blank: false
        city nullable: false, blank: false
        region nullable: false, blank: false
        postCode nullable: false, blank: false
        iban nullable: false, blank: false
        bic nullable: false, blank: false
    }
}