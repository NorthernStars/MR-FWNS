#!/bin/bash

botid=0

function clean_up(){
	kill -s 9 $botid
	echo "killing process id $botid"
	exit 0
}

trap clean_up SIGHUP SIGINT SIGTERM

eval "$* &"

while :
do
	botid=$!
done
