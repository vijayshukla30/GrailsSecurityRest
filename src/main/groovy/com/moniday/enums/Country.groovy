package com.moniday.enums

enum Country {
    FRANCE('France'), LONDON('London'), INDIA('India')

    private final String value

    Country(String value) {
        this.value = value
    }

    public String value() {
        return value
    }

    public String getKey() {
        return name()
    }

    public static list() {
        [FRANCE, LONDON, INDIA]
    }
}