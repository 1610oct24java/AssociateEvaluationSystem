'use strict';

var app; // the base application for angular.
var baseDirectory = "TestBank";
var url = "/" + baseDirectory + "/"; 

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
		/*
		 * var getFormatList = function() { $http.get(url + "format") .then(
		 * function(response { formatList = response.data; this.fList =
		 * formatList; });}
		 */
		this.getFormatList = () => {
			$http.get(url + "format")
			.then(response => {
				this.fList = response.data;
			}); // $http end;
		}; // getFormatList() end
		
		angular.element(document).ready(() => {
			this.getFormatList();
		}); // angular element end
	}); // FormatController end

	app.controller('QuestionController', function($http) {
		
		this.formatSet = false;
		this.questionTextChanged = false;
		this.optionTextChanged = false;
		this.correctValue = false;
		this.addButton = false;
		this.updatedQuestion;
		this.show = false;
		this.qList;
		this.deleteme = 0;
		this.selected = {};
		this.format = {
			format : 0,
			formatName : ''
		};
		
		this.option = {
				optionId: 0,
				optionText: '',
				correct: -1
		};
		
		this.question = {
			question: {
				questionId : 0,
				questionText : '',
				format : {
					formatId : 0,
					formatName : ''
				}
			},
			tags : null,
			categories: null,
			multiChoice:null,
			dragDrops:null,
			snippetTemplate:null
		};
		
		// Retrieves the List of Questions from the Database
		this.getQuestionList = () => {
			$http.get(url + "question")
				.then(response => {	
					this.qList = response.data;
				}); // $http end
		}; // getQuestionList() end
		
		// Adds a option to a Question being created
		this.addOption = () => {
			if(this.question.multiChoice == null){
				this.question.multiChoice = [];
			}// end if
			if(this.option.optionText == ''){
				alert("Please enter a Option");
			} else if(this.option.correct == -1){
				alert("Please Choose Yes or No for a Correct Answer");
			} else {
				this.question.multiChoice.push(this.option);
				this.option = {
						optionId: 0,
						optionText: '',
						correct: -1
				};
			}// end if
		};
		// This functions ensures a user populates all the necessary fields for
		// a question.
		this.addAddQuestionButton = (x) => {
			
			switch(x) {
			case 1:
				this.formatSet = true;
				break;
			case 2:
				this.questionTextChanged = true;
				break;
			case 3:
				this.optionTextChanged = true;
				break;
			case 4:
				this.correctValue = true;
				break;
			default:
			} // switch end
			
			if(this.formatSet === true 
					&& this.questionTextChanged === true 
					&& this.optionTextChanged === true 
					&& this.correctValue === true){
				this.addButton = true;
			}
		}; // addAddQuestionButton end
		
		this.resetQuestion = () => {
			this.question = {
					question : {	
						questionId : 0,
						questionText : '',
						format : {
							formatId : 0,
							formatName : ''
						}
					},
						tags : null,
						categories: null,
						multiChoice:null,
						dragDrops:null,
						snippetTemplate:null
					}; // this.question end
		} //this.requeQuestion() end
		
		this.addQuestion = () => {
			this.question.question.format = this.format;
			if (this.question.question.format.formatId === 0) {
				alert("please choose a format type");
			} else {
				if(this.question.questionText != ''){
				$http.post(url + "fullQuestion", this.question)
					.success(response => {
						this.question.question = response.data;
						if (this.question.question == null) {
							alert("Error Saving Question Please Try Again");
							this.resetQuestion();
						} else {
							this.getQuestionList();
							this.resetQuestion();
							}// inner most if end
						}); // $http end
					} else {
					alert("Please Enter A Question Text");
				}// mid if end
			}	// outer if end
		}; // addQuestion() end

		this.deleteQuestion = () => {
			$http.delete(url + "question/" + this.deleteme)
				.success(() => {
					this.getQuestionList();
					alert("delete successful");
				})
				.error(() => {
					alert("unable to properly delete the question");
				}); // $http end
		}; // deleteQuestion end
		
		this.showUpdateQuestion = (aQuestion) => {
			if(this.question == null){
				this.show=false;
			} else {
				this.updatedQuestion = aQuestion;
				this.show = true;
			}	// if end
		}; // showUpdateQuestion end

		this.updateQuestion = () => {
			this.question.question.format = this.format;
			if (this.format.formatId === 0) {
				alert("please choose a format type");
			} else {
				$http.put(url + "question", this.question)
					.success(response => {
						this.question = response.data;
						if (this.question == null) {
							alert("Error Saving Question Please Try Again");
						} else {
							this.getQuestionList();	
							this.resetQuestion();
							this.show = false;
						} // inner if end
					}); // $http end
			} // outer if end	
		} // updateQuestion() end
		
		angular.element(document).ready(() => {
			this.getQuestionList();
		}); // angular.element end
	}); // QuestionController end
})();// the end of the closure invoking the function within the closure.
