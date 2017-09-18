package com.moniday

import com.firebase.Account
import com.firebase.PersonalDetail
import com.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.google.firebase.auth.UserRecord.CreateRequest as CreateRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.tasks.Task
import com.moniday.User as Owner
import com.moniday.command.AccountDetailCO
import com.moniday.command.PersonalDetailCO
import com.moniday.command.UserCO
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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        Account account = new Account(accountDetailCO.bankName, accountDetailCO.bankUsername, accountDetailCO.bankPassword)
        DatabaseReference accountRef = ref.child("users/$fireBaseId/accountDetail")
        accountRef.setValue(account)
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