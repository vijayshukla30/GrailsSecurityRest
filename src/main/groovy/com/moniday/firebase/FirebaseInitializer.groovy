package com.moniday.firebase

import com.firebase.User
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseCredentials
import com.google.firebase.database.*

class FirebaseInitializer {
    private static final String DATABASE_URL = "https://moniday-f3590.firebaseio.com/"

    private static DatabaseReference database;

    static void startFirebaseApp(String path) {
        FileInputStream fileInputStream = new FileInputStream(path)
        FirebaseOptions options = new FirebaseOptions.Builder().setCredential(FirebaseCredentials.fromCertificate(fileInputStream)).setDatabaseUrl(DATABASE_URL).build()
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options)
        println firebaseApp.name
        database = FirebaseDatabase.getInstance().getReference()
        startListeners()
    }

    static void startListeners() {
        database.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                final String postId = snapshot.key
                final User user = snapshot.getValue(User.class)

                println("Post Id ${postId} ************ ${user.properties}")
            }

            @Override
            void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            void onCancelled(DatabaseError error) {

            }
        })
    }
}