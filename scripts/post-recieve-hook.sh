#!/bin/bash
while read oldrev newrev refname
do
    branch=$(git rev-parse --symbolic --abbrev-ref $refname)
    if [ "main" = "$branch" ]; then
        # Do something
    fi
done