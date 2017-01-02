app.controller('NavCtrl', function($scope, $http, $location) {
	
	$http.get("http://localhost:8080/core/user")
	.then(function(response) {
		if (response.data.authenticated && response.data.principal.authorities[0].authority === "ROLE_RECRUITER") {
			
		}
	})
	
});