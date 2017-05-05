#!/bin/bash

timeOut="timeout $1";
runKey="$2";
runTest="$3";
shift 3;

count=0;
#For each argument in the @Args
#	at the bottom of the Answer Key File 
for argSet in "$@"; do	
	(
		res=`$timeOut $runTest $argSet` ;
		#Code returned after executing last command
		#	0 usually means no problems
		#	? anything else problem
		code=$?;
		if [ $code -ne 0 ]; then
			echo "ERROR CODE ($code): Running user code produced an invalid result with arg $argSet" ;
			exit 1;		
		else
			(echo "key $count: $($runKey $argSet)") &
			(echo "test $count: $($runTest $argSet)") &
		fi
		wait;
	) &
let "count += 1";
done;
wait;
