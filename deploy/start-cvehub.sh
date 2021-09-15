#!/bin/sh

result=`ps -axu | grep -v "grep" | grep cvehub | wc -l`
if [ $result -ge 1 ]
then
  echo "there is a exist process"
  cvehub_pid=`ps -axu | grep -v "grep" | grep cvehub | awk '{ print $2 }'`
  kill -9 $cvehub_pid
  echo "kill -9 $cvehub_pid"
else
  echo "No running process"
fi

# start cvehub
cd /home/sunhanwu/cvehub/
chmod +x ./target/cvehub-0.0.1-SNAPSHOT.jar
rm ./nohup.out
nohup java -jar ./target/cvehub-0.0.1-SNAPSHOT.jar &
