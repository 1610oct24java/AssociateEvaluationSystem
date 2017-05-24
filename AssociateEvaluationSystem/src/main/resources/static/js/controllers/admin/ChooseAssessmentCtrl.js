adminApp.controller('ChooseAssessmentCtrl', function($scope, $mdToast, $http, $anchorScroll, SITE_URL, API_URL, ROLE){
    //list of assessments used to manipulate
    $scope.assList = [];

    //the default assessment stored into different object
    $scope.defaultAss = {};
    //default assessment is still in assList. Index is used to manipulate the default in the list
    $scope.defaultIndex = 0;

    //sets toast function
    $scope.showToast = function(message, type) {
        $mdToast.show($mdToast.simple(message)
            .parent(document.querySelectorAll('#toastContainer'))
            .position("top right")
            .action("OKAY")
            .highlightAction(true)
            .highlightClass('toastActionButton')
            .theme(type + '-toast')
            .hideDelay(5000)
        );
    };



//get number of sections for view 
    $scope.getNumOfSec = function(index){

        return $scope.assList[index].categoryRequestList.length;
    }

    $scope.defaultNumOfSec = function(){
        return $scope.defaultAss.categoryRequestList.length;
    }

//gets number of questions for questions
    $scope.getTotalNumOfQuestions = function(index){
        var totalQuestions = 0;
        for(var i = 0; i < $scope.assList[index].categoryRequestList.length; i++){
            totalQuestions = totalQuestions + $scope.assList[index].categoryRequestList[i].csQuestions;
            totalQuestions = totalQuestions + $scope.assList[index].categoryRequestList[i].ddQuestions;
            totalQuestions = totalQuestions + $scope.assList[index].categoryRequestList[i].mcQuestions;
            totalQuestions = totalQuestions + $scope.assList[index].categoryRequestList[i].msQuestions;

        }
        return totalQuestions;
    }

    //function for default assessments total numberof questsion

    $scope.defaultTotalNumOfQuestions = function(index){
        var totalQuestions = 0;
        for(var i = 0; i < $scope.defaultAss.categoryRequestList.length; i++){
            totalQuestions = totalQuestions + $scope.defaultAss.categoryRequestList[i].csQuestions;
            totalQuestions = totalQuestions + $scope.defaultAss.categoryRequestList[i].ddQuestions;
            totalQuestions = totalQuestions + $scope.defaultAss.categoryRequestList[i].mcQuestions;
            totalQuestions = totalQuestions + $scope.defaultAss.categoryRequestList[i].msQuestions;

        }
        return totalQuestions;
    }


    //gets all the assessments requests
    $http({
        method: "GET",
        url: "allAssessments"
    }).then(function (response) {
        $scope.assList = response.data;


        for(var i = 0; i < $scope.assList.length; i++){

            $scope.assList[i].numSections = $scope.getNumOfSec(i);
            $scope.assList[i].numQuestions = $scope.getTotalNumOfQuestions(i);
            $scope.assList[i].index = i;

            if($scope.assList[i].hoursViewable == null){
                $scope.assList[i].allowed = false;
            }else{
                $scope.assList[i].allowed = true;
                $scope.assList[i].days = Math.floor($scope.assList[i].hoursViewable/24).toString();
                $scope.assList[i].hours = ($scope.assList[i].hoursViewable % 24).toString();
            }
            if($scope.assList[i].isDefault == 1){

                $scope.defaultAss = $scope.assList[i];
                $scope.defaultIndex = i;
                $scope.defaultAss.allow = $scope.assList[i].allowed;
            }
        }
    });

    //function for selecting default. will repopulate list after default is selected
    $scope.selectDefault = function(index){
        $http({
            method: "POST",
            url: "selectAssessment",
            data: $scope.assList[index]
        }).then(function(response){

            $http({
                method: "GET",
                url: "allAssessments"
            }).then(function (response) {
                $scope.assList = response.data;


                for(var i = 0; i < $scope.assList.length; i++){

                    $scope.assList[i].numSections = $scope.getNumOfSec(i);
                    $scope.assList[i].numQuestions = $scope.getTotalNumOfQuestions(i);
                    $scope.assList[i].index = i;

                    if($scope.assList[i].hoursViewable == null){
                        $scope.assList[i].allowed = false;
                    }else{
                        $scope.assList[i].allowed = true;
                        $scope.assList[i].days = Math.floor($scope.assList[i].hoursViewable/24).toString();
                        $scope.assList[i].hours = ($scope.assList[i].hoursViewable % 24).toString();
                    }
                    if($scope.assList[i].isDefault == 1){
                        $scope.defaultAss = $scope.assList[i];
                        $scope.defaultIndex = i;
                    }
                }
            });
        });
        $scope.showToast("New Default Selected", "success");
        $anchorScroll();
    }

    //gets each question type of the category
    $scope.getQuestionTypeOfCategory = function(category){
        if(category.csQuestions > 0){
            var type = "Code Snippet";
            return type;
        }
        else if(category.ddQuestions > 0){
            var type = "Drag and Drop";
            return type;
        }
        else if(category.mcQuestions > 0){
            var type = "Multiple Choice";
            return type;
        }
        else if(category.msQuestions > 0){
            var type = "Multiple Select";
            return type;
        }
    }

    //function to count the number of questions in each category for expanded view
    $scope.getNumberOfQuestionsInCategory = function(category){

        if(category.csQuestions > 0){
            var num = category.csQuestions;
            return num;
        }
        else if(category.ddQuestions > 0){
            var num = category.ddQuestions;
            return num;
        }
        else if(category.mcQuestions > 0){
            var num = category.mcQuestions;
            return num;
        }
        else if(category.msQuestions > 0){
            var num = category.msQuestions;
            return num;
        }
    }




    //checks if user tries to change it over a year long
    $scope.overAYear = function(days){
        var checkDays = parseInt(days);
        if (checkDays > 365){

            return true;

        }
        return false;
    }
    //checks if user tries to enter over 24 hours
    $scope.over24Hours = function(hours){
        var checkHours = parseInt(hours);
        if(checkHours > 23){
            return true;
        }
        return false;
    }

    //function to update the hours of the assessment
    $scope.updateHours = function(index){
        $scope.assList[$scope.defaultIndex].days = $scope.defaultAss.days;
        $scope.assList[$scope.defaultIndex].hours = $scope.defaultAss.hours;
        $scope.assList[$scope.defaultIndex].allowed = $scope.defaultAss.allowed;
        if($scope.assList[index].allowed){
            var daysHours = parseInt($scope.assList[index].days) * 24;
            var totalHours = daysHours + parseInt($scope.assList[index].hours);
            $scope.assList[index].hoursViewable = totalHours;
        }else{
            $scope.assList[index].hoursViewable = null;
        }


        $http({
            method: "POST",
            url: "updateViewableHours",
            data: $scope.assList[index]
        }).then(function(response){
            $http({
                method: "GET",
                url: "allAssessments"
            }).then(function (response) {
                $scope.assList = response.data;


                for(var i = 0; i < $scope.assList.length; i++){

                    if($scope.assList[i].hoursViewable == null){
                        $scope.assList[i].allowed = false;
                    }else{
                        $scope.assList[i].allowed = true;
                        $scope.assList[i].days = Math.floor($scope.assList[i].hoursViewable/24).toString();
                        $scope.assList[i].hours = ($scope.assList[i].hoursViewable % 24).toString();
                    }
                    if($scope.assList[i].isDefault == 1){
                        $scope.defaultAss = $scope.assList[i];
                        $scope.defaultIndex = i;
                    }
                }
            });
        });

        $scope.showToast("Updated Review Allowed", "success");

    }

    $scope.deleteAssessment = function(index, $window){
                $http({
                        method: "POST",
                        url: "assessmentrequest/delete",
                        data: $scope.assList[index]
                }).then(function(response){

                        $http({
                                method: "GET",
                                url: "allAssessments"
                        }).then(function (response) {
                                $scope.assList = response.data;


                                        for(var i = 0; i < $scope.assList.length; i++){

                                            $scope.assList[i].numSections = $scope.getNumOfSec(i);
                                        $scope.assList[i].numQuestions = $scope.getTotalNumOfQuestions(i);
                                        $scope.assList[i].index = i;

                                            if($scope.assList[i].hoursViewable == null){
                                                $scope.assList[i].allowed = false;
                                            }else{
                                                $scope.assList[i].allowed = true;
                                                $scope.assList[i].days = Math.floor($scope.assList[i].hoursViewable/24).toString();
                                                $scope.assList[i].hours = ($scope.assList[i].hoursViewable % 24).toString();
                                            }
                                        if($scope.assList[i].isDefault == 1){
                                                $scope.defaultAss = $scope.assList[i];
                                                $scope.defaultIndex = i;
                                            }
                                    }
                            });
                    });
                $scope.showToast("Successfully Deleted Assessment", "success");
                $anchorScroll();
    }



});