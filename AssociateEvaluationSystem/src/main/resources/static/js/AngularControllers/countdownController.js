/* COUNTDOWN TIMER LOGIC */
app.controller('CountdownController', function($scope, $rootScope, $interval) {
	
	var startTime = 3600;	// default
	$scope.seconds = startTime;
	$scope.timeLeft = "";
	$scope.barUpdate = getBarUpdate();
	$scope.submitModal = document.getElementById("submitModal");
    
	var timer = $interval(function(){
		$scope.barUpdate = {width:getBarUpdate()+'%'};
		
		//WHEN THE TIMER REACHES ZERO,
		//OPEN THE SUBMIT MODAL
		if ($scope.seconds < 0)
		{
			$interval.cancel(timer);
			showSubmitModal();
		}
	}, 1000);
	
	function getBarUpdate() {
		$scope.seconds = $scope.seconds - 1;
		
		var min = Math.floor($scope.seconds / 60);
		var sec = $scope.seconds % 60;
		
		if (min < 1) { 
			$scope.timeLeft = sec + "s";
		}else {
			$scope.timeLeft = min + "m " + sec + "s";
		}
		
		return (($scope.seconds) / startTime) * 100;
	}
	
	function showSubmitModal() {
		submitModal.style.display = "block";
	}
	
	$rootScope.initTimer = function (timeLimitInMin) {
		startTime = timeLimitInMin * 60;
		$scope.seconds = startTime;
    }
});