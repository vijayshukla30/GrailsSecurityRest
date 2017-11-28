package com.moniday

import com.fasterxml.jackson.databind.ObjectMapper
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
import com.moniday.dto.DeductionDetailDTO
import com.moniday.dto.PersonDTO
import com.moniday.dto.TransactionDTO
import com.moniday.firebase.FirebaseInitializer
import com.moniday.util.AppUtil

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

    def saveScrappedDataToFirebase(PersonDTO personDTO, String firebaseId, Boolean forUpdate) {
        println("NEW SCRAPPED RECORD")
        println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(personDTO))
        Map personalMap = FirebaseInitializer.getUserScrap(firebaseId)
        PersonDTO oldScrapRecord = null
        if (personalMap && (!forUpdate)) {
            oldScrapRecord = new PersonDTO(personalMap)
            println("OLD SCRAPPED RECORD")
            println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(oldScrapRecord))
            filterNewAccountAndTransaction(personDTO, oldScrapRecord)
        } else {
            oldScrapRecord = personDTO
        }

        //calculate deduction amount after new transactions are merged with old ones
        AppUtil.calculateDeductionAmount(oldScrapRecord)

        println("COMBINE SCRAPPED RECORD")
        println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(oldScrapRecord))

        Map personMap = [:]
        personMap.firstName = oldScrapRecord.firstName
        personMap.lastName = oldScrapRecord.lastName
        personMap.deductedMoney = oldScrapRecord.deductedMoney
        List deductionHistory = []

        oldScrapRecord.deductionHistory.each { DeductionDetailDTO deductionDetailDTO ->
            Map deductionDetail = [:]
            deductionDetail.toAccount = deductionDetailDTO.toAccount
            deductionDetail.fromAccount = deductionDetailDTO.fromAccount
            deductionDetail.amount = deductionDetailDTO.amount
            deductionDetail.approvalDate = deductionDetailDTO.approvalDate
            deductionDetail.deductionDate = deductionDetailDTO.deductionDate
            deductionDetail.isDeducted = deductionDetailDTO.isDeducted
            deductionHistory.add(deductionDetail)
        }
        personMap.deductionHistory = deductionHistory

        List accounts = []
        oldScrapRecord.accounts.each { AccountDTO accountDTO ->
            Map accountDetail = [:]
            accountDetail.typeOfAccount = accountDTO.typeOfAccount
            accountDetail.accountNumber = accountDTO.accountNumber
            accountDetail.balance = accountDTO.balance
            accountDetail.currencyType = accountDTO.currencyType
            accountDetail.isCardTransaction = accountDTO.isCardTransaction
            accountDetail.deductedMoney = accountDTO.deductedMoney
            List transactions = []
            accountDTO.transactions.each { TransactionDTO transactionDTO ->
                Map transactionDetails = [:]
                transactionDetails.transactionDate = transactionDTO.transactionDate
                transactionDetails.description = transactionDTO.description
                transactionDetails.amount = transactionDTO.amount
                transactionDetails.isCardTransaction = transactionDTO.isCardTransaction
                transactionDetails.transactionStatus = transactionDTO.status
                transactionDetails.transactionState = transactionDTO.state
                transactionDetails.grabAmount = transactionDTO.grabAmount
                transactions.add(transactionDetails)
            }
            accountDetail.transactions = transactions
            accounts.add(accountDetail)
        }
        personMap.accounts = accounts

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference scrapRef = ref.child("${FirebaseInitializer.USER_REF}/$firebaseId/scrapDetail")
        scrapRef.setValue(personMap)

        println("((((((((((((data saved to firebase is))))))))))")
        println(personMap)
    }

    PersonDTO filterNewAccountAndTransaction(PersonDTO newScrapRecord, PersonDTO oldScrapRecord) {
        List<AccountDTO> oldAccountList = oldScrapRecord.accounts
        List<AccountDTO> newAccountList = newScrapRecord.accounts

        newAccountList.each { AccountDTO newAccountDTO ->
            if (!(newAccountDTO in oldAccountList)) {
                oldAccountList.add(newAccountDTO)
            } else {
                oldAccountList.each { AccountDTO oldAccountDTO ->
                    if (newAccountDTO.equals(oldAccountDTO)) {
                        filterNewTransaction(newAccountDTO, oldAccountDTO)
                    }
                }
            }
        }

        return oldScrapRecord
    }

    def filterNewTransaction(AccountDTO newAccount, AccountDTO oldAccount) {
        List<TransactionDTO> oldTransactionList = oldAccount.transactions
        List<TransactionDTO> newTransactionList = newAccount.transactions

        newTransactionList.each { TransactionDTO newTransactionDTO ->
            if (!(newTransactionDTO in oldTransactionList)) {
                oldTransactionList.add(newTransactionDTO)
            }
        }
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

    def filterNewDeductionHistory(PersonDTO oldRecord, PersonDTO newRecord) {
        List<DeductionDetailDTO> oldDeductionList = oldRecord.deductionHistory
        List<DeductionDetailDTO> newDeductionList = newRecord.deductionHistory
        newDeductionList.each { DeductionDetailDTO newDeductionDetailDTO ->
            if (!(newDeductionDetailDTO in oldDeductionList)) {
                oldDeductionList.add(newDeductionDetailDTO)
            }
        }
    }

}