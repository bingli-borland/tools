#!/bin/sh
TEST_HOME=/home/bingli/testhttp
count 50
while [ "$count" != 0 ]; do
    java -jar test.jar com.bingli.performance.TestHttp http://localhost:8080/WebContainerTest/ /home/bingli/testhttp/error$count.log 1>/dev/null 2>&1 &
    let count--
done