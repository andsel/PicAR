grails.servlet.version = "2.5"
grails.project.work.dir = "target"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.source.level = 1.6
grails.project.target.level = 1.6

grails.project.dependency.resolution = {

    inherits "global"
    log "error"
    checksums true

    repositories {
        inherits true
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        compile "org.jadira.usertype:usertype.jodatime:1.9"
        compile "com.itextpdf:itextpdf:5.1.3"
        runtime 'mysql:mysql-connector-java:5.1.20'
    }

    plugins {
        compile ":hibernate:$grailsVersion"
        compile ":joda-time:1.3.1"
        compile ":jquery:1.7.1"
        compile ":resources:1.1.6"
        compile ":famfamfam:1.0.1"
        compile ":jquery-ui:1.8.15"
        compile ":mail:1.0"
        compile ":spring-security-core:1.2.7.2"
        compile ":spring-security-ui:0.2"
        compile ":webxml:1.4.1"
        compile ":searchable:0.6.4"
        compile ":standalone:1.1.1"
        compile ":database-migration:1.3.2"

        runtime ":twitter-bootstrap:2.1.1"
        runtime ":fields:1.1"
        runtime ":cache-headers:1.1.5"
        runtime ":cached-resources:1.0"
        runtime ":zipped-resources:1.0"

        build ":tomcat:$grailsVersion"
    }
}

