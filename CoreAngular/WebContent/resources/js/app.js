var app = angular.module('AESRecruiterApp',['ngRoute']);

//angular.module('AESRecruiterApp').constant('BASE_URL',)

//CoreAngular/resources/static

app.controller('navCtrl',function($scope,$location) {
    $scope.isActive = function (viewLocation) {
        return $location.path().includes(viewLocation);
    };
});

app.config(function($routeProvider,$locationProvider) {
    $locationProvider.html5Mode(true);
    $routeProvider
    .when('/CoreAngular', {
        templateUrl: 'CoreAngular/resources/static/views/login.html',
        controller : 'LoginCtrl'
    })
    .when('/RecruiterDashboard', {
        templateUrl: 'CoreAngular/resources/static/views/recruiter-dashboard.html',
        controller : 'RecruiterDashboardCtrl'
    })
    .when('/RegisterCanidate', {
        templateUrl: 'CoreAngular/resources/static/views/register-canidate.html',
        controller : 'RegisterCanidateCtrl'
    })
    .when('/CanidateViewer', {
        templateUrl: 'CoreAngular/resources/static/views/canidate-viewer.html',
        controller : 'CanidateViewerCtrl'
    })
    .otherwise({
        redirectTo: 'nhl.com'
    })
});




