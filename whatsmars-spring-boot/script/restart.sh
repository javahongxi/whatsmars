#!/usr/bin/env bash

echo "restart whatsmars-spring-boot ..."
cd /data/toutiao
./stop.sh
./start.sh
echo "done."