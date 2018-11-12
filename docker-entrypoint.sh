#!/bin/bash

set -eo pipefail

JAVA_OPTS=${JAVA_OPTS:"-XX:MaxPermSize=256M -Xmx1024M"}

exec java ${JAVA_OPTS} -Dlight-4j-config-dir=/config/${env} -jar taiji-console-fat-1.0.0.jar "$@"
