#! /bin/bash

# the test of diff
for i in * ;do
	if [ -f "../gccp-master/src/gc/$i" ];
		then
			echo "$i ..."
			diff $i "../gccp-master/src/gc/$i"
	fi
done
