#!/bin/bash

RED='\033[0;31m'
NC='\033[0m' # No Color
GREEN='\033[0;32m'
YELLOW='\033[1;33m'


nohup java -jar mohitconbackend/target/mohitconbackend-0.0.1-SNAPSHOT.jar > app.log 2>&1 </dev/null &

MSG='Starting Spring boot application on 8080'
APP=''
DT='.'

for (( i=5; i>0; i--)); do
  sleep 1
  APP=${APP}${DT}
  echo -ne "${GREEN}${MSG}${YELLOW}${APP}\r"
done

cd /home/ec2-user/projects/angularbootstrepdemo/dist/angularbootstrapdemo/

nohup angular-http-server -p 4200 > app.log 2>&1 </dev/null &

echo ""
MSG='Starting Angular application on 4200'
APP=''
DT='.'

for (( i=5; i>0; i--)); do
  sleep 1
  APP=${APP}${DT}
  echo -ne "${GREEN}${MSG}${YELLOW}${APP}\r"
done
echo -e ${NC}