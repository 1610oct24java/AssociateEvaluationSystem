app.controller("TagCtrl", function($http, $scope) {
	var url = "http://localhost:8080/TestBank/";
		$scope.tags =[];
		$scope.delTag = '';
		$scope.newTag = {
				tagId : 0,
				tagName : ''
		};
		
		$scope.getTags = function() {
			$http.get(url + "tag").then(
				// success
				function(response) {
					$scope.tags = response.data;
				}
			); 	
		};
		
		$scope.saveTag = function() {
			if($scope.newTag.tagName == ''){
			}
			else{
				$http.post(url + "tag", $scope.newTag).then(
					// success
					function() {
						// add new tag to the list
						$scope.tags.push({
							tagId : $scope.newTag.tagId,
							tagName: $scope.newTag.tagName});
						// clear the textbox
						$scope.newTag = {
							tagId : 0,
							tagName : ''
						};
					}
				);
			}
		};
		
		$scope.findIndexOfTag = function() {
			for(var i=0;i<$scope.tags.length;i++){
				if($scope.tags[i].tagName==$scope.delTag){
					return i;
				}
			}			
		};
		
		$scope.deleteTag = function() {
			if($scope.delTag==''){
				return;
			}
			$http.delete(url + "tag/" + $scope.delTag).then(
				function() {
					$scope.tags.splice($scope.findIndexOfTag(), 1);
					$scope.delTag = '';
				}
			);
		};
		
		$scope.getTags();
	});