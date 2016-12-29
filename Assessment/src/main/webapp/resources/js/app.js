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

	$scope.dragControlListeners1 = {
	};
});

app.controller("quizController", function($scope, $http, $location) {
	$scope.quiz = tstQuiz;
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.states = new Array();
	$scope.editors = new Array();

	var calcNumEditors = function() {
		for (var i = 0; i < $scope.quiz.questions.length; i++) {
			if ($scope.quiz.questions[i].type === 3) {
				$scope.numEditors++;
			}
			;
		}
		;
	};

	calcNumEditors();

	// Initialize states
	for (var i = 0; i < $scope.quiz.questions.length; i++) {
		var temp = {
			flagged : false,
			saved : false,
			open : false
		};
		$scope.states.push(temp);
	}
	;

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

	// EDITORS
	$scope.checkNeedEditor = function(questionIndex) {
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
			;
		}
		;
	};

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
	$scope.numPerPage = 3;
	$scope.maxSize = 5;
	
	$scope.$watch('currentPage + numPerPage', function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

		$scope.filteredQuestions = $scope.quiz.questions.slice(begin, end);
	});
});