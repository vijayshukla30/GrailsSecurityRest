package com.moniday.dto

class AccountDTO {
    String typeOfAccount
    String accountNumber
    Double balance
    String currencyType
    String deductedMoney = ""
    String creditCardDeductMoney = ""

    List<TransactionDTO> transactions = []

    AccountDTO() {

    }

    AccountDTO(Map accountMap) {
        this.typeOfAccount = accountMap?.typeOfAccount
        this.accountNumber = accountMap?.accountNumber
        this.balance = (accountMap?.balance as Double)
        this.currencyType = accountMap?.currencyType

        accountMap.transactions.each {
            this.transactions.add(new TransactionDTO(it))
        }
    }
}
