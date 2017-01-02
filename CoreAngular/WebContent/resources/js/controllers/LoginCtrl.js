app.controller('LoginCtrl', function($scope, $http) {
	$scope.showNav = false;
	$scope.login = function() {
		makeUser($scope);
		$http.post("http://localhost:8080/core/login",'', $scope.user)
		.then(function(response) {
			console.log("INSIDE RESPONSE TO /LOGIN");
			console.log(response.data);
			$http.get("http://localhost:8080/core/user")
			.then(function(response) {
				navVisible($http,$scope);
				console.log("INSIDE RESPONSE TO /USER");
				console.log(response.data);
				console.log('authenticated: ' + response.data.authenticated);
				console.log('username     : ' + response.data.principal.username);
				console.log('authority    : ' + response.data.principal.authorities[0].authority);
			})
		})
	}
});

function navVisible($http,$scope) {
	console.log("hi")
	$http.get("http://localhost:8080/core/user")
	.then(function(response) {
		console.log("NAVVVVVVVVVVVVVVVV");
		let authorized = response.data.authenticated; 
		if (authorized && response.data.principal.authorities[0].authority === "ROLE_RECRUITER") {
			$scope.showNav = true;
			console.log("AUTH");
			console.log($scope.showNav);
		} else {
			console.log("NOT AUTH");
			$scope.showNav = false;
		}
	})
}

function makeUser($scope) {
	var user = {
			params: {
				username: $scope.username,
				password: $scope.password
			}
	};

	$scope.user = user
	console.log(user);
}