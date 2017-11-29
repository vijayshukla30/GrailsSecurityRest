package com.moniday.dto


class DeductionDetailDTO {
    String fromAccount
    String toAccount
    Double amount
    String approvalDate
    String deductionDate = null
    Boolean isDeducted = Boolean.FALSE

    DeductionDetailDTO() {}

    DeductionDetailDTO(Map deductionDetailMap) {
        this.fromAccount = deductionDetailMap?.fromAccount
        this.toAccount = deductionDetailMap?.toAccount
        this.amount = deductionDetailMap?.amount
        this.approvalDate = deductionDetailMap?.approvalDate
        this.deductionDate = deductionDetailMap?.approvalDate
        this.isDeducted = deductionDetailMap?.isDeducted as Boolean
    }
}
