app.controller("dragController", function($scope) {
	// DRAG AND DROP
	$scope.dragControlListeners = {
		accept : function(sourceItemHandleScope, destSortableScope) {
			return boolean
		},
		itemMoved : function(event) {
			console.log("Item Moved!");
			console.log(event);
		},
		orderChanged : function(event) {
			console.log("Order Changed");
			console.log(event);
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
    	console.log("Saw, length: " + $rootScope.states.length);
		$scope.array = [];
		
		for(var i=0; i < $rootScope.states.length/5; i++){
        	$scope.array.push(i);
        	console.log("Tick");
        }
		console.log("After loop");
	});
	
	
});