/* COUNTDOWN TIMER LOGIC */
app.controller('CountdownController', function($scope, $rootScope, $interval) {
	
	var startTime = 1200;
	$scope.minutes = 0;
	$scope.seconds = startTime;
	$scope.barUpdate = getBarUpdate();
	$scope.submitModal = document.getElementById("submitModal");
	
//    m = checkTime(m);
//    s = checkTime(s);
    
	var timer = $interval(function(){
		$scope.barUpdate = {width:getBarUpdate()+'%'};
		
		//WHEN THE TIMER REACHES ZERO,
		//OPEN THE SUBMIT MODAL
		if ($scope.minutes < 0 && $scope.seconds < 0)
		{
			submitAssessment();
		}
	}, 1000);
	
	function getBarUpdate() {
		$scope.seconds = $scope.seconds - 1;
		
		if ($scope.seconds < 0)
		{
			$scope.seconds = 59;
			$scope.minutes = $scope.minutes - 1;
		}

		//console.log($scope.minutes + "m " + $scope.seconds + "s");
		
		if ($scope.minutes < 0) {
			$interval.cancel(timer);
			showSubmitModal();
			console.log("time over");
		}
		
		return ((($scope.minutes*60)+$scope.seconds) / startTime) * 100;
	}
	
	function checkTime(i) {
	    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
	    return i;
	}
	
	function showSubmitModal() {
		submitModal.style.display = "block";
	}
});