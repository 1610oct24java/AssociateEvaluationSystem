userApp.controller('CandidateCtrl', function($scope,$mdToast,$location,$http,SITE_URL, API_URL, ROLE) {

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
                if (asmt.length != 0) {
                    asmt.forEach(a=>{
                        a.createdTimeStamp = formatDate(a.createdTimeStamp);
                    a.finishedTimeStamp = formatDate(a.finishedTimeStamp)
                });
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
            });
    }

    //registers employee
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

                //TODO: response validation.

                encodedId = asmtId;

                // bring up the review assessment page.
                window.location = SITE_URL.BASE + API_URL.BASE + '/quizReview?asmt=' + encodedId;
            }
        }).error(function() {
        });

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