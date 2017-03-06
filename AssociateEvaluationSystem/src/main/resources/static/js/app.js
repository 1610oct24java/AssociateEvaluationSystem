var app = angular.module('AESCoreApp',[]);

app.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "",
	"PORT" : ":8080",
	
	"LOGIN": "index",
	"VIEW_CANDIDATES" : "view",
	"REGISTER_CANDIDATE" : "",
	"VIEW_EMPLOYEES" : "viewEmployees",
	
	"TRAINER_HOME" : "http://sports.cbsimg.net/images/nhl/blog/Ryan_Reaves_Kiss.JPG"
	
});

app.constant("API_URL", {
	"BASE"      : "/aes",
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
	"CANDIDATE" : "ROLE_CANDIDATE",
	"ADMIN"		: "ROLE_ADMIN"
});

app.controller('LoginCtrl', function($scope, $httpParamSerializerJQLike, $http, SITE_URL, API_URL, ROLE) {
	
	$scope.login = function() {
		makeUser($scope);
		$http({
			method : "POST",
			url : SITE_URL.BASE + API_URL.BASE + API_URL.LOGIN,
			headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8;'},
			data: $httpParamSerializerJQLike($scope.user)
		})//.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGIN, $httpParamSerializerJQLike($scope.user), {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8;'})
		.then(function(response) {
			$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
			.then(function(response) {
				//Removed console log for sonar cube.
				if (response.data.authenticated) {
					var authUser = {
						username : response.data.principal.username,
						authority: response.data.principal.authorities[0].authority
					}
					//Removed console log for sonar cube.
					$scope.authUser = authUser;
					switch ($scope.authUser.authority) {
					case ROLE.RECRUITER:
						window.location = SITE_URL.VIEW_CANDIDATES;
						break;
					case ROLE.CANDIDATE:
						$scope.candidateEmail = authUser.username;
						$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.CANDIDATE + $scope.candidateEmail + API_URL.LINK)
						.then(function(response) {
							window.location = response.data.urlAssessment;
							//Removed console log for sonar cube.
						})
						break;
					case ROLE.TRAINER:
						window.location = SITE_URL.TRAINER_HOME;
						break;
					case ROLE.ADMIN:
						window.location = SITE_URL.VIEW_EMPLOYEES;
						break;
					default:
						$scope.username = '';
						$scope.password = '';
						window.location = SITE_URL.LOGIN;
					}
				} else {
					$scope.username = '';
					$scope.password = '';
					$scope.bunkCreds = true;
				}
		})
	})
	}
}); //end login controller

function makeUser($scope) {
    var user = {
        username: $scope.username,
        password: $scope.password
    };

    $scope.user = user;
}
