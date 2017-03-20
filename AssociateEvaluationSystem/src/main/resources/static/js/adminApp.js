var adminApp = angular.module('adminApp',[]);

adminApp.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "",
	"PORT" : ":8080",
	
	"LOGIN": "index",
    "VIEW_EMPLOYEES" : "viewEmployees",
    "VIEW_CANDIDATES" : "view",
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
	"EMPLOYEE"	: "/employee",
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
	});
	
	$scope.roleType ="Recruiter";
	
	$scope.register = function() {

		var employeeInfo = {
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
            lastName      : $scope.lastName,
            salesforce    : null,
            recruiterId   : null,
            role          : "Recruiter",
			datePassIssued: null,
			format		  : null
		};
		
		$scope.postRegister(employeeInfo);
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(employeeInfo) {
		$scope.registerSuccessfulMsg = false;
		$scope.registerUnsuccessfulMsg = false;
		
		$http({
			method  : 'POST',
			url: SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/register",
			headers : {'Content-Type' : 'application/json'},
			data    : employeeInfo

		}).success( function() {
                $scope.registerSuccessfulMsg = true;
		}).error( function() {
				$scope.registerUnsuccessfulMsg = true;
		});
	}
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		});
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
			$scope.authUser = authUser;
			if($scope.authUser.authority != ROLE.ADMIN) {
				window.location = SITE_URL.LOGIN;
			}
			
			$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES)
			.then(function(response) {
				$scope.employees = response.data;
			});
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	    
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		});
	};

	$scope.deleteEmployee = function(email) {
		url = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/" + email + "/delete";
		
		$http.delete(url)
		.then(function (response) {
			//handle success
		}, function (error) {
			//handle error
		});
	}
	
	$scope.checkAll = function () {
		if (!$scope.selectedAll) {
			$scope.selectedAll = true;
		}else {
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
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			$scope.authUser = authUser;
			var role = $scope.authUser.authority;
			
			if(role == "ROLE_ADMIN") {
				// Continue to page
			}else {
				window.location = SITE_URL.LOGIN; // Deny page, re-route to login
			}
		} else {
			window.location = SITE_URL.LOGIN;
		}

	});
	
	$scope.update= function() {
		$scope.passNotMatch = false;
		$scope.passNotEntered = false;
		$scope.emailNotEntered = false;
		$scope.userNotFound = false;
		
		var employeeInfo = {
			newEmail      : $scope.newEmail,
			firstName     : $scope.firstName,
			lastName      : $scope.lastName,
			oldPassword   : $scope.oldPassword,
			newPassword   : $scope.newPassword,
		};

		if ($scope.oldEmail === "" || $scope.oldEmail == null)
		{	$scope.emailNotEntered = true; }
		
		if ($scope.newPassword !== $scope.confirmNewPassword)
		{
			$scope.passNotMatch = true;
			$scope.newPassword = '';
			$scope.confirmNewPassword = '';
		}
		
		if ($scope.oldPassword === "" || $scope.oldPassword == null)
		{	$scope.passNotEntered = true; }
		
		if ($scope.passNotMatch == false && $scope.passNotEntered == false 
				&& $scope.emailNotEntered == false)
		{
			$scope.postUpdate($scope.oldEmail, employeeInfo);
		}	
	}

	$scope.postUpdate = function(oldEmail, updateInfo) {
		var updateUrl = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN 
				+ API_URL.EMPLOYEE + "/" + $scope.oldEmail + "/update";
		
		$http({
			method  : 'PUT',
			url		: updateUrl,
			headers : {'Content-Type' : 'application/json'},
			data    : updateInfo
		}).success(function(data){
            if (!data){
                $scope.userNotFound = true;
            }
		}).error( function() {
			console.log("fail");
		});
	}

	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		});
	}
	
}); //end update credentials controller

//Billy Adding controller for assessment creation
adminApp.controller('CreateAssessmentCtrl',function ($scope,$http, SITE_URL, API_URL, ROLE) {

    $scope.times = [
        "15 Minutes",
        "30 Minutes",
        "45 Minutes",
        "60 Minutes"
    ]

    $scope.categories = [
        "HTML",
        "CSS",
        "JavaScript",
        "Object Oriented Programming",
        "Data Structures",
        "SQL",
        "C#",
        "Java",
        "Critical Thinking"
    ]

    $scope.types = [
        "Drag and Drop",
        "Multiple Choice",
        "Multiple Select",
        "Coding Snippet"
    ]

	//variables to hold time and assessment criteria
    $scope.assessment = [];
    $scope.time = '';

    //remove a criteria
    $scope.removeRow = function(index) {
        $scope.assessment.splice(index, 1);
    };

    //add a criteria
    $scope.addRow = function() {
        $scope.assessment.push({ 'category': $scope.category, 'type': $scope.type, 'quantity': $scope.quantity });
        $scope.category = '';
        $scope.type = '';
        $scope.quantity = '';
    };



    //creates url and performs AJAX call to appropriate REST endpoint
	//sending the assessment time and criteria
    $scope.createAssessment = function() {

        //build test JSON
        data = {"timeLimit": 45,
            "categoryRequestList": [
                {   "category": { "categoryId": 6,
                    "name": "core language"
                },
                    "msQuestions" : 5,
                    "mcQuestions" : 25,
                    "ddQuestions" : 0,
                    "csQuestions" : 1
                },{ "category": { "categoryId": 2,
                    "name": "OOP"
                },
                    "msQuestions" : 1,
                    "mcQuestions" : 3,
                    "ddQuestions" : 0,
                    "csQuestions" : 0
                },{ "category": { "categoryId": 3,
                    "name": "Data Structures"
                },
                    "msQuestions" : 1,
                    "mcQuestions" : 0,
                    "ddQuestions" : 0,
                    "csQuestions" : 0
                },{ "category": { "categoryId": 4,
                    "name": "SQL"
                },
                    "msQuestions" : 3,
                    "mcQuestions" : 5,
                    "ddQuestions" : 1,
                    "csQuestions" : 0
                }
            ]
        }

        //var sendUrl = SITE_URL.BASE + API_URL.BASE + "/assessmentrequest/" + "1";
        var sendUrl = SITE_URL.BASE + API_URL.BASE + "/assessmentrequest" + "/1/";

        $http({
            method  : 'PUT',
            url: sendUrl,
             headers : {'Content-Type' : 'application/json'},
             data    : data
        }).success( function(response) {
            console.log("success");
            console.log(sendUrl);
        }).error( function(response) {
            console.log("fail");
            console.log(sendUrl);
        });
    };


    //logout
    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function(response) {
                window.location = SITE_URL.LOGIN;
            })
    }

});
