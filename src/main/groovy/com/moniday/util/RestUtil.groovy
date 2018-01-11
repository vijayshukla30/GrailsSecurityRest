package com.moniday.util

import com.moniday.dto.AccountDTO

class RestUtil {

    static calculateAccountBalance(ArrayList<AccountDTO> accountDTOS) {
        Double amountSum = 0
        List<String> accounts = new ArrayList<String>()
        for (AccountDTO accountDTO : accountDTOS) {
            if (! accounts.contains(accountDTO.accountNumber)) {
                amountSum += accountDTO.balance
                accounts.add(accountDTO.accountNumber)
            }
        }
        return amountSum
    }

    static investmentDetails() {

    }
}
