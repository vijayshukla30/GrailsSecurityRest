package com.moniday.command

import com.moniday.enums.Country
import com.moniday.enums.Currency
import com.moniday.enums.Nationality

class PersonalDetailCO {
    String firstName
    String lastName
    Date dob
    Long age
    Nationality nationality
    Country countryOfResidence
    Currency currency
    static constraints = {
    }
}
