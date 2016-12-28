var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable', 'ngAnimate' ]);

app.controller("quizController", function($scope, $http, $location) {
	$scope.quiz = tstQuiz;
	$scope.oneAtATime = false;
	
	$scope.editor = ace.edit("editor1");
	$scope.editor.setTheme("ace/theme/monokai");
	$scope.editor.getSession().setMode("ace/mode/java");

	var editor2 = ace.edit("editor2");
	editor2.getSession().setMode("ace/mode/java");

	$scope.test = function () {
		console.log("Testing");
		var out = $scope.editor.getValue();
		console.log(out);
	}
	
	$scope.status = {
		isCustomHeaderOpen : false,
		isFirstOpen : true,
		isFirstDisabled : false
	};
});