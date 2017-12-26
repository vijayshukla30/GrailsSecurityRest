package com.moniday

class DeleteTokensJob {
    static triggers = {
        cron name: 'cronTrigger', cronExpression: "0 0/2 * * * ?"

    }

    def execute() {
        long currentTimeStamp = System.currentTimeMillis() - 30 * 60 * 1000
        def tokenList = AuthenticationToken.createCriteria().list {
            lt("dateCreated", currentTimeStamp)
        }
        tokenList*.delete()
    }
}
