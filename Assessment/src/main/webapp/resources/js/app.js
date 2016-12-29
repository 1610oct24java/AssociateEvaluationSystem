var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable',
		'ngAnimate' ]);

app.controller("dragController", function($scope) {
	// DRAG AND DROP
	$scope.dragControlListeners = {
		accept : function(sourceItemHandleScope, destSortableScope) {
			return boolean
		},
		itemMoved : function(event) {
		},
		orderChanged : function(event) {
		}
	};

	$scope.dragControlListeners1 = {};
});

app.controller("quizController", function($scope, $http, $location) {
	$scope.quiz = tstQuiz;
	$scope.answers = new Array();
	$scope.states = new Array();
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.editors = new Array();

	var incrementEditorIfNeeded = function(i) {
		if ($scope.quiz.questions[i].type === 3) {
			$scope.numEditors++;
		};
	};
	var makeState = function() {
		var temp = {
			flagged: false,
			saved: false,
			open: false
		};
		$scope.states.push(temp);
	};
	var makeAnswers = function(ndx) {
		if($scope.quiz.questions[ndx].type === 0) {
			$scope.answers.push(-1);
		} else if ($scope.quiz.questions[ndx].type === 1 ) {
			$scope.answers.push([-1]);
		} else {
			$scope.answers.push(-1);
		};
	}
	var initSetup = function() {
		for (var i = 0; i < $scope.quiz.questions.length; i++) {
			incrementEditorIfNeeded(i);
			makeState();
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
		if($scope.quiz.questions[ndxQuestion].type === 0) {
			console.log("1) Select Option: " + ndxOption + " in Question: " + ndxQuestion);
			$scope.answers[ndxQuestion] = ndxOption;
		} else if ($scope.quiz.questions[ndxQuestion].type === 1 ) {
			console.log("2) Select Option: " + ndxOption + " in Question: " + ndxQuestion);
			var foundAt = $scope.answers[ndxQuestion].indexOf(ndxOption)
			if (foundAt === -1){
				$scope.answers[ndxQuestion].push(ndxOption);
			} else {
				$scope.answers[ndxQuestion].splice(foundAt, 1);
			}
		};
	}
	
	$scope.checkChecked = function (ndxOption, ndxQuestion) {
		var output = false;
		
		if($scope.quiz.questions[ndxQuestion].type === 0) {
			if ($scope.answers[ndxQuestion] === ndxOption) {
				output = true;
			}
		} else if ($scope.quiz.questions[ndxQuestion].type === 1 ) {
			if ($scope.answers[ndxQuestion].indexOf(ndxOption) != -1){
				output = true;
			}
		};
		
		return output;
	}

	// EDITORS
	$scope.checkNeedEditor2 = function(questionIndex) {
		// console.log("Checking if q " + questionIndex + " needs an editor.");
		if ($scope.editors.length < $scope.numEditors) {
			var currQ = $scope.quiz.questions[questionIndex];

			if (currQ.type === 3) {
				// If this question is a coding question
				// Create an editor for it
				var newEditor = ace.edit("editor" + questionIndex);
				newEditor.setTheme("ace/theme/monokai");
				newEditor.getSession().setMode("ace/mode/" + "java");
				$scope.editors.push(newEditor);
			}
		}
	};
	
	$scope.checkNeedEditor = function(questionIndex) {
		var currQ = $scope.quiz.questions[questionIndex];
		if(currQ.type === 3) {
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

	/*
	 * var initEditors = function () { for (var i = 0; i <
	 * $scope.quiz.questions.length; i++) { console.log("Does " + i + " need an
	 * editor?"); checkNeedEditor(i); }; };
	 * 
	 * initEditors();
	 */

	// PAGINATION
	$scope.filteredQuestions = [];
	$scope.currentPage = 1;
	$scope.numPerPage = 5;
	$scope.maxSize = 5;

	$scope.$watch('currentPage + numPerPage', function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

		$scope.filteredQuestions = $scope.quiz.questions.slice(begin, end);
	});
});