package com.moniday.firebase

import com.firebase.Account
import com.firebase.Bank
import com.firebase.PersonalDetail
import com.firebase.User
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseCredentials
import com.google.firebase.database.*
import com.moniday.AdminSetting
import com.moniday.command.PersonalDetailCO

import java.util.concurrent.CountDownLatch

class FirebaseInitializer {
    private static final String DATABASE_URL
    public static String BANK_REF = "APP_BANKS"
    public static String USER_REF = "APP_USERS"

    private static DatabaseReference database

    static {
        DATABASE_URL = AdminSetting.get(1)?.firebaseServerUrl
    }

    static void startFirebaseApp(String path) {
        FileInputStream fileInputStream = new FileInputStream(path)
        FirebaseOptions options = new FirebaseOptions.Builder().setCredential(FirebaseCredentials.fromCertificate(fileInputStream)).setDatabaseUrl(DATABASE_URL).build()
        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options)
//        println firebaseApp.name
        database = FirebaseDatabase.getInstance().getReference()
        startListeners()
    }

    static void startListeners() {
        database.child(USER_REF).addChildEventListener(new ChildEventListener() {
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

    //Get User
    static User getUser(String userFireBaseId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(USER_REF + "/${userFireBaseId}")
        CountDownLatch countDownLatch = new CountDownLatch(1)
        User user = new User()
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setEmail(dataSnapshot.value.email)
                countDownLatch.countDown()
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                println(databaseError.code)
            }
        })
        waitForCountDownLatch(countDownLatch)
        return user
    }

    //Get Account Details of User
    static Account getUserAccount(String userFireBaseId) {
        DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference(USER_REF + "/${userFireBaseId}/accountDetail")
        CountDownLatch countDownLatch = new CountDownLatch(1)
        Account account = new Account()
        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                println dataSnapshot.properties
                println dataSnapshot.value.bankUserName
                account.setBankName(dataSnapshot.value.bankName)
                account.setBankUserName(dataSnapshot.value.bankUserName)
                account.setBankPassword(dataSnapshot.value.bankPassword)
                countDownLatch.countDown()
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                println(databaseError.code)
            }
        })
        waitForCountDownLatch(countDownLatch)
        println(account.properties)
        return account
    }

    //Save Personal Details of User
    static def savePersonalDetail(PersonalDetailCO personalDetailCO, String fireBaseId) {
        println("Saving Personal Detail")
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        PersonalDetail personalDetail = new PersonalDetail()
        personalDetail.setFirstName(personalDetailCO.firstName)
        personalDetail.setLastName(personalDetailCO.lastName)
        personalDetail.setDateOfBirth(personalDetailCO.dateOfBirth)
        personalDetail.setAge(personalDetailCO.age)
        personalDetail.setNationality(personalDetailCO.nationality.value)
        personalDetail.setCountryOfResidence(personalDetailCO.country.value)
        personalDetail.setCurrency(personalDetailCO.currency.value)
        DatabaseReference personRef = ref.child("${USER_REF}/$fireBaseId/personalDetail")
        personRef.setValue(personalDetail)
        println("Saved Personal Detail")
    }

    //Get Personal Details of User
    static Map getUserPersonalDetail(String userFireBaseId) {
        Map personalMap = [:]
        DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference(USER_REF + "/${userFireBaseId}/personalDetail")
        CountDownLatch countDownLatch = new CountDownLatch(1)
        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personalMap = (dataSnapshot.value as Map)
                countDownLatch.countDown()
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                println(databaseError.code)
            }
        })
        waitForCountDownLatch(countDownLatch)
        personalMap
    }

    //Get Scrap Details of User
    static Map getUserScrap(String userFireBaseId) {
        Map accountMap = [:]
        DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference(USER_REF + "/${userFireBaseId}/scrapDetail")
        CountDownLatch countDownLatch = new CountDownLatch(1)
        accountRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                println "Printing Scrap Details ---->>> " + dataSnapshot.value
//                println "Printing Scrap Details ----******************* " + dataSnapshot.properties
                accountMap = (dataSnapshot.value as Map)
                countDownLatch.countDown()
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                println(databaseError.code)
            }
        })
        waitForCountDownLatch(countDownLatch)
        accountMap
    }

    //Get All Users From Firebase
    static List<User> getUsers() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(USER_REF)
        final List<User> users = []
        CountDownLatch countDownLatch = new CountDownLatch(1)
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.children.eachWithIndex { DataSnapshot entry, int i ->
                    User user = new User()
                    user.setEmail(entry.value.email)
                    users.add(user)
                }
                countDownLatch.countDown()
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                println(databaseError.code)
            }
        })
        waitForCountDownLatch(countDownLatch)
        return users
    }

    //Save Banks
    static saveBanks(bankname, bankurl) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        DatabaseReference bankRef = ref.child(BANK_REF).push()
        Bank bank = new Bank(bankName: bankname, bankURL: bankurl)
        bankRef.setValue(bank)
    }

    //Get Bank
    static Bank getBank(String bankId) {
        DatabaseReference bankRef = FirebaseDatabase.getInstance().getReference(BANK_REF + "/$bankId")
        CountDownLatch countDownLatch = new CountDownLatch(1)
        Bank bank = new Bank()
        bankRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bank.setBankURL(dataSnapshot.value.bankURL)
                bank.setBankName(dataSnapshot.value.bankName)
                bank.setBankFirebaseId(dataSnapshot.key)
                countDownLatch.countDown()
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                println(databaseError.code)
            }
        })
        waitForCountDownLatch(countDownLatch)
        return bank
    }

    //Get All Banks From Firebase
    static List<Bank> getBanks() {
        DatabaseReference bankRef = FirebaseDatabase.getInstance().getReference(BANK_REF)
        final List<Bank> banks = []
        CountDownLatch countDownLatch = new CountDownLatch(1)
        bankRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.children.eachWithIndex { DataSnapshot entry, int i ->
                    Bank bank = new Bank()
                    bank.setBankURL(entry.value.bankURL)
                    bank.setBankName(entry.value.bankName)
                    bank.setBankFirebaseId(entry.key)
                    banks.add(bank)
                }
                countDownLatch.countDown()
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                println(databaseError.code)
            }
        })
        waitForCountDownLatch(countDownLatch)
        return banks
    }


    private static void waitForCountDownLatch(CountDownLatch countDownLatch) {
        try {
            countDownLatch.await()
        } catch (Exception) {

        }
    }
}