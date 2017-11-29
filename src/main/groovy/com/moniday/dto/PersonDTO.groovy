package com.moniday.dto

class PersonDTO {
    String firstName
    String lastName
    String deductedMoney = "0"
    String creditCardDeductMoney = "0"

    List<AccountDTO> accounts = []

    List<DeductionDetailDTO> deductionHistory = []

    PersonDTO() {

    }

    PersonDTO(Map accountMap) {
        this.firstName = accountMap?.firstName
        this.lastName = accountMap?.lastName
        this.deductedMoney = accountMap?.deductedMoney
        accountMap.accounts.each {
            this.accounts.add(new AccountDTO(it))
        }
        accountMap.deductionHistory.each {
            this.deductionHistory.add(new DeductionDetailDTO(it))
        }
    }
}
