package com.moniday.dto

import com.moniday.util.AppUtil

class DeductionDetailDTO {
    String fromAccount
    String toAccount
    Double amount
    Date approvalDate
    Date deductionDate = null
    Boolean isDeducted = Boolean.FALSE

    DeductionDetailDTO() {}

    DeductionDetailDTO(Map deductionDetailMap) {
        this.fromAccount = deductionDetailMap?.fromAccount
        this.toAccount = deductionDetailMap?.toAccount
        this.amount = deductionDetailMap?.amount
        this.approvalDate = AppUtil.generateDateFromString(deductionDetailMap?.approvalDate?.date as Long, deductionDetailMap?.approvalDate?.month as Long, deductionDetailMap?.approvalDate?.year as Long)
        this.deductionDate = AppUtil.generateDateFromString(deductionDetailMap?.approvalDate?.date as Long, deductionDetailMap?.approvalDate?.month as Long, deductionDetailMap?.approvalDate?.year as Long)
        this.isDeducted = deductionDetailMap?.isDeducted as Boolean
    }
}
