var AESCoreApp = angular.module('AESCoreApp', ['ngMaterial', 'ngMessages']);



AESCoreApp.constant("SITE_URL", {
    "HTTP" : "http://",
    "HTTPS": "https://",
    "BASE" : "",
    "PORT" : ":8080",

    "LOGIN": "index",
    "TRAINER_HOME" : "",
    "VIEW_CANDIDATES" : "view",
    "VIEW_EMPLOYEES" : "viewEmployees",
    "REGISTER_CANDIDATE" : "",
    "REGISTER_EMPLOYEE" : "",
    "ASSESSMENT_LANDING" : "assessmentLandingPage"
});


AESCoreApp.constant("API_URL", {
    "BASE"      : "/aes",
    "LOGIN"     : "/login",
    "LOGOUT"    : "/logout",
    "AUTH"      : "/security/auth",
    "CANDIDATE" : "/candidate/",
    "RECRUITER" : "/recruiter/",
    "LINK"      : "/link",
    "CANDIDATES": "/candidates"
});


AESCoreApp.constant("ROLE", {
    "RECRUITER" : "ROLE_RECRUITER",
    "TRAINER"   : "ROLE_TRAINER",
    "CANDIDATE" : "ROLE_CANDIDATE",
    "ADMIN"		: "ROLE_ADMIN"
});



AESCoreApp.config(function($mdThemingProvider) {

    var revOrangeMap = $mdThemingProvider.extendPalette("deep-orange", {
        "A200": "#F26925",
        "100": "rgba(242, 105, 37, 0.2)"
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

//On enter event
AESCoreApp.directive('onEnter', function() {
  return function(scope, elm, attr) {
    elm.bind('keypress', function(e) {
      if (e.keyCode === 13) {
        scope.$apply(attr.onEnter);
      }
    });
  };
});

// Inline edit directive
AESCoreApp.directive('inlineEdit', function($timeout) {
  return {
    scope: {
      model: '=inlineEdit',
      handleSave: '&onSave',
      handleCancel: '&onCancel'
    },
    link: function(scope, elm, attr) {
      var previousValue;
      
      scope.edit = function() {
        scope.editMode = true;
        previousValue = scope.model;
        
        $timeout(function() {
          elm.find('input')[0].focus();
        }, 0, false);
      };
      scope.save = function() {
        scope.editMode = false;
        
      };
      scope.cancel = function() {
        scope.editMode = false;
        scope.model = previousValue;
        scope.handleCancel({value: scope.model});
      };
    },
    template: '<div><input type="text" on-enter="save()" on-esc="cancel()" ng-model="model" ng-show="editMode">'
    	+ '<button ng-click="cancel()" ng-show="editMode"><span class="glyphicon glyphicon-remove"></span></button>'
    	+ '<button ng-click="save()" ng-show="editMode"><span class="glyphicon glyphicon-ok"></span></button>'
    	+ '<span ng-mouseenter="showEdit = true" ng-mouseleave="showEdit = false">'
    	+ '<span ng-hide="editMode" ng-click="edit()">{{model}}</span>'
    	+ '<a ng-show="showEdit" ng-click="edit()"><span class="glyphicon glyphicon-pencil"></span></a>'
    	+ '</span></div>'
  };
});

AESCoreApp.controller('CandidateCtrl', function($scope,$mdToast,$location,$http,SITE_URL, API_URL, ROLE) {
		
    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
        .then(function(response) {
            if (response.data.authenticated) {
                var authUser = {
                    username : response.data.principal.username,
                    authority: response.data.principal.authorities[0].authority
                }
                $scope.authUser = authUser;
                if($scope.authUser.authority != ROLE.RECRUITER) {
                    window.location = SITE_URL.LOGIN;
                }
                $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + $scope.authUser.username + API_URL.CANDIDATES)
                    .then(function(response) {
                        //$scope.candidates = response.data;
                        var c =  response.data;
                        for (var i=0; i<c.length; i++) {
                            if (c[i].grade == -1) {
                                c[i].grade = 'N/A';
                            }
                            c[i].expanded = false;
                        }
                        $scope.candidates = c;
                    })
            } else {
                window.location = SITE_URL.LOGIN;
            }
        });

    $scope.show2 = function(num, email){
        $scope.assessments = [];
        $scope.returnCheck = false;
        $http
            .get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + email + "/assessments")
            .then(function(response) {
                var asmt = response.data;
                console.log(asmt);
                if (asmt.length != 0) {
                    asmt.forEach(a=>{ a.createdTimeStamp = formatDate(a.createdTimeStamp);
                    a.finishedTimeStamp = formatDate(a.finishedTimeStamp)});
                }
                $scope.assessments = asmt;
                $scope.returnCheck = true;
            });

        var myEl = angular.element( document.querySelector( '#'+num ) );
        for(var i = 0; i < $scope.candidates.length; i++){
            var close = angular.element( document.querySelector( '#g'+$scope.candidates[i].userId ) );
            if((close[0].attributes[0].nodeValue == "ng-show" || close[0].attributes[1].nodeValue == "ng-show") && '#'+num != '#g'+$scope.candidates[i].userId){
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

    $scope.show = function(num){
    	
        var myEl = angular.element( document.querySelector( '#'+num ) );
        if(angular.element(document.querySelector('#'+num).classList)[0] == "ng-hide"){
            myEl.removeClass("ng-hide");
            myEl.addClass("ng-show");
        } else {
            myEl.removeClass("ng-show");
            myEl.addClass("ng-hide");
        }
    };
    
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
	
	// reset form and refresh page's cache of emails and recruiters
	$scope.resetRegistrationForm = function() {
		// reset all form state variables
		$scope.allEmails = [];
		$scope.buttonToggle = false; // by default
	}
	
	$scope.initializeRegistrationSelects = function() {
		// get all emails from the database
		$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + "/emails")
		.then(function(result) {
			$scope.allEmails = result.data;
			console.log($scope.allEmails);
		});
	}

    $scope.register = function() {

        var candidateInfo = {
            userId        : null,
            email         : $scope.email,
            firstName     : $scope.firstName,
            lastName      : $scope.lastName,
            salesforce    : null,
            recruiterId   : null,
            role          : null,
            datePassIssued: null,
            format		  : null // $scope.program.value
        };
        $scope.postRegister(candidateInfo);

        $scope.firstName = '';
        $scope.lastName = '';
        $scope.email = '';
    };

    $scope.postRegister = function(candidateInfo) {
    	$scope.registerSuccessfulMsg = false;
    	$scope.registerUnsuccessfulMsg = false;
    	
    	$http({
            method  : 'POST',
            url: SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + $scope.authUser.username + API_URL.CANDIDATES,
            headers : {'Content-Type' : 'application/json'},
            data    : candidateInfo
        }).success( function(res) {
        	$scope.registerSuccessfulMsg = true;
        	
        	// clear form.
		    $scope.resetRegistrationForm();
		    $scope.initializeRegistrationSelects();
        }).error( function(res) {
        	$scope.registerUnsuccessfulMsg = true;
        });
    };

    $scope.sendAssessment = function(candidateEmail,program) {
        var candidateInfo = {
            userId        : null,
            email         : candidateEmail,
            lastName      : null,
            salesforce    : null,
            recruiterId   : null,
            role          : null,
            datePassIssued: null,
            format		  : program
        };
        $scope.postSendAssessment(candidateInfo);

        return true;
    };
    
    $scope.showToast = function(message) {
    	$mdToast.show($mdToast.simple().textContent(message).parent(document.querySelectorAll('#toastContainer')).position("top right").action("OKAY").highlightAction(true));
    };

    $scope.postSendAssessment = function(candidateInfo) {
        $scope.sendSuccessful = false;
        
        $http({
            method  : 'POST',
            url: SITE_URL.BASE + API_URL.BASE +"/recruiter/candidate/assessment",
            headers : {'Content-Type' : 'application/json'},
            data    : candidateInfo
        }).success( function() {
            $scope.sendSuccessful = true;
            $scope.showToast("Successfully Sent an Assessment");
        }).error( function() {
            $scope.showToast("Failed to Send an Assessment");
        });
        console.log($scope.sendSuccessful);
    };

    $scope.options = [{
        name: 'Java',
        value: 'Java'
    }, {
        name: '.NET',
        value: '.net'
    }];

    $scope.logout = function() {
        window.location = API_URL.BASE + API_URL.LOGOUT;
    };

    //data
    $scope.candidates;
    
    // first time retrieving emails from the database,
    // when page loads
    $scope.initializeRegistrationSelects();

});

function sleep(milliseconds) {
	  var start = new Date().getTime();
	  for (var i = 0; i < 1e7; i++) {
	    if ((new Date().getTime() - start) > milliseconds){
	      break;
	    }
	  }
	}

function formatDate(date) {
    if (date == null) {
        return "";
    }
    var d = new Date(date);
    var min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
    return (d.getMonth() + 1) + "/" + d.getDate() + "/" + d.getFullYear() + " " + d.getHours() + ":" + min;
}


AESCoreApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
	 var mc = this;

	    // functions
	    // sets navbar to current page even on refresh
	    mc.findCurrentPage = function() {

	        // var path = $location.path().replace("/", "");
	        var path = window.location.pathname.substr(1);

	        switch(path) {
	            case "aes/recruit" : return "register";
	            case "aes/updateUser" : return "settings";
	            default : return "overview"
	        }
	    };
	    
	    mc.currentPage = mc.findCurrentPage();

	   /* mc.buildToggler = function(navID) {
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

	 // $scope.toggleLeft = buildDelayedToggler('left');
	    $scope.toggleRight = buildToggler('right');
	    $scope.isOpenRight = function() {
	        return $mdSidenav('right').isOpen();
	    };

	    $scope.toggleAss = buildToggler('ass');
	    $scope.isOpenAss = function(){
	    	return $mdSidenav('ass').isOpen();
	    };
	    
	    function buildToggler(navID) {
	        return function() {
	            // Component lookup should always be available since we are not using `ng-if`
	            $mdSidenav(navID)
	                .toggle()
	        };
	    }*/

});

AESCoreApp.controller('LoginCtrl', function($scope, $httpParamSerializerJQLike, $http, SITE_URL, API_URL, ROLE) {

    $scope.login = function() {
        makeUser($scope);
        $http({
            method : "POST",
            url : SITE_URL.BASE + API_URL.BASE + API_URL.LOGIN,
            headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8;'},
            data: $httpParamSerializerJQLike($scope.user)
        })
            .then(function(response) {
                $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
                    .then(function(response) {
                        if (response.data.authenticated) {
                            var authUser = {
                                username : response.data.principal.username,
                                authority: response.data.principal.authorities[0].authority
                            }
                            $scope.authUser = authUser;
                            switch ($scope.authUser.authority) {
                                case ROLE.RECRUITER:
                                    window.location = SITE_URL.VIEW_CANDIDATES;
                                    break;
                                case ROLE.CANDIDATE:
                                   /* $scope.candidateEmail = authUser.username;
                                    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.CANDIDATE + $scope.candidateEmail + API_URL.LINK)
                                        .then(function(response) {
                                            window.location = response.data.urlAssessment;
                                        })*/
                                	window.location = SITE_URL.ASSESSMENT_LANDING;
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
