#!/bin/sh

# Add enviroment to MyFeeLab
MYFREELAB_LIB="/usr/lib/myfreelab"
MYFREELAB_JAR="/opt/myfreelab/MyFreeLab-v0.9.3Alpha.jar"
PATH=$PATH:$MYFREELAB_LIB/bin

# Run MyFreeLab Jar
$MYFREELAB_LIB/bin/java -Xrs -jar $MYFREELAB_JAR
