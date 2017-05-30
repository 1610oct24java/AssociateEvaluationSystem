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

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
            break;
        }
    }
}

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
    if (!date) {
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
    if (!date) {
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
