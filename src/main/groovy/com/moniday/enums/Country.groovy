package com.moniday.enums

enum Country {
    GB('GB'),
    FR('FR')

    private final String value

    Country(String value) {
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
        [GB, FR]
    }
}