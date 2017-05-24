/* COUNTDOWN TIMER LOGIC */
asmt.controller('CountdownController', function($scope, $rootScope, $interval, $timeout) {
	
	var startTime = -111;	// default to show loading message
	$scope.seconds = startTime;
	$scope.timeLeft = "";
	var testLength = 500;
	$scope.barUpdate = getBarUpdate();
	$scope.submitModal = document.getElementById("submitModal");
	$scope.timerTextColor = {color:'#474C55'};
    
	var timer = $interval(function(){
		$scope.barUpdate = {width:getBarUpdate()+'%'};
		
		// Change timer text colors once the color has decreased by 50%
		// to a darker grey color to keep readability
		if ($scope.seconds != -111 && ($scope.seconds < startTime/2))
		{
			$scope.timerTextColor = {color:'#474C55'};
		}
		
		//WHEN THE TIMER REACHES ZERO,
		//OPEN THE SUBMIT MODAL
		if ($scope.seconds < 0 && $scope.seconds != -111)
		{
			$rootScope.stopTimer();
			
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
			
			return (($scope.seconds) / testLength) * 100;
		}
	}
	
	function showSubmitModal() {
		submitModal.style.display = "block";
		
		$timeout(function () {
			$rootScope.submitAssessment();
	    }, 3000);
	}
	
	$rootScope.initTimer = function (timeLimit, newTime) {
		if(newTime == -1){
			startTime = timeLimit * 60;
			$scope.seconds = startTime;
		}
		else{
			startTime = newTime * 60;
			$scope.seconds = startTime;
			
		}
		testLength = timeLimit * 60;
		
    }

	$rootScope.stopTimer = function () {
		$interval.cancel(timer);
	}
});