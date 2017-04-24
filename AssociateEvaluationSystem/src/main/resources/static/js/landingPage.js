var landingApp = angular.module("landingPageApp",[]);

landingApp.constant("SITE_URL", {
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


landingApp.constant("API_URL", {
    "BASE"      : "/aes",
    "LOGIN"     : "/login",
    "LOGOUT"    : "/logout",
    "AUTH"      : "/security/auth",
    "CANDIDATE" : "/candidate/",
    "RECRUITER" : "/recruiter/",
    "LINK"      : "/link",
    "CANDIDATES": "/candidates"
});


landingApp.constant("ROLE", {
    "RECRUITER" : "ROLE_RECRUITER",
    "TRAINER"   : "ROLE_TRAINER",
    "CANDIDATE" : "ROLE_CANDIDATE",
    "ADMIN"		: "ROLE_ADMIN"
});

landingApp.controller("landingPageCtrl", function($scope, $http, $rootScope, 
		SITE_URL, API_URL) {
	var quizPage = "";
	$scope.testtaker = "Loading...";
	$scope.time = "";
	var QUIZ_REST_URL = "/aes/rest/";

	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			$scope.authUser = authUser;
			$scope.candidateEmail = authUser.username;
            $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.CANDIDATE + $scope.candidateEmail + API_URL.LINK)
                .then(function(response) {
                    console.log(response.data.urlAssessment);
                  		quizPage = response.data.urlAssessment;
                  		console.log(quizPage);
                  		go();
                  })
		}
	});

	
	var go = function(){
		var assessmentId = quizPage.substring(quizPage.search("=")+1);
		console.log(quizPage);
		
	$http({
		method: 'GET',
		url: QUIZ_REST_URL  + assessmentId,
		headers: {'Content-Type': 'application/json'}
	})
	.then(function(response) {
		console.log(response.data);
		// Check response for assessment availability
		console.log(response);
		if (response.data.msg === "allow"){
			console.log("test");
			$rootScope.protoTest = response.data.assessment;
			$scope.testtaker = "Welcome " + $rootScope.protoTest.user.firstName + " " + $rootScope.protoTest.user.lastName;
			$scope.time = response.data.timeLimit;
		} else {
			console.log("I hate this game")
		}
	})
	}
	
	$scope.gotoQuiz = function(){
		console.log("test");
		window.location = quizPage;
	}
});
