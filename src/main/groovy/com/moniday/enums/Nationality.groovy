package com.moniday.enums

enum Nationality {
    INDIA('India'),
    UK('UK')

    private final String value

    Nationality(String value) {
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
        [INDIA, UK]
    }
}