app.controller('CategoryCtrl', function($scope, $http){
	var url = "http://localhost:8080/TestBank/";
	$scope.categories =[];
	$scope.delCategory = '';
	$scope.newCategory = {
			categoryId : 0,
			name : ''
	};
	
	function getCategories() {
		var array = $http.get(url + "category").then(
				function(response){
					return response.data;
				}); 
		return array.$$state;
	};
	
	$scope.saveCategory = function() {
		if($scope.newCategory.name == ''){
			// alert dem bitches
		}
		else{
			$http.post(url + "category", $scope.newCategory).then(
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
		$http.delete(url + "category/" + $scope.delCategory).then(
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
	
	$scope.categories =  getCategories();
	console.log($scope.categories);
});