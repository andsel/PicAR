To launch stasndalone
java -Dworkspace.dir=./target -Dgrails.env=standalone -jar target/standalone-0.1.jar 

To build the standalone version
grails -Dgrails.env=standalone build-standalone --jetty
