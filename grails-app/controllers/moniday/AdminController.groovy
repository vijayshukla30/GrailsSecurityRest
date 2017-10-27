package moniday

import com.moniday.AdminSetting
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class AdminController {

    def index() {
        render(view: 'admin')
    }

    def viewAdminSetting() {
        def adminSetting = AdminSetting.get(1)
        render(view: 'adminSetting',model:[values:adminSetting])
    }

    def updateAdminSetting() {
        def adminSetting = AdminSetting.get(1)
        println(params.)
        bindData(adminSetting,params)
        adminSetting.save(flush: true)
        flash.message="Data Updated Successfully"
        redirect(action: "index")
    }
}
