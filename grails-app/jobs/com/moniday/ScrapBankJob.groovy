package com.moniday

class ScrapBankJob {
    static concurrent = false
    def moneyCollectionService
    static triggers = {
    }

    def execute(context) {
        // execute job
        println "Going to Execute Job to Scrap and collect money from the Account"
        String  userName = context.mergedJobDataMap.get("username")
        moneyCollectionService.collectMoney(userName)
        println "Money Collection Job Finished"
    }
}
