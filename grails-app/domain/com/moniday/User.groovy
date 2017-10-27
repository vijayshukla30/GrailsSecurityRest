package com.moniday

import com.moniday.command.UserCO
import com.moniday.enums.Country
import com.moniday.enums.Currency
import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class User implements Serializable {

    private static final long serialVersionUID = 1
    String uniqueId = UUID.randomUUID().toString()
    String username
    String password
    String firstName
    String lastName
    Country country = Country.GB
    Currency currency = Currency.GBP
    String firebaseId
    String mangoPayId
    String mangoPayWalletId
    String mangoPayMandateId
    String mangoPayBankccountId

    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    boolean isAdmin = false
    Date dateCreated
    Date lastUpdated

    Set<Role> getAuthorities() {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true, email: true
        firebaseId nullable: true, blank: false, unique: true
        mangoPayId nullable: true, blank: false, unique: true
        mangoPayWalletId nullable: true, blank: false, unique: true
        mangoPayMandateId nullable: true, blank: false, unique: true
        mangoPayBankccountId nullable: true, blank: false, unique: true
        firstName nullable: true, blank: false
        lastName nullable: true, blank: false
    }

    static mapping = {
        password column: '`password`'
    }

    User() {

    }

    User(UserCO userCO) {
        this.username = userCO.username
        this.password = userCO.password
    }
}