#!/bin/sh

# Add enviroment to MyFreeLab
MYFREELAB_LIB="/usr/lib/myfreelab"
MYFREELAB_JAR="/opt/myfreelab/MyFreeLab.jar"
PATH=$PATH:$MYFREELAB_LIB/bin

# Run MyFreeLab Jar
$MYFREELAB_LIB/bin/java -Xrs -jar $MYFREELAB_JAR
