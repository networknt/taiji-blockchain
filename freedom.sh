#!/bin/bash

set -ex

APPLICATION=$1
COMMAND=$2


showHelp() {
    echo " "
    echo "Error: $1 $2"
    echo " "
    echo "    testnet.sh [APPLICATION] [COMMAND]"
    echo " "
    echo "    where [APPLICATION] is the command line application your command should be applied. For example, wallet"
    echo "    where [COMMAND] is the action you want to perform with the application. For example, create or send"
    echo " "
    echo "    example: ./testnet.sh wallet create"
    echo "    example: ./testnet.sh wallet send"
    echo "    example: ./testnet.sh wallet update"
    echo "    example: ./testnet.sh wallet fromkey"
    echo " "
}

calljava() {
    echo "Running the command line with command $COMMAND"
    java -jar -Dlight-4j-config-dir=config/freedom taiji-console/build/libs/taiji-console-fat-1.0.0.jar $APPLICATION $COMMAND
}

if [ -z $COMMAND ]; then
    showHelp "[COMMAND] parameter is missing"
    exit
fi

if [ -z $APPLICATION ]; then
    showHelp "[APPLICATION] parameter is missing"
    exit
fi

calljava;
