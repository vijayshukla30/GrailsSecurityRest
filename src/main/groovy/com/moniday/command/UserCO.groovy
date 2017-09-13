package com.moniday.command

import com.moniday.User
import grails.validation.Validateable

class UserCO implements Validateable {
    String uniqueId
    String username
    String password
    String confirmPassword
    static constraints = {
        uniqueId nullable: true, blank: true
        username(nullable: false, blank: false, email: true, validator: { val, obj ->
            User user = User.findByUniqueId(obj.uniqueId)
            if (!(user?.username?.equals(val))) {
                if (User.findByUsername(val)) {
                    return "user.already.exists"
                } else {
                    return true
                }
            } else {
                return true
            }
        })
        password(nullable: false, blank: false, minLength: 6)
        confirmPassword(validator: { val, obj ->
            if (!val.equals(obj.confirmPassword)) {
                return false
            }
        })
    }
}