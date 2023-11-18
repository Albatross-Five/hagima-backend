#!/bin/bash
export JAVA_HOME=/root/.sdkman/candidates/java/current
source /root/.bashrc
set -a
source /root/hagima-env
set +a
cd /root/hagima-backend
git pull
/root/.sdkman/candidates/gradle/current/bin/gradle clean build
sudo systemctl restart hagima