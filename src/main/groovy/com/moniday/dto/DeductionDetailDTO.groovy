package com.moniday.dto

class DeductionDetailDTO {
    String fromAccount
    String toAccount
    Date approvalDate
    Date deductionDate = null
    Boolean isDeducted = Boolean.FALSE

    DeductionDetailDTO() {}

    DeductionDetailDTO(Map deductionDetailMap) {
        this.fromAccount = deductionDetailMap?.fromAccount
        this.toAccount = deductionDetailMap?.toAccount
        this.approvalDate = deductionDetailMap?.approvalDate
        this.deductionDate = deductionDetailMap?.deductionDate
        this.isDeducted = deductionDetailMap?.isDeducted as Boolean
    }
}
