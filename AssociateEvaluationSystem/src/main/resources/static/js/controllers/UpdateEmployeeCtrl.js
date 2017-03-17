/**
 * Created by SLEDGEHAMMER on 3/9/2017.
 */
angular.module('AESCoreApp').controller('UpdateEmployeeCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {
    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
        .then(function(response) {
            if (response.data.authenticated) {
                var authUser = {
                    username : response.data.principal.username,
                    authority: response.data.principal.authorities[0].authority
                }
                $scope.authUser = authUser;
                var role = $scope.authUser.authority;

                if(role == "ROLE_RECRUITER") {
                    // Continue to page
                }else {
                    window.location = SITE_URL.LOGIN; // Deny page, re-route to login
                }
            } else {
                window.location = SITE_URL.LOGIN;
            }
        })

    $scope.update= function() {
        $scope.passNotMatch = false;
        $scope.passNotEntered = false;
        $scope.emailNotEntered = false;
        $scope.updateUnsuccessful = false;

        var employeeInfo = {
            newEmail      : $scope.newEmail,
            firstName     : $scope.firstName,
            lastName      : $scope.lastName,
            oldPassword   : $scope.oldPassword,
            newPassword   : $scope.newPassword,
        };

        if ($scope.oldEmail === "" || $scope.oldEmail == null)
        {	$scope.emailNotEntered = true; }

        if ($scope.oldEmail !== $scope.authUser.username){
            $scope.updateUnsuccessful = true;
        }

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
            if (!$scope.updateUnsuccessful){
                $scope.postUpdate(employeeInfo);
            }
        }
    };

    $scope.postUpdate = function(info) {
    	var updateUrl = SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER 
		+ API_URL.EMPLOYEE + "/" + $scope.oldEmail + "/update";
        $http({
            method  : 'PUT',
            url		: updateUrl,
            headers : {'Content-Type' : 'application/json'},
            data    : info
        }).success(function(data){
            if (!data){
                $scope.updateUnsuccessful = true;
            }
        }).error( function() {
            console.log("fail");
        });
    };

    $scope.logout = function() {
    	$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
    	.then(function(response) {
    		window.location = SITE_URL.LOGIN;
    	});
    }
});