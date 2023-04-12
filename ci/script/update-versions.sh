#!/bin/bash

## install versions
## curl -sL https://raw.githubusercontent.com/gissily/versions-tools/main/install.sh | sudo bash

set -e

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
SCRIPT_DIR_PATH=`dirname ${SCRIPT}`
CI_DIR_PATH=`dirname ${SCRIPT_DIR_PATH}`
ROOT_PATH=`dirname ${CI_DIR_PATH}`

UPDATE_FLAG=/tmp/versionUpdate
PARENT_FLAG=/tmp/parentUpdate

if [ -f "${UPDATE_FLAG}" ];then

  echo "update versions"
  ${SCRIPT_DIR_PATH}/dependency-version.sh

  if [ -f "${PARENT_FLAG}" ];then
    echo "update parent"
    ${SCRIPT_DIR_PATH}/opcal-cloud-version.sh
  fi
  ${SCRIPT_DIR_PATH}/clean-pom.sh

  message=$(cat ${UPDATE_FLAG})
  git add .
  git commit -m "${message}"
  git push

else
  echo "no updates"
fi

