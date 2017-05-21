var landingApp = angular.module("landingPageApp", []);

landingApp.constant("SITE_URL", {
    "HTTP": "http://",
    "HTTPS": "https://",
    "BASE": "",
    "PORT": ":8080",

    "LOGIN": "index",
    "TRAINER_HOME": "",
    "VIEW_CANDIDATES": "view",
    "VIEW_EMPLOYEES": "viewEmployees",
    "REGISTER_CANDIDATE": "",
    "REGISTER_EMPLOYEE": "",
    "ASSESSMENT_LANDING": "assessmentLandingPage"
});


landingApp.constant("API_URL", {
    "BASE": "/aes",
    "LOGIN": "/login",
    "LOGOUT": "/logout",
    "AUTH": "/security/auth",
    "CANDIDATE": "/candidate/",
    "RECRUITER": "/recruiter/",
    "LINK": "/link",
    "CANDIDATES": "/candidates"
});


landingApp.constant("ROLE", {
    "RECRUITER": "ROLE_RECRUITER",
    "TRAINER": "ROLE_TRAINER",
    "CANDIDATE": "ROLE_CANDIDATE",
    "ADMIN": "ROLE_ADMIN"
});

landingApp.controller("landingPageCtrl", function ($scope, $http, $rootScope,
                                                   $window, SITE_URL, API_URL) {
    var quizPage = "";
    $scope.testtaker = "Loading...";
    var QUIZ_REST_URL = "/aes/rest/landing/";
    $scope.landingScript = "";
    $scope.hideBox = true;
    $scope.myTime = "";


    //RICHARD: get global settings to see if user can review
    var settingsUrl = "/aes/admin/globalSettings"
    $scope.getSettings = function () {
        $scope.getSettingsUnsuccessful = false;
        $http({
            method: 'GET',
            url: settingsUrl
        }).success(function (data) {
            if (!data) {
                $scope.getSettingsUnsuccessful = true;
            } else {
                $scope.settings = {};
                $scope.settings.keys = [];
                data.forEach(function (s) {
                    $scope.settings[s.propertyId] = s;
                    $scope.settings.keys.push(s.propertyId);
                });
            }
        }).error(function () {
            $scope.getSettingsUnsuccessful = true;
        });
    }
    $scope.getSettings();

    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
        .then(function (response) {
            if (response.data.authenticated) {
                var authUser = {
                    username: response.data.principal.username,
                    authority: response.data.principal.authorities[0].authority
                }
                $scope.authUser = authUser;
                $scope.candidateEmail = authUser.username;
                $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.CANDIDATE + $scope.candidateEmail + API_URL.LINK)
                    .then(function (response) {
                        console.log(response.data.urlAssessment);
                        quizPage = response.data.urlAssessment;
                        console.log(quizPage);
                        go();
                    })
            }
        });


    var go = function () {
        var assessmentId = quizPage.substring(quizPage.search("=") + 1);
        console.log(quizPage);

        $http({
            method: 'GET',
            url: QUIZ_REST_URL + assessmentId,
            headers: {'Content-Type': 'application/json'}
        })
            .then(function (response) {
                console.log(response.data);
                // Check response for assessment availability
                console.log(response);

                if (response.data.msg === "allow") {
                    console.log("test");
                    $rootScope.protoTest = response.data.assessment;
                    $scope.testtaker = "Welcome " + response.data.firstName + " " + response.data.lastName;
                    $scope.landingScript = response.data.landingScript;
                    // Changes the button on the assessment landing page based on time
                    if (response.data.timestamp != null) {
                        $scope.myTime = "Continue Assessment";
                    } else {
                        $scope.myTime = "Start Assessment";
                    }

                    $scope.hideBox = false;
                } else {

                    checkGS();
                }
            })
    }
    //RICHARD
    //If logging in after test is taken,
    //we check the review global settings
    //to determine if user can review test or not
    var checkGS = function () {
        var assessmentId = quizPage.substring(quizPage.search("=") + 1);
        console.log(quizPage);

        $http({
            method: 'GET',
            url: "/aes/rest/" + assessmentId,
            headers: {'Content-Type': 'application/json'}
        })
            .then(function (response) {

                if ($scope.settings[1].propertyValue == "true") {
                    //get max time allowed from global settings
                    //add that to the time that the test was finished
                    //and compare that resultant time with the current time
                    //if the resultant time is later than now, allow the review
                    //else we send the user away
                    var finTS = response.data.assessment.finishedTimeStamp;
                    var GSTime = $scope.settings[2].propertyValue;
                    var ts = finTS + (60 * 60 * 1000 * GSTime);
                    var maxReviewTime = new Date(ts);
                    var nowTime = new Date();
                    //compare times
                    if (nowTime < maxReviewTime) {
                        $window.location.href = '/aes/quizReview?asmt=' + assessmentId;
                    }
                    else {
                        $window.location.href = '/aes/expired';
                    }
                }
                else {
                    $window.location.href = '/aes/expired';
                }
            })
    }
    $scope.gotoQuiz = function () {
        window.location = quizPage;
    }
});
