package moniday

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: 'public', action: 'index')
        "/register"(controller: 'public', action: 'register')
        '/reset-password'(controller: 'public', action: 'forgetPassword')
        "/user/$uniqueId/personal-detail"(controller: 'account', action: 'personalDetail')
        "/user/$uniqueId/account-detail"(controller: 'account', action: 'accountDetail')
        "/user/$uniqueId/security-detail"(controller: 'account', action: 'securityDetail')
        "/admin/$uniqueId/show-user-details"(controller: 'admin', action: 'showUserDetails')
        "/admin/$uniqueId/show-account-details"(controller: 'admin', action: 'showUserAccountDetail')
        "/admin/$uniqueId/approveAmountDeduction"(controller: 'admin', action: 'approveAmountDeduction')
        "/admin/$uniqueId/conformAmountDeduction"(controller: 'admin', action: 'conformAmountDeduction')

        "/api/put/personalDetail"(controller: 'rest', action: 'savePersonalDetail')
        "/api/get/personalDetail"(controller: 'rest', action: 'personalDetail')
        "/api/get/banks"(controller: 'rest', action: 'banks')
        "/api/put/bank"(controller: 'rest', action: 'saveBank')
        "/api/put/accountDetail"(controller: 'rest', action: 'saveAccountDetail')
        "/api/put/debitmendatedetail"(controller: 'rest', action: 'saveDebitMendateDetail')
        "/api/get/adminsetting"(controller: 'rest', action: 'adminSettings')
        "/api/put/adminsetting"(controller: 'rest', action: 'updateAdminSettings')
        "/api/get/scrapdata"(controller: 'rest', action: 'getScrapRecord')
        "/api/get/deductionhistory"(controller: 'rest', action: 'getDeductionHistory')
        "/api/logout"(controller: 'rest', action: 'logout')
        "/api/get/countries"(controller: 'rest', action: 'getCountries')
        "/api/get/currencies"(controller: 'rest', action: 'getCurrencies')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
