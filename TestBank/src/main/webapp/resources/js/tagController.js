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
		
		this.saveTag = () =>{
			if(this.newTag.tagName == ''){
				alert("Please enter a name for the Tag");
			}
			else{
				console.log(this.newTag);
				$http.post(url + "tag", this.newTag).then(
					// success
					(response) => {
						// add new tag to the list
						this.tags.push({
							tagId : this.newTag.tagId,
							tagName: this.newTag.tagName});
						// clear the textbox
						this.newTag = {
							tagId : 0,
							tagName : ''
						};
					},
					// failure
					() => {
						alert("Unable to save tag!");
					}
				);
			}
		};
		
		this.getTags();
	});
	
})();