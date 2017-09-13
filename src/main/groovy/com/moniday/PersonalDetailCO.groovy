package com.moniday

import com.moniday.enums.CountryOfResidence
import com.moniday.enums.Currency
import com.moniday.enums.Nationality

class PersonalDetailCO {
    String firstName
    String lastName
    Date dob
    Long age
    Nationality nationality
    CountryOfResidence countryOfResidence
    Currency currency
    static constraints = {
    }
}
