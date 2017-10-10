package moniday

import com.moniday.firebase.FirebaseInitializer

class BootStrap {
    def bootStrapService
    def grailsApplication

    def init = { servletContext ->
        bootStrapService.createRole()
        bootStrapService.createAdmin()
        bootStrapService.createSubAdmin()
        FirebaseInitializer.startFirebaseApp(grailsApplication.config.getProperty('firebase.configuration.json'))
    }
    def destroy = {
    }
}