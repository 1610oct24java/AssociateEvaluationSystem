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
				$http.post(url + "tag", this.newTag).then(
					// success
					() => {
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
		
		this.findIndexOfTag = () =>{
			for(var i=0;i<this.tags.length;i++){
				if(this.tags[i].tagName==this.delTag){
					return i;
				}
			}			
		};
		
		this.deleteTag = () =>{
			if(this.delTag==''){
				alert("Unable to remove tag!");
				return;
			}
			$http.delete(url + "tag/" + this.delTag).then(
				// success
				() => {
					this.tags.splice(this.findIndexOfTag(), 1);
					this.delTag = '';
				},
				// failure
				() => {
					alert("Unable to remove tag!")
				}
			);
		};
		
		this.getTags();
	});
	
})();