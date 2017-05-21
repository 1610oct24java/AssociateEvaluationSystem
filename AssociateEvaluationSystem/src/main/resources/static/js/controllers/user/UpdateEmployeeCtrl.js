/**
 * Created by SLEDGEHAMMER on 3/9/2017.
 */


var userApp = angular.module('userApp');



userApp.constant("SITE_URL", {
    "HTTP" : "http://",
    "HTTPS": "https://",
    "BASE" : "",
    "PORT" : ":8080",

    "LOGIN": "index",
    "TRAINER_HOME" : "",
    "VIEW_CANDIDATES" : "view",
    "VIEW_EMPLOYEES" : "viewEmployees",
    "REGISTER_CANDIDATE" : "",
    "REGISTER_EMPLOYEE" : ""
});


userApp.constant("API_URL", {
    "BASE"      : "/aes",
    "LOGIN"     : "/login",
    "LOGOUT"    : "/logout",
    "AUTH"      : "/security/auth",
    "CANDIDATE" : "/candidate/",
    "RECRUITER" : "/recruiter",
    "LINK"      : "/link",
    "CANDIDATES": "/candidates"
});


userApp.constant("ROLE", {
    "RECRUITER" : "ROLE_RECRUITER",
    "TRAINER"   : "ROLE_TRAINER",
    "CANDIDATE" : "ROLE_CANDIDATE",
    "ADMIN"		: "ROLE_ADMIN"
});



userApp.config(function($mdThemingProvider) {

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




userApp.controller('UpdateEmployeeCtrl', function ($scope, $location, $mdToast, $http, SITE_URL, API_URL, ROLE) {
	$scope.newEmail = null;
    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
        .then(function (response) {
            if (response.data.authenticated) {
                var authUser = {
                    username: response.data.principal.username,
                    authority: response.data.principal.authorities[0].authority
                }
                $scope.authUser = authUser;
                var role = $scope.authUser.authority;

                if (role == "ROLE_RECRUITER") {
                    // Continue to page
                	$scope.loadData();
                } else {
                    window.location = SITE_URL.LOGIN; // Deny page, re-route to login
                }
            } else {
                window.location = SITE_URL.LOGIN;
            }
        })
    
    
    $scope.showToast = function(message) {
    	$mdToast.show($mdToast.simple().textContent(message).parent(document.querySelectorAll('#toastContainer')).position("top right").action("OKAY").highlightAction(true));
    };
    
    $scope.loadData = function(){
    	var url = SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + "/" + $scope.authUser.username + "/update";
    	$http({
            method: 'GET',
            url: url
    	}).then(
    			function (response){
    				var user = response.data;
    				$scope.oldEmail = user.email;
    				$scope.firstName = user.firstName;
    				$scope.lastName = user.lastName;
    			}, 
    			function (){
    				$scope.showToast("Unable to get user");
    				
    			}
    	);
    }
    
    

    $scope.update = function () {
        $scope.passNotMatch = false;
        $scope.passNotEntered = false;
        $scope.emailNotEntered = false;
        $scope.updateUnsuccessful = false;
        $scope.updateSuccessful = false;

        var employeeInfo = {
            newEmail: $scope.newEmail,
            firstName: $scope.firstName,
            lastName: $scope.lastName,
            oldPassword: $scope.oldPassword,
            newPassword: $scope.newPassword,
        };

        if ($scope.oldEmail === "" || $scope.oldEmail == null) {
            $scope.emailNotEntered = true;
        }

        if ($scope.oldEmail !== $scope.authUser.username) {
            $scope.updateUnsuccessful = true;
        }

        if ($scope.newPassword !== $scope.confirmNewPassword) {
            $scope.passNotMatch = true;
            $scope.newPassword = '';
            $scope.confirmNewPassword = '';
        }

        if (!$scope.oldPassword === "" || $scope.oldPassword == null) {
            $scope.passNotEntered = true;
        }

        if (!$scope.passNotMatch && !$scope.passNotEntered
            && !$scope.emailNotEntered && !$scope.updateUnccessful) {
        	
            $scope.postUpdate(employeeInfo);
            
        }
    };
    


    $scope.postUpdate = function (info) {
        var updateUrl = SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER
            + "/" + $scope.oldEmail + "/update";
        $http({
            method: 'PUT',
            url: updateUrl,
            headers: {'Content-Type': 'application/json'},
            data: info
        }).success(function (data) {
        	$scope.showToast(data.msg);
        	$scope.oldPassword = "";
        	$scope.newPassword = "";
        	$scope.confirmNewPassword = "";
        	
        	//logs out user if email was changed
        	if ($scope.newEmail){
        		$scope.logout();
        	}
        	
        }).error(function (error) {
        	$scope.showToast(error.msg);
        	$scope.oldPassword = "";
        	$scope.newPassword = "";
        	$scope.confirmNewPassword = "";
        	$scope.newEmail = "";
        });
    };

    $scope.logout = function () {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function (response) {
                window.location = SITE_URL.LOGIN;
            });
    }
});

userApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
	var mc = this;

    // functions
    // sets navbar to current page even on refresh
    mc.findCurrentPage = function() {

        // var path = $location.path().replace("/", "");
        var path = window.location.pathname.substr(1);

        switch(path) {
            case "aes/recruit" : return "register";
            case "aes/updateUser" : return "updateRecruiter";
            default : return "overview"
        }
    };
    
    mc.currentPage = mc.findCurrentPage();

});
