dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = 'org.hibernate.dialect.MySQL5InnoDBDialect'
}

environments {
    development {
        grails.serverURL = "http://dev.moniday.com:8080"
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/moniday_dev?autoreconnect=true"
            username = "root"
            logSql = false
            password = "nextdefault"
        }
        grails {
            mangopay {
                mangopayUrl = "https://api.sandbox.mangopay.com/"
                clientId = "monidaytest"
                email = "gaelitier@gmail.com"
                passPhrase = "1td1tjaJG3NEJLdfhnWRDAw2btMaXKZJth4Yk0UxJNCmDDO7aZ"
            }
        }
        firebase.configuration.json = "${userHome}/.grails/firebase-service.json"
    }
    test {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:mysql://localhost:3306/moniday_test?autoreconnect=true"
            username = "root"
            logSql = false
            password = "nextdefault"
        }
        grails {
            moniday {
                monidayUrl = "https://dashboard.sandbox.mangopay.com/"
                clientId = "moniday-ltd"
                email = "d.rmascio@gmail.com"
                password = "Azerty0334!"
            }
        }
    }
    production {
        dataSource {
            username = "root"
            password = "nextdefault"
            dbCreate = "none"
            url = "jdbc:mysql://localhost:3306/moniday?autoreconnect=true&useUnicode=yes&characterEncoding=UTF-8"
            pooled = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis = 1800000
                timeBetweenEvictionRunsMillis = 1800000
                numTestsPerEvictionRun = 3
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = true
                validationQuery = "SELECT 1"
            }
        }
    }
    staging {
        grails.serverURL = "http://13.58.157.62"
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/moniday_prod?autoreconnect=true"
            username = "root"
            logSql = false
            password = "7wL3jH2295zWS"
            pooled = true
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis = 1800000
                timeBetweenEvictionRunsMillis = 1800000
                numTestsPerEvictionRun = 3
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = true
                validationQuery = "SELECT 1"
            }
        }
        grails {
            mangopay {
                mangopayUrl = "https://api.sandbox.mangopay.com/"
                clientId = "monidaytest"
                email = "gaelitier@gmail.com"
                passPhrase = "1td1tjaJG3NEJLdfhnWRDAw2btMaXKZJth4Yk0UxJNCmDDO7aZ"
            }
        }
        firebase.configuration.json = "/home/ubuntu/.grails/firebase-service.json"
    }
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.moniday.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.moniday.UserRole'
grails.plugin.springsecurity.authority.className = 'com.moniday.Role'

grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/account/index'
grails.plugin.springsecurity.failureHandler.defaultFailureUrl = '/?login_error=1'
grails.plugin.springsecurity.auth.loginFormUrl = '/'
grails.plugin.springsecurity.logout.afterLogoutUrl = '/'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.rest.token.generation.useSecureRandom = false
grails.plugin.springsecurity.rest.token.generation.useUUID = true
//authentication configuration option
grails.plugin.springsecurity.rest.login.active = true
grails.plugin.springsecurity.rest.login.endpointUrl = '/api/login'
grails.plugin.springsecurity.rest.login.failureStatusCode = 401
//json credential extraction configuration properties
grails.plugin.springsecurity.rest.login.useJsonCredentials = true
grails.plugin.springsecurity.rest.login.usernamePropertyName = 'username'
grails.plugin.springsecurity.rest.login.passwordPropertyName = 'password'
/*//parameter extraction configuration option
grails.plugin.springsecurity.rest.login.useRequestParamsCredentials= true
grails.plugin.springsecurity.rest.login.usernamePropertyName = 'username'
grails.plugin.springsecurity.rest.login.passwordPropertyName = 'password'*/
//logout configuration option
grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'
//use gorm for token storage
grails.plugin.springsecurity.rest.token.storage.useGorm = true
//token generation configuration option
grails.plugin.springsecurity.rest.token.generation.useSecureRandom = true
//gorm configuration option
grails.plugin.springsecurity.rest.token.storage.gorm.tokenDomainClassName = "com.moniday.AuthenticationToken"
grails.plugin.springsecurity.rest.token.storage.gorm.tokenValuePropertyName = "tokenValue"
grails.plugin.springsecurity.rest.token.storage.gorm.usernamePropertyName = 'username'
//Token rendering configuration options
grails.plugin.springsecurity.rest.token.rendering.usernamePropertyName = "username"
grails.plugin.springsecurity.rest.token.rendering.authoritiesPropertyName = "roles"
grails.plugin.springsecurity.rest.token.rendering.tokenPropertyName = "access_token"
//disabling bearer tokens support for full response customisation
grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
//Validation endpoint configuration options
grails.plugin.springsecurity.rest.token.validation.active = true
grails.plugin.springsecurity.rest.token.validation.endpointUrl = "/api/validate"
grails.plugin.springsecurity.rest.token.validation.headerName = "Authorization"


grails.plugin.springsecurity.filterChain.chainMap = [
        //Stateless chain
        [
                pattern: '/api/**',
                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
        ],

        //Traditional chain
        [
                pattern: '/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ]
]