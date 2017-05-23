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

adminApp.controller('EmployeeViewCtrl', function($scope,$mdToast, $http, SITE_URL, API_URL, ROLE, $window) {
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
    		return;
    	}
    	
    	// delete the employee if it is not the system user.
    	
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
	
	// display the review-assessment page
	$scope.showAssessment = function(a) {
		
		// clone the assessment passed in so changes to it don't affect the view.
		assessment = {
				assessmentId: a.assessmentId,
				user: a.user,
				grade: a.grade,
				timeLimit: a.timeLimit,
				createdTimeStamp: reformatDate(a.createdTimeStamp), // reformat date of the assessment to an iso format (which spring can convert back into a TimeStamp)
				finishedTimeStamp: reformatDate(a.finishedTimeStamp), // reformat date of the assessment to an iso format (which spring can convert back into a TimeStamp)
				template: a.template,
				options: a.options,
				assessmentDragDrop: a.assessmentDragDrop,
				fileUpload: a.fileUpload
		}
		
		// hold the encoded id of the assessment.
		var encodedId = null;
		
		// get the encoded equivalent of the assessment's id so the quiz review of the assessment can be brought up.
		$http({
			method  : 'POST',
			url		: SITE_URL.BASE + API_URL.BASE + "/rest/encode",
			headers : {'Content-Type' : 'application/json'},
			data    : assessment

		}).success( function(response) {
		    if(!response){
		       
            }
            else {
            	

            	var asmtId = response.data;
            	
            	
            	encodedId = asmtId;
            	
            	// bring up the review assessment page.
            	window.location = SITE_URL.BASE + API_URL.BASE + '/quizReview?asmt=' + encodedId;
            }
		}).error(function() {
					});
		
	};
	
    // open/close viewing assessments for a candidate.
    $scope.viewAssessments = function(num, email){
        $scope.assessments = [];
        $scope.returnCheck = false;
        $http
            .get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + "/" + email + "/assessments") // get the assessments of this candidate
            .then(function(response) {
                var asmt = response.data;
                if (asmt.length != 0) {
                    asmt.forEach(a=>{ a.createdTimeStamp = a.createdTimeStamp = formatDate(a.createdTimeStamp);
                    a.finishedTimeStamp = formatDate(a.finishedTimeStamp)});
                }
                $scope.assessments = asmt;
                $scope.returnCheck = true;
            });

        var myEl = angular.element( document.querySelector( '#'+num ) );
        for(var i = 0; i < $scope.employees.length; i++){
            var close = angular.element( document.querySelector( '#g'+$scope.employees[i].userId ) );
            if((close[0].attributes[0].nodeValue == "ng-show" || close[0].attributes[1].nodeValue == "ng-show") && '#'+num != '#g'+$scope.employees[i].userId){
                close.removeClass("ng-show");
                close.addClass("ng-hide");
            }
        }

        if(angular.element(document.querySelector('#'+num).classList)[0] == "ng-hide"){
            myEl.removeClass("ng-hide");
            myEl.addClass("ng-show");
        } else {
            myEl.removeClass("ng-show");
            myEl.addClass("ng-hide");
        }
    };
    
    // check whether an employee is a candidate (used in deciding whether to show view-assessments button)
    $scope.isCandidate = function(employee) {
    	if (employee.role.roleTitle.toUpperCase() === "CANDIDATE") {
    		return true;
    	}
    	else {
    		return false;
    	}
    };
    
    // closes all assessments being viewed (necessary when searching or re-ordering the display of employees) //FIXME: when searchbar has input, opening assessments breaks
    $scope.closeAllAssessments = function() {
        for(var i = 0; i < $scope.employees.length; i++){
            var close = angular.element( document.querySelector( '#g'+$scope.employees[i].userId ) );
            if((close[0].attributes[0].nodeValue == "ng-show" || close[0].attributes[1].nodeValue == "ng-show")){
                close.removeClass("ng-show");
                close.addClass("ng-hide");
            }
        }
    }
    
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

	

	
	$scope.validateReview = function ()
	{
		
		
		if(($scope.assdays == null || $scope.asshours ==null || $scope.asshours <0 || $scope.assdays<0 )||(($scope.assdays ==0 && $scope.asshours == 0 ) && $scope.assReviewCheck == true))
		
			{
			
			
			 return true;
			
			
			}
		else
			{
			
			return false;
			}
			
		
		
	}
	
	$scope.allowReview = function()
	{
		
		var totalHours = 0;
		

		if($scope.assReviewCheck)
			{
			totalHours= ($scope.assdays * 24) + $scope.asshours;
			$scope.assdays = 0;
			$scope.asshours = 0; 
			}
		else
			{
			$scope.assdays = 0;
			$scope.asshours = 0; 
			}
		
	}
	
	
	
	$scope.checkDuplicate = function () {
		 var flag = false;
		 var count = 0;

			angular.forEach($scope.sections , function (category) {
			
				if( $scope.category == category.category && $scope.type == category.type)
					{

						flag = true;
					}
			
	count ++;

			});

			
				if( flag == true)
				{
					return true;

				}
				else {
					return false;
		
					}
		};
	
	
	
	
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
    	$scope.assdays = 0;
		$scope.asshours = 0; 
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
        $scope.totalTypes = typeCount($scope.assessments);
        $scope.totalQuestions += quantity;
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
        
        var mcCount = $scope.assessments[index]['mcQuestions'];
        var msCount = $scope.assessments[index]['msQuestions'];
        var ddCount = $scope.assessments[index]['ddQuestions'];
        var csCount = $scope.assessments[index]['csQuestions'];

        var tempQuantity = mcCount + msCount + ddCount + csCount;

        if($scope.assessments[index]['category'].categoryId == 6){
            $scope.coreCount--;

            if ($scope.coreCount == 0) {
                $scope.coreLanguage = false;
            }

         }


    	 $scope.assessments.splice(index, 1);
    	
         $scope.sections.splice(index,1);

         
         
         
       
       
        UpdateTotals(-tempQuantity);
    };

    $scope.addRow = function() {


    	$scope.catInt;
    	$scope.typeInt;
    	$scope.notEnoughQuestions;
    	
    	(function(){
    		
    		if($scope.category.toUpperCase() === "CSS"){
    	    	$scope.catInt = 11;

    		}else if($scope.category.toUpperCase() === "CORE LANGUAGE"){
    	    	$scope.catInt = 1;


    		}else if($scope.category.toUpperCase() === "CRITICAL THINKING"){
    	    	$scope.catInt = 5;

    		}else if($scope.category.toUpperCase() === "DATA STRUCTURES"){
    	    	$scope.catInt = 3;

    		}else if($scope.category.toUpperCase() === "HTML"){
    	    	$scope.catInt = 10;

    		}else if($scope.category.toUpperCase() === "JAVASCRIPT"){
    	    	$scope.catInt = 12;

    		}else if($scope.category.toUpperCase() === "OOP"){
    	    	$scope.catInt = 2;

    		}else if($scope.category.toUpperCase() === "SQL"){
    	    	$scope.catInt = 4;

    		}
    		
    		if($scope.type.toUpperCase() === "MULTIPLE CHOICE"){
    			$scope.typeInt = 1;
    		}else if($scope.type.toUpperCase() === "MULTIPLE SELECT"){
    			$scope.typeInt = 2;

    		}else if($scope.type.toUpperCase() === "DRAG AND DROP"){
    			$scope.typeInt = 3;

    		}else if($scope.type.toUpperCase() === "CODE SNIPPET"){
    			$scope.typeInt = 4;

    		}
    		
    	})();
    	
        $http({
            method: "GET",
            url: ("assessmentrequest/"+$scope.catInt  + "/" + $scope.typeInt +"/" + $scope.quantity + "/")
        }).then(function (response) {
            $scope.numOfQuestions = response.data;
            if($scope.quantity > $scope.numOfQuestions){
            	alert("There are only " + $scope.numOfQuestions + " of those questions available.");
            }else{
            	 $scope.sections.push({ 'category': $scope.category, 'type': $scope.type, 'quantity': $scope.quantity });


                 var tempCategory = $.grep($scope.categories, function(e){ return e.name == $scope.category; });


                 var msQuestions=0, mcQuestions=0, ddQuestions=0, csQuestions=0;

                 switch($scope.type) {
                     case $scope.types[0].formatName : { /* Multiple Choice changed to cs */
                        
                         csQuestions += $scope.quantity;
                          break;
                     }
                     case $scope.types[1].formatName : { /* Multiple Select changed to dd */
                         
                         ddQuestions += $scope.quantity;
                          break;
                     }
                     case $scope.types[2].formatName : { /* Drag 'n' Drop changed to mc */
                        
                         mcQuestions += $scope.quantity;
                         break;
                     }
                     case $scope.types[3].formatName : { /* Code Snippet changed to ms */
                         
                         msQuestions += $scope.quantity;
                          break;
                     }
                     default : {
                         
                          break;
                     }
                 }

                 if(tempCategory[0].categoryId == 6){
                     $scope.coreLanguage = true;
                     $scope.coreCount++;
                    
                     if($scope.assessments.length > 0){
                    	 var indexOfCoreLanguage = 0;
	                     for(var i = 0; i < $scope.assessments.length; i++){
	                    	 if($scope.assessments[i].category.categoryId == 6){
	                    		 indexOfCoreLangage = i;
	                    	 }
	                     }
	                     $scope.assessments[indexOfCoreLanguage].csQuestions += csQuestions;
	                     $scope.assessments[indexOfCoreLanguage].msQuestions += msQuestions;
	                     $scope.assessments[indexOfCoreLanguage].ddQuestions += ddQuestions;
	                     $scope.assessments[indexOfCoreLanguage].mcQuestions += mcQuestions;
                     }else{
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
                     }
                     
                 }else{
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
                 }
                 
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
            	 
            	 if($scope.asshours + ($scope.assdays * 24) == 0){
            		 $scope.totalHourz = null;
            	 }else{
            		 $scope.totalHourz = $scope.asshours + ($scope.assdays * 24);
            	 }


                 data = {
                     "timeLimit": $scope.time,
                     "categoryRequestList": $scope.assessments,
                     "name": $scope.name,
                     "hoursViewable" : $scope.totalHourz,
                     "isDefault" : 0
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
                            
                         }).error(function(response) {
                             $scope.showToast("Assessment creation failed", "fail");
                            
                         });
                     }
            }
        });


    };




    //initialize data
    $http({
        method: "GET",
        url: "category"
    }).then(function (response) {
        $scope.categories = response.data;
        
        $scope.showToast("Got all categories", "success");
    });

    $http({
        method: "GET",
        url: "rest/format"
    }).then(function (response) {
        $scope.types = response.data;
       
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
        var path = window.location.pathname.substr(5);

        switch (path) {
            case "index.html":
                return "employees";
            case "update.html":
                return "employees";
            case "registerEmployee":
                return "employees";
            case "updateEmployee":
                return "employees";
            case "manageQuestions":
                return "questions";
            case "addQuestions":
                return "questions";
            case "chooseAssessment":
                return "assessments";
            case "createAssessment":
                return "assessments";
            case "New.html":
                return "assessments";
            case "parser":
                return "parser";
            case "globalSettings":
                return "globalSettings";
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

adminApp.controller('ChooseAssessmentCtrl', function($scope, $mdToast, $http, $anchorScroll, SITE_URL, API_URL, ROLE){
	
	//list of assessments used to manipulate
    $scope.assList = [];
    
    //the default assessment stored into different object
    $scope.defaultAss = {};
    //default assessment is still in assList. Index is used to manipulate the default in the list
    $scope.defaultIndex = 0;
    
    //sets toast function
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
    
    //function for default assessments total numberof questsion
    
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
       

        for(var i = 0; i < $scope.assList.length; i++){
            
        	$scope.assList[i].numSections = $scope.getNumOfSec(i);
        	$scope.assList[i].numQuestions = $scope.getTotalNumOfQuestions(i);
        	$scope.assList[i].index = i;
        	
            if($scope.assList[i].hoursViewable == null){
            	$scope.assList[i].allowed = false;
            }else{
            	$scope.assList[i].allowed = true;
            	$scope.assList[i].days = Math.floor($scope.assList[i].hoursViewable/24).toString();
            	$scope.assList[i].hours = ($scope.assList[i].hoursViewable % 24).toString();
            }
            if($scope.assList[i].isDefault == 1){
            	
                $scope.defaultAss = $scope.assList[i];
                $scope.defaultIndex = i;
                $scope.defaultAss.allow = $scope.assList[i].allowed;
            }
        }
    });

    //function for selecting default. will repopulate list after default is selected
    $scope.selectDefault = function(index, $window){
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
                	
                	$scope.assList[i].numSections = $scope.getNumOfSec(i);
                	$scope.assList[i].numQuestions = $scope.getTotalNumOfQuestions(i);
                	$scope.assList[i].index = i;
                   
                    if($scope.assList[i].hoursViewable == null){
                    	$scope.assList[i].allowed = false;
                    }else{
                    	$scope.assList[i].allowed = true;
                    	$scope.assList[i].days = Math.floor($scope.assList[i].hoursViewable/24).toString();
                    	$scope.assList[i].hours = ($scope.assList[i].hoursViewable % 24).toString();
                    }
                    if($scope.assList[i].isDefault == 1){
                        $scope.defaultAss = $scope.assList[i];
                        $scope.defaultIndex = i;
                    }
                }
            });
        });
        $scope.showToast("New Default Selected", "success");
        $anchorScroll();
    }
    
    /* Created by jesse 5/21/2017 */
    $scope.deleteAssessment = function(index, $window){
        $http({
            method: "POST",
            url: "assessmentrequest/delete",
            data: $scope.assList[index]
        }).then(function(response){

            $http({
                method: "GET",
                url: "allAssessments"
            }).then(function (response) {
                $scope.assList = response.data;
                

                for(var i = 0; i < $scope.assList.length; i++){
                	
                	$scope.assList[i].numSections = $scope.getNumOfSec(i);
                	$scope.assList[i].numQuestions = $scope.getTotalNumOfQuestions(i);
                	$scope.assList[i].index = i;
                   
                    if($scope.assList[i].hoursViewable == null){
                    	$scope.assList[i].allowed = false;
                    }else{
                    	$scope.assList[i].allowed = true;
                    	$scope.assList[i].days = Math.floor($scope.assList[i].hoursViewable/24).toString();
                    	$scope.assList[i].hours = ($scope.assList[i].hoursViewable % 24).toString();
                    }
                    if($scope.assList[i].isDefault == 1){
                        $scope.defaultAss = $scope.assList[i];
                        $scope.defaultIndex = i;
                    }
                }
            });
        });
        $scope.showToast("Successfully Deleted Assessment", "success");
        $anchorScroll();
    }
    
    //gets each question type of the category
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
    
    //function to count the number of questions in each category for expanded view
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
    
    

    
    //checks if user tries to change it over a year long
    $scope.overAYear = function(days){
    	var checkDays = parseInt(days);
    	if (checkDays > 365){

    		return true;
    		
    	}
    	return false;
    }
    //checks if user tries to enter over 24 hours
    $scope.over24Hours = function(hours){
    	var checkHours = parseInt(hours);
    	if(checkHours > 23){
    		return true;
    	}
    	return false;
    }
    
    //function to update the hours of the assessment
    $scope.updateHours = function(index){
    	$scope.assList[$scope.defaultIndex].days = $scope.defaultAss.days;
    	$scope.assList[$scope.defaultIndex].hours = $scope.defaultAss.hours;
    	$scope.assList[$scope.defaultIndex].allowed = $scope.defaultAss.allowed;
    	if($scope.assList[index].allowed){
	    	var daysHours = parseInt($scope.assList[index].days) * 24;
	    	var totalHours = daysHours + parseInt($scope.assList[index].hours);
	    	$scope.assList[index].hoursViewable = totalHours;
    	}else{
    		$scope.assList[index].hoursViewable = null;
    	}
    	
    	
            $http({
                method: "POST",
                url: "updateViewableHours",
                data: $scope.assList[index]
            }).then(function(response){
                $http({
                    method: "GET",
                    url: "allAssessments"
                }).then(function (response) {
                    $scope.assList = response.data;
                    

                    for(var i = 0; i < $scope.assList.length; i++){
                        
                        if($scope.assList[i].hoursViewable == null){
                        	$scope.assList[i].allowed = false;
                        }else{
                        	$scope.assList[i].allowed = true;
                        	$scope.assList[i].days = Math.floor($scope.assList[i].hoursViewable/24).toString();
                        	$scope.assList[i].hours = ($scope.assList[i].hoursViewable % 24).toString();
                        }
                        if($scope.assList[i].isDefault == 1){
                            $scope.defaultAss = $scope.assList[i];
                            $scope.defaultIndex = i;
                        }
                    }
                });
            });
            
            $scope.showToast("Updated Review Allowed", "success");

        }
    	
    	
 

});

//directive used to allow only nubmer inputs for text inputs. 
adminApp.directive('customValidation', function(){
	   return {
	     require: 'ngModel',
	     link: function(scope, element, attrs, ChooseAssessmentCtrl) {

	    	 ChooseAssessmentCtrl.$parsers.push(function (inputValue) {  	
	    	
	         var transformedInput = inputValue.replace(/[^0-9]/g, ''); 

	         if (transformedInput!=inputValue) {
	        	 ChooseAssessmentCtrl.$setViewValue(transformedInput);
	        	 ChooseAssessmentCtrl.$render();
	         }         

	         return transformedInput;         
	       });
	     }
	   };
	});

// formats dates from being in total-milliseconds-from-epoch format into a human-readable, presentable format.
function formatDate(date) {
    if (date == null) {
        return "";
    }
    var d = new Date(date);
    var min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
    return (d.getMonth() + 1) + "/" + d.getDate() + "/" + d.getFullYear() + " " + d.getHours() + ":" + min;
}

// reformats dates formatted by formatDate() from (M/D/YYYY H:MM) to an iso format (YYYY-MM-DDTHH:mm:ss.SSSZ)
function reformatDate(date) {
	if (!date || date == null) {
		return "";
	}
	
	// split up the date and time components to make easier to change.
	var datetime = date.split(' ');
	
	// reformat the calendar-date components.
	var oldDate = datetime[0];
	var oldDateComponents = oldDate.split('/');
	var YYYY = oldDateComponents[2];
	var MM = oldDateComponents[0].length === 1 ? '0' + oldDateComponents[0] : oldDateComponents[0]; // month should be in the form 'MM'.
	var DD = oldDateComponents[1];
	
	// reformat the clock-time components.
	var oldTime = datetime[1];
	var oldTimeComponents = oldTime.split(':');
	var hh = oldTimeComponents[0].length === 1 ? '0' + oldTimeComponents[0] : oldTimeComponents[0]; // hours should be in the form 'hh'.
	var mm = oldTimeComponents[1];
	var ss = '00'; // seconds aren't preserved in formatDate() from the original TimeStamp, so here just hardcoding 0...
	var sss = '000'; // same situation as seconds, so here just hardcoding 0 for milliseconds...
	
	// assemble the iso date from the reformatted date and time components.
	var isoDate = YYYY + '-' + MM + '-' + DD + 'T' + // add the date components.
		hh + ':' + mm + ':' + ss + '.' + sss + 'Z'; // add the time components.
	
	// return the iso-formatted version of the date.
	return isoDate;
}

// formats dates from being in total-milliseconds-from-epoch format into an iso format (YYYY-MM-DDThh:mm:ss.sssZ).
function formatDateIso(date) {
	if (!date || date == null) {
		return "";
	}
	
	var jsDate = new Date(date);
	
	// get the calendar-date components of the iso date. 
	var YYYY = jsDate.getFullYear();
	var MM = (jsDate.getMonth() + 1) < 10 ? '0' + (jsDate.getMonth() + 1) : (jsDate.getMonth() + 1); // month should be in the form 'MM'.
	var DD = jsDate.getDate() < 10 ? '0' + jsDate.getDate() : jsDate.getDate(); // day should be in the form 'DD'.
	
	// get the clock-time components of the iso date.
	var hh = jsDate.getHours() < 10 ? '0' + jsDate.getHours() : jsDate.getHours(); // hours should be in the form 'hh'.
	var mm = jsDate.getMinutes() < 10 ? '0' + jsDate.getMinutes() : jsDate.getMinutes(); // minutes should be in the form 'mm'.
	var ss = jsDate.getSeconds() < 10 ? '0' + jsDate.getSeconds() : jsDate.getSeconds(); // seconds should be in the form 'ss'.
	var sss = '000'; //just hardcoded 000 for milliseconds...
	
	// assemble the iso date from the date and time components.
	var isoDate = YYYY + '-' + MM + '-' + DD + 'T' + // add the date components.
		hh + ':' + mm + ':' + ss + '.' + sss + 'Z'; // add the time components.
		
	// return the iso-formatted version of the date.
	return isoDate;
}
