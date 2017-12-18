package com.moniday.util

import com.moniday.AuthenticationToken

class TokenGenerator {

    static AuthenticationToken generateAuthenticationToken(String username) {
        AuthenticationToken authenticationToken = new AuthenticationToken(username: username, tokenValue: UUID.randomUUID().toString().replaceAll("-", ""))
        authenticationToken.save(flush: true)
        return authenticationToken
    }

}
