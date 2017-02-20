#!/bin/bash

#hulqBASH: Hosted Universal Language Qualifier in BASH.
#This script was designed and developed by Michael Rod
#for use by Revature LLC. 
#######################################################
#This is the core logic for the utility. It manages 
#language determination, compilation,and execution, 
#and returns the output to the Java logic
#######################################################
#DO NOT EDIT THIS FILE
#To add new file type handling: 
#### add the file extension to fileTypes.hulq
#### then add a file to /hulqConfig named 
#### [extension].hulq (without [])
#### the contents of these files are defined in the 
#### readme located in /hulqConfig.
#######################################################

#extract file extensions from input filenames
function get_file_types {
	keyLang=${1:`expr index "$1" .`}
	testLang=${2:`expr index "$2" .`}
}

#get configuration from config files
function read_config_file {
	# get key config
	inFileName=$keyFileName;
	source $1;
		keyType=$type;
		keyCustomOut=$customOut;
		keyCompiler=$compiler;
		keyExecutor=$executor;
		if [[ $keyCustomOut == "y" ]]; then
			keyRunnable="key.out";
		else
			keyRunnable=$outFile;
		fi;

	# get test config
	inFileName=$testFileName;
	source $2;
		testType=$type;
		testCustomOut=$customOut;
		testCompiler=$compiler;
		testExecutor=$executor;
		if [[ $testCustomOut == "y" ]]; then
			testRunnable="test.out";
		else
			testRunnable=$outFile;
		fi;
}	

#compile key file
function compile_key {
	if [[ $keyCustomOut == "y" ]]; then
		$keyCompiler $keyRunnable $keyFileName;
	else
		$keyCompiler $keyFileName;	
	fi;		
}

#compile test file
function compile_test {
	if [[ $testCustomOut == "y" ]]; then
		$testCompiler $testRunnable $testFileName;
	else
		$testCompiler $testFileName;
	fi;
}

#remove downloaded and compiled file
function remover {
	#remove runnable files
	rm "$testRunnable";
	rm "$keyRunnable";	
	
	#if runnable file was distinct from downloaded source(key)
	if [[ -e $keyFileName ]]; then
		rm "$keyFileName";
	fi

	#if runnable file was distinct from downloaded source(test)
	if [[ -e $testFileName ]]; then
		rm "$testFileName";
	fi
	
}

#set input names to variables
keyFileName=$1
testFileName=$2

#shift command variables by 2
shift 2;

#if the file types are invalid, exit
if [[ "$(bash hulqConfig/checkLanguages.sh $keyFileName $testFileName)" != "true" ]]; then
	echo "ERROR(f): invalid file types";
	exit;
fi;

#get the file extensions
get_file_types $keyFileName $testFileName

#set config variables
read_config_file "hulqConfig/$keyLang.hulq" "hulqConfig/$testLang.hulq" 

#if key needs to be compiled
if [[ $keyType == "compiled" ]]; then
	compile_key;
fi;

#if test needs to be compiled
if [[ $testType == "compiled" ]]; then
	compile_test;
fi;

#if test failed to compile
if [[ ! -e $testRunnable ]]; then 
	echo "ERROR(c:t): test failed to compile or does not exist";
	exit; 
fi 

#if key failed to compile
if [[ ! -e $keyRunnable ]]; then
	echo "ERROR(c:k): key failed to compile or does not exist";
	exit; 
fi

runTest=$testExecutor$testRunnable;
runKey=$keyExecutor$keyRunnable; 
count=0;
for argSet in "$@"; do	
	(
		
		#echo the result (goes out to the service calling this)
		(echo "key $count: $($runKey $argSet)") &
		(echo "test $count: $($runTest $argSet)") &
		wait;
		
	) &
let "count += 1";
done;
wait;
remover;
