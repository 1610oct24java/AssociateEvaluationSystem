var app = angular.module("quizApp", []);

app.controller("quizController", function ($scope, $http, $location) {
	$scope.quiz = tstQuiz;
	
	
});