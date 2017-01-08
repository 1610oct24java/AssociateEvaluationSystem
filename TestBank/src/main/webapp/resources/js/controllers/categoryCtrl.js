app.controller('CategoryCtrl', ['$scope', function($scope, $http){
	var url = "http://localhost:8080/TestBank/";
	this.categories =[];
	this.delCategory = '';
	this.newCategory = {
			categoryId : 0,
			name : ''
	};
	
	this.getCategories = () =>{
		$http.get(url + "category").then(
			// success
			(response) => {
				this.categories = response.data;
			},
			// failure
			() => {
				// failure
			}
		); 	
	};
	
	this.saveCategory = () =>{
		if(this.newCategory.name == ''){
			//just to shut up sonarQube
		}
		else{
			$http.post(url + "category", this.newCategory).then(
				// success
				() => {
					// add new category to the list
					this.categories.push({
						categoryId : this.newCategory.categoryId,
						name: this.newCategory.name});
					// clear the textbox
					this.newCategory = {
						categoryId : 0,
						name : ''
					};
				},
				// failure
				() => {
					// failure
				}
			);
		}
	};
	
	this.deleteCategory = () =>{
		$http.delete(url + "category/" + this.delCategory).then(
			// success
			() => {
				this.categories.splice(this.findIndexOfCategory(), 1);
				this.delCategory = '';
			},
			// failure
			() => {
				// failure
			}
		);
	};
	
	// Find the index of the category matching delCategory.
	// The index is needed to remove the element from the list.
	this.findIndexOfCategory = () =>{
		for(var i=0;i<this.categories.length;i++){
			if (this.categories[i].name == this.delCategory){
				return i;
			}
		}
	};
	
	this.getCategories();
	
}]);