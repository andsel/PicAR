# PicAR
Semplice web app in Grails per archiviare fotografie e loro schede descrittive

Per lanciarlo in modo standalone
java -Dworkspace.dir=./target -Dgrails.env=standalone -jar target/standalone-0.1.jar 

Per fare la build della versioe standalone
grails -Dgrails.env=standalone build-standalone --jetty

Per fare la build del .war file 
grails -Dgrails.env=standalone war

Usare versione grails 2.2.4 e JDK 1.7.x

Per creare un pacchetto autocontenuto, si parte da una distribuzione di Jetty e si sostituise root.war con picar-<version>.war rinominato in root.war




