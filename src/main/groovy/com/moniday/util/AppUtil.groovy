package com.moniday.util

import com.mangopay.core.enumerations.CountryIso
import com.mangopay.core.enumerations.CurrencyIso
import com.moniday.AdminSetting
import com.moniday.dto.AccountDTO
import com.moniday.dto.DeductionDetailDTO
import com.moniday.dto.PersonDTO
import com.moniday.dto.TransactionDTO
import com.moniday.enums.Country
import com.moniday.enums.Currency
import com.moniday.enums.TransactionState
import com.moniday.enums.TransactionStatus
import com.moniday.firebase.FirebaseInitializer

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.ZoneId
import java.util.regex.Pattern

class AppUtil {

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

    static String createBankDateString(String dateString) {
        //format will be date/month/year
        String formatedDateString
        if (!dateString) {
            formatedDateString = null
        } else {
            String[] dateValues = dateString.split(Pattern.quote("/"))
            int day = dateValues[0] as Integer
            int month = dateValues[1] as Integer
            int currentYear = Year.now().getValue()
            if (LocalDate.now().isBefore(LocalDate.of(currentYear, month, day))) {
                //today's date comes before transaction date... transaction is of previous year
                currentYear -= 1
            }
            LocalDate dobDate = LocalDate.of(currentYear, month, day)
            TimeZone tz = TimeZone.getTimeZone("Europe/Berlin")
            Date date = Date.from(dobDate.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant())
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")
            sdf.setTimeZone(tz)
            formatedDateString = sdf.format(date)
        }
        return formatedDateString
    }

    static String createBankDateString(Date date) {
        TimeZone tz = TimeZone.getTimeZone("Europe/Berlin")
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z")
        sdf.setTimeZone(tz)
        return sdf.format(date)
    }

    static String getDateStringForView(String dateString) {
        Date date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").parse(dateString)
        return new SimpleDateFormat("dd MMM yyyy").format(date)
    }

    static void calculateDeductionAmount(PersonDTO personDTO) {
        List<AccountDTO> accountDTOS = personDTO.accounts
        Double totalAmountSum = 0
        println "Calculate the amount to be deducted from the accounts"
        accountDTOS.each { AccountDTO accountDTO ->
            if (accountDTO?.isCardTransaction) {
                Double accountMoney = calculateAmountOnAccount(accountDTO)
                //if accountDTO already has some deductedMoney value then new value must be added to it
                Double deductedMoney = Double.parseDouble(accountDTO.deductedMoney)
                deductedMoney = deductedMoney + accountMoney
                accountDTO.deductedMoney = "$deductedMoney"
                totalAmountSum += (deductedMoney)
            }
        }
        personDTO.deductedMoney = "${totalAmountSum.round(2)}"
        /*
        * if total amount sum is greater then a certain value then
        * change the status of transaction to PENDING
        *
        * */
        if (totalAmountSum > AdminSetting.get(1).minDeductionAmount) {
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

    static PersonDTO approveDeduction(String firebaseId, Boolean forOneAccount, String accountNumber) {
        Map personalMap = FirebaseInitializer.getUserScrap(firebaseId)
        PersonDTO personDTO = new PersonDTO(personalMap)
        Double accountDeductedMoney = 0.0
        if (forOneAccount) {
            println("from apputil for one account only")
            personDTO.accounts.each { AccountDTO accountDTO ->
                if ((accountDTO.accountNumber == accountNumber) && accountDTO.isCardTransaction) {
                    println("approving for ${accountDTO.accountNumber}")
                    accountDeductedMoney = accountDTO.deductedMoney as Double
                    personDTO.deductedMoney = ((personDTO.deductedMoney as Double) - (accountDeductedMoney)) as String
                    accountDTO.deductedMoney = "0"
                    println("${personDTO.deductedMoney} person money after deduction")
                    changeTransactionStatusToApproved(accountDTO)
                    DeductionDetailDTO deductionDetailDTO = getDeductionDetailDTO(accountDTO.accountNumber, "123456789", accountDeductedMoney)
                    personDTO.deductionHistory.add(deductionDetailDTO)
                }
            }
        } else {
            println("form apputil for all accounts")
            personDTO.accounts.each { AccountDTO accountDTO ->
                if (accountDTO.isCardTransaction) {
                    accountDeductedMoney = accountDTO.deductedMoney as Double
                    println("approving for ${accountDTO.accountNumber}")
                    personDTO.deductedMoney = ((personDTO.deductedMoney as Double) - (accountDeductedMoney)) as String
                    accountDTO.deductedMoney = "0"
                    changeTransactionStatusToApproved(accountDTO)
                    DeductionDetailDTO deductionDetailDTO = getDeductionDetailDTO(accountDTO.accountNumber, "123456789", accountDeductedMoney)
                    personDTO.deductionHistory.add(deductionDetailDTO)
                }
            }
        }
        personDTO
    }

    static changeTransactionStatusToApproved(AccountDTO accountDTO) {
        accountDTO.transactions.each { TransactionDTO transactionDTO ->
            if (transactionDTO.status == TransactionStatus.PENDING) {
                println("changing status to approved")
                transactionDTO.status = TransactionStatus.APPROVED
            }
        }
    }

    static DeductionDetailDTO getDeductionDetailDTO(String fromAccount, String toAccount, Double amount) {
        DeductionDetailDTO deductionDetailDTO = new DeductionDetailDTO()
        deductionDetailDTO.fromAccount = fromAccount
        deductionDetailDTO.toAccount = toAccount
        deductionDetailDTO.amount = amount
        deductionDetailDTO.approvalDate = createBankDateString(new Date())
        deductionDetailDTO
    }

    //datetocompare , todays date
    static boolean dateBefore(String dateString, Date date) {
        Date dateOne = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").parse(dateString)
        return dateOne.before(date)
    }
}