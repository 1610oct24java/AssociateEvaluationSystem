app.controller('RegisterCanidateCtrl', function($scope,$location,$http) {

	$scope.register = function() {

		var canidateInfo = JSON.stringify({
			userId        : null,
			email         : $scope.email,
			firstName     : $scope.firstName,
			lastName      : $scope.lastName,
			salesforce    : null,
			recruiterId   : null,
			role          : null,
			datePassIssued: null
		});

		console.log('canidateInfo: ' + canidateInfo);

		$scope.firstName = '';
		$scope.lastName = '';
		$scope.email = '';
		$scope.program = '';
	};

	$scope.postRegister = function(canidateInfo) {
		$http({
			method  : 'POST',
			url: 'http://localhost:8080/core/canidates',
			headers : {'Content-Type' : 'application/json'},
			data    : 'canidateInfo'
		}).success( function(res) {
			console.log('success');
			console.log(res);
		}).error( function(res) {
			console.log('error');
			console.log(res);
		});
	};

	//populate dynamically from db
	$scope.options = [{
		name: 'Java',
		value: 'java'
	}, {
		name: 'SDET',
		value: 'sdet'
	}, {
		name: '.NET',
		value: 'dotnet'
	}];

});