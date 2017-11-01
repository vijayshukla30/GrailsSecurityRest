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

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
