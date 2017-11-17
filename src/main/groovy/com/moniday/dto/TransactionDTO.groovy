package com.moniday.dto

import com.moniday.enums.TransactionStatus
import com.moniday.util.AppUtil

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
        println(transactionMap?.transactionDate)
        if (transactionMap?.transactionDate) {
            this.transactionDate = AppUtil.generateDateFromString(transactionMap?.transactionDate?.date as Long, transactionMap?.transactionDate?.month as Long, transactionMap?.transactionDate?.year as Long)
            this.description = transactionMap?.description
            this.amount = transactionMap?.amount
            this.isCardTransaction = transactionMap?.isCardTransaction
            this.status = transactionMap?.status ?: TransactionStatus.NOT_PROCESSED
            this.grabAmount = transactionMap?.grabAmount
        }
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