#!/usr/bin/env bash

PROJECT_ROOT="/home/ec2-user/app"
JAR_FILE="$PROJECT_ROOT/app.jar"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

echo "$TIME_NOW > stop.sh 스크립트 실행" >> $DEPLOY_LOG

CURRENT_PID=$(pgrep -f "$JAR_FILE")

if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 현재 실행 중인 애플리케이션 없음" >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 실행 중인 애플리케이션 종료 시도 (PID: $CURRENT_PID)" >> $DEPLOY_LOG

  kill -15 "$CURRENT_PID"
  sleep 5

  CURRENT_PID=$(pgrep -f "$JAR_FILE")
  if [ -n "$CURRENT_PID" ]; then
    echo "$TIME_NOW > 정상 종료 실패 → 강제 종료 시도" >> $DEPLOY_LOG
    kill -9 "$CURRENT_PID"
    echo "$TIME_NOW > 애플리케이션 강제 종료 완료" >> $DEPLOY_LOG
  else
    echo "$TIME_NOW > 애플리케이션 정상 종료 완료" >> $DEPLOY_LOG
  fi
fi
