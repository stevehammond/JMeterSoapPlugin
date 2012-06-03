#!/bin/bash

JMETER='./apache-jmeter-2.7/bin/jmeter.sh'
SUFFIX=`date '+%y%m%d%H'`
HOST=localhost
PORT=8080
#DURATION=36000

# start jmeter in GUI mode
$JMETER -ttestplan/plugin-calc-service.jmx -l_jmeter/results.txt.$SUFFIX \
    -j_jmeter/jmeter.log.$SUFFIX -Jduration=$DURATION \
    -Jhost=$HOST -Jport=$PORT

