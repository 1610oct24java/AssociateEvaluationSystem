app.controller("quizController", function($scope, $rootScope, $http, $location) {
	$rootScope.states = new Array();
	$scope.answers = new Array();
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.editors = new Array();
	$rootScope.protoTest;
	$scope.questions = [];
	$scope.snippetSubmissions = [];
	// $scope.questions = $scope.protoTest.template.templateQuestion;
	$scope.protoTest2 = {};
	getQuizQuestions();

	var incrementEditorIfNeeded = function(i) {
		if ($scope.questions[i].templateQuestion.format.formatId === 3) {
			$scope.numEditors++;
		};
	};
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
		if($scope.questions[ndx].templateQuestion.format.formatId === 0) {
			$scope.answers.push(-1);
		} else if ($scope.questions[ndx].templateQuestion.format.formatId === 1 ) {
			$scope.answers.push([-1]);
		} else {
			$scope.answers.push(-1);
		};
	}
	var initSetup = function() {
		for (var i = 0; i < $scope.questions.length; i++) {
			incrementEditorIfNeeded(i);
			makeState(i);
			makeAnswers(i);
		};
	};
	// initSetup();

	$scope.collapseQuestion = function(index) {
		console.log("Entered collapse: glyph id=" + index);
		$scope.states[index].open = !$scope.states[index].open;
	};
	
	$scope.handleSaveClick = function(index) {
		//If question is not already saved, save it
		if (!$scope.states[index].saved) {
			saveQuestion(index);
		}
	}

	var saveQuestion = function(index) {
		var q = $scope.questions[index];
		console.log("Saving question " + q.templateQuestionId + ": " + q.templateQuestion.questionText);
		
		if(q.templateQuestion.format.formatId === 2) {
			console.log("Drag and drop");
			for (var i = 0; i < q.templateQuestion.dragDrops.length; i++) {
				var assessmentDragDrop = {
						assessmentDragDropId : ((q.templateQuestionId) * 100 + i),
						userOrder : i+1,
						assessmentId : $scope.protoTest.assessmentId,
						dragDrop : q.templateQuestion.dragDrops[i]
				};
				$rootScope.protoTest.assessmentDragDrop.push(assessmentDragDrop);
			};
		} else if (q.templateQuestion.format.formatId === 3) {
			console.log("Code snippets");
			
		}
		
		$scope.states[index].saved = !$scope.states[index].saved;
	};

	$scope.flagQuestion = function(index) {
		console.log("Entered flag: glyph id=" + index);
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
			console.log("2) Select Option: " + ndxOption + " in Question: " + ndxQuestion);
			var foundAt = $scope.answers[ndxQuestion].indexOf(ndxOption)
			var answer = $scope.questions[ndxQuestion].templateQuestion.multiChoice[ndxOption];
			if (foundAt === -1){
				$scope.answers[ndxQuestion].push(ndxOption);
				$rootScope.protoTest.options.push(answer);
			} else {
				$scope.answers[ndxQuestion].splice(foundAt, 1);
				$rootScope.protoTest.options.splice(foundAt, 1);
			}
		};
	}
	
	$scope.checkChecked = function (ndxOption, ndxQuestion) {
		var output = false;
		
		if($scope.questions[ndxQuestion].templateQuestion.format.formatId === 0) {
			if ($scope.answers[ndxQuestion] === ndxOption) {
				output = true;
			}
		} else if ($scope.questions[ndxQuestion].templateQuestion.format.formatId === 1 ) {
			if ($scope.answers[ndxQuestion].indexOf(ndxOption) != -1){
				output = true;
			}
		};
		return output;
	}
	
	$scope.$watch('questions', function() {
		console.log("Test watcher");
	});
	
	// EDITORS	
	$scope.aceLoaded = function(_editor) {
	    console.log("Loaded: ");
	    console.log(_editor);
	    var id = 0 + _editor.container.id;
	    console.log("Id2: " + id);
	    console.log("Value: " + _editor.container.id);
	};
	
	$scope.aceChanged = function(e) {
		var id2 = e[1].container.id;
		console.log("Edit ID: " + id2);
		var editor = e[1];
		var SnippetUpload = function(_code, _questionId){
			this.code = _code;
			this.questionId = _questionId;
		};
		
		newSnippet = new SnippetUpload(editor.getValue(), id2.substr(6, id2.length));
		console.log(newSnippet);
		
		for (var i = 0; i < $scope.snippetSubmissions.length; i++){
			if ($scope.snippetSubmissions[i].questionId = newSnippet.questionId){
				$scope.snippetSubmissions.splice(i, 1);
			}
		}
		$scope.snippetSubmissions.push(newSnippet);
	};
	
	$scope.checkNeedEditor = function(questionIndex) {
		var currQ = $scope.questions[questionIndex];
		if(currQ.templateQuestion.format.formatId === 3) {
			// If this question is a coding question
			var temp = ace.edit("editor" + questionIndex);
			temp.setTheme("ace/theme/monokai");
			temp.getSession().setMode("ace/mode/" + "java");
			
			var foundAt = $scope.editors.indexOf(temp);
			if (foundAt != -1) {
				$scope.editors[foundAt] = temp;
			} else {
				$scope.editors.push(temp);
			}
		}
	}

	// PAGINATION
 	$scope.filteredQuestions = [];
	$scope.currentPage = 1;
	$scope.numPerPage = 3;
	$scope.maxSize = 5;
	
	$scope.jumpPage = function (numPage) {
		console.log("Jump to Page: " + numPage);
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
		console.log("Tryna get dat Ass...essment");
		$http({
			method: 'GET',
			url: QUIZ_REST_URL,
			headers: {'Content-Type': 'application/json'}
		})
		.then(function(response) {
		    //First function handles success
		    console.log("Received Quiz Object= " + response.data);
		    $rootScope.protoTest = response.data;
			$scope.questions = $rootScope.protoTest.myTemplate.templateQuestion;
		    initSetup();
		    $rootScope.initQuizNav();
		}, function(response) {
		    //Second function handles error
			console.log("ERROR: broken data: " + response.data);
		    console.log("ERROR: status code: " + response.status);
		});
	};
	
	$scope.submitAssessment = function(){
		answerData = {
				assessment : $rootScope.protoTest,
				snippetUpload : $scope.snippetSubmissions
		};
		//answerData = $rootScope.protoTest;
		console.log(answerData);
		postAssessment(answerData);
	}
	
	function postAssessment(answerData){
		$http({
			method: 'POST',
			url: QUIZ_SUBMIT_REST_URL,
			headers: {'Content-Type': 'application/json'},
			data: answerData
		})
		.then(function(response) {
			//First function handles success
			console.log("Answers sent: " + response.data);
		}, function(response) {
			//Second function handles error
			console.log("status code: " + response.status);
		});
	}
});