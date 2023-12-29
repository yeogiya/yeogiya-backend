#!/usr/bin/env bash

PROJECT_ROOT="/home/ec2-user/app/yeogiya"
JAR_FILE="$PROJECT_ROOT/yeogiya-web/build/libs/yeogiya-web-0.0.1-SNAPSHOT.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/yeogiya-web/build/libs/*.jar /home/ec2-user/deploy

source /home/ec2-user/deploy/env/yeogiya.env
echo $JASYPT_PASSWORD

# 실행 권한 추가
echo "> $JAR_FILE 에 실행 권한 추가"
chmod +x $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar \
          -Dspring.profiles.active=dev \
          -Djasypt.encryptor.password=$JASYPT_PASSWORD \
          $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG