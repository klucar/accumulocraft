#!/bin/bash


cd spongevanilla || exit
export CLASSPATH=$CLASSPATH:/Users/klucar/code/accumulocraft/target/accumulocraft-1.0-SNAPSHOT.jar
java -Xmx2048M -Xms512M -jar ./spongevanilla-1.8-3.1.0-BETA-155.jar nogui
