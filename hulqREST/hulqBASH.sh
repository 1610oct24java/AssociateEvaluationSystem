#!/bin/bash

#hulqBASH: Hosted Universal Language Qualifier in BASH.
#This script was designed and developed by Michael Rod
#(Magic Mike) for use by Revature LLC. 
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

#FUNCTION TO GET FILE TYPES ==================================================================
#extract file extensions from input filenames
function get_file_types {
	keyLang=${1:`expr index "$1" .`}
	testLang=${2:`expr index "$2" .`}
}

#FUNCTION TO CONFIG FILE ==================================================================
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
		
		echo customOut: $customOut testCustomOut: $testCustomOut outFile: $outFile testRunnable: $testRunnable
}	

#COMPILE KEY FILE ==================================================================
#compile key file
function compile_key {
	if [[ $keyCustomOut == "y" ]]; then
		$keyCompiler $keyRunnable $keyFileName;
	else
		$keyCompiler $keyFileName;	
	fi;		
}

#COMPILE TEST FILE ==================================================================
#compile test file
function compile_test {
	if [[ $testCustomOut == "y" ]]; then
		$testCompiler $testRunnable $testFileName;
	else
		$testCompiler $testFileName;
	fi;
}

#REMOVE DOWNLOADED AND COMPILE FILE ==================================================================
#remove downloaded and compiled file
function remover {
	#remove runnable files... FIX LATER if you want
	if [[ $keyExecutor == "java " ]]; then 
		rm "$keyRunnable.class";
		if [[ $testExecutor == "java " ]]; then
			rm "$testRunnable.class";
		else
			rm "$testRunnable";
		fi;
	else 
		rm "$testRunnable";
		rm "$keyRunnable";
	fi
	
	#if runnable file was distinct from downloaded source(key)
	if [[ -e $keyFileName ]]; then
		rm "$keyFileName";
	fi

	#if runnable file was distinct from downloaded source(test)
	if [[ -e $testFileName ]]; then
		rm "$testFileName";
	fi
	
}
#=============================== END FUNCTIONS =============================================
#===========================================================================================

#set input names to variables
timeOutLimit=$1
keyFileName=$2
testFileName=$3

#shift command variables by 3
# 	<file1><file2> timeOutLimit arg1 arg2 
# 		=
#	arg1 arg2
shift 3;

#Returns true if file extensions types match and are valid types  
testVar="$(bash hulqConfig/checkLanguages.sh $keyFileName $testFileName)";

#if the file types are invalid, exit
if [[ "$testVar" != "true" ]]; then
	echo "ERROR(f): invalid file types";
	exit;
fi;

#get the file extensions
get_file_types $keyFileName $testFileName

#set config variables
read_config_file "hulqConfig/$keyLang.hulq" "hulqConfig/$testLang.hulq" 

#======================== COMPILATION ==========================================
#if key needs to be compiled
if [[ $keyType == "compiled" ]]; then
	compile_key;
fi;

#if test needs to be compiled
if [[ $testType == "compiled" ]]; then
	compile_test;
fi;
#======================== END COMPILATION ==========================================

#======================== COMPILATION RESULT ===============================
# User Input -------------------------
#if test is a java program
if [[ $testExecutor == "java " ]]; then
	#if test failed to compile
	if [[ ! -e $testRunnable.class ]]; then 
		echo "ERROR(c:t): test failed to compile or does not exist";
		exit; 
	fi 
else
	#if test failed to compile
	if [[ ! -e $testRunnable ]]; then 
		echo "ERROR(c:t): test failed to compile or does not exist";
		exit; 
	fi 
fi

# Answer Key -------------------------
#if key is a java prgram
if [[ $keyExecutor == "java " ]]; then
	#if key failed to compile
	if [[ ! -e $keyRunnable.class ]]; then
		echo "ERROR(c:k): key failed to compile or does not exist";
		exit; 
	fi
else
	#if key failed to compile
	if [[ ! -e $keyRunnable ]]; then
		echo "ERROR(c:k): key failed to compile or does not exist";
		exit; 
	fi
fi
#======================== END COMPILATION RESULT ===============================
#Form execution command
# i.e java <class>
# 	python <file>
runTest=$testExecutor$testRunnable;
runKey=$keyExecutor$keyRunnable; 
count=0;
timeOut="timeout $timeOutLimit";
echo "timeout limit: $timeOutLimit" >> nohup.out;

#====================== START THE DOCKER CONTAINER ============================
#Copy files over --------------------------
addFile=$testRunnable;
addFile2=$keyRunnable;
if [[ $testExecutor == "java " ]]; then
	addFile="$addFile.class";
fi;

if [[ $keyExecutor == "java " ]]; then
	addFile2="$addFile2.class";
fi;

#Add files to Dockerfile -------------------
`echo "COPY $addFile ." >> Dockerfile`;
`echo "COPY $addFile2 ." >> Dockerfile`;

argString="";
for argSet in "$@"; do
	argString="$argString \"$argSet\"";
done;

#Add command to run to Dockerfile -----------
#	This case run our script with files and arguments
`echo "CMD ./TheHulq.sh $timeOutLimit \"$runKey\" \"$runTest\" $argString" >> Dockerfile`;

#Build the container from the Dockerfile
#	Redirects everything to linux's Black Hole 
docker build -q -t marco_test . > /dev/null;

#Run the container
docker run -t  marco_test;

#====================== END START THE DOCKER CONTAINER ============================

#Remove files from our ec2
remover;

#========================================================================================================
#	REMOVE THE FILES AFTER THEY HAVE BEEN ADDED TO THE DOCKER FILE
sed -i '/FILES SHOULD GO HERE/q' Dockerfile;

#Remove any containers that have been exited 
docker rm $(docker ps -a -q) > /dev/null;

#Remove any images that have no group 
res=$(docker images | grep "<none>" | awk {'print $3'});
if [ ! -z $res ]; then
	docker rmi $(docker images | grep "<none>" | awk {'print $3'}) > /dev/null;
fi;

