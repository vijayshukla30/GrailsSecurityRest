package com.moniday.command

import com.moniday.enums.CountryOfResidence

class DirectDebitMandateCO {
    String owner
    String addressLine1
    String city
    String region
    String postCode
    CountryOfResidence countryOfResidence
    String IBAN
    String BIC
    static constraints = {
    }
}