#!/bin/bash

cat $1 | while read -r linha; do
#for linha in $(cat $1); do
    tab=${linha:0:1}
    if [ -z "${tab##[0-9]*}" ] && [ -n "${tab}" ]; then
        count=0
        while [ ${count} -lt ${tab} ]; do
            echo -n "   "
            let count=count+1
        done
    fi
    echo ${linha}
done
