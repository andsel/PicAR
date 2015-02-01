#!/bin/sh

grails -Dgrails.env=standalone build-standalone --jetty
rm -Rf picar
mkdir picar
cp target/standalone*.jar picar
cp launch.bat picar/
