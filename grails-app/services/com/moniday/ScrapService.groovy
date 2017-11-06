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
    def fireBaseService

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

    def scrapCAPCA(String bankUrl, String username, String password) {
//        username = "43650502079"
//        password = "060128"
        println("Bank Url --> $bankUrl, Username---> $username, password -----> $password)")
        PersonDTO personDTO = new PersonDTO()

        Browser.drive {
            println "/////////1/////////"
            go bankUrl
            println(title)
            println driver.properties
            println navigator.properties
            Navigator liElement = $("li#acces_aux_comptes")
            println "/////////2/////////"
            Navigator hrefElement = liElement.children("a")
            hrefElement.click()
            Navigator usernameField = $(name: "CCPTE").module(TextInput)
            println "/////////3/////////"
            usernameField.text = username
            println "/////////4/////////"

            password.each { String pass ->
                $("table#pave-saisie-code tr td").each {
                    def pasStr = it.text()
                    pasStr = pasStr.replaceAll("\\s", "")
                    if (pasStr.contains(pass)) {
                        it.children("a").click()
                    }
                }
            }
            println "/////////5/////////"

            $("p.validation.clearboth span.droite a.droite")[1].click()
            Navigator advisor = $("div#racineGDC").children("div.bloc-pap-texte").children("p")[0]
            List<String> names = advisor.text()?.split("\n")
            if (names) {
                personDTO.firstName = names[0]
                personDTO.lastName = names[1]
            }
            println "/////////6/////////"

            List<AccountDTO> accountDTOS = personDTO.accounts
            ["colcellignepaire", "colcelligneimpaire"].each { String css ->
                $("table.ca-table tr.$css").each { Navigator rowNavigator ->
                    Navigator accountRow = rowNavigator.children("td")
                    AccountDTO accountDTO = new AccountDTO()
                    accountDTO.typeOfAccount = accountRow[0].text()?.replaceAll("\\s", "")
                    accountDTO.accountNumber = accountRow[2].text()?.replaceAll("\\s", "")
                    accountDTO.balance = accountRow[4].text()?.replaceAll("\\s|,", "") as Long
                    accountDTO.currencyType = accountRow[5].text()?.replaceAll("\\s", "")
                    accountDTOS.add(accountDTO)
                    accountRow[0].children("form").children("a").click()
                    def transactionTable
                    if (accountDTO.typeOfAccount == "CCHQ") {
                        transactionTable = $(By.xpath("/html/body/div[1]/table/tbody/tr[7]/td/table/tbody/tr/td[3]/div/div/div/div[1]/div[5]/table[2]/tbody"))
                    } else if (accountDTO.typeOfAccount == "CEL") {
                        transactionTable = $(By.xpath("/html/body/div[1]/table/tbody/tr[7]/td/table/tbody/tr/td[3]/div/div/div/div[1]/div[4]/table[2]/tbody"))
                    } else {
                        println accountDTO.typeOfAccount
                    }
                    if (transactionTable) {
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

    def scrapCreditAgricole(String bankURL, String bankUserName, String bankPassword) {
        PersonDTO personDTO = new PersonDTO()
        println("Scrapping ")
        Browser.drive {
            go bankURL
            $(".toolbar-action-important").click()
            println("Login page title " + title)

            Navigator usernameField = $(name: "CCPTE").module(TextInput)
            usernameField.text = bankUserName
            bankPassword.each { String pass ->
                $("#pave-saisie-code tr td").each {
                    def pasStr = it.text()
                    pasStr = pasStr.replaceAll("\\s", "")
                    if (pasStr.contains(pass)) {
                        it.children("a").click()
                    }
                }
            }

            $("p.validation.clearboth span.droite a.droite")[0].click()   //login button
            String nameString = $("table tr td.titretetiere.cel-texte")[0].text().replaceAll("-\\s\\S*", "")
            List<String> names = nameString?.split("\\s")
            if (names) {
                personDTO.firstName = names[1]
                personDTO.lastName = names[2]
            }

            extractAccountAndTrans(personDTO, 0, "colcellignepaire")
            extractAccountAndTrans(personDTO, 0, "colcelligneimpaire")
        }
        return personDTO
    }

    def extractAccountAndTrans(PersonDTO personDTO, def extractingAccount, String css) {
        Browser browser = new Browser()
        Navigator tableAccountRow = browser.$("table.ca-table tr.$css")
        def totalAccount = tableAccountRow.size() - 1

        println tableAccountRow.size()
        println "accountRows " + tableAccountRow
        println "accountRows details " + tableAccountRow*.text()
        println("extractingAccount ${extractingAccount}")

        Navigator accountRow = tableAccountRow[extractingAccount]
        Navigator accountColumn = accountRow.children("td")
        AccountDTO accountDTO = new AccountDTO()
        accountDTO.typeOfAccount = accountColumn[0].text()?.replaceAll("\\s", "")
        accountDTO.accountNumber = accountColumn[2].text()?.replaceAll("\\s", "")
        accountDTO.balance = accountColumn[4].text()?.replaceAll("\\s|,", "") as Long
        accountDTO.currencyType = accountColumn[5].text()?.replaceAll("\\s", "")
        accountColumn[0].children("form").children("a").click()
        //extract the transactions for current account
        List<TransactionDTO> transactionDTOS = accountDTO.transactions
        if (accountDTO.typeOfAccount == "LDD") {
            browser.$("table.ca-table")[1].$("tbody tr").each {
                TransactionDTO transactionDTO = new TransactionDTO()
                transactionDTO.date = it.$("td")[0].text()?.replaceAll("\\s", "")
                transactionDTO.description = it.$("td")[1].text().replaceAll("\n", " ")
                transactionDTO.amount = it.$("td")[2].text()?.replaceAll("\\s|,", "") as Long
                transactionDTOS.add(transactionDTO)
            }
        } else if (accountDTO.typeOfAccount == "CCHQ") {
            if (browser.$("table.ca-table")[2].$("tbody tr").isEmpty()) {
                browser.$("table.ca-table")[1].$("tbody tr").each {
                    TransactionDTO transactionDTO = new TransactionDTO()
                    transactionDTO.date = it.$("td")[0].text()?.replaceAll("\\s", "")
                    transactionDTO.description = it.$("td")[2].text()
                    transactionDTO.amount = it.$("td")[4].text()?.replaceAll("\\s|,", "") as Long
                    transactionDTOS.add(transactionDTO)
                }
            } else {
                browser.$("table.ca-table")[2].$("tbody tr").each {
                    TransactionDTO transactionDTO = new TransactionDTO()
                    transactionDTO.date = it.$("td")[0].text()?.replaceAll("\\s", "")
                    transactionDTO.description = it.$("td")[2].text()
                    transactionDTO.amount = it.$("td")[4].text()?.replaceAll("\\s|,", "") as Long
                    transactionDTOS.add(transactionDTO)
                }
            }
        }

        personDTO.accounts.add(accountDTO)
        println "Click back Button to check the page"

        Navigator backToHome = browser.$("li#ariane-home").siblings().first().children()
        backToHome.click()

        if (totalAccount > extractingAccount) {
            extractAccountAndTrans(personDTO, extractingAccount + 1, css)
        }
    }

    def scrapBnpParibas(String bankURL, String bankUserName, String bankPassword) {
        String bank_password = bankPassword
        println("Scrapping ")
        Browser.drive {
            go bankURL
            println(title)
            $("#client-nbr").value(bankUserName)            //username field
            //$("#secret-nbr").value(bank_password).value(bank_password)            //password field
            def passwordMatrix = $("#secret-nbr-keyboard")  //virtual keyboard
            String imageurl = passwordMatrix.attr("style").toString().replace("background-image: url(", "").replace(");", "")
            imageurl = "http://www.everyeducaid.co.nz/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/B/A/BAR129.1475537601.jpg"
            println("Image URL " + imageurl)

            imageurl = imageurl.replaceAll("data:image/png;base64,", "")
            byte[] decoded = imageurl.decodeBase64()
            String path = "/home/Vebs@BM/Pictures/image.png"
            new File(path).withOutputStream {
                it.write(imageurl);
            }

            String data = OCRUtill.crackImage(path)
            print(data + " data *******")
            $("submitIdent").click()                        //submit button
        }

        //https://mabanque.bnpparibas/identification-wspl-pres/grille/c74416731216067524868722018227048230297
        return new PersonDTO()
    }

    List<TransactionDTO> extractTransaction(Navigator transNav, String accountType) {
        List<TransactionDTO> transactionDTOS = []
        transNav.children("tr").each { rowNav ->
            def cellNav = rowNav.children("td")
            if (accountType == "CCHQ") {
                TransactionDTO transactionDTO = new TransactionDTO()
                transactionDTO.date = cellNav[0].text()
                transactionDTO.description = cellNav[2].text()
                transactionDTO.amount = cellNav[4].text()
                transactionDTOS.add(transactionDTO)
            } else if (accountType == "CEL") {
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