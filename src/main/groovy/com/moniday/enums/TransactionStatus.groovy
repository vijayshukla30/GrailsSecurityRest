package com.moniday.enums

enum TransactionStatus {
    PROCESSED('Processed'),
    PENDING('Pending'),
    APPROVED("Approved"),
    NOT_PROCESSED("Not Processed"),
    PROCESSING("Processing")


    private final String value

    TransactionStatus(String value) {
        this.value = value
    }

    public String getValue() {
        return value
    }

    public String getKey() {
        return name()
    }

    String toString() {
        return value
    }
}