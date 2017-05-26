userApp.controller('recruiterDashboardCtrl', function($scope,$mdToast,$location,$http,SITE_URL, API_URL, ROLE) {

    $scope.average = 0;
    $scope.candidateCount = 0;
    $scope.asmtCount = 0;
    $scope.sum = 0;
    $scope.averagesOverTime = [];

    function show2(num, email) {
        $scope.assessments = [];
        $scope.returnCheck = false;

        $http
            .get(SITE_URL.BASE + API_URL.BASE + API_URL.RECRUITER + email + "/assessments")
            .then(function (response) {
                var asmt = response.data;
                if (asmt.length != 0) {
                    asmt.forEach(function(a){
                        var timestamp = a.finishedTimeStamp;
                        var grade = a.grade;
                        if(grade != -1){
                            var point = {x:timestamp, y:grade};
                            $scope.averagesOverTime.push(point);
                            $scope.sum += a.grade;
                            $scope.asmtCount += 1;
                        }
                    });
                }
                $scope.assessments = asmt;
                $scope.returnCheck = true;
            });
    };

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
                            sumAndCount = show2(c[i].userId, c[i].email);
                            c[i].expanded = false;
                        }
                        $scope.candidates = c;
                    })
            } else {
                window.location = SITE_URL.LOGIN;
            }
        });
    $(document).ready(function(){
        var ctx = document.getElementById("myChart").getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'scatter',
            data: {
                labels: dateLabels(7),
                datasets: [{
                    label: 'Assessment scores within time frame',
                    data: $scope.averagesOverTime,
                    fill: false
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });
    });
    function updateGraph(){
        var ctx = document.getElementById("myChart").getContext('2d');
        var myChart = new Chart(ctx, {
            data: {
                labels: dateLabels(7),
                datasets: [{
                    label: 'Assessment scores within time frame',
                    data: $scope.averagesOverTime,
                    fill: false
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero:true
                        }
                    }]
                }
            }
        });
    }
    function dateLabels(numDays){
        var dateLabels = [];
        var nowTime = new Date();
        nowTime.setDate(nowTime.getDate() - numDays);
        for(var i = 0; i < numDays; i++){
            dateLabels[i] = (nowTime.toLocaleDateString("en-US"));
            nowTime.setDate(nowTime.getDate() + 1);
        }
        return dateLabels;
    }

});
