/**
 * 
 */

function googleTranslateElementInit() {
	new google.translate.TranslateElement({
		pageLanguage : 'en',
		layout : google.translate.TranslateElement.InlineLayout.SIMPLE
	}, 'google_translate_element');
}

var app = angular.module('myApp', []);
app.controller('Controller', function($scope, $http) {

	$scope.login = function() {
		makeUser($scope);
		$http.post("http://localhost:1993/Assessment/login", '', $scope.user)
			.then(function(response) {
				console.log("INSIDE RESPONSE TO /LOGIN");
				console.log(response.data);
				$http.get("http://localhost:1993/Assessment/user")
					.then(function(response) {
						console.log("INSIDE RESPONSE TO /USER");
						console.log(response.data);

					})
			})
	}
});

function makeUser($scope) {
	var user = {
		params : {
			username : $scope.username,
			password : $scope.password
		}
	};

	$scope.user = user
	console.log(user);
}