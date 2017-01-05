app.controller("quizController", function($scope, $rootScope, $http, $location) {
	$rootScope.states = new Array();
	$scope.answers = new Array();
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.editors = new Array();
	$scope.protoTest = tstQuiz2;
	$scope.questions = $scope.protoTest.template.templateQuestion

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
	initSetup();

	$scope.collapseQuestion = function(index) {
		console.log("Entered collapse: glyph id=" + index);
		$scope.states[index].open = !$scope.states[index].open;
	};

	$scope.saveQuestion = function(index) {
		console.log("Entered save: glyph id=" + index);
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
			console.log("ndxOption: " + ndxOption + ", ndxQuestion: " + ndxQuestion);
			// Handles multiple choice questions
			$scope.answers[ndxQuestion] = ndxOption;
			var answer = $scope.questions[ndxQuestion].templateQuestion.multiChoice[ndxOption];
			var question = $scope.questions[ndxQuestion].templateQuestion;
			var foundAt = $scope.protoTest.options.indexOf(answer);
			// If it's not already in the system
			if (foundAt === -1) {
				// Make sure there's only one option per questions
				for (var i = 0; i < $scope.protoTest.options.length; i++) {
					if ($scope.protoTest.options[i].questionId === question.questionId){
						$scope.protoTest.options.splice(i, 1);
					}
				}
				$scope.protoTest.options.push(answer);
			}
		} else if ($scope.questions[ndxQuestion].templateQuestion.format.formatId === 1 ) {
			// Handles multiple select questions
			console.log("2) Select Option: " + ndxOption + " in Question: " + ndxQuestion);
			var foundAt = $scope.answers[ndxQuestion].indexOf(ndxOption)
			console.log("Found at: " + foundAt);
			var answer = $scope.questions[ndxQuestion].templateQuestion.multiChoice[ndxOption];
			if (foundAt === -1){
				$scope.answers[ndxQuestion].push(ndxOption);
				$scope.protoTest.options.push(answer);
			} else {
				console.log("Delete at: " + foundAt);
				$scope.answers[ndxQuestion].splice(foundAt, 1);
				$scope.protoTest.options.splice(foundAt, 1);
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

	// EDITORS	
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
	$scope.numPerPage = 5;
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
	
	// AJAX
	function getQuizQuestions() {
		$http({
			method: 'GET',
			url: '/Assessment/getAssessmentData',
			headers: {'Content-Type': 'application/json'}
		})
		.then(function(response) {
		    //First function handles success
		    console.log("Received Quiz Object= " + response.data);
		    $scope.protoTest = response.data;
		        
		}, function(response) {
		    //Second function handles error
		    console.log("ERROR: status code: " + response.status);
		});
	}
});