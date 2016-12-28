'use strict';

var app; //the base application for angular.
var port = ":8090"; //the port for ajax calls.
var baseDirectory = "TestBank" //the base directory for AJAX calls.
var domain = "http://localhost"; //the base domain for AJAX calls.
var url = domain + port + "/" + baseDirectory + "/"; //a concatenation of the domain, port, and base directory to establish a base url.  
var formatList = [];
var questionformat = {};
var questionList = [];
var question = {};
var variable;

/* A JavaScript closure of a function using ES2015 concise syntax.
 * Using concise syntax of (()=> {})(); is equivalent of window.onload = function() {};
 * This function uses Angular to control the overall functionality of the HTML page.  
 */
(() => {

	//creating the module for the base application whose name is 'app'
	app = angular.module('app', [ 'ui.bootstrap', 'ui.bootstrap.tpls' ]);

	app.controller('FormatController', function($http) {
		this.fList;
		this.format;
		this.getFormatList = () => {
			$http.get(url + "format")
				.then(response => {
					formatList = response.data;
					this.fList = formatList;
				});
		};

		this.getFormat = () => {
			$http.get(url + "format/" + format)
		};

		this.setFormat = () => {
			questionformat = this.format;
		};
		angular.element(document).ready(() => {
			this.getFormatList();
		});
	});

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
			}
		};
		this.qList;
		this.deleteme = 0;
		this.getQuestionList = () => {
			$http.get(url + "question")
				.then(response => {
					questionList = response.data;
					this.qList = questionList;
				});
		};

		this.addQuestion = () => {

			this.question.format = questionformat;
			if (questionformat.formatId === 0) {
				alert("please choose a format type");
			} else {
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
								}
							};
						}
						;
					});

			}

		};

		this.deleteQuestion = () => {
			$http.delete(url + "question/" + this.deleteme)
				.success((response) => {
					this.getQuestionList();
					alert("delete successful");
				})
				.error(() => {
					alert("unable to properly delete the question");
				});
		};
		
		this.showUpdateQuestion = () => {
			if(this.question == null){
				
				this.show=false;
			} else {
				this.updatedQuestion = this.question;
				console.log('updating question ' + this.updatedQuestion);
				
				this.show = true;
			}	
		};

		this.updateQuestion = () => {
	
		};
		angular.element(document).ready(() => {
			this.getQuestionList();
			$http.get('http://localhost:8090/TestBank/test')
			.success((response) => {
				variable = response.data;
				console.log(variable);
			});
		});
	});
}) //the end of the closure
(); //invoking the function within the closure.