#!/bin/bash

function remover {
	##### HANDLE C++ FILES
	if [[ "$keyFileName" == *.cpp || "$keyFileName" == *.c ]]; then 
		#remove executables 
		rm "$executableKey";
		rm "$executableTest";
	
	##### HANDLE C# FILES
	elif [[ "$keyFileName" == *.cs ]]; then 
	#remove executables 
	rm "${keyFileName%$".cs"}.exe";
	rm "${testFileName%$".cs"}.exe";	

	##### HANDLE JAVA FILES
	elif [[ "$keyFileName" == *.java ]]; then 
		#remove executables 
		rm "${keyFileName%$".java"}.class";
		rm "${testFileName%$".java"}.class";	
	fi	

	#remove source files
	rm "$keyFileName";
	rm "$testFileName";
}

#assign file names from command line arguments
keyFileName=$1;
testFileName=$2;

# shift command line arguments <- 2, giving the first set of arguments 
# to be run the index of $1
shift 2;

# set evaluation status default to pass
evalStatus="pass";

##### HANDLE FILES WITH DIFFERENT TYPES
if [[ "${keyFileName##*.}" != "${testFileName##*.}" ]]; then
	evalStatus="incompatible file types";
	echo $evalStatus;
	remover;
	exit 0;
fi;

##### HANDLE C++ FILES
if [[ "$keyFileName" == *.cpp ]]; then 

	#set variables 
	codeCompiler="g++ -o";
	executableKey="$keyFileName.out";
	executableTest="$testFileName.out";
	runKey="./$keyFileName.out";
	runTest="./$testFileName.out";

##### HANDLE C FILES
elif [[ "$keyFileName" == *.c ]]; then 

	#set variables 
	codeCompiler="gcc -o";
	executableKey="$keyFileName.out";
	executableTest="$testFileName.out";
	runKey="./$executableKey";
	runTest="./$executableTest";

##### HANDLE C# FILES
elif [[ "$keyFileName" == *.cs ]]; then 

	#set variables 
	codeCompiler="mcs";
	executableKey="";
	executableTest="";
	runKey="mono ${keyFileName%$".cs"}.exe";
	runTest="mono ${testFileName%$".cs"}.exe";	

##### HANDLE JAVA FILES
elif [[ "$keyFileName" == *.java ]]; then 
	
	#set variables 
	codeCompiler="javac";
	executableKey="";
	executableTest="";
	runKey="java ${keyFileName%$".java"}";
	runTest="java ${testFileName%$".java"}";
		
##### HANDLE INVALID FILETYPES	
else
	evalStatus="unsupported file type";
	echo $evalStatus;
	remover;
	exit 0;
fi

##### TESTING LOGIC
	#compile files
	$codeCompiler $executableKey $keyFileName;	
	$codeCompiler $executableTest $testFileName;
	
	#execute files
	for argSet in "$@"
	do
		#store the output of the snippet to a variable
		keyResult="$($runKey $argSet)";
		testResult="$($runTest $argSet)";
		
		#if the outputs are different set status to fail and exit
		if [[ $keyResult != $testResult ]]; then
			evalStatus="fail";
			break;
		fi
	done;
echo $evalStatus
remover;
exit 0;
