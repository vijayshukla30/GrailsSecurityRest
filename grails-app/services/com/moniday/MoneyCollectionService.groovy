package com.moniday

import com.firebase.Account
import com.firebase.Bank
import com.moniday.dto.PersonDTO
import com.moniday.firebase.FirebaseInitializer
import com.moniday.util.AppUtil

class MoneyCollectionService {
    def transactional = false
    def scrapService
    def fireBaseService

    def collectMoney(String userName) {
        User user = User.findByUsername(userName)
        Account account = FirebaseInitializer.getUserAccount(user.firebaseId)
        Bank bank = FirebaseInitializer.getBank(account.bankName)
        String bankName = bank.bankName
        PersonDTO personDTO = null
        switch (bankName) {
            case "BNP PARIBAS":
                personDTO = scrapService.scrapBnpParibas(bank.bankURL, account.bankUserName, account.bankPassword)
                break
            case "CREDIT AGRICOLE":
                personDTO = scrapService.scrapCreditAgricole(bank.bankURL, account.bankUserName, account.bankPassword)
                break
            case "ca-pca":
                personDTO = scrapService.scrapCAPCA(bank.bankURL, account.bankUserName, account.bankPassword)
                break
            default:
                println("No such bank found")
        }
        if (personDTO) {
            AppUtil.calculateDeductionAmount(personDTO)
            fireBaseService.saveScrappedDataToFirebase(personDTO, user?.firebaseId)
        }
    }
}
