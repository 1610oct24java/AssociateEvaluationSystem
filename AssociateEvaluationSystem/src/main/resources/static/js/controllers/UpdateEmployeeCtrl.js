/**
 * Created by SLEDGEHAMMER on 3/9/2017.
 */


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
    "REGISTER_EMPLOYEE" : ""
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




AESCoreApp.controller('UpdateEmployeeCtrl', function ($scope, $location, $mdToast, $http, SITE_URL, API_URL, ROLE) {
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
                } else {
                    window.location = SITE_URL.LOGIN; // Deny page, re-route to login
                }
            } else {
                window.location = SITE_URL.LOGIN;
            }
        })

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

        if ($scope.oldPassword === "" || $scope.oldPassword == null) {
            $scope.passNotEntered = true;
        }

        if ($scope.passNotMatch == false && $scope.passNotEntered == false
            && $scope.emailNotEntered == false) {
            if (!$scope.updateUnsuccessful) {
                $scope.postUpdate(employeeInfo);
            }
        }
    };
    
    $scope.showToast = function(message) {
    	$mdToast.show($mdToast.simple().textContent(message).parent(document.querySelectorAll('#toastContainer')).position("top right").action("OKAY").highlightAction(true));
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
        	$scope.showToast("Successfully Updated User");
           /* $scope.updateSuccessful = true;
            $scope.updateUnsuccessful = false;*/
        }).error(function () {
        	$scope.showToast("Failed to Update User");
            /*$scope.updateUnsuccessful = true;
            $scope.updateSuccessful = false;*/
        });
    };

    $scope.logout = function () {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function (response) {
                window.location = SITE_URL.LOGIN;
            });
    }
});

AESCoreApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
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
