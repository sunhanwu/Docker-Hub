#!/bin/sh

cd /home/sunhanwu/cvehub/
chmod +x cvehub-0.0.1-SNAPSHOT.jar
setsid nohup java -jar cvehub-0.0.1-SNAPSHOT.jar &