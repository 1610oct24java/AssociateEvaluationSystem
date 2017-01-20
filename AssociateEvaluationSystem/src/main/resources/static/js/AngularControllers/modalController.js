app.controller('ModalController', function($scope, $interval, $http) {
	
	$scope.submitAssessment = function(){
		var answerData = {"id":1};
		
		postAssessment(answerData);
	}
	
	function postAssessment(answerData){
		$http({
			method: 'POST',
			url: 'http://localhost:8090/aes/rest/submitAssessment',
			headers: {'Content-Type': 'application/json'},
			data: answerData
		});
	}
});