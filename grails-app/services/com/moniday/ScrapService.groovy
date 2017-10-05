package com.moniday

import com.moniday.dto.AccountDTO
import com.moniday.dto.PersonDTO
import com.moniday.geb.page.capca.CaPcaHomePage
import com.moniday.geb.page.capca.DashboardPage
import com.moniday.geb.page.capca.LoginPage
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
        String url = "https://www.ca-pca.fr/"
        String username = "43650502079"
        String password = "060128"
        PersonDTO personDTO = new PersonDTO()

        Browser.drive {
            /*go url
            println("title $title")
            Navigator liElement = $("li#acces_aux_comptes")
            Navigator hrefElement = liElement.children("a")
            hrefElement.click()
            println("New Title $title")
            Navigator usernameField = $(name: "CCPTE").module(TextInput)
            usernameField.text = username

            password.each { String pass ->
                $("table#pave-saisie-code tr td").each {
                    def pasStr = it.text()
                    pasStr = pasStr.replaceAll("\\s", "")
                    if (pasStr.contains(pass)) {
                        it.children("a").click()
                    }
                }
            }

            $("p.validation.clearboth span.droite a.droite")[1].click()
            Navigator advisor = $("div#racineGDC").children("div.bloc-pap-texte").children("p")[0]
            List<String> names = advisor.text()?.split("\n")
            println(names)
            if (names) {
                personDTO.firstName = names[0]
                personDTO.lastName = names[1]
            }

            List<AccountDTO> accountDTOS = personDTO.accounts
            ["colcellignepaire", "colcelligneimpaire"].each { String css ->
                $("table.ca-table tr.$css").each { Navigator rowNavigator ->
                    Navigator accountRow = rowNavigator.children("td")
                    AccountDTO accountDTO = new AccountDTO()
                    accountDTO.typeOfAccount = accountRow[0].text()
                    accountDTO.accountNumber = accountRow[2].text()
                    accountDTO.balance = accountRow[4].text()?.replaceAll("\\s|,", "") as Long
                    accountDTO.currencyType = accountRow[5].text()
                    accountDTOS.add(accountDTO)
                    extractTransaction(accountRow[0].children("form").children("a"), accountDTO)
                }
            }*/

            CaPcaHomePage pcaHomePage = to(CaPcaHomePage)
            assert page instanceof CaPcaHomePage
            println "page title from capca home page--> " + pcaHomePage.pageTitle

            println "???????????????????????????????????????"

            pcaHomePage.loginPageLink.click()

            LoginPage loginPage = at(LoginPage)
            println(loginPage.pageTitle)
            loginPage.usernameField = username

            password.each { String pass ->
                $("table#pave-saisie-code tr td").each {
                    def pasStr = it.text()
                    pasStr = pasStr.replaceAll("\\s", "")
                    if (pasStr.contains(pass)) {
                        it.children("a").click()
                    }
                }
            }

            loginPage.submitButton.click()
            println "???????????????????????????????????????"

            DashboardPage dashboardPage = at(DashboardPage)
            assert page instanceof DashboardPage
            println "Login Successful " + dashboardPage.pageTitle
            Thread.sleep(1000)
            /*if (!dashboardPage.advisor.isEmpty()) {
                List<String> names = dashboardPage.advisor.text()?.split("\n")
                println(names)
                if (names) {
                    personDTO.firstName = names[0]
                    personDTO.lastName = names[1]
                }
            }*/


        }
        return personDTO
    }
/*
    def extractTransaction(Navigator accountLink, AccountDTO accountDTO) {
        accountLink.click()
    }*/
}