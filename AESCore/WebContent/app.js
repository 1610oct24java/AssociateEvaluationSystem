var app = angular.module('AESCoreApp',[]);

app.controller('LoginCtrl', function($scope, $http) {
	$scope.login = function() {
		console.log('LOGIN CALLLED');
		makeUser($scope);
		console.log('MAKE USER CALLED');
		$http.post("http://localhost:8080/core/login",'', $scope.user)
		.then(function(response) {
			console.log('INSIDE POST TO LOGIN');
			$http.get("http://localhost:8080/core/security/auth")
			.then(function(response) {
				if (response.data.authenticated) {
					var authUser = {
						username : response.data.principal.username,
						authority: response.data.principal.authorities[0].authority
					}
					console.log(authUser);
					$scope.authUser = authUser;
					switch ($scope.authUser.authority) {
					case 'ROLE_RECRUITER':
						window.location = 'viewCandidates.html';
						break;
					case 'ROLE_CANDIDATE':
						window.location = 'https://usatftw.files.wordpress.com/2016/05/usp-nhl_-stanley-cup-playoffs-dallas-stars-at-st-_002.jpg?w=1000&h=600&crop=1';
						break;
					case 'ROLE_TRAINER':
						window.location = 'http://sports.cbsimg.net/images/nhl/blog/Ryan_Reaves_Kiss.JPG';
						break;
					default:
						window.location = 'index.html';
					}
				}
		})
	})
	}
}); //end login controller

app.controller('RegisterCanidateCtrl', function($scope,$location,$http) {

	authorize($scope,$http);
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
	console.log('INSIDE AUTHORIZE');
	$http.get("http://localhost:8080/core/security/auth")
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			console.log(authUser);
			$scope.authUser = authUser;
			if($scope.authUser.authority != 'ROLE_RECRUITER') {
				window.location = 'index.html';
			}
			
			//			switch ($scope.authUser.authority) {
//			case 'ROLE_RECRUITER':
//				window.location = 'viewCandidates.html';
//				break;
//			case 'ROLE_CANDIDATE':
//				window.location = 'index.html';
//				break;
//			case 'ROLE_TRAINER':
//				window.location = 'http://sports.cbsimg.net/images/nhl/blog/Ryan_Reaves_Kiss.JPG';
//				break;
//			default:
//				window.location = 'index.html';
//			}
//			if ($scope.authUser.authority === 'ROLE_RECRUITER') {
//				window.location = 'viewCandidates.html';
//			}
//			else if ($scope.authUser.authority === 'ROLE_CANDIDATE') {
//				window.location = 'https://usatftw.files.wordpress.com/2016/05/usp-nhl_-stanley-cup-playoffs-dallas-stars-at-st-_002.jpg?w=1000&h=600&crop=1';
//			}
//			else if ($scope.authUser.authority === 'ROLE_TRAINER') {
//				window.location = 'http://sports.cbsimg.net/images/nhl/blog/Ryan_Reaves_Kiss.JPG';
//			} else {
//				window.location = 'index.html';
//			}
			//return authUser;
		} 
//		else {
//			window.location = 'https://cbsstlouis.files.wordpress.com/2016/05/unknown.jpg?w=420';
//		}
//		console.log('authenticated: ' + authenticated);
//		console.log('username     : ' + response.data.principal.username);
//		$scope.recruiterEmail = response.data.principal.username;
//		console.log('authority    : ' + response.data.principal.authorities[0].authority);
//		window.location = 'viewCandidates.html';
	})
}