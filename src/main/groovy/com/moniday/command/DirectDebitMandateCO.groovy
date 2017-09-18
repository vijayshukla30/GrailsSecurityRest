package com.moniday.command

import com.moniday.enums.Country
import grails.validation.Validateable

class DirectDebitMandateCO implements Validateable {
    String owner
    String addressLine1
    String city
    String region
    String postCode
    Country countryOfResidence
    String IBAN
    String BIC
    static constraints = {
    }
}