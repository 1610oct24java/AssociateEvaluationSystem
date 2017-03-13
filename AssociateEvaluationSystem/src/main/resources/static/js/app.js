var app = angular.module('AESCoreApp',[]);

app.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "",
	"PORT" : ":8080",
	
	"LOGIN": "index",
	"VIEW_CANDIDATES" : "view",
	"REGISTER_CANDIDATE" : "",
	"VIEW_EMPLOYEES" : "viewEmployees",
	
	"TRAINER_HOME" : "",

    "REGISTER_EMPLOYEE" : ""
	
});

app.constant("API_URL", {
	"BASE"      : "/aes",
	"LOGIN"     : "/login",
	"LOGOUT"    : "/logout",
	"AUTH"      : "/security/auth",
	"CANDIDATE" : "/candidate/",
	"RECRUITER" : "/recruiter/",
	"LINK"      : "/link",
	"CANDIDATES": "/candidates"
});

app.constant("ROLE", {
	"RECRUITER" : "ROLE_RECRUITER",
	"TRAINER"   : "ROLE_TRAINER",
	"CANDIDATE" : "ROLE_CANDIDATE",
	"ADMIN"		: "ROLE_ADMIN"
});

app.controller('LoginCtrl', function($scope, $httpParamSerializerJQLike, $http, SITE_URL, API_URL, ROLE) {
	
	$scope.login = function() {
		makeUser($scope);
		$http({
			method : "POST",
			url : SITE_URL.BASE + API_URL.BASE + API_URL.LOGIN,
			headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8;'},
			data: $httpParamSerializerJQLike($scope.user)
		})//.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGIN, $httpParamSerializerJQLike($scope.user), {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8;'})
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
						$scope.candidateEmail = authUser.username;
						$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.CANDIDATE + $scope.candidateEmail + API_URL.LINK)
						.then(function(response) {
							window.location = response.data.urlAssessment;
						})
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

app.controller('RegisterCanidateCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

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

		var canidateInfo = {
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

		$scope.postRegister(canidateInfo);
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(canidateInfo) {
		$http({
			method  : 'POST',
			url: SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + $scope.authUser.username + API_URL.CANDIDATES,
			headers : {'Content-Type' : 'application/json'},
			data    : canidateInfo
		}).success( function(res) {
			//Console log removed for sonarqube.
		}).error( function(res) {
			//Console log removed for sonarqube.
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

}); //end register candidate controller

app.controller('CandidateViewCtrl', function($scope,$http, SITE_URL, API_URL, ROLE) {

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

//Billy Adding controller for assessment creation
app.controller('CreateAssessmentCtrl',function ($scope,$http, SITE_URL, API_URL, ROLE) {

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
        });


    $(document).ready(function() {
        $("#add_row").on("click", function() {
            // Dynamic Rows Code

            // Get max row id and set new id
            var newid = 0;
            $.each($("#tab_logic tr"), function() {
                if (parseInt($(this).data("id")) > newid) {
                    newid = parseInt($(this).data("id"));
                }
            });
            newid++;

            var tr = $("<tr></tr>", {
                id: "addr"+newid,
                "data-id": newid
            });

            // loop through each td and create new elements with name of newid
            $.each($("#tab_logic tbody tr:nth(0) td"), function() {
                var cur_td = $(this);

                var children = cur_td.children();
                
                var td;

                // add new td and element if it has a nane
                if ($(this).data("name") != undefined) {
                    td = $("<td></td>", {
                        "data-name": $(cur_td).data("name")
                    });

                    var c = $(cur_td).find($(children[0]).prop('tagName')).clone().val("");
                    c.attr("name", $(cur_td).data("name") + newid);
                    c.appendTo($(td));
                    td.appendTo($(tr));
                } else {
                    td = $("<td></td>", {
                        'text': $('#tab_logic tr').length
                    }).appendTo($(tr));
                }
            });


            // add the new row
            $(tr).appendTo($('#tab_logic'));

            $(tr).find("td button.row-remove").on("click", function() {
                $(this).closest("tr").remove();
            });
        });


        // Sortable Code
        var fixHelperModified = function(e, tr) {
            var $originals = tr.children();
            var $helper = tr.clone();

            $helper.children().each(function(index) {
                $(this).width($originals.eq(index).width())
            });

            return $helper;
        };

        $(".table-sortable tbody").sortable({
            helper: fixHelperModified
        }).disableSelection();

        $(".table-sortable thead").disableSelection();



        $("#add_row").trigger("click");
    });


    //logout
    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function(response) {
                window.location = SITE_URL.LOGIN;
            })
    }

})

function makeUser($scope) {
	var user = {
				username: $scope.username,
				password: $scope.password
	};

	$scope.user = user;
}


