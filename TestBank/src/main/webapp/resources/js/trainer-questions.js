'use strict';

var app; //the base application for angular.
var port = ":8090"; //the port for ajax calls.
var baseDirectory = "TestBank" //the base directory for AJAX calls.
var domain = "http://192.168.0.15"; //the base domain for AJAX calls.
var url = domain + port + "/" + baseDirectory + "/"; //a concatenation of the domain, port, and base directory to establish a base url.  

/* A JavaScript closure of a function using ES2015 concise syntax.
 * Using concise syntax of (()=> {})(); is equivalent of window.onload = function() {};
 * This function uses Angular to control the overall functionality of the HTML page.  
 */
(() => 
{
	//creating the module for the base application whose name is 'app'
	app = angular.module('app', []); 
	
	app = angular.controller('FormatController', ($http) =>
	{
		this.formatList = [];
		this.format = {};
		this.getFormatList = () =>
		{
			$http.get(url+ "format")
				.then(response => 
				{
					this.formatList = response.data;
				});
		};
		
		this.getFormat = () => 
		{
			$http.get(url+"format/" +format)
		};
		
		getFormatList();

	});
	
	app = angular.controller('QuestionController', ($http)=>
	{
		this.questionList = [];
		this.question = {};
		this.getQuestionList = () => 
		{
			$http.get(url+"question")
				.success((response)=>{
					this.questionList = response.data;
				});
		}
		
		getQuestionList();
	});
})  //the end of the closure
(); //invoking the function within the closure.