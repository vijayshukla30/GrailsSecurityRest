package com.moniday

import com.moniday.command.UserCO
import com.moniday.enums.Authority
import grails.gorm.transactions.Transactional

class AccountService {
    def fireBaseService

    @Transactional
    User saveUser(UserCO userCO) {
        User user = new User(userCO)
        if (userCO.validate()) {
            user.firebaseId = fireBaseService.createUser(userCO)
        }

        if (user.validate()) {
            user.save(flush: true)
            assignRole(user)
            return user
        } else {
            user.errors.allErrors.each {
                println(it)
            }
            return null
        }
    }

    def assignRole(User user) {
        Role role = Role.findByAuthority(Authority.ROLE_USER.value())
        UserRole.create(user, role)
    }
}