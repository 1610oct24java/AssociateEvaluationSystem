var adminApp = angular.module('adminApp',[]);

adminApp.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "",
	"PORT" : ":8080",
	
	"LOGIN": "index",
	"VIEW_EMPLOYEES" : "viewEmployees",
	"REGISTER_EMPLOYEE" : ""
});

adminApp.constant("API_URL", {
	"BASE"      : "/aes",
	"LOGIN"     : "/login",
	"LOGOUT"    : "/logout",
	"AUTH"      : "/security/auth",
	"CANDIDATE" : "/candidate/",
	"RECRUITER" : "/recruiter/",
	"TRAINER"	: "/trainer/",
	"LINK"      : "/link",
	"EMPLOYEES" : "/employees",
	"ADMIN"		: "/admin"
});

adminApp.constant("ROLE", {
	"RECRUITER" : "ROLE_RECRUITER",
	"TRAINER"   : "ROLE_TRAINER",
	"CANDIDATE" : "ROLE_CANDIDATE",
	"ADMIN"		: "ROLE_ADMIN"
});

app.controller('RegisterEmployeeCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			$scope.authUser = authUser;
			if($scope.authUser.authority != ROLE.ADMIN) {
				window.location = SITE_URL.LOGIN;
			}
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	
	$scope.register = function() {

		var employeeInfo = {
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
			lastName      : $scope.lastName,
			salesforce    : null,
			recruiterId   : null,
			role          : $scope.employeeType.value,
			datePassIssued: null,
			format		  : null
		};

		var urlSpecific = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN;
		
		if (employeeInfo.role === "Recruiter")
		{
			urlSpecific = urlSpecific + API_URL.RECRUITER;
		}else if (employeeInfo.role === "Trainer")
		{
			urlSpecific = urlSpecific + API_URL.TRAINER;
		}
		
		urlSpecific = urlSpecific + employeeInfo.email 
		+ "/" + employeeInfo.lastName + "/" + employeeInfo.firstName

		$scope.postRegister(urlSpecific, employeeInfo);
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(urlSpecific, employeeInfo) {
		
		$http({
			method  : 'POST',
			url: urlSpecific,
			headers : {'Content-Type' : 'application/json'},
			data    : employeeInfo
		}).success( function(res) {
			//Removed console log for sonar cube.
		}).error( function(res) {
			//Removed console log for sonar cube.
		});
	};

	$scope.options = [{
		name: 'Recruiter',
		value: 'Recruiter'
	}, {
		name: 'Trainer',
		value: 'Trainer'
	}];
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			//Removed console log for sonar cube.
			window.location = SITE_URL.LOGIN;
		})
	}

}); //end register candidate controller

app.controller('EmployeeViewCtrl', function($scope, $http, SITE_URL, API_URL, ROLE) {

	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			$scope.authUser = authUser;
			if($scope.authUser.authority != ROLE.ADMIN) {
				window.location = SITE_URL.LOGIN;
			}
			
			$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES)
			.then(function(response) {
				$scope.employees = response.data;
			})
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			//Removed console log for sonar cube.
			window.location = SITE_URL.LOGIN;
		})
	}
	
});

function makeUser($scope) {
	var user = {
				username: $scope.username,
				password: $scope.password
	};

	$scope.user = user;
}

app.controller('UpdateEmployeeCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			$scope.authUser = authUser;
			if($scope.authUser.authority != ROLE.ADMIN) {
				window.location = SITE_URL.LOGIN;
			}
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	
	$scope.register = function() {

		var employeeInfo = {
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
			lastName      : $scope.lastName,
			salesforce    : null,
			recruiterId   : null,
			role          : $scope.employeeType.value,
			datePassIssued: null,
			format		  : null
		};

		var urlSpecific = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN;
		
		if (employeeInfo.role === "Recruiter")
		{
			urlSpecific = urlSpecific + API_URL.RECRUITER;
		}else if (employeeInfo.role === "Trainer")
		{
			urlSpecific = urlSpecific + API_URL.TRAINER;
		}
		
		urlSpecific = urlSpecific + employeeInfo.email 
		+ "/" + employeeInfo.lastName + "/" + employeeInfo.firstName
		
		$scope.postRegister(urlSpecific, employeeInfo);
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(urlSpecific, employeeInfo) {
		
		$http({
			method  : 'POST',
			url: urlSpecific,
			headers : {'Content-Type' : 'application/json'},
			data    : employeeInfo
		}).success( function(res) {
			//Removed console log for sonar cube.
		}).error( function(res) {
			//Removed console log for sonar cube.
		});
	};

	$scope.options = [{
		name: 'Recruiter',
		value: 'Recruiter'
	}, {
		name: 'Trainer',
		value: 'Trainer'
	}];
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			//Removed console log for sonar cube.
			window.location = SITE_URL.LOGIN;
		})
	}

}); //end update credentials controller
