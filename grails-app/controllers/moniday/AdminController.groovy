package moniday

import com.moniday.AdminSetting
import com.moniday.firebase.FirebaseInitializer
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class AdminController {

    def index() {
        render(view: 'admin')
    }

    def viewAdminSetting() {
        def adminSetting = AdminSetting.get(1)
        render(view: 'adminSetting', model: [values: adminSetting])
    }

    def updateAdminSetting() {
        def adminSetting = AdminSetting.get(1)
        bindData(adminSetting, params)
        adminSetting.save(flush: true)
        flash.message = "Data Updated Successfully"
        redirect(action: "index")
    }

    def addBank() {
        render(view: "addBank")
    }

    def saveBanks() {
        String bankName = params.bankName
        String bankUrl = params.bankUrl
        FirebaseInitializer.saveBanks(bankName, bankUrl)
        redirect(action: "index")
    }
}
