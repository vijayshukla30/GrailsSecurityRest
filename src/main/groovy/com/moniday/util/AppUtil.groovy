package com.moniday.util

import com.mangopay.core.enumerations.CountryIso
import com.mangopay.core.enumerations.CurrencyIso
import com.moniday.dto.AccountDTO
import com.moniday.dto.PersonDTO
import com.moniday.dto.TransactionDTO
import com.moniday.enums.Country
import com.moniday.enums.Currency

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Month
import java.util.regex.Pattern

class AppUtil {
    public static Month getMonth(String monthString) {
        Month month = null
        switch (monthString) {
            case "1":
                month = Month.JANUARY
                break
            case "2":
                month = Month.FEBRUARY
                break
            case "3":
                month = Month.MARCH
                break
            case "4":
                month = Month.APRIL
                break
            case "5":
                month = Month.MAY
                break
            case "6":
                month = Month.JUNE
                break
            case "7":
                month = Month.JULY
                break
            case "8":
                month = Month.AUGUST
                break
            case "9":
                month = Month.SEPTEMBER
                break
            case "10":
                month = Month.OCTOBER
                break
            case "11":
                month = Month.NOVEMBER
                break
            case "12":
                month = Month.DECEMBER
                break
        }

        return month
    }

    static CountryIso countryToCountryISO(Country value) {
        return CountryIso.values()[value.ordinal()]
    }

    static CurrencyIso currencyToCurrencyISO(Currency value) {
        return CurrencyIso.values()[value.ordinal()]
    }

    static Long generateUnixTimeStampFromDate(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm")
        Long unixTime

        try {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
            unixTime = date.getTime()
            unixTime = unixTime / 1000
        } catch (Exception ex) {
            println(ex.message)
            return null
        }
        return unixTime
    }

    static void calculateDeductionAmount(PersonDTO personDTO) {
        List<AccountDTO> accountDTOS = personDTO.accounts
        Double totalAmountSum = 0
        Double totalCreditCardAmountSum = 0

        println "Calculate the amount to be deducted from the accounts"
        accountDTOS.each { AccountDTO accountDTO ->
            List amounts = calculateAmountOnAccount(accountDTO)
            Double accountMoney = amounts[0]
            Double creditCardAccountMoney = amounts[1]
            accountDTO.deductedMoney = "$accountMoney"
            accountDTO.creditCardDeductMoney = "$creditCardAccountMoney"
            totalAmountSum += (accountMoney)
            totalCreditCardAmountSum += (creditCardAccountMoney)
        }
        personDTO.deductedMoney = "${totalAmountSum}"
        personDTO.creditCardDeductMoney = "${totalCreditCardAmountSum}"
    }

    static List calculateAmountOnAccount(AccountDTO accountDTO) {
        Double amountSum = 0
        Double creditCardAmountSum = 0
        List<TransactionDTO> transactionDTOS = accountDTO.transactions
        transactionDTOS.each { TransactionDTO transactionDTO ->
            if (transactionDTO.amount?.contains("-")) {
                println "DEBIT TRANSACTION"
                List<String> amountList = transactionDTO.amount?.split(Pattern.quote("."))
                if (amountList.size() > 1) {
                    if (transactionDTO?.isCardTransaction) {
                        creditCardAmountSum += (amountList[amountList.size() - 1] as Double)
                    } else {
                        amountSum += (amountList[amountList.size() - 1] as Double)
                    }
                }
            }
        }
        return [(amountSum / 100), (creditCardAmountSum / 100)]
    }
}