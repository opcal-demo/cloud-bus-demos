#!/bin/bash

## this script depends on xmlstarlet
set -e

SCRIPT=`readlink -f "${BASH_SOURCE:-$0}"`
SCRIPT_DIR_PATH=`dirname ${SCRIPT}`
CI_DIR_PATH=`dirname ${SCRIPT_DIR_PATH}`
ROOT_PATH=`dirname ${CI_DIR_PATH}`

DEPENDENCIES_FILE=${ROOT_PATH}/ci/maven/dependencies.properties

VERSION=$(props value ${DEPENDENCIES_FILE} opcal-cloud-starter-parent.version)

echo "opcal cloud version is [${VERSION}]"
# update opcal-cloud-starter-parent version
xmlstarlet edit -P -L -O -u "/_:project/_:parent/_:version" -v ${VERSION} ${ROOT_PATH}/pom.xml
