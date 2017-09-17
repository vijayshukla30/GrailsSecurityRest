package com.moniday.command

import com.moniday.enums.Country

class DirectDebitMandateCO {
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