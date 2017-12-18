package com.moniday.dto

class AccountDTO {
    String typeOfAccount
    String accountNumber
    Double balance
    String currencyType
    String deductedMoney = "0"
    String creditCardDeductMoney = ""
    Boolean isCardTransaction = Boolean.FALSE

    List<TransactionDTO> transactions = []

    AccountDTO() {

    }

    AccountDTO(Map accountMap) {
        this.typeOfAccount = accountMap?.typeOfAccount
        this.accountNumber = accountMap?.accountNumber
        this.balance = (accountMap?.balance as Double)
        this.currencyType = accountMap?.currencyType
        this.isCardTransaction = accountMap?.isCardTransaction
        this.deductedMoney = accountMap?.deductedMoney
        accountMap.transactions.each {
            this.transactions.add(new TransactionDTO(it))
        }
    }

    @Override
    boolean equals(Object obj) {
        boolean equal = true
        if (obj instanceof AccountDTO) {
            AccountDTO accountDTO = (AccountDTO) obj
            if ((accountDTO.accountNumber != this.accountNumber) || (accountDTO.typeOfAccount != this.typeOfAccount) || (accountDTO.isCardTransaction != this.isCardTransaction)) {
                equal = false
            }
        }
        return equal
    }
}
