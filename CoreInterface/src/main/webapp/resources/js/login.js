var app = angular.module('myApp', []);
var items = 0;

app.controller('myController', function($scope, $http, $filter) {
	console.log("ASD")
	
	$scope.login = function() {
		
		user={};
		user.email = $scope.email;
		user.password = $scope.password;
		console.log(user);
		
		$http.post("login", user)
	}
});