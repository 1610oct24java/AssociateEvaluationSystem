angular.module('adminApp').controller('UpdateEmployeeCtrl', function($scope,$location,$http,$routeParams, SITE_URL, API_URL, ROLE) {
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