#!/bin/bash
botPID=0

function clean_up(){
	if [$botPID -gt 0 ]; then
		kill -s 9 $botPID
		exit 0
	fi
}

trap clean_up SIGHUP SIGINT SIGTERM

eval "$* &"

i=1
while [ $i -gt 0 ];