#!/bin/bash
#
# Start script for third-party-test-harness

PORT=8080
exec java -jar -Dserver.port="${PORT}" "third-party-test-harness.jar"
