package com.moniday

class AuthenticationToken {

    String username
    String tokenValue
    Long dateCreated

    static constraints = {
    }

    AuthenticationToken() {
        this.dateCreated = System.currentTimeMillis()
    }
}
