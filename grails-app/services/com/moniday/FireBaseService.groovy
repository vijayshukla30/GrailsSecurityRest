package com.moniday

import com.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.google.firebase.auth.UserRecord.CreateRequest as CreateRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.tasks.Task
import com.moniday.User as Owner
import grails.gorm.transactions.Transactional

@Transactional
class FireBaseService {

    def serviceMethod() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference userRef = ref.child("users")
        Map<String, User> data = [:]

        data.put("vijay", new User("Vijay", "Shukla"))
        data.put("ABCDE", new User("ABCDE", "FGHIJ"))

        println(data)
        println("*****************************************************")
        userRef.setValue(data)
    }

    def createUser(Owner owner, String password) {
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