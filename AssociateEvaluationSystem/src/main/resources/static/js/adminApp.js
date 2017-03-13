var adminApp = angular.module('adminApp',[]);

adminApp.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "",
	"PORT" : ":8080",
	
	"LOGIN": "index",
    "VIEW_EMPLOYEES" : "viewEmployees",
    "VIEW_CANDIDATES" : "view",
    "REGISTER_EMPLOYEE" : ""
});

adminApp.constant("API_URL", {
	"BASE"      : "/aes",
	"LOGIN"     : "/login",
	"LOGOUT"    : "/logout",
	"AUTH"      : "/security/auth",
	"CANDIDATE" : "/candidate/",
	"RECRUITER" : "/recruiter",
	"TRAINER"	: "/trainer",
	"LINK"      : "/link",
	"EMPLOYEES" : "/employees",
	"ADMIN"		: "/admin"
});

adminApp.constant("ROLE", {
	"RECRUITER" : "ROLE_RECRUITER",
	"TRAINER"   : "ROLE_TRAINER",
	"CANDIDATE" : "ROLE_CANDIDATE",
	"ADMIN"		: "ROLE_ADMIN"
});

adminApp.controller('RegisterEmployeeCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {
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
	})
	$scope.names =["Recruiter"];
	
	$scope.register = function() {

		var employeeInfo = {
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
			lastName      : $scope.lastName,
			salesforce    : null,
			recruiterId   : null,
			role          : "Recruiter",  //$scope.employeeType.value,
			datePassIssued: null,
			format		  : null
		};

		var urlSpecific = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.RECRUITER;
		
		//Maybe used in the future
		/*if (employeeInfo.role === "Recruiter")
		{
			urlSpecific = urlSpecific + API_URL.RECRUITER;
		}else if (employeeInfo.role === "Trainer")
		{
			urlSpecific = urlSpecific + API_URL.TRAINER;
		}*/
		
		urlSpecific = urlSpecific + "/" + employeeInfo.email 
		+ "/" + employeeInfo.lastName + "/" + employeeInfo.firstName
		
		$scope.postRegister(urlSpecific, employeeInfo);
		
		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(urlSpecific, employeeInfo) {
		$http({
			method  : 'POST',
			url: urlSpecific,
			headers : {'Content-Type' : 'application/json'},
			data    : employeeInfo

		}).success( function(res) {
			//Removed console log for sonarqube.
		}).error( function(res) {
			//Removed console log for sonarqube.
		});
	};


	/*$scope.options = [{
		name: 'Recruiter',
		value: 'Recruiter'
	}, {
		name: 'Trainer',
		value: 'Trainer'
	}];*/
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			//Removed console log for sonarqube.
			window.location = SITE_URL.LOGIN;
		})
	}


}); //end register Employee controller

adminApp.controller('EmployeeViewCtrl', function($scope, $http, SITE_URL, API_URL, ROLE) {
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
			
			$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES)
			.then(function(response) {

				$scope.employees = response.data;
			})
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	    
	
	
	
	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			//Removed console log for sonarqube.
			window.location = SITE_URL.LOGIN;
		})
	};
	//By Hajira Zahir
	//Delete user
	 $scope.Delete = function (email) {
	        url = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES + "/Delete/" + email + "/";
	        $http.delete(url)
	        .then(function (response) {
	        	//Removed console log for sonarqube.
	        }, function (error) {
	        	//Removed console log for sonarqube.
	        });
	    }
	


      //added by Hajira Zahir
      $scope.checkAll = function () {
          if (!$scope.selectedAll) {
              $scope.selectedAll = true;
          } else {
              $scope.selectedAll = false;
          }
          angular.forEach($scope.employees, function (employees) {
        	  employees.selected = $scope.selectedAll;
          });
      };   
	
});

function makeUser($scope) {
    var user = {
        username: $scope.username,
        password: $scope.password
    };

    $scope.user = user;
}

adminApp.controller('UpdateEmployeeCtrl', function($scope,$location,$http,SITE_URL, API_URL, ROLE) {

	$http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
	.then(function(response) {
		if (response.data.authenticated) {
			var authUser = {
				username : response.data.principal.username,
				authority: response.data.principal.authorities[0].authority
			}
			$scope.authUser = authUser;
			var role = $scope.authUser.authority;
			
			if(role == "ROLE_ADMIN" || role == "ROLE_RECRUITER" || role == "ROLE_TRAINER") {
				// Continue to page
			}else {
				window.location = SITE_URL.LOGIN; // Deny page, re-route to login
			}
		} else {
			window.location = SITE_URL.LOGIN;
		}
	})
	
	$scope.update= function() {
		$scope.passNotMatch = false;
		$scope.passNotEntered = false;
		$scope.emailNotEntered = false; 
		
		var employeeInfo = {
			newEmail      : $scope.newEmail,
			firstName     : $scope.firstName,
			lastName      : $scope.lastName,
			oldPassword   : $scope.oldPassword,
			newPassword   : $scope.newPassword,
		};

		if ($scope.oldEmail === "" || $scope.oldEmail == null)
		{	$scope.emailNotEntered = true; }
		
		if ($scope.newPassword !== $scope.confirmNewPassword)
		{
			$scope.passNotMatch = true;
			$scope.newPassword = '';
			$scope.confirmNewPassword = '';
		}
		
		if ($scope.oldPassword === "" || $scope.oldPassword == null)
		{	$scope.passNotEntered = true; }
		
		if ($scope.passNotMatch == false && $scope.passNotEntered == false 
				&& $scope.emailNotEntered == false)
		{
			var updateUrl = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN 
					+ API_URL.EMPLOYEES + "/update/" + $scope.oldEmail + "/";
			
			$scope.postUpdate(updateUrl, employeeInfo);
		}
		
	};

	$scope.postUpdate = function(updateUrl, info) {
		
		$http({
			method  : 'PUT',
			url: updateUrl,
			headers : {'Content-Type' : 'application/json'},
			data    : info
		}).success( function(response) {
			//Removed console log for sonarqube.
			//$scope.logout();
		}).error( function(response) {
			//Removed console log for sonarqube.
		});
	};

	$scope.logout = function() {
		$http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
		.then(function(response) {
			window.location = SITE_URL.LOGIN;
		})
	}
	
}); //end update credentials controller

//Billy Adding controller for assessment creation
adminApp.controller('CreateAssessmentCtrl',function ($scope,$http, SITE_URL, API_URL, ROLE) {

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

});
