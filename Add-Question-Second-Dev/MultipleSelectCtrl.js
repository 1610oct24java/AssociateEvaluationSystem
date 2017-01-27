var app = angular.module("putInQuestion");

app.controller('multiSelectCtrl',['$scope','Data',
	function($scope, Data){

	$scope.optionList = [];

	$scope.$on('data_shared', function(){
		var options = Data.getData();
		$scope.optionList = options;
	})




	$scope.addOption = function(){//add tags to question

		if($scope.option !=null && $scope.option !='')
			if(!$scope.optionList.includes($scope.option))//checks for duplicates

		{
            $scope.optionList.push($scope.option);
            $scope.option = '';
            
        }
        
       	console.log(!$scope.optionList.includes($scope.option));
        console.log($scope.optionList);


	};

}]);