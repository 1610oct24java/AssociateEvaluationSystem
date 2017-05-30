adminApp.controller('CreateAssessmentCtrl', function($scope, $http, $mdToast, $location, SITE_URL, API_URL, ROLE) {
    //initialize data
    $http({
        method: "GET",
        url: "category"
    }).then(function (response) {
        $scope.categories = response.data;

        $scope.showToast("Got all categories", "success");
    });

    $http({
        method: "GET",
        url: "rest/format"
    }).then(function (response) {
        $scope.types = response.data;
        $scope.showToast("Got all formats", "success");
    });


    //logout
    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function(response) {
                window.location = SITE_URL.LOGIN;
            })
    };


    $scope.validateReview = function ()
    {
        if((($scope.assdays ==0 && $scope.asshours == 0 ) && $scope.assReviewCheck))
            return true;

        return false;
    };


    $scope.allowReview = function()
    {
        if($scope.assReviewCheck)
        {
            var totalHours= ($scope.assdays * 24) + $scope.asshours;
            $scope.assdays = 0;
            $scope.asshours = 0;
        }
        else
        {
            $scope.assdays = 0;
            $scope.asshours = 0;
        }

    };



    $scope.checkDuplicate = function () {
        var flag = false;
        var count = 0;

        angular.forEach($scope.sections , function (category) {

            if( $scope.currentCategory.name == category.category && $scope.currentType.formatName == category.type)
            {
                flag = true;
            }
            count ++;

        });


        if(flag)
            return true;


        return false;
    };

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

    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
        .then(function(response) {
            if (response.data.authenticated) {
                var authUser = {
                    username: response.data.principal.username,
                    authority: response.data.principal.authorities[0].authority
                };
                $scope.authUser = authUser;
                if ($scope.authUser.authority != ROLE.ADMIN) {
                    window.location = SITE_URL.LOGIN;
                };
            } else
                window.location = SITE_URL.LOGIN;
        });

    angular.element(document).ready(function () {
        $scope.times = [15, 30, 45, 60, 75, 90];
        $scope.type = '';
        $scope.category = '';

        /* check to see if editing an assessment request 
         * or creating a new one
         */
        $scope.id = $location.search().id;
        if($scope.id != null){
            $scope.edit = true;
            angular.element(document.querySelector('#create'))[0].innerText = 'Edit Assessment';
            document.getElementById("create").value = "Edit Assessment";
        }


        $scope.coreLanguage = false;
        $scope.coreCount = 0;
        $scope.showModal = false;
        $scope.assdays = 0;
        $scope.asshours = 0;

        if($scope.edit){
            $http({
                method: "GET",
                url: "assessmentrequest/"+$scope.id+"/"
            }).then(function(response){
                $scope.assessments = response.data.categoryRequestList;
                $scope.sections = [];
                $scope.totalQuestions = 0;
                $scope.name = response.data.name;
                $scope.isDefault = response.data.isDefault;

                /*Load the sections */
                var secName;
                var secType;
                var secQuantity

                for (var i = 0; i < $scope.assessments.length; i++) {

                    secName = $scope.assessments[i].category.name;
                    if(secName =="core language" || secName == "Java"||secName == ".net"){
                        $scope.coreLanguage = true;
                        $scope.coreCount++;
                    }

                    if($scope.assessments[i].csQuestions != 0){
                        secType = 'Code Snippet';
                        secQuantity = $scope.assessments[i].csQuestions;
                    } else if($scope.assessments[i].ddQuestions != 0){
                        secType = 'Drag and Drop';
                        secQuantity = $scope.assessments[i].ddQuestions;
                    } else if($scope.assessments[i].mcQuestions != 0){
                        secType = 'Multiple Choice';
                        secQuantity = $scope.assessments[i].mcQuestions;
                    } else {
                        secType = 'Multiple Select';
                        secQuantity = $scope.assessments[i].msQuestions;
                    }

                    $scope.sections.push({ 'category': secName, 'type': secType, 'quantity': secQuantity });
                    $scope.totalQuestions += secQuantity;
                }

                $scope.time= response.data.timeLimit;
                UpdateTotals(0);
            });
        } else{
            $scope.assessments = [];
            $scope.sections = [];
            $scope.totalQuestions = 0;
            $scope.totalCategories = 0;
            $scope.totalTypes = 0;
            $scope.time;
            $scope.isDefault = 0;
        }
    });

    function UpdateTotals(quantity) {
        $scope.totalCategories = UniqueArraybyId($scope.assessments, "category");
        $scope.totalTypes = typeCount($scope.assessments);
        $scope.totalQuestions += quantity;
    }


    function UniqueArraybyId(collection, keyname) {
        var output = [], keys = [];

        angular.forEach(collection, function(item) {
            var key = item[keyname];
            if (keys.indexOf(key) === -1) {
                keys.push(key);
                output.push(item);
            }
        });
        return output.length;
    };

    //returns number of types in th
    function typeCount(collection){

        var types = 0;
        var mcBool = false;
        var msBool = false;
        var ddBool = false;
        var csBool = false;
        angular.forEach(collection, function(item){

            if(item['mcQuestions'] > 0 && !mcBool){
                types++;
                mcBool = true;
            }
            if(item['msQuestions'] > 0 && !msBool){
                types++;
                msBool = true;
            }
            if(item['ddQuestions'] > 0 && !ddBool){
                types++;
                ddBool = true;
            }
            if(item['csQuestions'] > 0 && !csBool){
                types++;
                csBool = true;
            }

        });
        return types;
    }

    $scope.removeRow = function(index) {

        var mcCount = $scope.assessments[index]['mcQuestions'];
        var msCount = $scope.assessments[index]['msQuestions'];
        var ddCount = $scope.assessments[index]['ddQuestions'];
        var csCount = $scope.assessments[index]['csQuestions'];

        var tempQuantity = mcCount + msCount + ddCount + csCount;


        var checkCore = $scope.assessments[index]['category'].name;
        if(checkCore =="core language" || checkCore =="Java" || checkCore ==".net"){
            $scope.coreCount--;

            if ($scope.coreCount == 0) {
                $scope.coreLanguage = false;
            }

        }


        $scope.assessments.splice(index, 1);
        $scope.sections.splice(index,1);
        UpdateTotals(-tempQuantity);

    };

    $scope.maxQuestions;
    $scope.availabilityString ="";
    $scope.catInt;
    $scope.typeInt;


    function updateCategoryInt(){
        if($scope.category != ''){
            $scope.currentCategory = JSON.parse($scope.category);
            $scope.catInt = $scope.currentCategory.categoryId;
        }
    }

    function updateTypeInt(){
        if($scope.type != ''){
            $scope.currentType = JSON.parse($scope.type);
            $scope.typeInt = $scope.currentType.formatId;
        }
    }


    $scope.updateMax = function(){
        if($scope.type != '' && $scope.category != ''){
            updateCategoryInt();
            updateTypeInt();
            $http({
                method: "GET",
                url: ("assessmentrequest/"+$scope.catInt  + "/" + $scope.typeInt +"/" + 1 + "/")
            }).then(function (response) {
                $scope.maxQuestions = response.data;

                if($scope.maxQuestions == 0){
                    $scope.availabilityString = "(0 questions available)";
                }else{
                    $scope.availabilityString = "(1 - "+$scope.maxQuestions+" available)";
                }
            });
        }
    }


    //creates url and performs AJAX call to appropriate REST endpoint
    //sending the assessment time and criteria
    $scope.createAssessment = function() {

        if($scope.asshours + ($scope.assdays * 24) == 0){
            $scope.totalHourz = null;
        }else{
            $scope.totalHourz = $scope.asshours + ($scope.assdays * 24);
        }

        data = {
            "timeLimit": $scope.time,
            "categoryRequestList": $scope.assessments,
            "hoursViewable" : $scope.totalHourz,
            "name": $scope.name,
            "isDefault" : $scope.isDefault,
        };

        if($scope.edit){
            data["assessmentRequestId"] = $scope.id;
        }

        if($scope.coreLanguage == false){
            $scope.showToast("Core Language Section Required", "fail");
        }


        //var sendUrl = SITE_URL.BASE + API_URL.BASE + "/assessmentrequest/" + "1";
        // var sendUrl = SITE_URL.BASE + API_URL.BASE + "/assessmentrequest" + "/1/";
        else if($scope.coreLanguage == true){
            $http({
                method: 'PUT',
                url: (SITE_URL.BASE + API_URL.BASE + "/assessmentrequest" + "/1/"),
                headers: { 'Content-Type': 'application/json' },
                data: data
            }).success(function(response) {
                if($scope.edit){
                    $scope.showToast("Assessment successfully edited", "success");
                } else {
                    $scope.showToast("Assessment successfully created", "success");
                }

            }).error(function(response) {
                if($scope.edit){
                    $scope.showToast("Assessment editing failed", "fail");
                }else{
                    $scope.showToast("Assessment creation failed", "fail");
                }
            });
        }
    }
    $scope.addRow = function() {

        updateCategoryInt();
        updateTypeInt();


        $http({
            method: "GET",
            url: ("assessmentrequest/"+$scope.catInt  + "/" + $scope.typeInt +"/" + $scope.maxQuestions + "/")
        }).then(function (response) {
            $scope.numOfQuestions = response.data;
            $scope.quantity = $scope.maxQuestionsInput;
            if ($scope.quantity < $scope.numOfQuestions)
                $scope.sections.push({
                    'category': $scope.currentCategory.name,
                    'type': $scope.currentType.formatName,
                    'quantity': $scope.quantity
                });


            var tempCategory = $.grep($scope.categories, function (e) {
                return e.name == $scope.currentCategory.name;
            });


            var msQuestions = 0, mcQuestions = 0, ddQuestions = 0, csQuestions = 0;

            switch ($scope.currentType.formatName) {
                case $scope.types[0].formatName : { /* Multiple Choice changed to cs */

                    csQuestions += $scope.quantity;
                    break;
                }
                case $scope.types[1].formatName : { /* Multiple Select changed to dd */

                    ddQuestions += $scope.quantity;
                    break;
                }
                case $scope.types[2].formatName : { /* Drag 'n' Drop changed to mc */

                    mcQuestions += $scope.quantity;
                    break;
                }
                case $scope.types[3].formatName : { /* Code Snippet changed to ms */

                    msQuestions += $scope.quantity;
                    break;
                }
                default : {

                    break;
                }
            }

            if (tempCategory[0].name == "core language" || tempCategory[0].name == "Java" || tempCategory[0].name == ".net") {
                $scope.coreLanguage = true;
                $scope.coreCount++;
            }


            $scope.assessments.push({
                "category": {
                    "categoryId": tempCategory[0].categoryId,
                    "name": tempCategory[0].name
                },
                "msQuestions": msQuestions,
                "mcQuestions": mcQuestions,
                "ddQuestions": ddQuestions,
                "csQuestions": csQuestions

            });

            UpdateTotals($scope.quantity);
            $scope.sectionForm.$setPristine();
            $scope.sectionForm.$setUntouched();
            $scope.category = '';
            $scope.type = '';
            $scope.quantity = '';
            $scope.maxQuestions = '';
            $scope.availabilityString = '';
            $scope.showToast("Success - Section added", "success");



        });
    };

});