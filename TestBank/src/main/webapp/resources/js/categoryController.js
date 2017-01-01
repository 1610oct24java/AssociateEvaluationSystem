"use strict";

var url = "http://localhost:8080/TestBank/";
var app;
var clist;
(function() {
	app = angular.module("myCatMod", [ "ui.bootstrap", "ui.bootstrap.tpls" ]);
	app.controller("CatModCtrl", function($http) {
		this.categories =[];
		this.getCategories = () =>{
			$http.get(url + "category").then(
				// success
				(response) => {
					this.categories = response.data;
					clist=this.categories;
					console.log(clist[0]);
				},
				// failure
				() => {
					alert("Unable to retrieve categories!");
				}
			); 	
		};
		this.getCategories();
	});
})();
