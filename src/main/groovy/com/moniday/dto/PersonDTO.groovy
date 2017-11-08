package com.moniday.dto

class PersonDTO {
    String firstName
    String lastName
    String deductedMoney = ""

    List<AccountDTO> accounts = []

    PersonDTO() {

    }

    PersonDTO(Map accountMap) {
        this.firstName = accountMap.firstName
        this.lastName = accountMap.lastName
        accountMap.accounts.each {
            this.accounts.add(new AccountDTO(it))
        }
    }
}
