// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = 'org.picar' // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

workspace.dir = System.getProperty('workspace.dir')
println "Booting with workspace dir ${workspace.dir}"

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
		grails.resources.debug = true
    }
    production {
        grails.logging.jul.usebridge = false
        grails.dbconsole.enabled = true
        grails.dbconsole.urlRoot = '/admin/dbconsole'

        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
    }

    standalone {
        grails.logging.jul.usebridge = false
        grails.dbconsole.enabled = true
        grails.dbconsole.urlRoot = '/admin/dbconsole'

        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
    }
}

// log4j configuration
log4j = {
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%c{2} %m%n')

        environments {
            development {
                rollingFile name: "picar", maxFileSize: 1024 * 1024, file: "picar.log"
                rollingFile name: "stacktrace", maxFileSize: 1024 * 1024, file: "picar-stacktrace.log"
            }

            standalone {
                rollingFile name: "picar", maxFileSize: 1024 * 1024, file: "${workspace.dir}/picar.log"
                rollingFile name: "stacktrace", maxFileSize: 1024 * 1024, file: "${workspace.dir}/picar-stacktrace.log"
            }

            production {
                rollingFile name: "picar", maxFileSize: 1024 * 1024, file: "/opt/jetty-9/logs/picar.log"
                rollingFile name: "stacktrace", maxFileSize: 1024 * 1024, file: "/opt/jetty-9/logs/picar-stacktrace.log"
            }
        }
    }

    root {
        warn 'stdout'
        environments {
            production {
                warn "picar"
            }

            standalone {
                debug "picar"
            }
        }
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'

    debug 'grails.plugins.springsecurity.ui.SpringSecurityUiService'

    def picarLogging = ['grails.app.controllers.com.picar']
    debug stdout: picarLogging
//    debug 'org.springframework.security'
}

// Added by the Joda-Time plugin:
//grails.gorm.default.mapping = {
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateMidnight, class: org.joda.time.DateMidnight
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTime, class: org.joda.time.DateTime
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTimeZoneAsString, class: org.joda.time.DateTimeZone
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDurationAsString, class: org.joda.time.Duration
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentInstantAsMillisLong, class: org.joda.time.Instant
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentInterval, class: org.joda.time.Interval
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDate, class: org.joda.time.LocalDate
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime, class: org.joda.time.LocalDateTime
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalTime, class: org.joda.time.LocalTime
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentPeriodAsString, class: org.joda.time.Period
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentTimeOfDay, class: org.joda.time.TimeOfDay
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentYearMonthDay, class: org.joda.time.YearMonthDay
//	"user-type" type: org.jadira.usertype.dateandtime.joda.PersistentYears, class: org.joda.time.Years
//}
//
//jodatime.format.html5 = true

grails.plugins.twitterbootstrap.fixtaglib = true


// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'org.dna.picar.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'org.dna.picar.UserRole'
grails.plugins.springsecurity.authority.className = 'com.dna.picar.Role'

//Spring Security configuration
grails.plugins.springsecurity.auth.loginFormUrl = "/login/index"
//grails.plugins.springsecurity.successHandler.defaultTargetUrl = "/card/index"
grails.plugins.springsecurity.successHandler.defaultTargetUrl = "/login/route"
grails.plugins.springsecurity.ui.password.validationRegex = '^.*$'

grails.plugins.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugins.springsecurity.interceptUrlMap = [
        '/js/**':         ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/css/**':        ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/images/**':     ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/*':             ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/login/**':      ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/logout/**':     ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/search/index':    ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/card/show/**':    ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/card/retrieveImage/**':    ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/dbconsole/**':  ['IS_AUTHENTICATED_ANONYMOUSLY'], //only for development!!!
        '/card/reindex':   ['ROLE_ADMIN'],
        '/card/**':        ['ROLE_CATALOG'],
        '/user/list/*':   ['ROLE_ADMIN'],
        '/administration/*':   ['ROLE_ADMIN'],
        '/admin/dbconsole/**': ['ROLE_ADMIN'],
]


grails.resources.modules = {
    picar {
        resource url:'/css/picar.css', disposition: 'head'
    }
}
