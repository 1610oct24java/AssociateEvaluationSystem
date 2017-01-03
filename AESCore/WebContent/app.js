var app = angular.module('AESCoreApp',[]);

app.controller('LoginCtrl', function($scope, $http) {
	$scope.login = function() {
		makeUser($scope);
		$http.post("http://localhost:8080/core/login",'', $scope.user)
		.then(function(response) {
			$http.get("http://localhost:8080/core/auth")
			.then(function(response) {
				console.log('authenticated: ' + response.data.authenticated);
				console.log('username     : ' + response.data.principal.username);
				console.log('authority    : ' + response.data.principal.authorities[0].authority);
				window.location = 'viewCandidates.html';
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
			format		  : $scope.program.name
		};

		console.log(canidateInfo);
		$scope.postRegister(canidateInfo);
		
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(canidateInfo) {
		console.log("POSTREGISTER")
		var email = $scope.recruiterEmail;
		$http({
			method  : 'POST',
			url: 'http://localhost:8080/core/recruiter/' + email + '/candidates',
			headers : {'Content-Type' : 'application/json'},
			data    : canidateInfo
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

app.controller('CandidateViewCtrl', function($scope,$http) {
	var email = 'asd@gmail.com';
	$http.get('http://localhost:8080/core/recruiter/' + email + '/candidates')
	.then(function(response) {
		console.log(response.data);
		$scope.candidates = response.data;
	})		
});

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

function authorize($scope, $http) {
	$http.get("http://localhost:8080/core/auth")
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			return authUser;
		}
//		console.log('authenticated: ' + authenticated);
//		console.log('username     : ' + response.data.principal.username);
//		$scope.recruiterEmail = response.data.principal.username;
//		console.log('authority    : ' + response.data.principal.authorities[0].authority);
//		window.location = 'viewCandidates.html';
	})
}