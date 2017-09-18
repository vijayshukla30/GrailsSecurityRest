package com.moniday.command

import grails.validation.Validateable

class SecurityDetailCO implements Validateable {
    String question
    String answer

    static constraints = {
    }
}