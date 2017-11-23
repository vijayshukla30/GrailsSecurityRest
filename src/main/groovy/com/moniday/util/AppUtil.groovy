package com.moniday.util

import com.mangopay.core.enumerations.CountryIso
import com.mangopay.core.enumerations.CurrencyIso
import com.moniday.dto.AccountDTO
import com.moniday.dto.PersonDTO
import com.moniday.dto.TransactionDTO
import com.moniday.enums.Country
import com.moniday.enums.Currency
import com.moniday.enums.TransactionState
import com.moniday.enums.TransactionStatus

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.ZoneId
import java.util.regex.Pattern

class AppUtil {

    static final Double MINDEDUCTEDMONEY = 5

    static def getEnumByString(String code, def enumData) {
        def data = null
        enumData.values().each {
            if (it.value == code) {
                data = it
                return false
            }
        }
        return data
    }

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

    static Date convertBankDateStringToDate(String dateString) {
        //format will be date/month/year
        //TODO: Need Refactoring
        Date date
        if (dateString == "") {
            date = null
        } else {
            String[] dateValues = dateString.split(Pattern.quote("/"))
            int day = Integer.parseInt(dateValues[0])
            int month = Integer.parseInt(dateValues[1])
            int currentYear = Year.now().getValue()
            if (LocalDate.now().isBefore(LocalDate.of(currentYear, month, day))) {
                //today's date comes before transaction date... transaction is of previous year
                currentYear = currentYear - 1
            }
            LocalDate dobDate = LocalDate.of(currentYear, month, day)
            date = Date.from(dobDate.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant())
        }
        return date
    }

    static Date generateDateFromString(Long date, Long month, Long year) {
        LocalDate dobDate = LocalDate.of(year as int, getMonth("${month + 1}"), date as int)
        Date.from(dobDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    static void calculateDeductionAmount(PersonDTO personDTO) {
        List<AccountDTO> accountDTOS = personDTO.accounts
        Double totalAmountSum = 0
        println "Calculate the amount to be deducted from the accounts"
        accountDTOS.each { AccountDTO accountDTO ->
            if (accountDTO?.isCardTransaction) {
                Double accountMoney = calculateAmountOnAccount(accountDTO)
                accountDTO.deductedMoney = "$accountMoney"
                totalAmountSum += (accountMoney)
            }
        }
        /*
        * in case the deducted money could not get more than specified value then
        * we have to wait for next scrapping.
        * amountSum received from newly added transaction must be added to already present
        * value until it gets above a specified value
        *
        * reset the deductedMoney to 0 once the transaction is done after admin approval
        * */
        Double deductedMoney = Double.parseDouble(personDTO.deductedMoney)
        deductedMoney = deductedMoney + totalAmountSum.round(2)
        personDTO.deductedMoney = "${deductedMoney.round(2)}"
        /*
        * if total amount sum is greater then a certain value then
        * change the status of transaction to PENDING
        *
        * */
        if (totalAmountSum > MINDEDUCTEDMONEY) {
            changeTransactionStatusToPending(personDTO)
        }
    }

    static Double calculateAmountOnAccount(AccountDTO accountDTO) {
        Double amountSum = 0
        List<TransactionDTO> transactionDTOS = accountDTO.transactions
        transactionDTOS.each { TransactionDTO transactionDTO ->
            if (transactionDTO.state == TransactionState.NEW) {     //calculate amount only if transaction state in NEW
                if (transactionDTO.amount?.contains("-")) {
                    List<String> amountList = transactionDTO.amount?.split(Pattern.quote("."))
                    println amountList
                    if (amountList.size() > 1) {
                        def extraAmount = ((amountList[amountList.size() - 1] as Double) / 100)
                        transactionDTO.grabAmount = ((extraAmount > 0 ? 1 - extraAmount : 0.0) as Double).round(2)
                        amountSum += transactionDTO.grabAmount
                    }
                }
                transactionDTO.state = TransactionState.OLD     //change the state of transaction to OLD
            }
        }
        return amountSum.round(2)
    }

    static void changeTransactionStatusToPending(PersonDTO personDTO) {
        /*
        * Change only the card transactions with status as NOT PROCESSED to PENDING
        *
        * */
        List<AccountDTO> accountDTOS = personDTO.accounts
        accountDTOS.each { AccountDTO accountDTO ->
            if (accountDTO?.isCardTransaction) {
                List<TransactionDTO> transactionDTOS = accountDTO.transactions
                transactionDTOS.each { TransactionDTO transactionDTO ->
                    if (transactionDTO.status == TransactionStatus.NOT_PROCESSED && transactionDTO.state == TransactionState.OLD) {
                        transactionDTO.status = TransactionStatus.PENDING
                    }
                }
            }
        }
    }
}