#!/usr/bin/env bash

app_name="whatsmars-spring-boot"

process_no=`ps -ef|grep java|grep ${app_name} | awk '{print $2}'`
if test -n "${process_no}"
then
  kill -SIGTERM ${process_no}
  sleep 2
fi

process_no_again=`ps -ef|grep java|grep ${app_name} | awk '{print $2}'`
if test -n "${process_no_again}"
then
  echo "Process ${process_no_again} is still running."
  kill -9 ${process_no_again}
  echo "Process ${process_no_again} has been killed.(-9)"
else
  echo "Process ${process_no} has been killed."
fi
