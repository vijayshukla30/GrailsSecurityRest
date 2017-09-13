package moniday

class BootStrap {
    def bootStrapService
    def init = { servletContext ->
        bootStrapService.createRole()
        bootStrapService.createAdmin()
        bootStrapService.createSubAdmin()
    }
    def destroy = {
    }
}