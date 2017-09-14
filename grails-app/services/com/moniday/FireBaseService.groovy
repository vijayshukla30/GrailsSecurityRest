package com.moniday

import com.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
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
}