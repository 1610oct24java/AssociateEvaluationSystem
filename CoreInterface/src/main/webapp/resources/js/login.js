var app = angular.module('myApp', []);
var items = 0;

app.controller('myController', function($scope, $http) {
	
	$scope.login = function() {
		
		var user={};
		user.email = $scope.email;
		user.password = $scope.password;
		
		$http.post("login", user)
	}
});