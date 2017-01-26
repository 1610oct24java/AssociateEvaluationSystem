/* COUNTDOWN TIMER LOGIC */
app.controller('CountdownController', function($scope, $rootScope, $interval, $timeout) {
	
	var startTime = -111;	// default to show loading message
	$scope.seconds = startTime;
	$scope.timeLeft = "";
	$scope.barUpdate = getBarUpdate();
	$scope.submitModal = document.getElementById("submitModal");
	$scope.timerTextColor = {color:'#ffffff'};
    
	var timer = $interval(function(){
		$scope.barUpdate = {width:getBarUpdate()+'%'};
		
		if ($scope.seconds != -111 && ($scope.seconds < startTime/2))
		{
			$scope.timerTextColor = {color:'#474C55'};
		}
		
		//WHEN THE TIMER REACHES ZERO,
		//OPEN THE SUBMIT MODAL
		if ($scope.seconds < 0 && $scope.seconds != -111)
		{
			$interval.cancel(timer);
			
			$timeout(function () {
				showSubmitModal();
		    }, 1000);
		}
	}, 1000);
	
	function getBarUpdate() {
		// Until the time limit is loaded, display loading message
		if ($scope.seconds == -111)
		{
			$scope.timeLeft = "Loading...";
			return -111;
			
		}else {
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
	}
	
	function showSubmitModal() {
		submitModal.style.display = "block";
		
		$timeout(function () {
			$rootScope.submitAssessment();
	    }, 3000);
	}
	
	$rootScope.initTimer = function (timeLimitInMin) {
		startTime = timeLimitInMin * 60;
		$scope.seconds = startTime;
    }
});