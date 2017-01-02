var app = angular.module('AESRecruiterApp',['ngRoute']);

app.config(function($routeProvider,$locationProvider) {
	$locationProvider.html5Mode(true);
	$routeProvider
	.when('/CoreAngular', {
		templateUrl: 'CoreAngular/resources/static/views/login.html',
		controller : 'LoginCtrl'
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

var userAuth = function() {
	$http.get("http://localhost:8080/core/user")
	.then(function(response) {
		console.log("INSIDE RESPONSE TO /USER IN USERAUTH()");
		let authorized = response.data.authenticated; 
		if (authorized && response.data.principal.authorities[0].authority === "ROLE_RECRUITER") {
			return true;
		}
		return false;
	})
};




