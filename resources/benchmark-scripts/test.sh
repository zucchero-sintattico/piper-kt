#!/bin/bash

# first argument: ssh address where exec kubectl commands
# second argument: number of tests
# third argument: server under test
# 4th...N: numbers of replicas to test

for replica in "${@:4}"
do
    echo "Test with $replica replicas"
    echo "Test with $replica replicas" >> results.txt
    ssh $1 "kubectl scale deployment --replicas $replica users-service"
    sleep 180

    for ((i=0; i<$2; i++))
    do
        echo "Test $i"
        echo "Test $i" >> results.txt
        wrk -t10 -c150 -d60s $3 -s ./post.lua >> results.txt
        sleep 30
    done
    echo "" >> results.txt
done
