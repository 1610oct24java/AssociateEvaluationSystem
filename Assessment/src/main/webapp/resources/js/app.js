var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable', 'ngAnimate' ]);

app.controller("quizController", function($scope, $http, $location) {
	$scope.quiz = tstQuiz;
	$scope.oneAtATime = false;
	
	$scope.editors = new Array();
	
	/*$scope.editor = ace.edit("editor1");
	$scope.editor.setTheme("ace/theme/monokai");
	$scope.editor.getSession().setMode("ace/mode/java");*/

/*	var editor2 = ace.edit("editor2");
	editor2.getSession().setMode("ace/mode/java");*/

	$scope.test = function () {
		console.log("Testing");
		var out = $scope.editor.getValue();
		console.log(out);
	};
	
	$scope.status = {
		isCustomHeaderOpen : false,
		isFirstOpen : true,
		isFirstDisabled : false
	};
	
	$scope.checkNeedEditor = function (questionIndex) {
		console.log("checkNeedEditor(" + questionIndex + ")");
		var currQ = $scope.quiz.questions[questionIndex]
		console.log(currQ.type);
		
		if (currQ.type === 3) {
			// If this question is a coding question
			console.log("Code question found");
			//Create an editor for it
			var newEditor = ace.edit("editor" + questionIndex);
			newEditor.setTheme("ace/theme/monokai");
			newEditor.getSession().setMode("ace/mode/java");
			$scope.editors.push(newEditor);
		}
	}
});