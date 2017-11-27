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

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
