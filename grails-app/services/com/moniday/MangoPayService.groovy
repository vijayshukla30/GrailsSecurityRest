package com.moniday

import com.mangopay.MangoPayApi
import com.mangopay.core.APIs.ApiMandates
import com.mangopay.core.APIs.ApiUsers
import com.mangopay.core.APIs.ApiWallets
import com.mangopay.core.Address
import com.mangopay.core.ResponseException
import com.mangopay.core.Sorting
import com.mangopay.core.enumerations.BankAccountType
import com.mangopay.core.enumerations.CountryIso
import com.mangopay.core.enumerations.CultureCode
import com.mangopay.core.enumerations.SortDirection
import com.mangopay.entities.BankAccount
import com.mangopay.entities.Mandate
import com.mangopay.entities.User as MangoUser
import com.mangopay.entities.UserNatural
import com.mangopay.entities.Wallet
import com.mangopay.entities.subentities.BankAccountDetailsIBAN
import com.moniday.command.DirectDebitMandateCO
import com.moniday.command.PersonalDetailCO
import com.moniday.util.AppUtil
import grails.core.GrailsApplication
import grails.web.mapping.LinkGenerator

import java.text.SimpleDateFormat

class MangoPayService {
    static transactional = false
    GrailsApplication grailsApplication
    LinkGenerator grailsLinkGenerator

    def serviceMethod() {
        try {
            MangoPayApi payApi = new MangoPayApi()
            payApi.Config.ClientId = grailsApplication.config.getProperty('grails.mangopay.clientId')
            payApi.Config.ClientPassword = grailsApplication.config.getProperty('grails.mangopay.passPhrase')
            payApi.Config.BaseUrl = grailsApplication.config.getProperty('grails.mangopay.mangopayUrl')
            payApi.Config.DebugMode = true

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
        println("creating MangoPay Account")
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
            userNatural.Birthday = AppUtil.generateUnixTimeStampFromDate(new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").parse(personalDetailCO.dateOfBirth))
            userNatural.Nationality = AppUtil.countryToCountryISO(personalDetailCO.nationality)
            userNatural.CountryOfResidence = AppUtil.countryToCountryISO(personalDetailCO.country)
            ApiUsers apiUsers = payApi.Users
            userNatural = apiUsers.create(userNatural)
            user.mangoPayId = userNatural.Id
            user.firstName = personalDetailCO.firstName
            user.lastName = personalDetailCO.lastName
            user.country = personalDetailCO.country
            user.save(flush: true)
        } catch (Exception ex) {
            println(ex.message)
        }
        println("created MangoPay Account")
    }

    def createWalletForUser(User user) {
        println("creating MangoPay Wallet")
        MangoPayApi payApi = new MangoPayApi()
        payApi.Config.ClientId = grailsApplication.config.getProperty('grails.mangopay.clientId')
        payApi.Config.ClientPassword = grailsApplication.config.getProperty('grails.mangopay.passPhrase')
        payApi.Config.BaseUrl = grailsApplication.config.getProperty('grails.mangopay.mangopayUrl')
        payApi.Config.DebugMode = true
        Wallet wallet = new Wallet()
        wallet.Tag = user.uniqueId
        wallet.Owners = [user.mangoPayId]
        wallet.Description = user.uniqueId
        wallet.Currency = AppUtil.currencyToCurrencyISO(user.currency)
        ApiWallets apiWallets = payApi.Wallets
        wallet = apiWallets.create(wallet)
        user.mangoPayWalletId = wallet.Id
        user.save(flush: true)
        println("created MangoPay Wallet")
    }

    def createMandateForUser(DirectDebitMandateCO debitMandateCO, User user) {
        MangoPayApi payApi = new MangoPayApi()
        payApi.Config.ClientId = grailsApplication.config.getProperty('grails.mangopay.clientId')
        payApi.Config.ClientPassword = grailsApplication.config.getProperty('grails.mangopay.passPhrase')
        payApi.Config.BaseUrl = grailsApplication.config.getProperty('grails.mangopay.mangopayUrl')
        payApi.Config.DebugMode = true

        BankAccountDetailsIBAN bAN = new BankAccountDetailsIBAN()
        bAN.IBAN = debitMandateCO.iban
        bAN.BIC = debitMandateCO.bic

        BankAccount bankAccount = new BankAccount()
        bankAccount.Tag = user.uniqueId
        bankAccount.OwnerName = debitMandateCO.owner
        bankAccount.OwnerAddress = new Address()
        bankAccount.OwnerAddress.City = debitMandateCO.city
        bankAccount.OwnerAddress.Country = CountryIso.GB
        bankAccount.OwnerAddress.PostalCode = debitMandateCO.postCode
        bankAccount.OwnerAddress.AddressLine1 = debitMandateCO.addressLine1
        bankAccount.OwnerAddress.Region = debitMandateCO.region
        bankAccount.Type = BankAccountType.IBAN
        bankAccount.Details = bAN

        ApiUsers apiUsers = payApi.Users
        bankAccount = apiUsers.createBankAccount(user?.mangoPayId, bankAccount)
        user.mangoPayBankccountId = bankAccount.Id
        Mandate mandate = new Mandate()
        mandate.Tag = user?.uniqueId
        mandate.BankAccountId = bankAccount.Id
        mandate.Culture = CultureCode.EN
        mandate.ReturnURL = grailsLinkGenerator.link(controller: 'account', action: "mangoPayReturn", absolute: true)
        println("mandate.ReturnURL ${mandate.ReturnURL}")
        ApiMandates apiMandates = payApi.Mandates
        mandate = apiMandates.create(mandate)
        user.mangoPayMandateId = mandate.Id
        user.save(flush: true)
    }
}