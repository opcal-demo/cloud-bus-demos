#!/bin/bash

## install props
## curl -sL https://raw.githubusercontent.com/gissily/properties-tools/main/install.sh | sudo bash

set -e

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
SCRIPT_DIR_PATH=`dirname ${SCRIPT}`
CI_DIR_PATH=`dirname ${SCRIPT_DIR_PATH}`
ROOT_PATH=`dirname ${CI_DIR_PATH}`

DEFAULT_FILE=${ROOT_PATH}/ci/maven/dependencies.properties

PROPERTIES_FILE=${1:-${DEFAULT_FILE}}

KEYS=($(props keys ${PROPERTIES_FILE}))

for i in "${!KEYS[@]}"
do
  key=${KEYS[${i}]}
  value=$(props value ${PROPERTIES_FILE} ${key})
  echo "key: ${key} - value: ${value}"
  ${ROOT_PATH}/mvnw versions:set-property -Dproperty=${key} -DnewVersion=${value} >> /dev/null 2>&1
done
