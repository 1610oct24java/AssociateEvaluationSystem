/**
 * @class AES.userApp.recruiterDashboardCtrl
 */

userApp.controller('recruiterDashboardCtrl', function($scope,$mdToast,$location,$http,SITE_URL, API_URL, ROLE) {

    $scope.average;
    $scope.candidateCount;
    $scope.asmtCount;
    $scope.sum;
    $scope.graphData = [];
    $scope.timeFrame;
    $scope.assessments = [];

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
                        $scope.candidateCount = c.length;
                        for (var i=0; i<c.length; i++) {
                            getAssessments(c[i].userId, c[i].email);
                            c[i].expanded = false;
                        }
                    })
            } else {
                window.location = SITE_URL.LOGIN;
            }
        });

    function getAssessments(num, email) {
        $http
            .get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + email + "/assessments")
            .then(function (response) {
                var candidateAsmts = response.data;
                candidateAsmts.forEach(function(a){
                   if(a.grade != -1){
                       $scope.assessments.push(a);
                       $scope.asmtCount += 1;
                   }
                })
                $scope.returnCheck = true;
            });
    };

    function filterAssessments(){
        $scope.graphData = [];
        $scope.sum = 0;
        $scope.asmtCount= 0;
        var startTimeRange = new Date();
        startTimeRange.setDate(startTimeRange.getDate()-$scope.timeFrame);
        $scope.assessments.forEach(function(a){
            var timestamp = a.finishedTimeStamp;
            var grade = a.grade;
            if(grade != -1 && timestamp > startTimeRange.getTime()){
                var point = [new Date(timestamp), grade];
                $scope.graphData.push(point);
                $scope.sum += a.grade;
                $scope.asmtCount += 1;
            }
        });
        $scope.asmtCount ? $scope.average = $scope.sum/$scope.asmtCount:$scope.average=0;
    }

    $scope.updateGraph = function() {
        filterAssessments();
        var startTimeRange = new Date();
        startTimeRange.setDate(startTimeRange.getDate()-$scope.timeFrame);
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
        var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));
        chart.draw(data, options);
    }

    $(document).ready(function(){
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback($scope.updateGraph);
        filterAssessments();
    });

});


