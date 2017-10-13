package com.moniday

import com.mangopay.MangoPayApi
import com.mangopay.core.APIs.ApiUsers
import com.mangopay.core.ResponseException
import com.mangopay.core.Sorting
import com.mangopay.core.enumerations.CountryIso
import com.mangopay.core.enumerations.SortDirection
import com.mangopay.entities.User as MangoUser
import com.mangopay.entities.UserNatural
import com.moniday.command.PersonalDetailCO
import com.moniday.util.AppUtil
import grails.core.GrailsApplication

class MangoPayService {
    static transactional = false
    GrailsApplication grailsApplication

    def serviceMethod() {
        try {
            MangoPayApi payApi = new MangoPayApi()
            payApi.Config.ClientId = grailsApplication.config.getProperty('grails.mangopay.clientId')
            payApi.Config.ClientPassword = grailsApplication.config.getProperty('grails.mangopay.passPhrase')
            payApi.Config.BaseUrl = grailsApplication.config.getProperty('grails.mangopay.mangopayUrl')
            payApi.Config.DebugMode = true

            println(payApi.Config.properties)

            Sorting sorting = new Sorting()
            sorting.addField("CreationDate", SortDirection.asc)
            List<MangoUser> users = payApi.Users.getAll(null, sorting)
            println(users)

            println(payApi.Clients.get()?.properties)
            println("((((((((((((((((((((((((((((((((((((((((")
        } catch (ResponseException responseException) {
            println("Exception MangoPay")
            println responseException.message
        } catch (Exception ex) {
            println("Super Class Exception")
        }
    }

    def createUser(User user, PersonalDetailCO personalDetailCO) {
        try {
            MangoPayApi payApi = new MangoPayApi()
            payApi.Config.ClientId = grailsApplication.config.getProperty('grails.mangopay.clientId')
            payApi.Config.ClientPassword = grailsApplication.config.getProperty('grails.mangopay.passPhrase')
            payApi.Config.BaseUrl = grailsApplication.config.getProperty('grails.mangopay.mangopayUrl')
            payApi.Config.DebugMode = true

            MangoUser userNatural = new UserNatural()
            userNatural.Tag = user?.uniqueId
            userNatural.FirstName = personalDetailCO.firstName
            userNatural.LastName = personalDetailCO.lastName
            userNatural.Email = user.username
            userNatural.Birthday = AppUtil.generateUnixTimeStampFromDate(personalDetailCO.dateOfBirth)
            userNatural.Nationality = CountryIso.IN
            userNatural.CountryOfResidence = CountryIso.IN
            ApiUsers apiUsers = payApi.Users
            apiUsers.create(userNatural)
        } catch (Exception ex) {
            println(ex.message)
        }
    }
}