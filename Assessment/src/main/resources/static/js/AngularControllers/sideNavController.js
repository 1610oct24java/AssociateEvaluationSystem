app.controller('SideNavController', function($scope, $interval, $http) {

	$scope.submitAssessment = function() {
		answerData = {
			"id" : 1
		};

		postAssessment(answerData);
	}

	function postAssessment(answerData) {
		$http({
			method : 'POST',
			url : 'submitAssessment',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : answerData
		}).then(function(response) {
			// First function handles success
			console.log("Answers sent: " + response.data);
		}, function(response) {
			// Second function handles error
			console.log("status code: " + response.status);
		});
	}
});