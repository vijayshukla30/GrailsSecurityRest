package com.moniday.enums

enum TransactionState {
    OLD("old"),
    NEW("new")

    private final String value

    TransactionState(String value) {
        this.value = value
    }

    public String getKey() {
        return name()
    }

    public String getValue() {
        return value
    }

    @Override
    String toString() {
        return value
    }
}