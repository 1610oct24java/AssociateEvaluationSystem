app.controller("quizController", function($scope, $rootScope, $http, $location) {
	$rootScope.states = [];
	$scope.answers = [];
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.editors = [];
	$rootScope.protoTest;
	$scope.questions = [];
	$rootScope.snippetSubmissions = [];
	$scope.protoTest2 = {};
	getQuizQuestions();
	
	//console.log(window.location.href);

	var makeState = function(input) {
		var temp = {
			id: input,
			flagged: false,
			saved: false,
			open: true
		};
		$scope.states.push(temp);
	};
	var makeAnswers = function(ndx) {
		if ($scope.questions[ndx].question.format.formatName === "Multiple Choice" ) {
			$scope.answers.push([-1]);
		}
		else {
			$scope.answers.push(-1);
		}
	}
	var initSetup = function() {
		for (var i = 0; i < $scope.questions.length; i++) {
			makeState(i);
			makeAnswers(i);
		}
	};

	$scope.collapseQuestion = function(index) {
		$scope.states[index].open = !$scope.states[index].open;
	};
	
	var saveQuestion = function(index) {

		var q = $scope.questions[index];
		
		if(q.question.format.formatName === "Drag and Drop") {
			for (var i = 0; i < q.question.dragdrop.length; i++) {
				var assessmentDragDrop = {
						assessmentDragDropId : 0,//((q.questionId) * 100 + i),
						userOrder : i+1,
						assessmentId : $scope.protoTest.assessmentId,
						dragDrop : q.question.dragdrop[i]
				};
				$rootScope.protoTest.assessmentDragDrop.push(assessmentDragDrop);
			}
		}
		
		$scope.states[index].saved = true;
	};

	$scope.handleSaveClick = function(index) {
		//If question is not already saved, save it
		if (!$scope.states[index].saved) {
			saveQuestion(index);
		}
	}

	$scope.flagQuestion = function(index) {
		$scope.states[index].flagged = !$scope.states[index].flagged;
	};
	
	$scope.selectOption = function (ndxOption, ndxQuestion) {
		// Handles putting the answer into the javascript object when the uesr
		// selects an option
		var answer;
		
		if($scope.questions[ndxQuestion].question.format.formatName === "Multiple Choice") {
			// Handles multiple choice questions
			$scope.answers[ndxQuestion] = ndxOption;
			answer = $scope.questions[ndxQuestion].question.option[ndxOption]; // Store selected answer
			
			var questionOptionIdArray = []; // Get options from question
			
			// Gets options from current selecting question into an array.
			for (var z=0; z < $scope.questions[ndxQuestion].question.option.length; z++)
			{
				questionOptionIdArray.push($scope.questions[ndxQuestion].question.option[z].optionId);
			}
			
			// Checks the answer selection array for previous multiple choice selections and deletes
			// them from the array since we only want ONE radial selection kept in the answer array
			for (var z2=0; z2 < questionOptionIdArray.length; z2++)
			{
				for (var z3=0; z3 < $rootScope.protoTest.options.length; z3++)
				{
					if ($rootScope.protoTest.options[z3].optionId === questionOptionIdArray[z2])
					{
						// Remove previously selected multiple choices from same question
						$rootScope.protoTest.options.splice(z3,1);
					}
				}
			}
			
			// Finally, add current selected multiple choice option to the answers array
			$rootScope.protoTest.options.push(answer);
			
		}else if ($scope.questions[ndxQuestion].question.format.formatName === "Multiple Select" ) {
			// Handles multiple select questions
			answer = $scope.questions[ndxQuestion].question.option[ndxOption];
			var foundAt = $rootScope.protoTest.options.indexOf(answer);
			
			if (foundAt === -1) {
				// User has not selected this previously, add it
				$rootScope.protoTest.options.push(answer);
			} else {
				// User has selected this previously, delete it.
				$rootScope.protoTest.options.splice(foundAt, 1);
			}
		}
		saveQuestion(ndxQuestion);
	}
	
	$scope.checkChecked = function (ndxOption, ndxQuestion) {
		var output = false;
		
		if($scope.questions[ndxQuestion].question.format.formatName === "Multiple Choice") {
			// Handle multiple choice
			if ($scope.answers[ndxQuestion] === ndxOption) {
				output = true;
			}
		} else if ($scope.questions[ndxQuestion].question.format.formatName === "Multiple Select") {
			// Handle multiple select
			var answer = $scope.questions[ndxQuestion].question.option[ndxOption];
			var foundAt = $rootScope.protoTest.options.indexOf(answer);
			if (foundAt === -1){
				// This answer has not been previously selected
				output = false;
			} else {
				// This answer has been previously selected, should be marked
				output = true;
			}
		}
		return output;
	}
	
	// EDITORS	
	$scope.getType = function(filetype) {
		var output;
		switch(filetype) {
		case "java" :
			output = "java";
			break;
		case "cpp" :
        case "c++" :
        case "c" :
			output = "c_cpp";
			break;
		case "cs" :
			output = "csharp";
			break;
		default : 
			output = "java";
			break;
		}
		return output;
	}
	
	$scope.aceChanged = function(e) {
		var id2 = e[1].container.id;
		var editor = e[1];
		var SnippetUpload = function(_code, _questionId, _fileType){
			this.code = _code;
			this.questionId = _questionId;
			this.fileType = _fileType;
		};
		var snippetQuestionIndex;
		var q = {};
		for (var i = 0; i < $scope.questions.length; i++ ) {
			if ($scope.questions[i].question.questionId == id2.substr(6, id2.length)){
				q = $scope.questions[i];
				snippetQuestionIndex = i;
			}
		}
		
		var incFileType = q.question.snippetTemplates[0].fileType;
		var newSnippet = new SnippetUpload(editor.getValue(), +id2.substr(6, id2.length), incFileType);
		
		for (i = 0; i < $rootScope.snippetSubmissions.length; i++){
			if ($rootScope.snippetSubmissions[i].questionId = newSnippet.questionId){
				$rootScope.snippetSubmissions.splice(i, 1);
			}
		}
		$rootScope.snippetSubmissions.push(newSnippet);
		saveQuestion(snippetQuestionIndex);
	};

	// PAGINATION
 	$scope.filteredQuestions = [];
	$scope.currentPage = 1;
	$scope.numPerPage = 3;
	$scope.maxSize = 5;
	
	$scope.jumpPage = function (numPage) {
		$scope.currentPage = numPage;
	};

	$scope.$watch('currentPage + numPerPage', function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

		$scope.filteredQuestions = $scope.questions.slice(begin, end);
	});
	
	$scope.$watch('questions', function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

		$scope.filteredQuestions = $scope.questions.slice(begin, end);
	});
	
	// AJAX
	function getQuizQuestions() {
		
		//console.log(QUIZ_REST_URL + $location.search().asmt);
		$http({
			method: 'GET',
			url: QUIZ_REST_URL + $location.search().asmt,
			headers: {'Content-Type': 'application/json'}
		})
		.then(function(response) {
		    //First function handles success
		    $rootScope.protoTest = response.data;
			$scope.questions = $rootScope.protoTest.template.templateQuestion;
			$rootScope.protoTest.options = [];
		    initSetup();
		    $rootScope.initQuizNav();
		});
	}
	
	$scope.submitAssessment = function(){

		$rootScope.protoTest.assessmentDragDrop.forEach(function(entry){

			delete entry.assessmentId;
			entry.assessment = {"assessmentId" : $rootScope.protoTest.assessmentId,};
		});

		var answerData = {
				assessment : $rootScope.protoTest,
				snippetUploads : $rootScope.snippetSubmissions
		};

		$http({
			method: 'POST',
			url: "aes/rest/submitAssessment",
			headers: {'Content-Type': 'application/json'},
			data: answerData
		});
	}
	
});