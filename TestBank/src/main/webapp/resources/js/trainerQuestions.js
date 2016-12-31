	'use strict';

var app; // the base application for angular.
var port = ":8090"; // the port for ajax calls.
var baseDirectory = "TestBank" // the base directory for AJAX calls.
var domain = "http://localhost"; // the base domain for AJAX calls.
var url = domain + port + "/" + baseDirectory + "/"; // a concatenation of
														// the domain, port, and
														// base directory to
														// establish a base url.
var formatList = [];
var questionformat = {};
var questionList = [];
var question = {};
var category = {};
var categoryList = [];
/*
 * A JavaScript closure of a function using ES2015 concise syntax. Using concise
 * syntax of (()=> {})(); is equivalent of window.onload = function() {}; This
 * function uses Angular to control the overall functionality of the HTML page.
 */
(() => {

	// creating the module for the base application whose name is 'app'
	app = angular.module('app', [ 'ui.bootstrap', 'ui.bootstrap.tpls' ]);

	app.controller('FormatController', function($http) {
		this.fList;
		this.format;
		/*
		 * var getFormatList = function() { $http.get(url + "format") .then(
		 * function(response { formatList = response.data; this.fList =
		 * formatList; });}
		 */
		this.getFormatList = () => {
			$http.get(url + "format")
			.then(response => {
				formatList = response.data;
				this.fList = formatList;
			});//$http end;
		};//getFormatList() end

		this.getFormat = () => {
			$http.get(url + "format/" + format)
		}; //getFormat() end

		this.setFormat = () => {
			questionformat = this.format;
		}; //setFormat() end
		
		angular.element(document).ready(() => {
			this.getFormatList();
		}); //angular element end
	}); //FormatController end

	app.controller('QuestionController', function($http) {
		this.updatedQuestion;
		this.show = false;
		this.selected = {};
		this.format = {
			format : 0,
			formatName : ''
		};

		this.question = {
			questionId : 0,
			questionText : '',
			format : {
				formatId : 0,
				formatName : ''
			},
			tags : [],
			category: [],
			multiChoice:[],
			dragDrops:[],
			snippetTemplate:[]
		};
		this.qList;
		this.deleteme = 0;
		this.getQuestionList = () => {
			$http.get(url + "question")
				.then(response => {
					questionList = response.data;
					this.qList = questionList;
				}); //$http end
		}; //getQuestionList() end

		this.addQuestion = () => {
			this.question.format = questionformat;
			if (questionformat.formatId === 0) {
				alert("please choose a format type");
			} else {
				if(this.question.questionText != ''){
				$http.post(url + "question", this.question)
					.success((response) => {
						this.question = response.data;
						if (question == null) {
							alert("Error Saving Question Please Try Again");
						} else {
							this.getQuestionList();
							this.question = {
									questionId : 0,
									questionText : '',
									format : {
										formatId : 0,
										formatName : ''
									},
									tags : [],
									category: [],
									multiChoice:[],
									dragDrops:[],
									snippetTemplate:[]
								}; //this.question end
							};//inner most if end 
						}); //$http end
				} else {
					alert("Please Enter A Question Text");
				}//mid if end
			}	//outer if end	
		}; //addQuestion() end

		this.deleteQuestion = () => {
			$http.delete(url + "question/" + this.deleteme)
				.success((response) => {
					this.getQuestionList();
					alert("delete successful");
				})
				.error(() => {
					alert("unable to properly delete the question");
				}); //$http end
		}; //deleteQuestion end
		
		this.showUpdateQuestion = (aQuestion) => {
			if(this.question == null){
				this.show=false;
			} else {
				this.updatedQuestion = aQuestion;
				question = aQuestion;
				console.log(this.updatedQuestion);
				this.show = true;
			}	//if end
		}; //showUpdateQuestion end

		this.updateQuestion = () => {
			this.question.format = questionformat;
			if (questionformat.formatId === 0) {
				alert("please choose a format type");
			} else {
				$http.put(url + "question", this.question)
					.success((response) => {
						this.question = response.data;
						if (question == null) {
							alert("Error Saving Question Please Try Again");
						} else {
							this.getQuestionList();
							this.question = {
									questionId : 0,
									questionText : '',
									format : {
										formatId : 0,
										formatName : ''
									},
									tags : [],
									category: [],
									multiChoice:[],
									dragDrops:[],
									snippetTemplate:[]
								};	//this.question end	
							this.show = false;
						} //inner if end
					}); //$http end
			}; //outer if end
			
			} //updateQuestion() end
		angular.element(document).ready(() => {
			this.getQuestionList();
		}); //angular.element end
	}); //QuestionController end
	
	app.controller('CategoryController', function($http){
			
	}); //CategoryController end
	app.controller('TagController', function($http){
			
	}); //TagController end
})// the end of the closure
(); // invoking the function within the closure.
