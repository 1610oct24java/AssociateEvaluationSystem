angular.module('bankApp').controller('CategoryCtrl', function($scope, $http){
	$scope.categories;
	$scope.delCategory = '';
	$scope.newCategory = {
			categoryId : 0,
			name : ''
	};
	
	$scope.getCategories = function() {
		$http.get("category").then(
			function(response) {
				$scope.categories = response.data;
			})
	}
	
	$scope.saveCategory = function() {
		if($scope.newCategory.name == ''){
			// #alert dem bitches
		}
		else{
			$http.post("category", $scope.newCategory).then(
				// success
				function() {
					// add new category to the list
					$scope.categories.push({
						categoryId : $scope.newCategory.categoryId,
						name: $scope.newCategory.name});
					// clear the textbox
					$scope.newCategory = {
						categoryId : 0,
						name : ''
					};
				}
			);
		}
	};
	
	$scope.deleteCategory = function() {
		$http.delete("category/" + $scope.delCategory).then(
			// success
			function() {
				$scope.categories.splice($scope.findIndexOfCategory(), 1);
				$scope.delCategory = '';
			}
		);
	};
	
	// Find the index of the category matching delCategory.
	// The index is needed to remove the element from the list.
	$scope.findIndexOfCategory = function() {
		for(var i=0;i<$scope.categories.length;i++){
			if ($scope.categories[i].name == $scope.delCategory){
				return i;
			}
		}
	};
	
	$scope.getCategories();
	
});