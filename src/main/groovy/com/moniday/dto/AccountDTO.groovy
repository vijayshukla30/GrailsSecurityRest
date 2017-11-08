package com.moniday.dto

class AccountDTO {
    String typeOfAccount
    String accountNumber
    Long balance
    String currencyType
    String deductedMoney = ""

    List<TransactionDTO> transactions = []

    AccountDTO() {

    }

    AccountDTO(Map accountMap) {
        this.typeOfAccount = accountMap.typeOfAccount
        this.accountNumber = accountMap.accountNumber
        this.balance = (accountMap.balance as Long)
        this.currencyType = accountMap.currencyType

        accountMap.transactions.each {
            this.transactions.add(new TransactionDTO(it))
        }
    }
}
