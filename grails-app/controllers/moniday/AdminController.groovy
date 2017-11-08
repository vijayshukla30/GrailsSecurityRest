package moniday

import com.moniday.AdminSetting
import com.moniday.User
import com.moniday.dto.PersonDTO
import com.moniday.firebase.FirebaseInitializer
import com.moniday.util.AppUtil
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class AdminController {

    def index(Integer max) {
        max = (params.max as Integer) ?: 10
        Integer offset = (params.offset as Integer) ?: 0
        List<User> users = User.createCriteria().list(sort: 'firstName', order: 'asc', max: max, offset: offset) {
            eq('isAdmin', false)
        }
        render(view: 'admin', model: [users: users, userCount: users?.totalCount])
    }

    def viewAdminSetting() {
        AdminSetting adminSetting = AdminSetting.get(1)
        render(view: 'adminSetting', model: [adminSetting: adminSetting])
    }

    def updateAdminSetting() {
        AdminSetting adminSetting = AdminSetting.get(1)
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
        String bankUrl = params.bankURL
        FirebaseInitializer.saveBanks(bankName, bankUrl)
        redirect(action: "index")
    }

    def showUserDetails(String uniqueId) {
        User user = User.findByUniqueId(uniqueId)
        if (user) {
            println("I found User")
            Map personalMap = FirebaseInitializer.getUserScrap(user?.firebaseId)
            PersonDTO personDTO = new PersonDTO(personalMap)
            AppUtil.calculateDeductionAmount(personDTO)
            println personDTO.properties
            render(view: 'userPersonalDetails', model: [tabName: "PersonalDetail", personDTO: personDTO, user: user])
        } else {
            flash.error = "No User has been found please check it..."
            redirect(action: 'index')
            return
        }
    }

    def showUserAccountDetail(String uniqueId) {

    }

    def showUserDebitMandateDetail(String uniqueId) {

    }
}
