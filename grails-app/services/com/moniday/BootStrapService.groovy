package com.moniday

import grails.gorm.transactions.Transactional

@Transactional
class BootStrapService {

    static final String roleAdmin = 'ROLE_ADMIN'
    static final String roleSubAdmin = 'ROLE_SUB_ADMIN'
    static final String roleUser = 'ROLE_USER'

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
            user = new User(username: 'system@moniday.com', password: '123@moniday', fireBaseUserId: '******').save(flush: true)
            UserRole.create(user, role)
        }
    }

    def createSubAdmin() {
        Role role = Role.findOrCreateByAuthority(roleSubAdmin)
        User user = User.findByUsername("admin@moniday.com")
        if (!user) {
            user = new User(username: 'admin@moniday.com', password: 'moniday@123', fireBaseUserId: '*****').save(flush: true)
            UserRole.create(user, role)
        }
    }
}