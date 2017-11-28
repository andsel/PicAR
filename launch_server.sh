#! /bin/sh
java -DSTOP.PORT=8090 -DSTOP.KEY=stop_jetty -Dworkspace.dir=./workspace  -jar start.jar