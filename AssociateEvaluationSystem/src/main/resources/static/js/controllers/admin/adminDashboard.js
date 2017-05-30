/**
 * Created by Richard Wingert on 5/24/2017.
 */
adminApp.controller('AdminDashboardCtrl', function ($scope, $mdToast, $http, SITE_URL, API_URL, ROLE) {
    $scope.employees;
    $scope.roleCnts=[];
    $scope.recruiterCnts=[];
    $scope.recruiters=[];
    $scope.candidates=[];
    //constructor for role object
    function role(type, count){
        this.type=type;
        this.count=count;
    }
    function rec(fname, lname,userId, count, email){
        this.fname=fname;
        this.lname=lname;
        this.userId=userId;
        this.count=count;
        this.email = email;
    }


    //store all employees to $scope.employees
    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
        .then(function (response) {
            //check if authenticated
            if (response.data.authenticated) {
                var authUser = {
                    username: response.data.principal.username,
                    authority: response.data.principal.authorities[0].authority
                }
                //if not authenticated, redirect to login
                $scope.authUser = authUser;
                if ($scope.authUser.authority != ROLE.ADMIN) {
                    window.location = SITE_URL.LOGIN;
                }

                $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES)
                    .then(function (response) {
                        $scope.employees = response.data;
                        console.log($scope.employees);
                        console.log($scope.authUser.username);
                        $scope.getRoleCnts();
                        $scope.getRecruiterCnts()
                    });
            } else {
                window.location = SITE_URL.LOGIN;
            }
        });

    //iterate through all users and get their role.
    //store roles found into $scope.roleCnts and keep a count of all roles.
    //to dynamically track roles, we must also iterate through the roles array
    $scope.getRoleCnts = function () {
        //get employee count types
        for (var i = 0; i < $scope.employees.length; i++) {
            var added=false;
            for (var k = 0; k < $scope.roleCnts.length; k++) {
                if ($scope.employees[i].role.roleTitle==$scope.roleCnts[k].type ){
                    $scope.roleCnts[k].count++;
                    added=true;
                }
            }
            if (!added){
                var c = new role($scope.employees[i].role.roleTitle, 1);
                $scope.roleCnts.push(c)
            }
            //set recuiter list
            if($scope.employees[i].role.roleTitle=="recruiter") {
                $scope.recruiters.push($scope.employees[i])
            }
            //set candidate list
            else if($scope.employees[i].role.roleTitle=="candidate") {
                $scope.candidates.push($scope.employees[i])
            }
        }
    };

    //get number of candidates each recruiter has
    $scope.getRecruiterCnts = function () {
        //init recruiter count array
        for (var i = 0; i<$scope.recruiters.length;i++){
            var r = new rec($scope.recruiters[i].firstName, $scope.recruiters[i].lastName, $scope.recruiters[i].userId, 0, $scope.recruiters[i].email );
            $scope.recruiterCnts.push(r);
        }
        var delr = new rec("No Listed","Recruiter", 0, 0 );
        $scope.recruiterCnts.push(delr);
        //for each candidate, find their recruiter in our recruiter array
        //and increment the count for that recruiter
        for(var j=0; j<$scope.candidates.length;j++){
            var added=false;
            for(var k=0;k<$scope.recruiterCnts.length;k++){
                if($scope.candidates[j].recruiterId==$scope.recruiterCnts[k].userId){
                    $scope.recruiterCnts[k].count++;
                    added=true;
                }
            }
            if(!added){
                $scope.recruiterCnts[$scope.recruiterCnts.length-1].count++;
            }
        }
    }
    $scope.graphData = [];
    $scope.timeFrame;
    $scope.assessments = [];
    $scope.returnCheck = false;

    function getCandidates(recruiterUsername){
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
                    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER +"/"+recruiterUsername + "/candidates")
                        .then(function(response) {
                            var c =  response.data;
                            $scope.assessments = [];
                            for (var i=0; i<c.length; i++) {
                                getAssessments(c[i].userId, c[i].email);
                                c[i].expanded = false;
                            }
                            console.log($scope.assessments);
                        })

                } else {
                    window.location = SITE_URL.LOGIN;
                }
            });
    }

    function getAssessments(num, email) {
        $http
            .get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + "/"+email + "/assessments")
            .then(function (response) {
                var candidateAsmts = response.data;
                candidateAsmts.forEach(function(a){
                    if(a.grade != -1){
                        $scope.assessments.push(a);
                    }
                });
                updateGraph(num);
                $scope.returnCheck = true;
            });
    };


    $scope.viewGraph = function(num, email){

        getCandidates(email);
        updateGraph(num);
        console.log("done");
        var myEl = angular.element( document.querySelector( '#g'+num ) );

        if(angular.element(document.querySelector('#g'+num).classList)[0] == "ng-hide"){
            myEl.removeClass("ng-hide");
            myEl.addClass("ng-show");
        } else {
            myEl.removeClass("ng-show");
            myEl.addClass("ng-hide");
        }
    };
    function updateGraph(recruiterId) {
        filterAssessments();
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(function(){
            var startTimeRange = new Date();
            startTimeRange.setDate(startTimeRange.getDate()-90);
            var data = new google.visualization.DataTable();
            data.addColumn('date', 'Date');
            data.addColumn('number', 'Grade');
            data.addRows($scope.graphData);
            var options = {
                title: 'Assessment Grade Scatterplot',
                hAxis: {
                    title: 'Date',
                    viewWindow: {
                        min: startTimeRange,
                        max: new Date()
                    }
                },
                vAxis: {title: 'Grade', minValue: 0, maxValue: 100},
                legend: 'none'
            };
            var chart = new google.visualization.ScatterChart(document.getElementById('chart'+recruiterId));
            chart.draw(data, options);
        });
    };

    function filterAssessments(){
        $scope.graphData = [];
        var startTimeRange = new Date();
        startTimeRange.setDate(startTimeRange.getDate()-90);
        $scope.assessments.forEach(function(a){
            var timestamp = a.finishedTimeStamp;
            var grade = a.grade;
            if(grade != -1 && timestamp > startTimeRange.getTime()){
                var point = [new Date(timestamp), grade];
                $scope.graphData.push(point);
            }
        });
    }

})

