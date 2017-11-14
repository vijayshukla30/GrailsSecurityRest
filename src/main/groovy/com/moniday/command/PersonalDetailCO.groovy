package com.moniday.command

import com.moniday.enums.Country
import com.moniday.enums.Currency
import com.moniday.util.AppUtil
import grails.validation.Validateable

class PersonalDetailCO implements Validateable {
    String firstName
    String lastName
    Date dateOfBirth
    Long age
    Country nationality
    Country country
    Currency currency
    static constraints = {
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
    }

    PersonalDetailCO() {

    }

    PersonalDetailCO(Map dataMap) {
        println dataMap
        this.firstName = dataMap.firstName
        this.lastName = dataMap.lastName
        this.country = AppUtil.getEnumByString(dataMap.countryOfResidence, Country)
        this.nationality = AppUtil.getEnumByString(dataMap.nationality, Country)
        this.currency = AppUtil.getEnumByString(dataMap.currency, Currency)
        this.dateOfBirth = AppUtil.generateDateFromString(dataMap.dateOfBirth.date, dataMap.dateOfBirth.month, dataMap.dateOfBirth.year)
    }
}