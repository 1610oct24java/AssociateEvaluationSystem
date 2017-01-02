var app = angular.module('AESCoreApp',[]);

app.controller('LoginCtrl', function($scope, $http) {
	$scope.login = function() {
		makeUser($scope);
		$http.post("http://localhost:8080/core/login",'', $scope.user)
		.then(function(response) {
			//console.log("INSIDE RESPONSE TO /LOGIN");
			//console.log(response.data);
			$http.get("http://localhost:8080/core/user")
			.then(function(response) {
				console.log("INSIDE RESPONSE TO /USER");
				console.log(response.data);
				console.log('authenticated: ' + response.data.authenticated);
				console.log('username     : ' + response.data.principal.username);
				console.log('authority    : ' + response.data.principal.authorities[0].authority);
				window.location = 'recruitCandidate.html';
			})
		})
	}
}); //end login controller

app.controller('RegisterCanidateCtrl', function($scope,$location,$http) {

	$scope.register = function() {

		var canidateInfo = {
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
			lastName      : $scope.lastName,
			salesforce    : null,
			recruiterId   : null,
			role          : null,
			datePassIssued: null,
			format		  : $scope.program
		};

		console.log(canidateInfo);

		
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(canidateInfo) {
		$http({
			method  : 'POST',
			url: 'http://localhost:8080/core/canidates',
			headers : {'Content-Type' : 'application/json'},
			data    : 'canidateInfo'
		}).success( function(res) {
			console.log('success');
			console.log(res);
		}).error( function(res) {
			console.log('error');
			console.log(res);
		});
	};

	//TODO populate dynamically from db
	$scope.options = [{
		name: 'Java',
		value: 'java'
	}, {
		name: 'SDET',
		value: 'sdet'
	}, {
		name: '.NET',
		value: 'dotnet'
	}];

}); //end register candidate controller

function makeUser($scope) {
	var user = {
			params: {
				username: $scope.username,
				password: $scope.password
			}
	};

	$scope.user = user
	//console.log(user);
}