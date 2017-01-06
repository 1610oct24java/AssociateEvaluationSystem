"use strict";

var url = "http://localhost:8080/TestBank/";
var app;

(function() {
	
	app = angular.module("myCatMod", [ "ui.bootstrap", "ui.bootstrap.tpls" ]);
	app.controller("CatModCtrl", function($http) {
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
					alert("Unable to retrieve categories!");
				}
			); 	
		};
		
		this.saveCategory = () =>{
			if(this.newCategory.name == ''){
				alert("Please enter a name for the category");
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
						alert("Unable to save category!");
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
					alert("Unable to remove category!")
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
		
	});
	
})();
