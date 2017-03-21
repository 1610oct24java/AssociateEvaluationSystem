/**
 * Created by SLEDGEHAMMER on 3/6/2017.


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
    		});
    	}else {
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
            format		  : null
        };
        
        $scope.postRegister(candidateInfo);

        $scope.firstName = '';
        $scope.lastName = '';
        $scope.email = '';
        $scope.program = '';
    };

    $scope.postRegister = function(candidateInfo) {
    	$scope.registerSuccessfulMsg = false;
		$scope.registerUnsuccessfulMsg = false;
		
        $http({
            method  : 'POST',
            url: SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + $scope.authUser.username + "/git candidate/register",
            headers : {'Content-Type' : 'application/json'},
            data    : candidateInfo
        }).success( function() {
				$scope.registerSuccessfulMsg = true;
        }).error( function() {
				$scope.registerUnsuccessfulMsg = true;
        });
    };

	$scope.deleteCandidate = function(email) {
		url = SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + API_URL.CANDIDATE + email + "/delete";
		
		$http.delete(url)
		.then(function (response) {
			//handle success
		}, function (error) {
			//handle error
		});
	}
	
    $scope.logout = function() {
        window.location = API_URL.BASE + API_URL.LOGOUT;
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

    $scope.show2 = function(num, email){
        $scope.assessments = [];
        $http
            .get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + email + "/assessments")
            .then(function(response) {
                var asmt = response.data;
                if (asmt.length != 0) {
                    asmt.forEach(a=>{ a.createdTimeStamp = formatDate(a.createdTimeStamp);
                    a.finishedTimeStamp = formatDate(a.finishedTimeStamp)});
                }
                $scope.assessments = asmt;
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

        // $scope.firstName = '';
        // $scope.lastName = '';
        //$scope.email = '';
        //$scope.program = '';
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
        }).error( function() {
        });
        console.log($scope.sendSuccessful);
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

});

function formatDate(date) {
    if (date == null) {
        return "";
    }
    var d = new Date(date);
    var min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
    return (d.getMonth() + 1) + "/" + d.getDate() + "/" + d.getFullYear() + " " + d.getHours() + ":" + min;
}