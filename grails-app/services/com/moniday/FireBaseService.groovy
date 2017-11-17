package com.moniday

import com.firebase.Account
import com.firebase.DirectDebitMandate
import com.firebase.MangoPayDetail
import com.firebase.User as FireBaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.google.firebase.auth.UserRecord.CreateRequest as CreateRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.tasks.Task
import com.moniday.command.AccountDetailCO
import com.moniday.command.DirectDebitMandateCO
import com.moniday.command.SecurityDetailCO
import com.moniday.command.UserCO
import com.moniday.dto.AccountDTO
import com.moniday.dto.PersonDTO
import com.moniday.dto.TransactionDTO
import com.moniday.firebase.FirebaseInitializer

class FireBaseService {
    static transactional = false

    String createUser(UserCO userCO) {
        try {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
            DatabaseReference userRef = ref.child(FirebaseInitializer.USER_REF).push()
            FireBaseUser user = new FireBaseUser(userCO.username)
            userRef.setValue(user)
            String fireBaseKey = userRef.key
            return fireBaseKey
        } catch (Exception ex) {
            println "Failed to Load data to server ${ex.message}"
            return ""
        }
    }

    def updateUserForMangoPay(User user) {
        MangoPayDetail payDetail = new MangoPayDetail(user.mangoPayId, user.mangoPayWalletId, user.mangoPayBankccountId, user.mangoPayMandateId)
        println payDetail.properties
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference mangoPayRef = ref.child("${FirebaseInitializer.USER_REF}/${user.firebaseId}/mangoPayDetail")
        mangoPayRef.setValue(payDetail)
    }

    def saveAccountDetail(AccountDetailCO accountDetailCO, String fireBaseId) {
        Account account = new Account(accountDetailCO.bankNameFirebase, accountDetailCO.bankUsername, accountDetailCO.bankPassword)
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        println(account.properties)
        DatabaseReference accountRef = ref.child("${FirebaseInitializer.USER_REF}/$fireBaseId/accountDetail")
        accountRef.setValue(account)
    }

    def saveSecurityDetail(def question, def answer, String fireBaseId) {
        List<String> questions = (question as List)
        List<String> answers = (answer as List)
        List<SecurityDetailCO> securityDetailCOS = []
        int size = answers.size()

        (0..(size - 1)).each {
            SecurityDetailCO securityDetailCO = new SecurityDetailCO(answer: answers[it], question: questions[it])
            if (securityDetailCO.validate()) {
                securityDetailCOS.add(securityDetailCO)
            }
        }

        Map<String, String> data = [:]
        securityDetailCOS.each {
            data.put("" + it.question, "" + it.answer)
        }
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference securityRef = ref.child("${FirebaseInitializer.USER_REF}/$fireBaseId/accountDetail/securityDetail")
        println(data)
        securityRef.setValue(data)
    }

    def saveDirectDebitMandateDetail(DirectDebitMandateCO debitMandateCO, String fireBaseId) {
        DirectDebitMandate debitMandate = new DirectDebitMandate(debitMandateCO.owner, debitMandateCO.addressLine1, debitMandateCO.city, debitMandateCO.region, debitMandateCO.postCode, debitMandateCO.country.value, debitMandateCO.iban, debitMandateCO.bic)
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference debitRef = ref.child("${FirebaseInitializer.USER_REF}/$fireBaseId/debitMandate")
        debitRef.setValue(debitMandate)
    }

    def saveScrappedDataToFirebase(PersonDTO personDTO, String firebaseId) {
        Map personalMap = FirebaseInitializer.getUserScrap(firebaseId)
        if (personalMap != null) {
            saveNewScrappedDataToFirebase(personDTO, firebaseId)
            return
        }
        println("executing saveScrappedData")
        PersonDTO existingData = new PersonDTO(personalMap)
        Map personMap = [:]
        personMap.firstName = personDTO.firstName
        personMap.lastName = personDTO.lastName

        List accounts = []
        personDTO.accounts.each { AccountDTO accountDTO ->
            Map accountDetail = [:]
            accountDetail.typeOfAccount = accountDTO.typeOfAccount
            accountDetail.accountNumber = accountDTO.accountNumber
            accountDetail.balance = accountDTO.balance
            accountDetail.currencyType = accountDTO.currencyType
            accountDetail.isCardTransaction = accountDTO.isCardTransaction
            List transactions = []
            accountDTO.transactions.each { TransactionDTO transactionDTO ->
                Map transactionDetails = [:]
                transactionDetails.transactionDate = transactionDTO.transactionDate
                transactionDetails.description = transactionDTO.description
                transactionDetails.amount = transactionDTO.amount
                transactionDetails.isCardTransaction = transactionDTO.isCardTransaction
                transactions.add(transactionDetails)
            }
            accountDetail.transactions = transactions
            accounts.add(accountDetail)
        }
        personMap.accounts = accounts

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference scrapRef = ref.child("${FirebaseInitializer.USER_REF}/$firebaseId/scrapDetail")
        scrapRef.setValue(personMap)
    }

    def saveNewScrappedDataToFirebase(PersonDTO newPersonDTO, String firebaseId) {
        //here we will deal with existing data we are getting from firebase
        println("executing saveNewScrappedDataToFirebase")
        Map personalMap = FirebaseInitializer.getUserScrap(firebaseId)
        PersonDTO existingData = new PersonDTO(personalMap)
        Map personMap = [:]
        personMap.firstName = existingData.firstName
        personMap.lastName = existingData.lastName
        List accounts = []
        existingData.accounts.each { AccountDTO accountDTO ->
            println("<<<<<<<<<working with account >>>>>>>>>" + accountDTO.accountNumber)
            Map accountDetail = [:]
            accountDetail.typeOfAccount = accountDTO.typeOfAccount
            accountDetail.accountNumber = accountDTO.accountNumber
            accountDetail.balance = accountDTO.balance
            accountDetail.currencyType = accountDTO.currencyType
            accountDetail.isCardTransaction = accountDTO.isCardTransaction
            List transactions = []
            accountDTO.transactions.each { TransactionDTO transactionDTO ->
                Map transactionDetails = [:]
                transactionDetails.transactionDate = transactionDTO.transactionDate
                transactionDetails.description = transactionDTO.description
                transactionDetails.amount = transactionDTO.amount
                transactionDetails.isCardTransaction = transactionDTO.isCardTransaction
                transactions.add(transactionDetails)
            }
            //if more transactions are added or initially transactions are empty
            if (moreTransactionsAdded(newPersonDTO, accountDTO) || accountDTO.transactions.isEmpty()) {
                println("+++++++++++yes more transactions are inserted into account +++++++++++++++++" + accountDTO.accountNumber)
                //get new transactions from freshly scrapped data as a List of Map
                List newTransactions = getNewTransactions(newPersonDTO, accountDTO)
                println("###############newly added transactions are ############" + newTransactions)
                //add new data to the original list at index 0
                transactions.addAll(0, newTransactions)
            } else {
                println("+++++++++++++++no new transactions added ++++++++++++++++++++")
            }

            accountDetail.transactions = transactions
            accounts.add(accountDetail)
            println("????????????????????????????done for one account????????????????????????????????????????")
        }
        personMap.accounts = accounts

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference scrapRef = ref.child("${FirebaseInitializer.USER_REF}/$firebaseId/scrapDetail")
        scrapRef.setValue(personMap)
    }

    private boolean moreTransactionsAdded(PersonDTO newPersonDTO, AccountDTO oldAccountDTO) {
        boolean newTransactionAdded = true
        for (AccountDTO newAccountDTO : newPersonDTO.getAccounts()) {
            if (newAccountDTO.equals(oldAccountDTO)) {
                //first Transaction from freshly scrapped data
                TransactionDTO newTransactionDTO = newAccountDTO.transactions.get(0)
                TransactionDTO oldTransactionDTO = oldAccountDTO.transactions.get(0)
                if (newTransactionDTO.equals(oldTransactionDTO)) {
                    newTransactionAdded = false
                    println("no new transaction added")
                }
                println("+++++++++old transaction++++" + oldTransactionDTO + "++++++++++++++++new transaction ++++++++++" + newTransactionDTO)
                break
            }
        }
        return newTransactionAdded
    }

    private List getNewTransactions(PersonDTO newPersonDTO, AccountDTO oldAccountDTO) {
        List transactions = []
        for (AccountDTO newAccountDTO : newPersonDTO.getAccounts()) {
            if (newAccountDTO.equals(oldAccountDTO)) {
                TransactionDTO oldTransactionDTO = oldAccountDTO.transactions.get(0)
                newAccountDTO.transactions.each { TransactionDTO newTransactionDTO ->
                    if (newTransactionDTO.equals(oldTransactionDTO)) {
                        return false
                    }
                    Map transactionDetails = [:]
                    transactionDetails.transactionDate = newTransactionDTO.transactionDate
                    transactionDetails.description = newTransactionDTO.description
                    transactionDetails.amount = newTransactionDTO.amount
                    transactionDetails.isCardTransaction = newTransactionDTO.isCardTransaction
                    transactions.add(transactionDetails)
                }
                break
            }
        }
        return transactions
    }

    def registerUser(User owner, String password) {
        CreateRequest createRequest = new CreateRequest()
        createRequest.uid = owner.uniqueId
        createRequest.email = owner?.username
        createRequest.emailVerified = false
        createRequest.password = password
        createRequest.displayName = "${owner.username}"
        createRequest.disabled = false

        Task<UserRecord> userRecordTask = FirebaseAuth.getInstance().createUser(createRequest).addOnSuccessListener({
            println(it.uid)
        }).addOnFailureListener({ println(it.message) })
    }

}