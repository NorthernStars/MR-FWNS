#!/bin/bash

botid=0

function clean_up(){
	echo "killing process id $botid" 
	kill -s 9 $!
	exit 0
}

trap clean_up SIGHUP SIGINT SIGTERM

eval "$* &"

while :
do
	botid=$!
done