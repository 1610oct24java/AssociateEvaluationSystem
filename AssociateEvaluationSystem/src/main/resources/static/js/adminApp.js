var adminApp = angular.module('adminApp',['ngMaterial', 'ngMessages']);

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
	"EMPLOYEES" : "/employees",
	"ADMIN"		: "/admin"
});

adminApp.constant("ROLE", {
	"RECRUITER" : "ROLE_RECRUITER",
	"TRAINER"   : "ROLE_TRAINER",
	"CANDIDATE" : "ROLE_CANDIDATE",
	"ADMIN"		: "ROLE_ADMIN"
});

adminApp.config(function($mdThemingProvider) {

    var revOrangeMap = $mdThemingProvider.extendPalette("deep-orange", {
        "A200": "#FB8C00",
        "100": "rgba(89, 116, 130, 0.2)"
    });

    var revBlueMap = $mdThemingProvider.extendPalette("blue-grey", {
        "500": "#37474F",
        "800": "#3E5360"
    });

    $mdThemingProvider.definePalette("revOrange", revOrangeMap);
    $mdThemingProvider.definePalette("revBlue", revBlueMap);

    $mdThemingProvider.theme("default")
        .primaryPalette("revBlue")
        .accentPalette("revOrange");
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
			format		  : null
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
			//Removed console log for sonar cube.
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
			window.location = SITE_URL.LOGIN;
		})
	};
	//By Hajira Zahir
	//Delete user
	 $scope.Delete = function (email) {
	        url = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES + "/Delete/" + email + "/";
	        $http.delete(url)
	        .then(function (response) {
	        }, function (error) {
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
			var updateUrl = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN 
					+ API_URL.EMPLOYEES + "/update/" + $scope.oldEmail + "/";
			
			$scope.postUpdate(updateUrl, employeeInfo);
		}
		
	};

	$scope.postUpdate = function(updateUrl, info) {
		
		$http({
			method  : 'PUT',
			url: updateUrl,
			headers : {'Content-Type' : 'application/json'},
			data    : info
		}).success(function(data){
                if (!data){
                    $scope.userNotFound = true;
                }
		}).error( function() {
			console.log("fail");
		});
	};

	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		})
	}
	
}); //end update credentials controller

//Billy Adding controller for assessment creation
adminApp.controller('CreateAssessmentCtrl', function($scope, $http, SITE_URL, API_URL, ROLE) {

    $(document).ready(function() {
        $('#example').DataTable({
            sDom: 'rt',
            fixedColumns: true,
            scrollY: "220px",
            scrollX: false,
            paging: false
        });
    });


    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
        .then(function(response) {
            if (response.data.authenticated) {
                var authUser = {
                    username: response.data.principal.username,
                    authority: response.data.principal.authorities[0].authority
                };
                $scope.authUser = authUser;
                if ($scope.authUser.authority != ROLE.ADMIN) {
                    window.location = SITE_URL.LOGIN;
                };
            } else {
                window.location = SITE_URL.LOGIN;
            }
        });

    $scope.times = [15, 30, 45, 60, 75, 90];
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
    ];
    $scope.types = [
        "Drag and Drop",
        "Multiple Choice",
        "Multiple Select",
        "Coding Snippet"
    ];

    $scope.assessments = [];
    $scope.totalQuestions = 0;
    $scope.totalCategories = 0;
    $scope.totalTypes = 0;
    $scope.time;

    function UpdateTotals(quantity) {
        $scope.totalCategories = UniqueArraybyId($scope.assessments, "category");
        console.log("Total categories = " + $scope.totalCategories);
        $scope.totalTypes = UniqueArraybyId($scope.assessments, "type");
        console.log("Total types = " + $scope.totalTypes);
        $scope.totalQuestions += quantity;
        console.log("Total questions = " + $scope.totalQuestions);
    }


    function UniqueArraybyId(collection, keyname) {
        var output = [],
            keys = [];

        angular.forEach(collection, function(item) {
            var key = item[keyname];
            if (keys.indexOf(key) === -1) {
                keys.push(key);
                output.push(item);
            }
        });
        return output.length;
    };

    $scope.removeRow = function(index) {
        var tempQuantity = $scope.assessments[index]['quantity'];
        $scope.assessments.splice(index, 1);
        UpdateTotals(-tempQuantity);
    };

    $scope.addRow = function() {
        $scope.assessments.push({ 'category': $scope.category, 'type': $scope.type, 'quantity': $scope.quantity });
        UpdateTotals($scope.quantity);
        $scope.sectionForm.$setPristine();
        $scope.sectionForm.$setUntouched();
        $scope.category = '';
        $scope.type = '';
        $scope.quantity = '';
    };

    //creates url and performs AJAX call to appropriate REST endpoint
    //sending the assessment time and criteria
    $scope.createAssessment = function() {

        //build test JSON
        data = {
            "timeLimit": $scope.time,
            "categoryRequestList": [{
                "category": {
                    "categoryId": 6,
                    "name": "core language"
                },
                "msQuestions": 5,
                "mcQuestions": 25,
                "ddQuestions": 0,
                "csQuestions": 1
            }, {
                "category": {
                    "categoryId": 2,
                    "name": "OOP"
                },
                "msQuestions": 1,
                "mcQuestions": 3,
                "ddQuestions": 0,
                "csQuestions": 0
            }, {
                "category": {
                    "categoryId": 3,
                    "name": "Data Structures"
                },
                "msQuestions": 1,
                "mcQuestions": 0,
                "ddQuestions": 0,
                "csQuestions": 0
            }, {
                "category": {
                    "categoryId": 4,
                    "name": "SQL"
                },
                "msQuestions": 3,
                "mcQuestions": 5,
                "ddQuestions": 1,
                "csQuestions": 0
            }]
        };

        //var sendUrl = SITE_URL.BASE + API_URL.BASE + "/assessmentrequest/" + "1";
        var sendUrl = SITE_URL.BASE + API_URL.BASE + "/assessmentrequest" + "/1/";

        $http({
            method: 'PUT',
            url: sendUrl,
            headers: { 'Content-Type': 'application/json' },
            data: data
        }).success(function(response) {
            console.log("success");
            console.log(sendUrl);
        }).error(function(response) {
            console.log("fail");
            console.log(sendUrl);
        });
    };




    //initialize data
    $http({
        method: "GET",
        url: "category"
    }).then(function (response) {
        $scope.categories = response.data;
    });

    $http({
        method: "GET",
        url: "format"
    }).then(function (response) {
        $scope.formats = response.data;
    });


    //logout
    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function(response) {
                window.location = SITE_URL.LOGIN;
            })
    };
});



adminApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
    var mc = this;

    // functions
    // sets navbar to current page even on refresh
    mc.findCurrentPage = function() {

        // var path = $location.path().replace("/", "");
        var path = window.location.pathname.substr(1);

        switch(path) {
            // case "aes/viewEmployees" : return "overview";
            case "aes/registerEmployee" : return "employees";
            case "aes/updateEmployee" : return "employees";
            case "aes/createAssessment" : return "assessments";
            case "aes/parser" : return "parser";
            default : return "overview"
        }
    };

    mc.buildToggler = function(navID) {
        return function() {
            $mdSidenav(navID)
                .toggle()
                .then(function() {
                    $log.debug("toggle " + navID + " is done");
                });
        };
    };
    $scope.isOpenLeft = function() {
        return $mdSidenav('left').isOpen();
    };


    // data
    mc.currentPage = mc.findCurrentPage();
    $scope.toggleLeft = mc.buildToggler('left');

});
