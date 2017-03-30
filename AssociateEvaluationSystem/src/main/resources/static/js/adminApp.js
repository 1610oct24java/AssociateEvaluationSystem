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

	$scope.register = function() {

		var employeeInfo = {
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
            lastName      : $scope.lastName,
            salesforce    : null,
            recruiterId   : null,
            role      	: null, //this is hardcoded in createEmployee. I'm not proud of this. -Sledgehammer
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

		}).success( function(data) {
		    if(!data || data.msg == "fail"){
		        $scope.registerUnsuccessfulMsg = true;
            }
            else {
                $scope.registerSuccessfulMsg = true;
            }
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
});

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
	});

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
	};

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

	$scope.update= function() {
		$scope.passNotMatch = false;
		$scope.passNotEntered = false;
		$scope.emailNotEntered = false;
		$scope.userNotFound = false;

		var employeeInfo = {
			oldEmail	: $scope.oldEmail,
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
    $scope.showToast("Success - Section added", "success");


    /* Converts Current Sections Table into a 'DataTables' table */
    $(document).ready(function() {
        $('#example').DataTable({
            sDom: 'rt',
            scrollY: "220px",
            scrollX: false,
            retrieve: true,
            paging: false
        });
    });


    /* Quantity of questions per format/type */
    var msQuestions = 0,
        mcQuestions = 0,
        ddQuestions = 0,
        csQuestions = 0;


    $scope.counts = [{ "category": "JavaScript", "formatType": "Multiple Choice", "total": 32 }, { "category": "OOP", "formatType": "Multiple Select", "total": 2 }, { "category": "C#", "formatType": "Multiple Select", "total": 8 }, { "category": "Java", "formatType": "Multiple Select", "total": 13 }, { "category": "CSS", "formatType": "Multiple Select", "total": 2 }, { "category": "Critical Thinking", "formatType": "Drag and Drop", "total": 1 }, { "category": "OOP", "formatType": "Multiple Choice", "total": 19 }, { "category": "C#", "formatType": "Multiple Choice", "total": 39 }, { "category": "HTML", "formatType": "Multiple Select", "total": 2 }, { "category": "Data Structures", "formatType": "Multiple Choice", "total": 6 }, { "category": "SQL", "formatType": "Drag and Drop", "total": 1 }, { "category": "SQL", "formatType": "Multiple Choice", "total": 17 }, { "category": "Data Structures", "formatType": "Multiple Select", "total": 2 }, { "category": "SQL", "formatType": "Multiple Select", "total": 4 }, { "category": "CSS", "formatType": "Multiple Choice", "total": 1 }, { "category": "Java", "formatType": "Multiple Choice", "total": 171 }, { "category": "JavaScript", "formatType": "Multiple Select", "total": 2 }, { "category": "Critical Thinking", "formatType": "Multiple Choice", "total": 13 }, { "category": "HTML", "formatType": "Multiple Choice", "total": 3 }, { "category": "Java", "formatType": "Code Snippet", "total": 9 }];
    $scope.types = [{ "formatId": 4, "formatName": "Code Snippet" }, { "formatId": 3, "formatName": "Drag and Drop" }, { "formatId": 1, "formatName": "Multiple Choice" }, { "formatId": 2, "formatName": "Multiple Select" }];
    $scope.categories = [{ "categoryId": 9, "name": "C#", "$$hashKey": "object:89" }, { "categoryId": 11, "name": "CSS", "$$hashKey": "object:90" }, { "categoryId": 5, "name": "Critical Thinking", "$$hashKey": "object:91" }, { "categoryId": 3, "name": "Data Structures", "$$hashKey": "object:92" }, { "categoryId": 10, "name": "HTML", "$$hashKey": "object:93" }, { "categoryId": 1, "name": "Java", "$$hashKey": "object:94" }, { "categoryId": 12, "name": "JavaScript", "$$hashKey": "object:95" }, { "categoryId": 2, "name": "OOP", "$$hashKey": "object:96" }, { "categoryId": 4, "name": "SQL", "$$hashKey": "object:97" }, { "categoryId": 6, "name": "core language", "$$hashKey": "object:98" }];
    $scope.times = [15, 30, 45, 60, 75, 90];



    /***********************************************************************************************************/
    /***********************************************************************************************************/
    /***********************************************************************************************************/
    /***********************************************************************************************************/
    /* Breaking down DB view in to special lists for some special shit!!! */
    $scope.uniqueCats = NewUniqueArraybyId($scope.counts, "category");

    function NewUniqueArraybyId(collection, keyname) {
        var keys = [],
            whatIWant = [];
        var formatQuantities;

        angular.forEach(collection, function(item) {
            var key = item[keyname];
            if (keys.indexOf(key) === -1) {
                keys.push(key);
            } else {
                var index = keys.indexOf(key);
            }
        });

        angular.forEach(keys, function(key) {
            var myData = { "category": key, "formats": [] };
            formatQuantities = { "name": "Multiple Choice", "type": "mcQuestions", "total": 0 };
            myData.formats.push(formatQuantities);
            formatQuantities = { "name": "Multiple Select", "type": "msQuestions", "total": 0 };
            myData.formats.push(formatQuantities);
            formatQuantities = { "name": "Drag and Drop", "type": "ddQuestions", "total": 0 };
            myData.formats.push(formatQuantities);
            formatQuantities = { "name": "Code Snippet", "type": "csQuestions", "total": 0 };
            myData.formats.push(formatQuantities);
            whatIWant.push(myData);
        });

        console.log(whatIWant[0]);

        var test = keys.indexOf("OOP");
        console.log(test);

        return keys;
    };

    function getMaxMin() {
        $scope.minQuantity = Math.min.apply(Math, $scope.counts.map(function(item) {
            return item.total;
        }));
        console.log("Min = " + $scope.minQuantity);
        $scope.maxQuantity = Math.max.apply(Math, $scope.counts.map(function(item) {
            return item.total;
        }));
        console.log("Max = " + $scope.maxQuantity);
    };


    $scope.updateCatAndTypes = function() {

    };
    /***********************************************************************************************************/
    /***********************************************************************************************************/
    /***********************************************************************************************************/
    /***********************************************************************************************************/
    $scope.sections = [];
    $scope.assessments = [];
    $scope.totalQuestions = 0;
    $scope.totalCategories = 0;
    $scope.totalTypes = 0;
    $scope.time;

    function UpdateTotals(quantity) {
        $scope.totalCategories = UniqueArraybyId($scope.sections, "category");
        $scope.totalTypes = UniqueArraybyId($scope.sections, "type");
        $scope.totalQuestions += quantity;
    };


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
        var tempQuantity = $scope.sections[index]['quantity'];
        $scope.sections.splice(index, 1);
        UpdateTotals(-tempQuantity);
    };

    $scope.addRow = function() {
        var tempCategory = $scope.categories[$scope.category];
        $scope.sections.push({ 'category': tempCategory, 'type': $scope.type, 'quantity': $scope.quantity });

        console.log("Sections - Length = " + $scope.sections.length);
        console.log($scope.sections);


        UpdateTotals($scope.quantity);
        $scope.sectionForm.$setPristine();
        $scope.sectionForm.$setUntouched();
        $scope.category = {};
        $scope.type = '';
        $scope.quantity = '';

        $scope.showToast("Success - Section added", "success");
    };


    function createJSON() {
        var data = [], json = { "timeLimit": $scope.time, "categoryRequestList": data };

        angular.forEach($scope.sections, function(section) {
            switch (section.type) {
                case $scope.types[0].formatName: { /* Multiple Choice */
                    mcQuestions = section.quantity;
                    console.log("case - 0: " + section.type); break; }
                case $scope.types[1].formatName: { /* Multiple Select */
                    msQuestions = section.quantity;
                    console.log("case - 1: " + section.type); break; }
                case $scope.types[2].formatName: { /* Drag 'n' Drop */
                    ddQuestions = section.quantity;
                    console.log("case - 2: " + section.type); break; }
                case $scope.types[3].formatName: { /* Code Snippet */
                    csQuestions = section.quantity;
                    console.log("case - 3: " + section.type); break; }
                default: {
                    console.log("case - default: " + section.type); break; }
            }

            data.push({
                "category": { "categoryId": section.category.categoryId,"name": section.category.name },
                "msQuestions": msQuestions,"mcQuestions": mcQuestions,
                "ddQuestions": ddQuestions,"csQuestions": csQuestions
            });


            /* Reset question quantity per section */
            msQuestions=0; mcQuestions=0; ddQuestions=0; csQuestions=0;
        });

        json.categoryRequestList = data;
        console.log("This is my personal hand made JSON!!!!!");
        console.log(json);
        return json;
    };


    /* Performs AJAX call to REST endpoint sending assessment time and criteria */
    $scope.createAssessment = function() {
        console.log("This is a masterpiece...");
        $http({
            method: 'PUT',
            url: (SITE_URL.BASE + API_URL.BASE + "/assessmentrequest" + "/1/"),
            headers: { 'Content-Type': 'application/json' },
            data: (createJSON())
        }).success(function() {
            $scope.showToast("Assessment created successfully", "success");
            console.log("Assessment creation success");
        }).error(function() {
            $scope.showToast("Assessment creation failed", "fail");
            console.log("Assessment creation failed");
        });
    };
});


adminApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
    var mc = this;

    // functions
    // sets navbar to current page even on refresh
    mc.findCurrentPage = function() {

        var path = window.location.pathname.substr(1);

        switch(path) {
            case "aes/registerEmployee" : return "employees";
            case "aes/updateEmployee"   : return "employees";
            case "aes/addQuestions"     : return "questions";
            case "aes/manageQuestions"  : return "questions";
            case "aes/createAssessment" : return "assessments";
            case "aes/parser"           : return "parser";
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


    // $scope.toggleLeft = buildDelayedToggler('left');
    $scope.toggleRight = buildToggler('right');
    $scope.isOpenRight = function() {
        return $mdSidenav('right').isOpen();
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
                .then(function() {
                    $log.debug("toggle " + navID + " is done");
                });
        }, 200);
    }

    function buildToggler(navID) {
        return function() {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav(navID)
                .toggle()
                .then(function() {
                    $log.debug("toggle " + navID + " is done");
                });
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
