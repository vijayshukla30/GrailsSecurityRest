package moniday

class MoneyCollectionJob {
    static concurrent = false
    def moneyCollectionService
    static triggers = {
        simple repeatInterval: 5 * 60 * 12 * 5000l // execute job once in 5 seconds
    }

    def execute() {
        // execute job
        println "Going to Execute Job to Scrap and collect money from the Account"
//        moneyCollectionService.collectMoney()
        println "Money Collection Job Finished"
    }
}
