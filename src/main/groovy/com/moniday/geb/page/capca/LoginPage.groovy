package com.moniday.geb.page.capca

import geb.Page
import geb.module.TextInput

class LoginPage extends Page {
    static at = { title == "AUTH" }
    static content = {
        pageTitle { title }
        submitButton(to: DashboardPage) { $("p.validation.clearboth span.droite a.droite")[1] }
        usernameField { $(name: "CCPTE").module(TextInput) }
    }
}
