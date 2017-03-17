/**
 * Created by SLEDGEHAMMER on 3/6/2017.
 */

angular.module('AESCoreApp').controller('CandidateCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

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

    $scope.expandd = function(candidate) {
        $scope.candidates.filter(c => c.email != candidate.email).forEach(c => {c.expanded = false});
        if (!candidate.expanded){
            $http
            .get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + candidate.email + "/assessments")
            .then(function(response) {
                candidate.expanded = true;
                var asmt = response.data;
                if (asmt.length != 0) {
                    asmt.forEach(a=>{ a.createdTimeStamp = formatDate(a.createdTimeStamp);
                    a.finishedTimeStamp = formatDate(a.finishedTimeStamp)});
                }
                $scope.assessments = asmt;
            })
        } else {
            candidate.expanded = false;
        }
        
    };

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
        //$scope.program = '';
    };

    $scope.postRegister = function(candidateInfo) {
        $http({
            method  : 'POST',
            url: SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + $scope.authUser.username + API_URL.CANDIDATES,
            headers : {'Content-Type' : 'application/json'},
            data    : candidateInfo
        }).success( function(res) {
            //Removed console log for sonar cube.
        }).error( function(res) {
            //Removed console log for sonar cube.
        });
    };

   /* $scope.options = [{
        name: 'Java',
        value: 'Java'
    }, {
        name: '.NET',
        value: '.net'
    }];*/
    $scope.sendAssessment = function() {

        var candidateInfo = {
            userId        : null,
            email         : $scope.email,
            firstName     : null,
            lastName      : null,
            salesforce    : null,
            recruiterId   : null,
            role          : null,
            datePassIssued: null,
            format		  : $scope.program.value
        };
        $scope.postRegister(candidateInfo);

       // $scope.firstName = '';
       // $scope.lastName = '';
        $scope.email = '';
        $scope.program = '';
    };

    $scope.postSendAssessment = function(candidateInfo) {
        $http({
            method  : 'POST',
            url: SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER +  API_URL.CANDIDATE,
            headers : {'Content-Type' : 'application/json'},
            data    : candidateInfo
        }).success( function(res) {
            //Removed console log for sonar cube.
        }).error( function(res) {
            //Removed console log for sonar cube.
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
    }
    
    
  //hide/show the tables
    $scope.IsHidden = true;
    $scope.ShowHide = function (email) {
        //If TABLE is hidden it will be visible and vice versa.
    	console.log("show hide function"+email);
        $scope.IsHidden = $scope.IsHidden ? false : true;
  	
    }
    
    
    
});

function formatDate(date) {
    if (date == null) {
        return "";
    }
    var d = new Date(date);
    var min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
    return (d.getMonth() + 1) + "/" + d.getDate() + "/" + d.getFullYear() + " " + d.getHours() + ":" + min;
}

