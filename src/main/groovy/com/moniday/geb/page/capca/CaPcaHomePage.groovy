package com.moniday.geb.page.capca

import geb.Page

class CaPcaHomePage extends Page {
    static url = "https://www.ca-pca.fr/"

    static content = {
        pageTitle { title }
        loginPageLink { $("li#acces_aux_comptes").children("a") }
    }
}
