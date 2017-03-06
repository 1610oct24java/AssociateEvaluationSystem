/**
 * Created by SLEDGEHAMMER on 3/6/2017.
 */

app.controller('CandidateCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

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
            } else {
                window.location = SITE_URL.LOGIN;
            }
        })

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
            format		  : $scope.program.value
        };
        $scope.postRegister(candidateInfo);

        $scope.firstName = '';
        $scope.lastName = '';
        $scope.email = '';
        $scope.program = '';
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

    $scope.options = [{
        name: 'Java',
        value: 'Java'
    }, {
        name: 'SDET',
        value: 'Sdet'
    }, {
        name: '.NET',
        value: '.net'
    }];

    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function(response) {
                window.location = SITE_URL.LOGIN;
            })
    }


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
                        }
                        $scope.candidates = c;
                    })
            } else {
                window.location = SITE_URL.LOGIN;
            }
        })

    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function(response) {
                window.location = SITE_URL.LOGIN;
            })
    }

});


