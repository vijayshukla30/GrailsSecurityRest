package moniday

import com.moniday.AdminSetting
import com.moniday.User
import com.moniday.firebase.FirebaseInitializer
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class AdminController {

    def index() {
        //get users list and pass it to admin.gsp
        List<User> users = User.getAll()
        render(view: 'admin',model: [users:users])
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
