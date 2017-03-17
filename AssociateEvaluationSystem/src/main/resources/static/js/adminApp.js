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
			//Removed console log for sonar cube.
		}).error( function(res) {
			//Removed console log for sonar cube.
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
			//Removed console log for sonar cube.
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
			window.location = SITE_URL.LOGIN;
		})
	};
	//By Hajira Zahir
	//Delete user
	 $scope.Delete = function (email) {
	        url = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES + "/Delete/" + email + "/";
	        $http.delete(url)
	        .then(function (response) {
	        }, function (error) {
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
			
			if(role == "ROLE_ADMIN") {
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
		$scope.userNotFound = false;
		
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
		}).success(function(data){
                if (!data){
                    $scope.userNotFound = true;
                }
		}).error( function() {
			console.log("fail");
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

    $scope.curricula = [
        "HTML",
        "CSS",
        "JavaScript",
        "Object Oriented Programming",
        "Data Structures",
        "SQL",
        "C#",
        "Java",
        "Critical Thinking"
    ]

    $scope.types = [
        "Drag and Drop",
        "Multiple Choice",
        "Multiple Select",
        "Coding Snippet"
    ]

    $scope.rows = [];

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
                // add new td and element if it has a name
                if ($(this).data("name") != undefined) {
                    var td = $("<td></td>", {
                        "data-name": $(cur_td).data("name")
                    });
                    var c = $(cur_td).find($(children[0]).prop('tagName')).clone().val("");
                    c.attr("name", $(cur_td).data("name") + newid);
                    c.appendTo($(td));
                    td.appendTo($(tr));
                } else {
                    var td = $("<td></td>", {
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



    var tableData = {};

    tableData.categories = $scope.categories;
    tableData.type = $scope.type;
    tableData.quantity = $scope.quantity;

    var tableArray = [tableData];


    // The 'newObj' object, and it's assignments, are used to generate
    // new objects to be placed within the 'cardArr' array object.
    $scope.newObj = {};
    $scope.newObj.requiredGrads = $scope.requiredGrads;
    $scope.newObj.reqDate = $scope.reqDate;
    $scope.newObj.requiredBatches = $scope.requiredBatches;
    $scope.newObj.startDate = $scope.startDate;
    $scope.newObj.formattedStartDate = $scope.formattedStartDate;
    $scope.newObj.batchType = $scope.batchType;

    $scope.cardArr = [$scope.newObj];   // Array of Required Trainee batch generation objects.


	/* FUNCTION - This method will assign the particular card objects
	 *            'btchType' variable to the selected value. */
    $scope.assignCurr = function(bType, index){

        $scope.cardArr[index].batchType = bType;

        if($scope.cardArr[index].requiredGrads > 0) {

            $scope.cumulativeBatches();

        }
    };



	/* FUNCTION - This method will add another card to the cardArr object,
	 *            ultimately generating another card in the 'required Trainee's'
	 *            tab in the Reports tab. */
    $scope.genCard = function(){

        var temp = {};

        temp.requiredGrads = $scope.requiredGrads;
        temp.reqDate = new Date();
        temp.requiredBatches = $scope.requiredBatches;
        temp.startDate = $scope.startDate;
        temp.formattedStartDate = $scope.formattedStartDate;
        temp.batchType = $scope.batchType;

        //pushes the value onto the end of the array.//
        $scope.cardArr.push(temp);

    };



	/* FUNCTION - This method will delete/remove a 'card' in the cardArr
	 *            object, at a given index.  The deleted 'card' will no
	 *            longer be displayed on the reports tab. */
    $scope.removeCardClick = function(index){
        $scope.cardArr.splice(index, 1);  // Removes a card object from the array index
        $scope.cumulativeBatches();       // Re-evaluates the cumulative batches.
    };

    //logout
    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function(response) {
                window.location = SITE_URL.LOGIN;
            })
    }

});
