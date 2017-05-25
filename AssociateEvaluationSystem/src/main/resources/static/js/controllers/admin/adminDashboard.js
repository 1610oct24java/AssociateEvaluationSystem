/**
 * Created by Richard Wingert on 5/24/2017.
 */
adminApp.controller('AdminDashboardCtrl', function ($scope, $mdToast, $http, SITE_URL, API_URL, ROLE, $window) {
    //var inits
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
    function rec(fname, lname,userId, count){
        this.fname=fname;
        this.lname=lname;
        this.userId=userId;
        this.count=count;
    }


    //store all employees to $scope.employees
    $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.AUTH)
        .then(function (response) {
            if (response.data.authenticated) {
                var authUser = {
                    username: response.data.principal.username,
                    authority: response.data.principal.authorities[0].authority
                }
                $scope.authUser = authUser;
                if ($scope.authUser.authority != ROLE.ADMIN) {
                    window.location = SITE_URL.LOGIN;
                }

                $http.get(SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + API_URL.EMPLOYEES)
                    .then(function (response) {
                        $scope.employees = response.data;
                        $scope.getRoleCnts();
                        $scope.getRecruiterCnts()
                        console.log($scope.roleCnts);
                        console.log($scope.recruiterCnts);
                        console.log($scope.employees);
                        console.log($scope.recruiters);
                        console.log($scope.candidates);
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
            //console.log($scope.employees[i]);
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
            var r = new rec($scope.recruiters[i].firstName, $scope.recruiters[i].lastName, $scope.recruiters[i].userId, 0 );
            $scope.recruiterCnts.push(r);
        }
        var r = new rec("Deleted","Recruiter", 0, 0 );
        $scope.recruiterCnts.push(r);
        //for each candidate, find their recruiter in our recruiter array
        //and increment the count for that recruiter
        for(var i=0; i<$scope.candidates.length;i++){
            var added=false;
            for(var k=0;k<$scope.recruiterCnts.length;k++){
                if($scope.candidates[i].recruiterId==$scope.recruiterCnts[k].userId){
                    $scope.recruiterCnts[k].count++;
                    added=true;
                }
            }
            if(!added){
                $scope.recruiterCnts[$scope.recruiterCnts.length-1].count++;
            }
        }
    };
})

