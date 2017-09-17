package com.moniday.enums

enum Nationality {
    FRANCE('France'), LONDON('London'), INDIA('India')

    private final String value

    Nationality(String value) {
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