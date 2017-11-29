package com.moniday.dto

import com.moniday.enums.TransactionState
import com.moniday.enums.TransactionStatus

class TransactionDTO {
    String transactionDate
    String description
    String amount
    Double grabAmount = 0.0
    Boolean isCardTransaction = Boolean.FALSE
    TransactionStatus status = TransactionStatus.NOT_PROCESSED
    TransactionState state = TransactionState.NEW

    TransactionDTO() {

    }

    TransactionDTO(Map transactionMap) {
        if (transactionMap?.transactionDate) {
            this.transactionDate = transactionMap?.transactionDate
            this.description = transactionMap?.description
            this.amount = transactionMap?.amount
            this.isCardTransaction = transactionMap?.isCardTransaction
            this.status = transactionMap?.transactionStatus ?: TransactionStatus.NOT_PROCESSED
            this.grabAmount = transactionMap?.grabAmount
            this.state = transactionMap?.transactionState
        }
    }

    @Override
    boolean equals(Object obj) {
        boolean equal = true
        if (obj instanceof TransactionDTO) {
            TransactionDTO transactionDTO = (TransactionDTO) obj
            if ((transactionDTO.transactionDate != this.transactionDate) && (transactionDTO.description != this.description) && (transactionDTO.amount != this.amount) && (transactionDTO.isCardTransaction != this.isCardTransaction)) {
                equal = false
            }
        }
        return equal
    }

    @Override
    String toString() {
        return "$transactionDate, $description, $amount, $isCardTransaction"
    }
}