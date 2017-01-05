(()=>{
	var app = angular.module('myApp', []);

	app.controller('myController', function($scope, $http) {

		$scope.login = function() {			
			var user={};
			user.j_username = $scope.email;
			user.j_password = $scope.password;
			
			$http.post("login", user);
		};
	});	
})();

