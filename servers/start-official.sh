#!/bin/bash

cd official || exit
java -Xmx1024M -Xms1024M -jar ./minecraft_server.1.8.8.jar nogui
