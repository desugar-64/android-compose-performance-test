#!/bin/sh

git checkout main

while read line;
do
    branchName="$line"
    git checkout "${branchName}"
    sleep 1
    cd android-client
    echo "connect your phone and unlock the screen"
    echo "đ...benchmarking ${branchName} ..."
    echo "...this process will take a long time, you can have some tea đĩ"
    ./gradlew --no-daemon --quiet :clean < /dev/null >> log
    sleep 1
    ./gradlew --stop </dev/null
    sleep 1
    ./gradlew --no-daemon --quiet --no-build-cache :benchmark:go < /dev/null >> log
    sleep 1
    ./gradlew --stop </dev/null
    sleep 1
    cd ..
done < compose-branches.txt

git checkout main
git status

echo "â benchmark completed!"
echo "âšī¸ you can check results in android-client/benchmark/benchmark_results directory"
