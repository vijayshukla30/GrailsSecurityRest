package moniday

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(view: "/index")

        "/register"(controller: 'public', action: 'register')
        '/reset-password'(controller: 'public', action: 'forgetPassword')
        "/user/$uniqueId/personal-detail"(controller: 'account', action: 'personalDetail')
        "/user/$uniqueId/account-detail"(controller: 'account', action: 'accountDetail')

        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
