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
	"RECRUITER" : "/recruiter",
	"TRAINER"	: "/trainer",
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

adminApp.controller('RegisterEmployeeCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

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
			if($scope.authUser.authority != ROLE.ADMIN) {
				window.location = SITE_URL.LOGIN;
			}
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	$scope.names =["Recruiter"];
	
	$scope.register = function() {

		var employeeInfo = {
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
			lastName      : $scope.lastName,
			salesforce    : null,
			recruiterId   : null,
			role          : "Recruiter",  //$scope.employeeType.value,
			datePassIssued: null,
			format		  : null//$scope.program.value
		};

		var urlSpecific = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.RECRUITER;
		
		//Maybe used in the future
		/*if (employeeInfo.role === "Recruiter")
		{
			urlSpecific = urlSpecific + API_URL.RECRUITER;
		}else if (employeeInfo.role === "Trainer")
		{
			urlSpecific = urlSpecific + API_URL.TRAINER;
		}*/
		
		urlSpecific = urlSpecific + "/" + employeeInfo.email 
		+ "/" + employeeInfo.lastName + "/" + employeeInfo.firstName
		
		console.log(urlSpecific + " testing url" + employeeInfo.role);
		
		$scope.postRegister(urlSpecific, employeeInfo);
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(urlSpecific, employeeInfo) {
		console.log("adminApp.js: POST REGISTER EMPLOYEE");
		console.log("url"+urlSpecific );
		console.log("employeeinfo"+employeeInfo);
		
		$http({
			method  : 'POST',
			url: urlSpecific,
			headers : {'Content-Type' : 'application/json'},
			data    : employeeInfo
		}).success( function(response) {
			console.log('adminApp.js: register employee success');
		}).error( function(response) {
			console.log(response.status);
		});
	};


	/*$scope.options = [{
		name: 'Recruiter',
		value: 'Recruiter'
	}, {
		name: 'Trainer',
		value: 'Trainer'
	}];*/
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		})
	}

}); //end register Employee controller

adminApp.controller('EmployeeViewCtrl', function($scope, $http, SITE_URL, API_URL, ROLE) {

	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			console.log(authUser);
			$scope.authUser = authUser;
			if($scope.authUser.authority != ROLE.ADMIN) {
				window.location = SITE_URL.LOGIN;
			}
			
			$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES)
			.then(function(response) {
				console.log(response.data);
				$scope.employees = response.data;
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
	};
	//Delete user
	 $scope.Delete = function (email) {
	        url = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES + "/Delete/" + email + "/";
	        console.log("THE URL IS " + url);
	        $http.delete(url)
	        .then(function (response) {
	            console.log(response);
	        }, function (error) {
	            console.log(error);
	        });
	    }
	


      //added by Hajira Zahir
      $scope.checkAll = function () {
          if (!$scope.selectedAll) {
              $scope.selectedAll = true;
          } else {
              $scope.selectedAll = false;
          }
          angular.forEach($scope.employees, function (employees) {
        	  employees.selected = $scope.selectedAll;
          });
      };   
	
});


function makeUser($scope) {
	var user = {
				username: $scope.username,
				password: $scope.password
	};

	$scope.user = user;
}

adminApp.controller('UpdateEmployeeCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

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
		
		console.log(employeeInfo);
		$scope.postRegister(urlSpecific, employeeInfo);
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(urlSpecific, employeeInfo) {
		console.log("adminApp.js: POST REGISTER EMPLOYEE")
		
		$http({
			method  : 'POST',
			url: urlSpecific,
			headers : {'Content-Type' : 'application/json'},
			data    : employeeInfo
		}).success( function(res) {
			console.log('adminApp.js: register employee success');
		}).error( function(res) {
			console.log('adminApp.js: register employee error');
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
			window.location = SITE_URL.LOGIN;
		})
	}

}); //end update credentials controller
