#!/bin/sh

cd /home/sunhanwu/cvehub/
chmod +x ./target/cvehub-0.0.1-SNAPSHOT.jar
setsid nohup java -jar ./target/cvehub-0.0.1-SNAPSHOT.jar &
