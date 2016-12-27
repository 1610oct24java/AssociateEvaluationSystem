'use strict';

var app; //the base application for angular.
var port = ":8090"; //the port for ajax calls.
var baseDirectory = "TestBank" //the base directory for AJAX calls.
var domain = "http://localhost"; //the base domain for AJAX calls.
var url = domain + port + "/" + baseDirectory + "/"; //a concatenation of the domain, port, and base directory to establish a base url.  
var formatList = [];
var format = {};
var questionList = [];
var question = {};

/* A JavaScript closure of a function using ES2015 concise syntax.
 * Using concise syntax of (()=> {})(); is equivalent of window.onload = function() {};
 * This function uses Angular to control the overall functionality of the HTML page.  
 */
(() => 
{
	
	//creating the module for the base application whose name is 'app'
	app = angular.module('app', ['ui.bootstrap', 'ui.bootstrap.tpls']); 
	
    app.controller('FormatController', function($http)
	{	
		this.getFormatList = () =>
		{
			$http.get(url+ "format")
				.then(response => 
				{
					console.log(response.data + " :: FL1");
					formatList = response.data;
					console.log(formatList + " :: FL2");
				});
		};
		
		this.getFormat = () => 
		{
			$http.get(url+"format/" +format)
		};
		
		
		angular.element(document).ready(()=> {
			this.getFormatList();
	    });
	});
	
	app.controller('QuestionController', function($http)
	{
		this.question ={
			questionId: 0,
			questionText : '',
			format: {
				formatId: 1,
				formatName: 'True/False'
			}
		};
		this.deleteme = 0;
		this.getQuestionList = () => 
		{
			$http.get(url+"question")
				.then(response=>{
					console.log(response.data + " :: QL1");
					this.questionList = response.data;
					console.log(this.questionList + " :: QL2");
					
				});
		};
		
		this.addQuestion = () => 
		{
			console.log("adding question: " + this.question)
			$http.post(url+"question", this.question)
				.success((response) => {
					this.question = response.data;
					if(question == null){
						alert("Error Saving Question Please Try Again")
					} else {
						this.getQuestionList();
						this.question ={
								questionId: 0,
								questionText : '',
								format: {
									formatId: 1,
									formatName: 'True/False'
								}
							};
					}
				});
		};
		
		this.deleteQuestion = () => 
		{
			$http.delete(url + "question/" + this.deleteme)
				.success((response) => {
					this.getQuestionList();
					alert("delete successful");
				})
				.error(()=>{
					alert("unable to properly delete the question");
				});
		};
		
		angular.element(document).ready(() => {
			this.getQuestionList();
	    });
	});
})  //the end of the closure
(); //invoking the function within the closure.