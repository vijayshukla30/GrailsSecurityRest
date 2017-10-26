package com.moniday

import com.moniday.enums.Authority
import grails.gorm.transactions.Transactional

@Transactional
class BootStrapService {

    static final String roleAdmin = Authority.ROLE_ADMIN.value()
    static final String roleSubAdmin = Authority.ROLE_SUB_ADMIN.value()
    static final String roleUser = Authority.ROLE_USER.value()

    def createRole() {
        if (!Role.findByAuthority(roleAdmin)) {
            new Role(authority: roleAdmin).save(flush: true)
        }
        if (!Role.findByAuthority(roleSubAdmin)) {
            new Role(authority: roleSubAdmin).save(flush: true)
        }
        if (!Role.findByAuthority(roleUser)) {
            new Role(authority: roleUser).save(flush: true)
        }
    }

    def createAdmin() {
        Role role = Role.findOrCreateByAuthority(roleAdmin)
        User user = User.findByUsername("admin@moniday.com")
        if (!user) {
            user = new User(username: 'admin@moniday.com', password: '123@moniday', firebaseId: "*").save(flush: true)
            UserRole.create(user, role)
        }
    }

    def createSubAdmin() {
        Role role = Role.findOrCreateByAuthority(roleSubAdmin)
        User user = User.findByUsername("system@moniday.com")
        if (!user) {
            user = new User(username: 'system@moniday.com', password: 'moniday@123', firebaseId: "**").save(flush: true)
            UserRole.create(user, role)
        }
    }

    def createAdminSetting() {
        AdminSetting adminSetting = new AdminSetting(mangoPayUrl: 'https://api.sandbox.mangopay.com/',
                mangoPayClientId: 'monidaytest', mangoPayEmailId: 'gaelitier@gmail.com',
                mangoPayPassKey: '1td1tjaJG3NEJLdfhnWRDAw2btMaXKZJth4Yk0UxJNCmDDO7aZ',
                sendgridUsername: 'pmungali', sendgridPassword: 'S@nd@ta*4231',
                sendgridEmail: 'puneetmungali93@gmail.com',firebaseConfigurationPath: 'static',
                firebaseServerUrl: 'https://moniday-3e5a7.firebaseio.com/').save(flush: true)
    }
}