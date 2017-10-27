package com.moniday.dto

class TransactionDTO {
    String date
    String description
    String amount

    TransactionDTO() {

    }

    TransactionDTO(Map transactionMap) {
        this.date = transactionMap.date
        this.description = transactionMap.description
        this.amount = transactionMap.amount
    }
}
