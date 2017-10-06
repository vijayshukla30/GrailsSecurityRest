package com.moniday

import com.moniday.dto.AccountDTO
import com.moniday.dto.PersonDTO
import com.moniday.dto.TransactionDTO
import com.moniday.ocr.OCRUtill
import geb.Browser
import geb.module.TextInput
import geb.navigator.Navigator
import org.openqa.selenium.By

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
            go url
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
                    println rowNavigator.text()
                    Navigator accountRow = rowNavigator.children("td")
                    AccountDTO accountDTO = new AccountDTO()
                    accountDTO.typeOfAccount = accountRow[0].text()?.replaceAll("\\s", "")
                    accountDTO.accountNumber = accountRow[2].text()?.replaceAll("\\s", "")
                    accountDTO.balance = accountRow[4].text()?.replaceAll("\\s|,", "") as Long
                    accountDTO.currencyType = accountRow[5].text()?.replaceAll("\\s", "")
                    accountDTOS.add(accountDTO)
                    println("*(((((((((((((((((1((((((((((((((((((((((")
                    println "${accountDTO.typeOfAccount} ------->>>> ${title}"
                    accountRow[0].children("form").children("a").click()
                    println "${accountDTO.typeOfAccount} ------->>>> ${title}"
                    println("*(((((((((((((((((((2((((((((((((((((((((")
                    def transactionTable
                    if (accountDTO.typeOfAccount == "CCHQ") {
                        println("*************11111111111111************")
                        transactionTable = $(By.xpath("/html/body/div[1]/table/tbody/tr[7]/td/table/tbody/tr/td[3]/div/div/div/div[1]/div[5]/table[2]/tbody"))
                    } else if (accountDTO.typeOfAccount == "CEL") {
                        println("*************%%%%%%%%%%%%%%%%%%%%%%%%%************")
                        transactionTable = $(By.xpath("/html/body/div[1]/table/tbody/tr[7]/td/table/tbody/tr/td[3]/div/div/div/div[1]/div[4]/table[2]/tbody/tr[1]"))
                    } else {
                        println "HHHHHHHHHHHHHHHHHHHHHHHHHHHH"
                        println accountDTO.typeOfAccount
                    }
                    if (transactionTable) {
                        println("I have transaction page")
                        List<TransactionDTO> transactionDTOS = extractTransaction(transactionTable, accountDTO.typeOfAccount)
                        accountDTO.transactions = transactionDTOS
                        def backToHome = $("li#ariane-home").siblings().first().children()
                        backToHome.click()
                    }
                }
            }
        }
        return personDTO
    }

    List<TransactionDTO> extractTransaction(Navigator transNav, String accountType) {
        List<TransactionDTO> transactionDTOS = []
        transNav.children("tr").each { rowNav ->
            println "Going to traverse table transaction"
            def cellNav = rowNav.children("td")
            if (accountType == "CCHQ") {
                println("*************2222222222222222************")
                TransactionDTO transactionDTO = new TransactionDTO()
                transactionDTO.date = cellNav[0].text()
                transactionDTO.description = cellNav[2].text()
                transactionDTO.amount = cellNav[4].text()
                transactionDTOS.add(transactionDTO)
            } else if (accountType == "CEL") {
                println("))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")
                TransactionDTO transactionDTO = new TransactionDTO()
                transactionDTO.date = cellNav[0].text()
                transactionDTO.description = cellNav[1].text()
                transactionDTO.amount = cellNav[2].text()
                transactionDTOS.add(transactionDTO)
            }
        }
        transactionDTOS
    }
}