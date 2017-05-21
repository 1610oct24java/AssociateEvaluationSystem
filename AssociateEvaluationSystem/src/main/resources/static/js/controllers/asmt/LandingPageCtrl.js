asmt.controller("landingPageCtrl", function($scope, $http, $rootScope,
                                        $window, SITE_URL, API_URL) {
    var quizPage = "";
    $scope.testtaker = "Loading...";
    var QUIZ_REST_URL = "/aes/rest/landing/";
    $scope.landingScript = "";
    $scope.hideBox = true;
    $scope.myTime = "";

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
                    $scope.testtaker = "Welcome " + response.data.firstName + " " + response.data.lastName;
                    $scope.landingScript = response.data.landingScript;
                    // Changes the button on the assessment landing page based on time
                    if (response.data.timestamp != null){
                        $scope.myTime = "Continue Assessment";
                    } else {
                        $scope.myTime = "Start Assessment";
                    }

                    $scope.hideBox = false;
                }else {
                    // Assessment was taken or time expired, redirecting to expired page
                    $window.location.href = '/aes/expired';
                }
            })
    }

    $scope.gotoQuiz = function(){
        window.location = quizPage;
    }
});