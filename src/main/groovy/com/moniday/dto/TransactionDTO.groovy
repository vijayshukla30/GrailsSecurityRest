package com.moniday.dto

import com.moniday.enums.TransactionStatus

class TransactionDTO {
    Date transactionDate
    String description
    String amount
    Double grabAmount = 0.0
    Boolean isCardTransaction = Boolean.FALSE
    TransactionStatus status = TransactionStatus.NOT_PROCESSED

    TransactionDTO() {

    }

    TransactionDTO(Map transactionMap) {
        this.transactionDate = transactionMap?.transactionDate
        this.description = transactionMap?.description
        this.amount = transactionMap?.amount
        this.isCardTransaction = transactionMap?.isCardTransaction
        this.status = transactionMap?.status
        this.grabAmount = transactionMap?.grabAmount
    }
}