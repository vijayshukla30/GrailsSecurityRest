package com.moniday

import com.mangopay.MangoPayApi
import com.mangopay.core.APIs.ApiUsers
import com.mangopay.core.ResponseException
import com.mangopay.core.enumerations.CountryIso
import com.mangopay.entities.User as MangoUser
import com.mangopay.entities.UserNatural
import grails.core.GrailsApplication

import java.time.Instant

class MangoPayService {
    static transactional = false
    GrailsApplication grailsApplication

    def serviceMethod() {
        try {
            MangoPayApi payApi = new MangoPayApi()
            payApi.Config.ClientId = grailsApplication.config.getProperty('grails.mangopay.clientId')
            payApi.Config.ClientPassword = grailsApplication.config.getProperty('grails.mangopay.password')
            payApi.Config.BaseUrl = grailsApplication.config.getProperty('grails.mangopay.mangopayUrl')
            payApi.Config.DebugMode = true

            /* Sorting sorting = new Sorting()
             sorting.addField("CreationDate", SortDirection.asc)
             List<MangoUser> users = payApi.Users.getAll(null, sorting)
             println(users)*/

            println(payApi.Clients.get()?.properties)
            println("((((((((((((((((((((((((((((((((((((((((")
        } catch (ResponseException responseException) {
            println("Exception MangoPay")
            responseException.printStackTrace()
        } catch (Exception ex) {
            println("Super Class Exception")
        }
    }

    def createUser() {
        try {
            MangoPayApi payApi = new MangoPayApi()
            payApi.Config.ClientId = grailsApplication.config.getProperty('grails.mangopay.clientId')
            payApi.Config.ClientPassword = grailsApplication.config.getProperty('grails.mangopay.password')
            payApi.Config.BaseUrl = grailsApplication.config.getProperty('grails.mangopay.mangopayUrl')
            payApi.Config.DebugMode = true
            println("Client Id ${payApi.Config.ClientId}")
            println("Client Password ${payApi.Config.ClientPassword}")
            println("Client Base URL ${payApi.Config.BaseUrl}")

            MangoUser user = new UserNatural()
            user.FirstName = "Vijay"
            user.Email = "vshukla684@gmail.com"
            user.LastName = "Shukla"
            user.Birthday = Instant.now().getEpochSecond()
            user.Nationality = CountryIso.IN
            user.CountryOfResidence = CountryIso.IN
            ApiUsers apiUsers = payApi.Users
            println(apiUsers.properties)
            apiUsers.create(user)
            println(user.properties)
        } catch (Exception ex) {

        }
    }
}