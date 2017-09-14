package moniday

import com.moniday.firebase.FirebaseInitializer

class BootStrap {
    def bootStrapService
    def init = { servletContext ->
        bootStrapService.createRole()
        bootStrapService.createAdmin()
        bootStrapService.createSubAdmin()
        FirebaseInitializer.startFirebaseApp()
    }
    def destroy = {
    }
}