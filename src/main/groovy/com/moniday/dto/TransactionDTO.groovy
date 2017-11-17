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
        this.status = transactionMap?.status ?: TransactionStatus.NOT_PROCESSED
        this.grabAmount = transactionMap?.grabAmount
    }

    @Override
    boolean equals(Object obj) {
        boolean equal = true
        if (obj instanceof TransactionDTO) {
            TransactionDTO transactionDTO = (TransactionDTO) obj
            if (transactionDTO.transactionDate != this.transactionDate || transactionDTO.description != this.description || transactionDTO.amount != this.amount || transactionDTO.isCardTransaction != this.isCardTransaction) {
                equal = false
            }
        }
        return equal
    }

    @Override
    String toString() {
        return (transactionDate + " , " + description + " , " + amount + " , " + isCardTransaction)
    }
}