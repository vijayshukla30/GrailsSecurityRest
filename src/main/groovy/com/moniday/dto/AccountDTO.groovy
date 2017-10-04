package com.moniday.dto

class AccountDTO {
    String typeOfAccount
    String accountNumber
    Long balance
    String currencyType

    List<TransactionDTO> transactions = []

}
