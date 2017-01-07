var app = angular.module('AESCoreApp',[]);

app.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "http://localhost:8080",
	"PORT" : ":8080",
	
	"LOGIN": "index.html",
	"VIEW_CANDIDATES" : "viewCandidates.html",
	"REGISTER_CANDIDATE" : "",
	
	"TRAINER_HOME" : "http://sports.cbsimg.net/images/nhl/blog/Ryan_Reaves_Kiss.JPG"
	
});

app.constant("API_URL", {
	"BASE"      : "/core",
	"LOGIN"     : "/login",
	"LOGOUT"    : "/logout",
	"AUTH"      : "/security/auth",
	"CANDIDATE" : "/candidate/",
	"RECRUITER" : "/recruiter/",
	"LINK"      : "/link",
	"CANDIDATES": "/candidates"
});

app.constant("ROLE", {
	"RECRUITER" : "ROLE_RECRUITER",
	"TRAINER"   : "ROLE_TRAINER",
	"CANDIDATE" : "ROLE_CANDIDATE"
});

app.controller('LoginCtrl', function($scope, $http, SITE_URL, API_URL, ROLE) {
	
	$scope.login = function() {
		console.log('LOGIN CALLLED');
		makeUser($scope);
		console.log('MAKE USER CALLED');
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGIN,'', $scope.user)
		.then(function(response) {
			console.log('INSIDE POST TO LOGIN');
			$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
			.then(function(response) {
				if (response.data.authenticated) {
					var authUser = {
						username : response.data.principal.username,
						authority: response.data.principal.authorities[0].authority
					}
					console.log(authUser);
					$scope.authUser = authUser;
					switch ($scope.authUser.authority) {
					case ROLE.RECRUITER:
						window.location = SITE_URL.VIEW_CANDIDATES;
						break;
					case ROLE.CANDIDATE:
						$scope.candidateEmail = authUser.username;
						$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.CANDIDATE + $scope.candidateEmail + API_URL.LINK)
						.then(function(response) {
							console.log(response.data);
							window.location = SITE_URL.HTTPS + response.data.urlAssessment;
							console.log('CHOOCKED');
						})
						break;
					case ROLE.TRAINER:
						window.location = SITE_URL.TRAINER_HOME;
						break;
					default:
						console.log('INVALID LOGIN?');
						$scope.username = '';
						$scope.password = '';
						window.location = SITE_URL.LOGIN;
					}
				} else {
					console.log('!!! INVALID LOGIN');
					$scope.username = '';
					$scope.password = '';
					$scope.bunkCreds = true;
				}
		})
	})
	}
}); //end login controller

app.controller('RegisterCanidateCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		console.log(response.data);
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			console.log(authUser);
			$scope.authUser = authUser;
			if($scope.authUser.authority != ROLE.RECRUITER) {
				window.location = SITE_URL.LOGIN;
			}
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	
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
			format		  : $scope.program.value
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
		$http({
			method  : 'POST',
			url: SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + $scope.authUser.username + API_URL.CANDIDATES,
			headers : {'Content-Type' : 'application/json'},
			data    : canidateInfo
		}).success( function(res) {
			console.log('success');
		}).error( function(res) {
			console.log('error');
		});
	};

	$scope.options = [{
		name: 'Java',
		value: 'Java'
	}, {
		name: 'SDET',
		value: 'Sdet'
	}, {
		name: '.NET',
		value: '.net'
	}];
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		})
	}

}); //end register candidate controller

app.controller('CandidateViewCtrl', function($scope,$http, SITE_URL, API_URL, ROLE) {

	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			console.log(authUser);
			$scope.authUser = authUser;
			if($scope.authUser.authority != ROLE.RECRUITER) {
				window.location = SITE_URL.LOGIN;
			}
			$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + $scope.authUser.username + API_URL.CANDIDATES)
			.then(function(response) {
				console.log(response.data);
				//$scope.candidates = response.data;
				var c =  response.data;
				console.log('length: ' + c.length);
				for (var i=0; i<c.length; i++) {
					console.log('c[i]: ' + c[i]);
					if (c[i].grade == -1) {
						console.log('GKJGKJHJKHJHK');
						c[i].grade = 'N/A';
					}
				}
				$scope.candidates = c;
			})
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		})
	}
	
});

function makeUser($scope) {
	var user = {
			params: {
				username: $scope.username,
				password: $scope.password
			}
	};

	$scope.user = user;
}

