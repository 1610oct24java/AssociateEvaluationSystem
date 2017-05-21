angular.module('adminApp').controller('RegisterEmployeeCtrl', function($scope,$mdToast,$location,$http,SITE_URL, API_URL, ROLE) {
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