package com.moniday.enums

enum Authority {
    ROLE_ADMIN("ROLE_ADMIN"), ROLE_SUB_ADMIN("ROLE_SUB_ADMIN"), ROLE_USER("ROLE_USER")

    private final String value

    Authority(String value) {
        this.value = value
    }

    public String value() {
        return value
    }
}