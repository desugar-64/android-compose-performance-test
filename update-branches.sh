#!/bin/sh

git checkout main

while read line;
do
    branchName="$line"
    git checkout "${branchName}" >> log
    git branch
    sleep 1
    git merge --no-ff main
    sleep 1
done < compose-branches.txt

git checkout main
git status

echo "✅ all the branches are up to date with main!"
