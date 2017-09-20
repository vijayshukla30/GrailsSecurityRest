package com.moniday

import com.firebase.Account
import com.firebase.DirectDebitMandate
import com.firebase.PersonalDetail
import com.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.google.firebase.auth.UserRecord.CreateRequest as CreateRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.tasks.Task
import com.moniday.User as Owner
import com.moniday.command.*
import grails.gorm.transactions.Transactional

@Transactional
class FireBaseService {

    String createUser(UserCO userCO) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference userRef = ref.child("users").push()
        User user = new User(userCO.username, userCO.password)
        println("*****************************************************")
        userRef.setValue(user)
        String fireBaseKey = userRef.key
        println("******************")
        println(fireBaseKey)
        println("******************")
        return fireBaseKey
    }

    def savePersonalDetail(PersonalDetailCO personalDetailCO, String fireBaseId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        PersonalDetail personalDetail = new PersonalDetail()
        personalDetail.setFirstName(personalDetailCO.firstName)
        personalDetail.setLastName(personalDetailCO.lastName)
        personalDetail.setDateOfBirth(personalDetailCO.dateOfBirth)
        personalDetail.setAge(personalDetailCO.age)
        personalDetail.setNationality(personalDetailCO.nationality.value)
        personalDetail.setCountryOfResidence(personalDetailCO.country.value)
        personalDetail.setCurrency(personalDetailCO.currency.value)
        DatabaseReference personRef = ref.child("users/$fireBaseId/personalDetail")
        personRef.setValue(personalDetail)
    }

    def saveAccountDetail(AccountDetailCO accountDetailCO, String fireBaseId) {
        Account account = new Account(accountDetailCO.bankName, accountDetailCO.bankUsername, accountDetailCO.bankPassword)
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference accountRef = ref.child("users/$fireBaseId/accountDetail")
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
        DatabaseReference securityRef = ref.child("users/$fireBaseId/accountDetail/securityDetail")
        println(data)
        securityRef.setValue(data)
    }

    def saveDirectDebitMandateDetail(DirectDebitMandateCO debitMandateCO, String fireBaseId) {
        DirectDebitMandate debitMandate = new DirectDebitMandate(debitMandateCO.owner, debitMandateCO.addressLine1, debitMandateCO.city, debitMandateCO.region, debitMandateCO.postCode, debitMandateCO.country.value, debitMandateCO.iban, debitMandateCO.bic)
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference debitRef = ref.child("users/$fireBaseId/debitMandate")
        debitRef.setValue(debitMandate)
    }

    def registerUser(Owner owner, String password) {
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