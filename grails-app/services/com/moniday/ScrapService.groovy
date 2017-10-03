package com.moniday

import com.moniday.ocr.OCRUtill
import geb.Browser
import geb.module.TextInput
import geb.navigator.Navigator

class ScrapService {
    static transactional = false

    def scrapBank() {
        String bank_url = "https://clients.boursorama.com/connexion/"
        String bank_username = "73681504"
        String bank_password = "25101925"
        println("Scrapping ")
        Browser.drive {
            go bank_url
            println(title)
            println $("#form_login").value()
            $("#form_login").value(bank_username)
            println $("#form_login").value()
            def passwordDiv = $("div.login-window__actions-mask")
            println(passwordDiv.properties)
            def hx = passwordDiv.children().first()
            println(hx.properties)
            println(hx.@id)
            def passwordMatrix = hx.children("div.sasmap")
            println(passwordMatrix.properties)
            /*waitFor(80, 0.3) {
                ($("ul.password-input li").size() > 0)
            }*/
            Map<String, String> detail = [:]
            passwordMatrix.children("ul").children("li").eachWithIndex { Navigator entry, int i ->
                println("Checking $i")
                Navigator spanKey = entry.children("span.sasmap__key")
                Navigator img = spanKey.children("img")
                String base64String = img.attr("src")
                base64String = base64String.replaceAll("data:image/png;base64,", "")
                detail.put(OCRUtill.textFromBase64(base64String, i), spanKey.attr("data-matrix-key"))
            }
            println("///////////////////////////////////////////////////////////////////////////")
            println(detail)
            println("///////////////////////////////////////////////////////////////////////////")
        }
    }

    def scrapCAPCA() {
        String url = "https://www.pca-g3-enligne.credit-agricole.fr/stb/entreeBam"
        String username = "43650502079"
        String password = "060128"

        Browser.drive {
            go url
            println("title $title")
            def liElement = $("li#acces_aux_comptes")
            def hrefElement = liElement.children("a")
            hrefElement.click()
            println("New Title $title")
            def usernameField = $(name: "CCPTE").module(TextInput)
            usernameField.text = username

            password.each { String pass ->
                println "pass " + pass
                $("table#pave-saisie-code tr td").each {
                    def pasStr = it.text()
                    pasStr = pasStr.replaceAll("\\s", "")
                    if (pasStr.contains(pass)) {
                        println "pasStr $pasStr"
                        it.children("a").click()
                    }
                }
            }

            $("p.validation.clearboth span.droite a.droite")[1].click()
        }
    }
}
