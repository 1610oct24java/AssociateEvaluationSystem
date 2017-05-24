adminApp.controller('CreateAssessmentCtrl', function($scope, $http, $mdToast, SITE_URL, API_URL, ROLE) {

    $scope.validateReview = function ()
    {
        if(($scope.assdays == null || $scope.asshours ==null || $scope.asshours <0 || $scope.assdays<0 )||(($scope.assdays ==0 && $scope.asshours == 0 ) && $scope.assReviewCheck == true))
            return true;
        else
            return false;
    };

    $scope.allowReview = function()
    {
        var totalHours = 0;


        if($scope.assReviewCheck)
        {
            totalHours= ($scope.assdays * 24) + $scope.asshours;
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

            if( $scope.category == category.category && $scope.type == category.type)
            {
                flag = true;
            }
            count ++;

        });


        if( flag == true)
            return true;
        else
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

    $(document).ready(function() {
        $('#example').DataTable({
            sDom: 'rt',
            fixedColumns: true,
            scrollY: "220px",
            scrollX: false,
            paging: false,
            "ordering": false
        });
        $scope.coreLanguage = false;
        $scope.coreCount = 0;
        $scope.showModal = false;
        $scope.assdays = 0;
        $scope.asshours = 0;
    });

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
            } else {
                window.location = SITE_URL.LOGIN;
            }
        });

    $scope.times = [15, 30, 45, 60, 75, 90];

    $scope.assessments = [];
    $scope.sections = [];
    $scope.totalQuestions = 0;
    $scope.totalCategories = 0;
    $scope.totalTypes = 0;
    $scope.time;

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

            if(item['mcQuestions'] > 0 && mcBool == false){
                types++;
                mcBool = true;
            }
            if(item['msQuestions'] > 0 && msBool == false){
                types++;
                msBool = true;
            }
            if(item['ddQuestions'] > 0 && ddBool == false){
                types++;
                ddBool = true;
            }
            if(item['csQuestions'] > 0 && csBool == false){
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

        if($scope.assessments[index]['category'].categoryId == 6){
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
        if($scope.category.toUpperCase() === "CSS"){
            $scope.catInt = 11;

        }else if($scope.category.toUpperCase() === "CORE LANGUAGE"){
            $scope.catInt = 1;

        }else if($scope.category.toUpperCase() === "CRITICAL THINKING"){
            $scope.catInt = 5;

        }else if($scope.category.toUpperCase() === "DATA STRUCTURES"){
            $scope.catInt = 3;

        }else if($scope.category.toUpperCase() === "HTML"){
            $scope.catInt = 10;

        }else if($scope.category.toUpperCase() === "JAVASCRIPT"){
            $scope.catInt = 12;

        }else if($scope.category.toUpperCase() === "OOP"){
            $scope.catInt = 2;

        }else if($scope.category.toUpperCase() === "SQL"){
            $scope.catInt = 4;
        }
    }
    function updateTypeInt(){
        if($scope.type.toUpperCase() === "MULTIPLE CHOICE"){
            $scope.typeInt = 1;

        }else if($scope.type.toUpperCase() === "MULTIPLE SELECT"){
            $scope.typeInt = 2;

        }else if($scope.type.toUpperCase() === "DRAG AND DROP"){
            $scope.typeInt = 3;

        }else if($scope.type.toUpperCase() === "CODE SNIPPET"){
            $scope.typeInt = 4;
        }
    }

    $scope.updateMax = function(){
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
    $scope.addRow = function() {

        updateCategoryInt();
        updateTypeInt();

        $http({
            method: "GET",
            url: ("assessmentrequest/"+$scope.catInt  + "/" + $scope.typeInt +"/" + $scope.maxQuestions + "/")
        }).then(function (response) {
            $scope.numOfQuestions = response.data;
            $scope.quantity=$scope.maxQuestions;
            if($scope.quantity > $scope.numOfQuestions){
                alert("There are only " + $scope.numOfQuestions + " of those questions available.");
            }else{
                $scope.sections.push({ 'category': $scope.category, 'type': $scope.type, 'quantity': $scope.quantity });


                var tempCategory = $.grep($scope.categories, function(e){ return e.name == $scope.category; });


                var msQuestions=0, mcQuestions=0, ddQuestions=0, csQuestions=0;

                switch($scope.type) {
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

                if(tempCategory[0].categoryId == 6){
                    $scope.coreLanguage = true;
                    $scope.coreCount++;

                    if($scope.assessments.length > 0){
                        var indexOfCoreLanguage = 0;
                        for(var i = 0; i < $scope.assessments.length; i++){
                            if($scope.assessments[i].category.categoryId == 6){
                                indexOfCoreLangage = i;
                            }
                        }
                        $scope.assessments[indexOfCoreLanguage].csQuestions += csQuestions;
                        $scope.assessments[indexOfCoreLanguage].msQuestions += msQuestions;
                        $scope.assessments[indexOfCoreLanguage].ddQuestions += ddQuestions;
                        $scope.assessments[indexOfCoreLanguage].mcQuestions += mcQuestions;
                    }else{
                        $scope.assessments.push({
                            "category": {
                                "categoryId": tempCategory[0].categoryId,
                                "name":  tempCategory[0].name
                            },
                            "msQuestions": msQuestions,
                            "mcQuestions": mcQuestions,
                            "ddQuestions": ddQuestions,
                            "csQuestions": csQuestions

                        });
                    }

                }else{
                    $scope.assessments.push({
                        "category": {
                            "categoryId": tempCategory[0].categoryId,
                            "name":  tempCategory[0].name
                        },
                        "msQuestions": msQuestions,
                        "mcQuestions": mcQuestions,
                        "ddQuestions": ddQuestions,
                        "csQuestions": csQuestions

                    });
                }

                UpdateTotals($scope.quantity);
                $scope.sectionForm.$setPristine();
                $scope.sectionForm.$setUntouched();
                $scope.category = '';
                $scope.type = '';
                $scope.quantity = '';
                $scope.showToast("Success - Section added", "success");
            };

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
                    "isDefault" : 0,
                    "name": $scope.name
                };

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
                        $scope.showToast("Assessment created successfully", "success");

                    }).error(function(response) {
                        $scope.showToast("Assessment creation failed", "fail");

                    });
                }
            }
        });
    };
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
});
