/**
 * @class AES.adminApp.EmployeeViewCtrl
 */

adminApp.controller('EmployeeViewCtrl', function($scope,$mdToast, $http, SITE_URL, API_URL, ROLE) {
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
                    });
            } else {
                window.location = SITE_URL.LOGIN;
            }
        });

    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function() {
                window.location = SITE_URL.LOGIN;
            });
    };

    $scope.showToast = function(message) {
        $mdToast.show($mdToast.simple().textContent(message).parent(document.querySelectorAll('#toastContainer')).position("center center").action("OKAY").highlightAction(true));
    };

    // deletes an employee only if it is not the system user.
    $scope.deleteEmployee = function(employee) {
        if (employee.role.roleTitle.toUpperCase() === "SYSTEM") {
            $scope.showToast("Cannot delete System User");
            return;
        }

        // delete the employee if it is not the system user.

     var url = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEE + "/" + employee.email + "/delete";
        $http.delete(url)
            .success( function(response) {
                $scope.showToast("User Deleted");
                $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES)
                    .then(function(response) {
                        $scope.employees = response.data;
                    });
            }).error( function() {
            $scope.showToast("Failed to Delete User");
        });
    }

    $scope.checkAll = function () {
        if (!$scope.selectedAll) {
            $scope.selectedAll = true;
        }else {
            $scope.selectedAll = false;
        }
        angular.forEach($scope.employees, function (employees) {
            employees.selected = $scope.selectedAll;
        });
    };

    // display the review-assessment page
    $scope.showAssessment = function(a) {

        // clone the assessment passed in so changes to it don't affect the view.
       var assessment = {
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
            if(response){

                var asmtId = response.data;


                encodedId = asmtId;

                // bring up the review assessment page.
                window.location = SITE_URL.BASE + API_URL.BASE + '/quizReview?asmt=' + encodedId;
            }
        })
    };

    // open/close viewing assessments for a candidate.
    $scope.viewAssessments = function(num, email){
        $scope.assessments = [];
        $scope.returnCheck = false;
        $http
            .get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + "/" + email + "/assessments") // get the assessments of this candidate
            .then(function(response) {
                var asmt = response.data;
                if (asmt.length != 0) {
                    asmt.forEach(a=>{ a.createdTimeStamp = formatDate(a.createdTimeStamp);
                    a.finishedTimeStamp = formatDate(a.finishedTimeStamp)});
                }
                $scope.assessments = asmt;
                $scope.returnCheck = true;
            });

        var myEl = angular.element( document.querySelector( '#'+num ) );
        for(var i = 0; i < $scope.employees.length; i++){
            var close = angular.element( document.querySelector( '#g'+$scope.employees[i].userId ) );
            if((close[0].attributes[0].nodeValue == "ng-show" || close[0].attributes[1].nodeValue == "ng-show") && '#'+num != '#g'+$scope.employees[i].userId){
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

    // check whether an employee is a candidate (used in deciding whether to show view-assessments button)
    $scope.isCandidate = function(employee) {
        if (employee.role.roleTitle.toUpperCase() === "CANDIDATE") {
            return true;
        }
       
            return false;
    };

   
    $scope.closeAllAssessments = function() {
        for(var i = 0; i < $scope.employees.length; i++){
            var close = angular.element( document.querySelector( '#g'+$scope.employees[i].userId ) );
            if((close[0].attributes[0].nodeValue == "ng-show" || close[0].attributes[1].nodeValue == "ng-show")){
                close.removeClass("ng-show");
                close.addClass("ng-hide");
            }
        }
    }

});