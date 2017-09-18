package com.moniday.command

import com.moniday.enums.Country
import com.moniday.enums.Currency
import com.moniday.enums.Nationality
import grails.validation.Validateable

class PersonalDetailCO implements Validateable {
    String firstName
    String lastName
    Date dateOfBirth
    Long age
    Nationality nationality
    Country country
    Currency currency
    static constraints = {
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
    }
}