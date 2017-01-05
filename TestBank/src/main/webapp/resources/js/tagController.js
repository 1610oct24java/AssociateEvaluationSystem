"use strict";

var url = "http://localhost:8080/TestBank/";
var app;

(function() {
	
	app = angular.module("tagMod", [ "ui.bootstrap", "ui.bootstrap.tpls" ]);
	app.controller("tagCtrl", function($http) {
		this.tags =[];
		this.delTag = '';
		this.newTag = {
				tagId : 0,
				tagName : ''
		};
		
		this.getTags = () =>{
			$http.get(url + "tag").then(
				// success
				(response) => {
					this.tags = response.data;
				},
				// failure
				() => {
					alert("Unable to retrieve tags!");
				}
			); 	
		};
		
		this.getTags();
	});
	
})();