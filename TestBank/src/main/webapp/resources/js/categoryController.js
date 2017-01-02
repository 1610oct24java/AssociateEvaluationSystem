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
					(response) => {
						console.log(response);
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
				(response) => {
					console.log(response);
				},
				// failure
				() => {
					alert("Unable to remove category!")
				}
			);
		};
		this.getCategories();
	});
})();
