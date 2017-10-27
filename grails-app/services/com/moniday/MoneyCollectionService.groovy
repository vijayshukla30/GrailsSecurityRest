package com.moniday

import com.firebase.Account
import com.firebase.Bank
import com.moniday.dto.PersonDTO
import com.moniday.firebase.FirebaseInitializer

class MoneyCollectionService {
    def transactional = false
    def scrapService
    def fireBaseService

    def collectMoney(String userName) {
        User user = User.findByUsername(userName)
        Account account = FirebaseInitializer.getUserAccount(user.firebaseId)
        Bank bank = FirebaseInitializer.getBank(account.bankName)
        PersonDTO personDTO = scrapService.scrapCAPCA(bank.bankURL, account.bankUserName, account.bankPassword)
        fireBaseService.saveScrappedDataToFirebase(personDTO, user?.firebaseId)
    }
}
