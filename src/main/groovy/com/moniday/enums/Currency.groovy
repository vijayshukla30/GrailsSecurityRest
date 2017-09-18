package com.moniday.enums

enum Currency {
    GBP('GBP'),
    EUR('EUR')

    private final String value

    Currency(String value) {
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
    public static list() {
        [GBP, EUR]
    }
}