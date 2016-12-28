var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable', 'ngAnimate' ]);

app.controller("quizController", function($scope, $http, $location) {
	$scope.quiz = tstQuiz;
	$scope.oneAtATime = false;

	$scope.status = {
		isCustomHeaderOpen : false,
		isFirstOpen : true,
		isFirstDisabled : false
	};
});