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
        String bankName = bank.bankName
        PersonDTO personDTO
        switch (bankName) {
            case "BNP PARIBAS":
                personDTO = scrapService.scrapBNP_PARIBAS(bank.bankURL, account.bankUserName, account.bankPassword)
                break
            case "CREDIT AGRICOLE":
                personDTO = scrapService.scrapCreditAgricole(bank.bankURL, account.bankUserName, account.bankPassword)
                break
            default:
                println("No such bank found")
        }
        fireBaseService.saveScrappedDataToFirebase(personDTO, user?.firebaseId)
    }
}
