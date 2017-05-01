var adminApp = angular.module('adminApp',['ngMaterial', 'ngMessages', 'ngRoute']);

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

    $mdThemingProvider.theme("success-toast");
    $mdThemingProvider.theme("fail-toast");

    $mdThemingProvider.theme("default")
        .primaryPalette("revBlue")
        .accentPalette("revOrange");
});

adminApp.directive('stringToNumber', function() {
	  return {
		    require: 'ngModel',
		    link: function(scope, element, attrs, ngModel) {
		      ngModel.$parsers.push(function(value) {
		        return '' + value;
		      });
		      ngModel.$formatters.push(function(value) {
		        return parseFloat(value);
		      });
		    }
		  };
		});

adminApp.controller('RegisterEmployeeCtrl', function($scope,$mdToast,$location,$http,SITE_URL, API_URL, ROLE) {
	$scope.roleTypes = [];
	$scope.allEmails = [];
	$scope.buttonToggle = false; // by default
	$scope.recruiter = null; // by default, unless admin picks candidate
	$scope.recruiterSelect = false; // by default, unless admin picks candidate
	$scope.allRecruiters = [];
	var recruiter = null;
	
	
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

	/* This function checks if email is in the database
	 * Disables registration if email is in the database
	 * */
	$scope.checkEmail = function(){
		var keepGoing = true;
		$scope.allEmails.forEach(function(email) {
			if(keepGoing) {

				if (email.toUpperCase() === $scope.email.toUpperCase()){ //case-insensitive email match
          /*alert("Email already registered.");*/
					$scope.buttonToggle = true;
					keepGoing = false;
				}
				else {
					$scope.buttonToggle = false;
				}
			}
		});	
	};
	
	// show the recruiter select menu if employee being registered is a candidate
	$scope.checkIfCandidate = function(){
		if ($scope.roleType.roleTitle.toUpperCase() === 'CANDIDATE'){
			$scope.recruiterSelect = true;
		} else {
			$scope.recruiterSelect = false;
		}
	};
	
	// reset form and refresh page's cache of emails and recruiters
	$scope.resetRegistrationForm = function() {
		// reset all form state variables
		$scope.allEmails = [];
		$scope.buttonToggle = false; // by default
		$scope.recruiter = null; // by default, unless admin picks candidate
		$scope.recruiterSelect = false; // by default, unless admin picks candidate
		$scope.allRecruiters = [];
	}
	
	$scope.initializeRegistrationSelects = function() {
		// populate roleTypes in registerEmployee View.
		$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/roles")
		.then(function(result) {
			// we don't want to display 'restuser' or 'system'
			result.data.forEach(function(role){
				if (role.roleTitle.toUpperCase() === 'RESTUSER'){
				}
				else if (role.roleTitle.toUpperCase() === 'SYSTEM'){
				}
				else {
					// if any other role, we add it to the select option
					$scope.roleTypes.push(role);
				}
			});
		});
		
		// get all emails from the database
		$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/emails")
		.then(function(result) {
			$scope.allEmails = result.data;
		});
		
		// get all recruiters from the database
		$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/recruiters")
		.then(function(result) {
			$scope.allRecruiters = result.data;
		});
	}
	
	$scope.register = function() {
		
		// if we're registering a candidate...
		if ($scope.recruiterSelect === true) {
			recruiter = $scope.recruiter.userId;
		}

		var employeeInfo = {
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
            lastName      : $scope.lastName,
            salesforce    : null,
            recruiterId   : recruiter,
            role          : $scope.roleType,
			datePassIssued: null,
			format		  : null
		};

		$scope.postRegister(employeeInfo);

		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
		$scope.roleType = '';
	};

	$scope.postRegister = function(employeeInfo) {
		$scope.registerSuccessfulMsg = false;
		$scope.registerUnsuccessfulMsg = false;

		$http({
			method  : 'POST',
			url: SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/register",
			headers : {'Content-Type' : 'application/json'},
			data    : employeeInfo

		}).success( function(data) {
		    if(!data || data.msg == "fail"){
		        $scope.registerUnsuccessfulMsg = true;
            }
            else {
                $scope.registerSuccessfulMsg = true;
            }
		    // clear form.
		    $scope.resetRegistrationForm();
		    $scope.initializeRegistrationSelects(); //needs to occur AFTER post completes; updates emails and recruiters in memory for validation purposes.
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
	
	$scope.initializeRegistrationSelects();
	
});

adminApp.controller('EmployeeViewCtrl', function($scope,$mdToast, $http, SITE_URL, API_URL, ROLE) {
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
	});

	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		});
	};
	
	 $scope.showToast = function(message) {
	    	$mdToast.show($mdToast.simple().textContent(message).parent(document.querySelectorAll('#toastContainer')).position("center center").action("OKAY").highlightAction(true));
	    };

	// deletes an employee only if it is not the system user.
    $scope.deleteEmployee = function(employee) {
    	if (employee.role.roleTitle.toUpperCase() === "SYSTEM") {
    		$scope.showToast("Cannot delete System User");
    		console.log("CAN'T DO SYSTEM USER DELETE");
    		return;
    	}
    	
    	// delete the employee if it is not the system user.
    	console.log("DOING NON-SYSTEM USER DELETE");
		url = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/" + employee.email + "/delete";
		$http.delete(url)
		.success( function(response) {
			$scope.showToast("User Deleted");
			$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES)
			.then(function(response) {
				$scope.employees = response.data;
			});
        }).error( function(response) {
        	$scope.showToast("Failed to Delete User");
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

adminApp.controller('UpdateEmployeeCtrl', function($scope,$location,$http,$routeParams, SITE_URL, API_URL, ROLE) {
	// list of candidates recruiter does not have.
	$scope.possibleCandidates = [];
	
	//list of candidates a recruiter does have.
	$scope.candidateList = [];
	
	
	
	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			};
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
	
	//loads up fields with existing data
	var userEmail = $location.search().email;
	if (userEmail){
		var getInfo = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/" + userEmail;
		$http.get(getInfo).then(function(response){
			var employee = response.data;
			$('#currentEmail').prop('readonly',true);
			$scope.oldEmail = employee.email;
			$scope.firstName = employee.firstName;
			$scope.lastName = employee.lastName;
			$scope.roleName = employee.role.roleTitle;
			$scope.userId = employee.userId;
			//get candidate list, if user role is recruiter
			if ($scope.roleName === "recruiter"){
				var getCandidateInfo = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/" + userEmail + "/getCandidates";
				$http.get(getCandidateInfo).then(function(response){
					$scope.candidateList = response.data;
					
					
					// loads a full candidate list and then starts the function to generate the list for the add candidate 
					var getCandidateListInfo = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + "/candidates"
					$http.get(getCandidateListInfo).then(function(response){
						$scope.allCandidates = response.data;
						$scope.updatePossibleCandidatesList()
					})
					
				});
			}
		});
		

	}
	
	
	//creates and updates the list of possible candidates to add
	$scope.updatePossibleCandidatesList = function(){
		$scope.possibleCandidates = [];
		$scope.allCandidates.forEach(function(candidate){
			if (candidate.recruiterId !== $scope.userId){
				$scope.possibleCandidates.push(candidate);
			}
		});
			
	}
	
	var move = function(objectToMove, fromArray, toArray){
		var i = fromArray.indexOf(objectToMove);
		var o = fromArray.splice(i, 1)[0];
		toArray.push(o);
	}
	
	$scope.toRight = function(){
		if ($scope.selectedCurrentCanidates != null){
			$scope.selectedCurrentCanidates.forEach(function(el){
				move(el, $scope.candidateList, $scope.possibleCandidates);
			});
		}
	}
	
	$scope.toLeft = function(){
		if ($scope.selectedNewCanidates != null){
			$scope.selectedNewCanidates.forEach(function(el){
				move(el, $scope.possibleCandidates, $scope.candidateList);
			});
		}
	}
	
	
	
	

	
	//$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/" + )

	$scope.update= function() { 
		$scope.passNotMatch = false;
		$scope.passNotEntered = false;
		$scope.emailNotEntered = false;
		$scope.userNotFound = false;
			

		var employeeInfo = {
			oldEmail		: $scope.oldEmail,
			newEmail      	: $scope.newEmail,
			firstName     	: $scope.firstName,
			lastName      	: $scope.lastName,
			oldPassword   	: $scope.oldPassword,
			newPassword   	: $scope.newPassword,
			candidates		: $scope.candidateList
		};
		
		//resets the error messages
		$scope.emailNotEntered = false;
		$scope.passNotMatch = false;
		$scope.emailNotEntered = false;
		$scope.updateSuccessful = false;
		$scope.updateUnsuccessful = false;
		

		if ($scope.oldEmail === "" || $scope.oldEmail == null)
		{	$scope.emailNotEntered = true; }

		if ($scope.newPassword !== $scope.confirmNewPassword)
		{
			$scope.passNotMatch = true;
			$scope.newPassword = '';
			$scope.confirmNewPassword = '';
		}

		if (!$scope.passNotMatch&& !$scope.passNotEntered
				&& !$scope.emailNotEntered)
		{
			$scope.postUpdate($scope.oldEmail, employeeInfo);
		}
	}

	$scope.postUpdate = function(oldEmail, updateInfo) {
		$scope.updateUnsuccessful = false;
		$scope.updateSuccessful = false;
		
		var updateUrl = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN
				+ API_URL.EMPLOYEE + "/" + $scope.oldEmail + "/update";

		$http({
			method  : 'PUT',
			url		: updateUrl,
			headers : {'Content-Type' : 'application/json'},
			data    : updateInfo
		}).success(function(data){
            if (!data){
                $scope.updateUnsuccessful = true;
            } else {
            	if($scope.newEmail){
            		$scope.oldEmail = $scope.newEmail;
            		$scope.newEmail = "";
            	}
            	
            	$scope.confirmNewPassword = "";
            	$scope.newPassword = "";
            	
            	$scope.updateSuccessful = true;
            }
		}).error( function() {
			 $scope.updateUnsuccessful = true;
		});
	}

	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		});
	}

}); //end update credentials controller

adminApp.controller('CreateAssessmentCtrl', function($scope, $http, $mdToast, SITE_URL, API_URL, ROLE) {	

    $scope.showToast = function(message, type) {
        $mdToast.show($mdToast.simple(message)
            .parent(document.querySelectorAll('#toastContainer'))
            .position("top right")
            .action("OKAY")
            .highlightAction(true)
            .highlightClass('toastActionButton')
            .theme(type + '-toast')
            .hideDelay(5000)
        );
    };

    $(document).ready(function() {
        $('#example').DataTable({
            sDom: 'rt',
            fixedColumns: true,
            scrollY: "220px",
            scrollX: false,
            paging: false,
            "ordering": false
        });
        $scope.coreLanguage = false;
        $scope.coreCount = 0;
        $scope.showModal = false;	
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


    $scope.assessments = [];
    $scope.sections = [];
    $scope.totalQuestions = 0;
    $scope.totalCategories = 0;
    $scope.totalTypes = 0;
    $scope.time;

    function UpdateTotals(quantity) {
        $scope.totalCategories = UniqueArraybyId($scope.assessments, "category");
        console.log("Total categories = " + $scope.totalCategories);
        //$scope.totalTypes = UniqueArraybyId($scope.assessments, "type");
        $scope.totalTypes = typeCount($scope.assessments);
        console.log("Total types = " + $scope.totalTypes);
        $scope.totalQuestions += quantity;
        console.log("Total questions = " + $scope.totalQuestions);
    }


    function UniqueArraybyId(collection, keyname) {
        var output = [], keys = [];

        angular.forEach(collection, function(item) {
            var key = item[keyname];
            if (keys.indexOf(key) === -1) {
                keys.push(key);
                output.push(item);
            }
        });
        return output.length;
    };

    //returns number of types in th
    function typeCount(collection){

        var types = 0;
        var mcBool = false;
        var msBool = false;
        var ddBool = false;
        var csBool = false;
        angular.forEach(collection, function(item){

            if(item['mcQuestions'] > 0 && mcBool == false){
                types++;
                mcBool = true;
            }
            if(item['msQuestions'] > 0 && msBool == false){
                types++;
                msBool = true;
            }
            if(item['ddQuestions'] > 0 && ddBool == false){
                types++;
                ddBool = true;
            }
            if(item['csQuestions'] > 0 && csBool == false){
                types++;
                csBool = true;
            }
            
        });
        return types;
    }

    $scope.removeRow = function(index) {
        console.log($scope.assessments[index]['mcQuestions']);
        var mcCount = $scope.assessments[index]['mcQuestions'];
        var msCount = $scope.assessments[index]['msQuestions'];
        var ddCount = $scope.assessments[index]['ddQuestions'];
        var csCount = $scope.assessments[index]['csQuestions'];

        var tempQuantity = mcCount + msCount + ddCount + csCount;

        //var tempQuantity = $scope.assessments[index]['quantity'];
        console.log($scope.assessments + "scopeassessments");

        if($scope.assessments[index]['category'].categoryId == 6){
            $scope.coreCount--;

            if ($scope.coreCount == 0) {
                $scope.coreLanguage = false;
            }

         }


    	 $scope.assessments.splice(index, 1);
    	
         $scope.sections.splice(index,1);

         
         
         
       
        //console.log($scope.assessments[index]['quantity'] + "THIS IS $SCOPE.ASSESMENTS[INDEX]['QUANTITY']");
        UpdateTotals(-tempQuantity);
    };

    $scope.addRow = function() {

        // $scope.temp = $scope.categories[$scope.categories.indexOf($scope.category.valueOf())];

        $scope.sections.push({ 'category': $scope.category, 'type': $scope.type, 'quantity': $scope.quantity });


        var tempCategory = $.grep($scope.categories, function(e){ return e.name == $scope.category; });


        // console.log(result);
        // console.log(result[0].categoryId);
        // console.log(result[0].name);
        // console.log($scope.category);

        var msQuestions=0, mcQuestions=0, ddQuestions=0, csQuestions=0;

        switch($scope.type) {
            case $scope.types[0].formatName : { /* Multiple Choice changed to cs */
                console.log("case - 0");
                csQuestions = $scope.quantity;
                console.log($scope.type); break;
            }
            case $scope.types[1].formatName : { /* Multiple Select changed to dd */
                console.log("case - 1");
                ddQuestions = $scope.quantity;
                console.log($scope.type); break;
            }
            case $scope.types[2].formatName : { /* Drag 'n' Drop changed to mc */
                console.log("case - 2");
                mcQuestions = $scope.quantity;
                console.log($scope.type); break;
            }
            case $scope.types[3].formatName : { /* Code Snippet changed to ms */
                console.log("case - 3");
                msQuestions = $scope.quantity;
                console.log($scope.type); break;
            }
            default : {
                console.log("case - default");
                console.log($scope.type); break;
            }
        }

        if(tempCategory[0].categoryId == 6){
            $scope.coreLanguage = true;
            $scope.coreCount++;
        }


        $scope.assessments.push({
            "category": {
                "categoryId": tempCategory[0].categoryId,
                "name":  tempCategory[0].name
            },
            "msQuestions": msQuestions,
            "mcQuestions": mcQuestions,
            "ddQuestions": ddQuestions,
            "csQuestions": csQuestions

        });


        console.log("Assessments - ");
        console.log($scope.assessments);


        UpdateTotals($scope.quantity);
        $scope.sectionForm.$setPristine();
        $scope.sectionForm.$setUntouched();
        $scope.category = '';
        $scope.type = '';
        $scope.quantity = '';
        $scope.showToast("Success - Section added", "success");
    };

    //creates url and performs AJAX call to appropriate REST endpoint
    //sending the assessment time and criteria
    $scope.createAssessment = function() {

        //build test JSON
        // data = {
        //     "timeLimit": $scope.time,
        //     "categoryRequestList": [{
        //         "category": {
        //             "categoryId": 6,
        //             "name": "core language"
        //         },
        //         "msQuestions": 5,
        //         "mcQuestions": 25,
        //         "ddQuestions": 0,
        //         "csQuestions": 1
        //     }, {
        //         "category": {
        //             "categoryId": 2,
        //             "name": "OOP"
        //         },
        //         "msQuestions": 1,
        //         "mcQuestions": 3,
        //         "ddQuestions": 0,
        //         "csQuestions": 0
        //     }, {
        //         "category": {
        //             "categoryId": 3,
        //             "name": "Data Structures"
        //         },
        //         "msQuestions": 1,
        //         "mcQuestions": 0,
        //         "ddQuestions": 0,
        //         "csQuestions": 1
        //     }, {
        //         "category": {
        //             "categoryId": 4,
        //             "name": "SQL"
        //         },
        //         "msQuestions": 3
        //     }]
        // };

        data = {
            "timeLimit": $scope.time,
            "categoryRequestList": $scope.assessments
        };
        
        if($scope.coreLanguage == false){
        	$scope.showToast("Core Language Section Required", "fail");
        }


        //var sendUrl = SITE_URL.BASE + API_URL.BASE + "/assessmentrequest/" + "1";
        // var sendUrl = SITE_URL.BASE + API_URL.BASE + "/assessmentrequest" + "/1/";
        else if($scope.coreLanguage == true){
                $http({
                    method: 'PUT',
                    url: (SITE_URL.BASE + API_URL.BASE + "/assessmentrequest" + "/1/"),
                    headers: { 'Content-Type': 'application/json' },
                    data: data
                }).success(function(response) {
                    $scope.showToast("Assessment created successfully", "success");
                    console.log("Assessment creation success");
                }).error(function(response) {
                    $scope.showToast("Assessment creation failed", "fail");
                    console.log("Assessment creation failed");
                });
            }

    };




    //initialize data
    $http({
        method: "GET",
        url: "category"
    }).then(function (response) {
        $scope.categories = response.data;
        console.log("Categories - ");
        console.log(response);
        $scope.showToast("Got all categories", "success");
    });

    $http({
        method: "GET",
        url: "rest/format"
    }).then(function (response) {
        $scope.types = response.data;
        console.log("Formats - ");
        console.log(response);
        $scope.showToast("Got all formats", "success");
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

        var path = window.location.pathname.substr(1);

        switch (path) {
            case "index.html":
                return "employees";
            case "update.html":
                return "employees";
            case "New.html":
                return "assessments";
            default:
                return "overview"
        }
    };

    mc.buildToggler = function(navID) {
        return function() {
            $mdSidenav(navID)
                .toggle()
        };
    };
    $scope.isOpenLeft = function() {
        return $mdSidenav('left').isOpen();
    };


    // data
    mc.currentPage = mc.findCurrentPage();
    $scope.toggleLeft = mc.buildToggler('left');


    // $scope.toggleLeft = buildDelayedToggler('left');
    $scope.toggleRight = buildToggler('right');
    $scope.isOpenRight = function() {
        return $mdSidenav('right').isOpen();
    };

    $scope.toggleAss = buildToggler('ass');
    $scope.isOpenAss = function(){
    	return $mdSidenav('ass').isOpen();
    };
    /**
     * Supplies a function that will continue to operate until the
     * time is up.
     */
    function debounce(func, wait, context) {
        var timer;

        return function debounced() {
            var context = $scope,
                args = Array.prototype.slice.call(arguments);
            $timeout.cancel(timer);
            timer = $timeout(function() {
                timer = undefined;
                func.apply(context, args);
            }, wait || 10);
        };
    }

    /**
     * Build handler to open/close a SideNav; when animation finishes
     * report completion in console
     */
    function buildDelayedToggler(navID) {
        return debounce(function() {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav(navID)
                .toggle()
        }, 200);
    }

    function buildToggler(navID) {
        return function() {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav(navID)
                .toggle()
        };
    }

});


adminApp.controller('LeftCtrl', function($scope, $timeout, $mdSidenav, $log) {
    $scope.close = function() {
        // Component lookup should always be available since we are not using `ng-if`
        $mdSidenav('left').close()
            .then(function() {
                $log.debug("close LEFT is done");
            });

    };
});


adminApp.controller('RightCtrl', function($scope, $timeout, $mdSidenav, $log) {
    $scope.close = function() {
        // Component lookup should always be available since we are not using `ng-if`
        $mdSidenav('right').close()
            .then(function() {
                $log.debug("close RIGHT is done");
            });
    };
});


adminApp.controller('manageQuestions', function($scope, $http, SITE_URL, API_URL, ROLE) {
    var mq = this;
});

adminApp.controller('ChooseAssessmentCtrl', function($scope, $http, SITE_URL, API_URL, ROLE){
	
    $scope.assList = [];
    $scope.defaultAss = {};
    $scope.defaultIndex = 0;



//get number of sections for view 
    $scope.getNumOfSec = function(index){
    
    	return $scope.assList[index].categoryRequestList.length;
    }

    $scope.defaultNumOfSec = function(){
        return $scope.defaultAss.categoryRequestList.length;
    }

//gets number of questions for questions
    $scope.getTotalNumOfQuestions = function(index){
        var totalQuestions = 0;
        for(var i = 0; i < $scope.assList[index].categoryRequestList.length; i++){
            totalQuestions = totalQuestions + $scope.assList[index].categoryRequestList[i].csQuestions;
            totalQuestions = totalQuestions + $scope.assList[index].categoryRequestList[i].ddQuestions;
            totalQuestions = totalQuestions + $scope.assList[index].categoryRequestList[i].mcQuestions;
            totalQuestions = totalQuestions + $scope.assList[index].categoryRequestList[i].msQuestions;

        }
        return totalQuestions;
    }
    
    $scope.defaultTotalNumOfQuestions = function(index){
        var totalQuestions = 0;
        for(var i = 0; i < $scope.defaultAss.categoryRequestList.length; i++){
            totalQuestions = totalQuestions + $scope.defaultAss.categoryRequestList[i].csQuestions;
            totalQuestions = totalQuestions + $scope.defaultAss.categoryRequestList[i].ddQuestions;
            totalQuestions = totalQuestions + $scope.defaultAss.categoryRequestList[i].mcQuestions;
            totalQuestions = totalQuestions + $scope.defaultAss.categoryRequestList[i].msQuestions;

        }
        return totalQuestions;
    }


    //gets all the assessments requests
    $http({
        method: "GET",
        url: "allAssessments"
    }).then(function (response) {
        $scope.assList = response.data;
        console.log($scope.assList);

        for(var i = 0; i < $scope.assList.length; i++){
            if($scope.assList[i].isDefault == 1){
                $scope.defaultAss = $scope.assList[i];
                $scope.defaultIndex = i;
            }
        }
    });

    $scope.selectDefault = function(index){
        $http({
            method: "POST",
            url: "selectAssessment",
            data: $scope.assList[index]
        }).then(function(response){

            $http({
                method: "GET",
                url: "allAssessments"
            }).then(function (response) {
                $scope.assList = response.data;

                for(var i = 0; i < $scope.assList.length; i++){
                    if($scope.assList[i].isDefault == 1){
                        $scope.defaultAss = $scope.assList[i];
                        $scope.defaultIndex = i;
                    }
                }
            });
        });

    }
    
    $scope.getQuestionTypeOfCategory = function(category){
    	if(category.csQuestions > 0){
    		var type = "Code Snippet";
    		return type;
    	}
    	else if(category.ddQuestions > 0){
    		var type = "Drag and Drop";
    		return type;
    	}
    	else if(category.mcQuestions > 0){
    		var type = "Multiple Choice";
    		return type;
    	}
    	else if(category.msQuestions > 0){
    		var type = "Multiple Select";
    		return type;
    	}
    }
    
    $scope.getNumberOfQuestionsInCategory = function(category){
    	if(category.csQuestions > 0){
    		var num = category.csQuestions;
    		return num;
    	}
    	else if(category.ddQuestions > 0){
    		var num = category.ddQuestions;
    		return num;
    	}
    	else if(category.mcQuestions > 0){
    		var num = category.mcQuestions;
    		return num;
    	}
    	else if(category.msQuestions > 0){
    		var num = category.msQuestions;
    		return num;
    	}
    }

});

/*adminApp.controller('SettingsViewCtrl', function($scope, $http, SITE_URL, API_URL, ROLE){
	
	var settingsUrl = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + "/globalSettings"
	
	$scope.getSettings = function(){
		$scope.getSettingsUnsuccessful = false;
		$http({
			method  : 'GET',
			url		: settingsUrl
		}).success(function(data){
            if (!data){
                $scope.getSettingsUnsuccessful = true;
            } else {
            	$scope.settings = {};
            	data.forEach(function (s){
            		$scope.settings[s.propertyId] = s;
            	});
            }
		}).error( function() {
			 $scope.getSettingsUnsuccessful = true;
		});
	}
	

	
	  this.findCurrentPage = function() {

	        // var path = $location.path().replace("/", "");
	        var path = window.location.pathname.substr(1);

	        switch(path) {
	            case "aes/registerEmployee" : return "employees";
	            case "aes/updateEmployee" : return "employees";
	            case "aes/createAssessment" : return "assessments";
	            case "aes/globalSettings" : return "globalSettings";
	            default : return "overview"
	        }
	    };
	    
	    this.buildToggler = function(navID) {
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
	
	    this.currentPage = this.findCurrentPage();
	    $scope.toggleLeft = this.buildToggler('left');
	
	$scope.getSettings();
});*/
