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
		this.tagList = '';
		this.catList = '';
		this.questionBeingUpdated = '';
		this.format = {
			format : 0,
			formatName : ''
		};
		
		this.option = {
				optionId: 0,
				optionText: '',
				correct: -1
		};
		
		this.categoriesInDatabase = null;
		this.tagsInDatabase = null;
		
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
			category: null,
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
		
		// Adds a tag to the Question being updated
		this.addTag = (tagName) => {
			if(this.question.tags == null){
				this.question.tags = [];
			}
			// search for tag in tagsInDatabase
			for(var i=0;i<this.tagsInDatabase.length;i++){
				if(tagName==this.tagsInDatabase[i].tagName){
					this.question.tags.push(this.tagsInDatabase[i]);
					return true;
				}
			}
			// tag not found
			alert('Invalid Tag');
			this.question.tags=null;
			return false;
		};
		
		// Adds a category to the Question being updated
		this.addCategory = (categoryName) => {
			if(this.question.category == null){
				this.question.category = [];
			}
			// search for category in categoriesInDatabase
			for(var i=0;i<this.categoriesInDatabase.length;i++){
				if(categoryName==this.categoriesInDatabase[i].name){
					this.question.category.push(this.categoriesInDatabase[i]);
					return true;
				}
			}
			// category not found
			alert('Invalid Category');
			this.question.category=null;
			return false;
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
						category: null,
						multiChoice:null,
						dragDrops:null,
						snippetTemplate:null
					}; // this.question end
		}; //this.requeQuestion() end
		
		this.addQuestion = () => {
			this.question.question.format = this.format;
			if (this.question.question.format.formatId === 0) {
				alert("please choose a format type");
			} else {
				if(this.question.questionText != ''){
				$http.post(url + "fullQuestion", this.question)
					.success(response => {
						this.question.question = response;
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
		}; // updateQuestion() end
		
		this.addCategories = () => {
			// get categories into an array
			if(this.catList != null && this.catList != ''){	
				var selectedCategories = this.catList.split(',');
				for (var i=0;i<selectedCategories.length;i++){
					if(!this.addCategory(selectedCategories[i])){
						return;
					}
				}
			}
		};
		
		this.addTags = () => {
			// get tags into an array
			if(this.tagList != null && this.tagList != ''){
				var selectedTags = this.tagList.split(',');
				for (var j=0;j<selectedTags.length;j++){
					if(!this.addTag(selectedTags[j])){
						return;
					}
				}
			}
		};
		
		this.addCategoriesAndTags = () => {
			// question must be selected
			if(this.questionBeingUpdated===''){
				alert('Please select a question number');
				return;
			}
			// a tag or category must be provided
			if(this.tagList==='' && this.catList===''){
				alert('Please provide a category or tag');
				return;
			}
			// initialize question
			this.question = this.qList[this.questionBeingUpdated-1];
			
			this.addCategories();
			this.addTags();

			// save the question
			$http.put(url + "question", this.question)
			.success(response => {
				if (response == null) {
					alert("Error Saving Question Please Try Again");
				} else {
					this.getQuestionList();	
					this.resetQuestion();
					this.show = false;
				} // inner if end
			}); // $http end
		}; // addCategoriesAndTags() end
		
		// Load categories from database so that they can be added to questions
		this.loadCategories = () => {
			$http.get(url + "category")
			.then(response => {	
				this.categoriesInDatabase = response.data;
			});
		};
		
		this.loadTags = () => {
			$http.get(url + "tag")
			.then(response => {
				this.tagsInDatabase = response.data;
				console.log(this.tagsInDatabase);
			})
		};
		
		angular.element(document).ready(() => {
			this.getQuestionList();
			this.loadCategories();
			this.loadTags();
		}); // angular.element end
	}); // QuestionController end
})();// the end of the closure invoking the function within the closure.
