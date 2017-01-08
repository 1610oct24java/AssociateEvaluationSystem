app.controller("quizController", function($scope, $rootScope, $http) {
	$rootScope.states = {};
	$scope.answers = {};
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.editors = {};
	$rootScope.protoTest;
	$scope.questions = [];
	$scope.snippetSubmissions = [];
	$scope.protoTest2 = {};
	getQuizQuestions();

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
		if ($scope.questions[ndx].templateQuestion.format.formatId === 1 ) {
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
		
		if(q.templateQuestion.format.formatId === 2) {
			for (var i = 0; i < q.templateQuestion.dragDrops.length; i++) {
				var assessmentDragDrop = {
						assessmentDragDropId : ((q.templateQuestionId) * 100 + i),
						userOrder : i+1,
						assessmentId : $scope.protoTest.assessmentId,
						dragDrop : q.templateQuestion.dragDrops[i]
				};
				$rootScope.protoTest.assessmentDragDrop.push(assessmentDragDrop);
			}
		}
		
		$scope.states[index].saved = !$scope.states[index].saved;
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
		
		if($scope.questions[ndxQuestion].templateQuestion.format.formatId === 0) {
			// Handles multiple choice questions
			$scope.answers[ndxQuestion] = ndxOption;
			var answer = $scope.questions[ndxQuestion].templateQuestion.multiChoice[ndxOption];
			var question = $scope.questions[ndxQuestion].templateQuestion;
			var foundAt = $rootScope.protoTest.options.indexOf(answer);
			// If it's not already in the system
			if (foundAt === -1) {
				// Make sure there's only one option per questions
				for (var i = 0; i < $rootScope.protoTest.options.length; i++) {
					if ($rootScope.protoTest.options[i].questionId === question.questionId){
						$rootScope.protoTest.options.splice(foundAt, 1);
					}
				}
				$rootScope.protoTest.options.push(answer);
			}
		} else if ($scope.questions[ndxQuestion].templateQuestion.format.formatId === 1 ) {
			// Handles multiple select questions
			foundAt = $scope.answers[ndxQuestion].indexOf(ndxOption)
			answer = $scope.questions[ndxQuestion].templateQuestion.multiChoice[ndxOption];
			if (foundAt === -1){
				$scope.answers[ndxQuestion].push(ndxOption);
				$rootScope.protoTest.options.push(answer);
			} else {
				$scope.answers[ndxQuestion].splice(foundAt, 1);
				$rootScope.protoTest.options.splice(foundAt, 1);
			}
		}
	}
	
	$scope.checkChecked = function (ndxOption, ndxQuestion) {
		var output = false;
		
		if($scope.questions[ndxQuestion].templateQuestion.format.formatId === 0) {
			if ($scope.answers[ndxQuestion] === ndxOption) {
				output = true;
			}
		} else if ($scope.questions[ndxQuestion].templateQuestion.format.formatId === 1 && $scope.answers[ndxQuestion].indexOf(ndxOption) != -1) {
			output = true;
		}
		return output;
	}
	
	$scope.$watch('questions', function() {
	});
	
	// EDITORS	
	$scope.aceLoaded = function(_editor) {
	    var id = 0 + _editor.container.id;
	};
	
	$scope.getType = function(filetype) {
		var output = "";
		switch(filetype) {
		case "java" :
			console.log("Filetype: java");
			output = "java";
			break;
		case "cpp" :
			console.log("Filetype: cpp");
			output = "c_cpp";
		case "cs" :
			console.log("Filetype: cs");
			output = "csharp";
		}
		return output;
	}
	
	$scope.aceChanged = function(e) {
		var id2 = e[1].container.id;
		var editor = e[1];
		var SnippetUpload = function(_code, _questionId){
			this.code = _code;
			this.questionId = _questionId;
		};
		
		var newSnippet = new SnippetUpload(editor.getValue(), id2.substr(6, id2.length));
		
		for (var i = 0; i < $scope.snippetSubmissions.length; i++){
			if ($scope.snippetSubmissions[i].questionId = newSnippet.questionId){
				$scope.snippetSubmissions.splice(i, 1);
			}
		}
		$scope.snippetSubmissions.push(newSnippet);
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
		$http({
			method: 'GET',
			url: QUIZ_REST_URL,
			headers: {'Content-Type': 'application/json'}
		})
		.then(function(response) {
		    //First function handles success
		    $rootScope.protoTest = response.data;
			$scope.questions = $rootScope.protoTest.myTemplate.templateQuestion;
		    initSetup();
		    $rootScope.initQuizNav();
		});
	}
	
	$scope.submitAssessment = function(){
		var answerData = {
				assessment : $rootScope.protoTest,
				snippetUpload : $scope.snippetSubmissions
		};
		postAssessment(answerData);
	}
	
	function postAssessment(answerData){
		$http({
			method: 'POST',
			url: QUIZ_SUBMIT_REST_URL,
			headers: {'Content-Type': 'application/json'},
			data: answerData
		});
	}
});