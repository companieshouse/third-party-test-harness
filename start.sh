#!/bin/bash
#
# Start script for third-party-test-harness
APP_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [[ -z "${MESOS_SLAVE_PID}" ]]; then
#removed global and private env for use only with app env
    source ~/.chs_env/third-party-test-harness/env

    PORT="${THIRD_PARTY_DUMMY_PORT}"
else
    PORT="$1"
    CONFIG_URL="$2"
    ENVIRONMENT="$3"
    APP_NAME="$4"

    source /etc/profile
#removed global and private env for use only with app env
    echo "Downloading environment from: ${CONFIG_URL}/${ENVIRONMENT}/${APP_NAME}"
    wget -O "${APP_DIR}/app_env" "${CONFIG_URL}/${ENVIRONMENT}/${APP_NAME}/env"
    source "${APP_DIR}/app_env"
fi

exec java ${JAVA_MEM_ARGS} -jar -Dserver.port="${PORT}" "${APP_DIR}/third-party-test-harness.jar"