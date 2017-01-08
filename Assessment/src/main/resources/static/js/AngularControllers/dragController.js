app.controller("dragController", function($scope) {
	// DRAG AND DROP
	$scope.dragControlListeners = {
		itemMoved : function() {
			// add 'event' to function() above if this is broken
		},
		orderChanged : function() {
			// add 'event' to function() above if this is broken
		}
	};
});

app.controller('QuizNavController', function($scope, $rootScope) {
	
	$scope.index = 0;
    $scope.array = [];
    
    $rootScope.initQuizNav = function () {
    	for (var i=0; i < $rootScope.states.length/5; i++) {
    		$scope.array.push(i);
    	}
    }
    
    $scope.$watch(function(){
    	return $rootScope.states;
    }, function() {
		$scope.array = [];
		
		for(var i=0; i < $rootScope.states.length/5; i++){
        	$scope.array.push(i);
        }
	});
	
	
});