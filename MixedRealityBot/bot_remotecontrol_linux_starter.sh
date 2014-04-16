#!/bin/bash

function clean_up(){
	kill -s 9 $! > log
	exit 0
}

trap clean_up SIGHUP SIGINT SIGTERM

eval "$* &"

while :
do
	echo "" > /dev/null
done
echo "end"