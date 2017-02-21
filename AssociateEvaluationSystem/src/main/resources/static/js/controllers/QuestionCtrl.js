app.module().controller('QuestionCtrl', qCtrl);
function qCtrl($http, $scope) {
	var qCtrl = this;
	/*
	 * var getFormatList = function() { $http.get(url + "format") .then(
	 * function(response { formatList = response.data; qCtrl.fList =
	 * formatList; });}
	 */
	
	angular.element(document).ready(function() {
		qCtrl.getFormatList();
	}); // angular element end
	//////////////////////////////////////////////////
	
	qCtrl.fList;
	qCtrl.formatSet = false;
	qCtrl.questionTextChanged = false;
	qCtrl.optionTextChanged = false;
	qCtrl.correctValue = false;
	qCtrl.addButton = false;
	qCtrl.updatedQuestion;
	qCtrl.show = false;
	qCtrl.qList;
	qCtrl.deleteme = 0;
	qCtrl.selected = {};
	qCtrl.tagList = '';
	qCtrl.catList = '';
	qCtrl.isMultiChoiceOption = false;
	qCtrl.isMultiSelectOption = false;
	qCtrl.isDragDrop = false;
	qCtrl.questionBeingUpdated = '';
	qCtrl.format = {
		format : 0,
		formatName : ''
	};
	
	qCtrl.option = {
			optionId: 0,
			optionText: '',
			correct: -1
	};

	qCtrl.getFormatList = function() {
		$http.get("format")
		.then(function(response) {
			qCtrl.fList = response.data;
			
		}); // $http end;
	} // getFormatList() end
	
	qCtrl.categoriesInDatabase = null;
	qCtrl.tagsInDatabase = null;
	
	qCtrl.question = {
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
	qCtrl.getQuestionList = function() {
		$http.get("question")
			.then(function(response) {	
				qCtrl.qList = response.data;
			}); // $http end
	}; // getQuestionList() end
	
	qCtrl.getQuestion = function(question){
		console.log(question)
		qCtrl.isOption = false;
		qCtrl.isDragDrop = false;
		if(question.format.formatName == "Drag and Drop"){
			qCtrl.isDragDrop = true;
			qCtrl.isMultiSelectOption = false;
			qCtrl.isMultiChoiceOption = false;
		}
		else if(question.format.formatName == "Multiple Select"){
			qCtrl.isDragDrop = false;
			qCtrl.isMultiSelectOption = true;
			qCtrl.isMultiChoiceOption = false;
		}
		else{
			qCtrl.isDragDrop = false;
			qCtrl.isMultiSelectOption = false;
			qCtrl.isMultiChoiceOption = true;
		}
		qCtrl.currentQuestion = question;
	}
	
	// Adds a option to a Question being created
	qCtrl.addOption = function() {
		if(qCtrl.question.multiChoice == null){
			qCtrl.question.multiChoice = [];
		}// end if
		if(qCtrl.option.optionText == '' || qCtrl.option.correct == -1){
			
		} else {
			qCtrl.question.multiChoice.push(qCtrl.option);
			qCtrl.option = {
					optionId: 0,
					optionText: '',
					correct: -1
			};
		}// end if
	};
	
	// Adds a tag to the Question being updated
	qCtrl.addTag = function(tagName) {
		if(qCtrl.question.tags == null){
			qCtrl.question.tags = [];
		}
		// search for tag in tagsInDatabase
		for(var i=0;i<qCtrl.tagsInDatabase.length;i++){
			if(tagName==qCtrl.tagsInDatabase[i].tagName){
				qCtrl.question.tags.push(qCtrl.tagsInDatabase[i]);
				return true;
			}
		}
		// tag not found
		qCtrl.question.tags=null;
		return false;
	};
	
	// Adds a category to the Question being updated
	qCtrl.addCategory = function(categoryName) {
		if(qCtrl.question.category == null){
			qCtrl.question.category = [];
		}
		// search for category in categoriesInDatabase
		for(var i=0;i<qCtrl.categoriesInDatabase.length;i++){
			if(categoryName==qCtrl.categoriesInDatabase[i].name){
				qCtrl.question.category.push(qCtrl.categoriesInDatabase[i]);
				return true;
			}
		}
		// category not found
		qCtrl.question.category=null;
		return false;
	};
	
	// This functions ensures a user populates all the necessary fields for
	// a question.
	qCtrl.addAddQuestionButton = function(x) {
		
		switch(x) {
		case 1:
			qCtrl.formatSet = true;
			break;
		case 2:
			qCtrl.questionTextChanged = true;
			break;
		case 3:
			qCtrl.optionTextChanged = true;
			break;
		case 4:
			qCtrl.correctValue = true;
			break;
		default:
		} // switch end
		
		if(qCtrl.formatSet === true 
				&& qCtrl.questionTextChanged === true 
				&& qCtrl.optionTextChanged === true 
				&& qCtrl.correctValue === true){
			qCtrl.addButton = true;
		}
	}; // addAddQuestionButton end
	
	qCtrl.resetQuestion = function() {
		qCtrl.question = {
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
				}; // qCtrl.question end
	}; //qCtrl.requeQuestion() end
	
	qCtrl.addQuestion = function() {
		qCtrl.question.question.format = qCtrl.format;
		if (qCtrl.question.question.format.formatId === 0) {
		} else {
			if(qCtrl.question.questionText != ''){
			$http.post("fullQuestion", qCtrl.question)
				.success(function(response) {
					qCtrl.question.question = response;
					if (qCtrl.question.question == null) {
						qCtrl.resetQuestion();
					} else {
						qCtrl.getQuestionList();
						qCtrl.resetQuestion();
						}// inner most if end
					}); // $http end
				} else {
			}// mid if end
		}	// outer if end
	}; // addQuestion() end

	qCtrl.deleteQuestion = function() {
		$http.delete("/question/" + qCtrl.deleteme)
			.success(function() {
				qCtrl.getQuestionList();
			})
			.error(function() {
			}); // $http end
	}; // deleteQuestion end
	
	qCtrl.showUpdateQuestion = function(aQuestion) {
		if(qCtrl.question == null){
			qCtrl.show=false;
		} else {
			qCtrl.updatedQuestion = aQuestion;
			qCtrl.show = true;
		}	// if end
	}; // showUpdateQuestion end

	qCtrl.updateQuestion = function() {
		qCtrl.question.question.format = qCtrl.format;
		if (qCtrl.format.formatId === 0) {
			
		} else {
			console.log(qCtrl.currentQuestion);
			$http.put("question", qCtrl.currentQuestion)
				.success(function(response) {
					qCtrl.question = response.data;
					if (qCtrl.currentQuestion == null) {
						
					} else {
						qCtrl.getQuestionList();	
						qCtrl.resetQuestion();
						qCtrl.show = false;
					} // inner if end
				}); // $http end
		} // outer if end	
	}; // updateQuestion() end
	
	qCtrl.addCategories = function() {
		// get categories into an array
		if(qCtrl.catList != null && qCtrl.catList != ''){	
			var selectedCategories = qCtrl.catList.split(',');
			for (var i=0;i<selectedCategories.length;i++){
				if(!qCtrl.addCategory(selectedCategories[i])){
					return;
				}
			}
		}
	};
	
	qCtrl.addTags = function() {
		// get tags into an array
		if(qCtrl.tagList != null && qCtrl.tagList != ''){
			var selectedTags = qCtrl.tagList.split(',');
			for (var j=0;j<selectedTags.length;j++){
				if(!qCtrl.addTag(selectedTags[j])){
					return;
				}
			}
		}
	};
	
	qCtrl.addCategoriesAndTags = function() {
		// question must be selected
		if(qCtrl.questionBeingUpdated ===''){
			return;
		}
		// a tag or category must be provided
		if(qCtrl.tagList ==='' && qCtrl.catList ===''){
			return;
		}
		// initialize question
		qCtrl.question = qCtrl.qList[qCtrl.questionBeingUpdated-1];
		
		qCtrl.addCategories();
		qCtrl.addTags();

		// save the question
		$http.put(url + "question", qCtrl.question)
		.success(function(response) {
			if (response == null) {
			} else {
				qCtrl.getQuestionList();	
				qCtrl.resetQuestion();
				qCtrl.show = false;
			} // inner if end
		}); // $http end
	}; // addCategoriesAndTags() end
	
	// Load categories from database so that they can be added to questions
	qCtrl.loadCategories = function() {
		$http.get("category")
		.then(function(response) {	
			qCtrl.categoriesInDatabase = response.data;
		});
	};
	
	qCtrl.loadTags = function() {
		$http.get("tag")
		.then(function(response) {
			qCtrl.tagsInDatabase = response.data;
		})
	};
	
	qCtrl.removeOption = function(option){
		$http.post("question/refresh/" + option.optionId, qCtrl.currentQuestion.questionId)
		.then(function(response){
			qCtrl.currentQuestion = response.data;
			qCtrl.getQuestion(qCtrl.currentQuestion);
			var length = qCtrl.qList.length;
			for(let i=0;i<length;i++){
				if(qCtrl.currentQuestion.questionId == qCtrl.qList[i].questionId){
					qCtrl.qList[i]=qCtrl.currentQuestion;
					console.log(qCtrl.qList[i]);
					break;
				}				
			}
		})	
	}
	
	qCtrl.removeDDOption = function(dragdrop){
		$http.post("question/deleteDragDrop/" + dragdrop.dragDropId, qCtrl.currentQuestion.questionId)
		.then(function(response){
			qCtrl.currentQuestion = response.data;
			qCtrl.getQuestion(qCtrl.currentQuestion)
			var length = qCtrl.qList.length;
			console.log(length);
			for(let i=0;i<length;i++){
				console.log(qCtrl.qList[i])
				if(qCtrl.currentQuestion.questionId == qCtrl.qList[i].questionId){
					console.log("Inside if statement.")
					console.log(qCtrl.currentQuestion)
					qCtrl.qList[i]=qCtrl.currentQuestion;
					console.log(qCtrl.qList[i]);
					break;
				}				
			}
		})
	}
	
	qCtrl.addOption = function(newOption){
		if(newOption != null && newOption != ""){
		$http.post("question/addOption/" + qCtrl.currentQuestion.questionId,newOption)
		.then(function(response){
			qCtrl.currentQuestion = response.data;
			qCtrl.getQuestion(qCtrl.currentQuestion)
			var length = qCtrl.qList.length;
			for(let i=0;i<length;i++){
				if(qCtrl.currentQuestion.questionId == qCtrl.qList[i].questionId){
					qCtrl.qList[i]=qCtrl.currentQuestion;
					qCtrl.newOption = "";
					break;
				}				
			}
		})
		}
	}
	
	qCtrl.addDragDrop = function(newDragDrop){
		if(newDragDrop != null && newDragDrop != ""){
		$http.post("question/addDragDrop/" + qCtrl.currentQuestion.questionId, newDragDrop)
		.then(function(response){
			qCtrl.currentQuestion = response.data;
			qCtrl.getQuestion(qCtrl.currentQuestion)
			var length = qCtrl.qList.length;
			for(let i=0;i<length;i++){
				if(qCtrl.currentQuestion.questionId == qCtrl.qList[i].questionId){
					qCtrl.qList[i]=qCtrl.currentQuestion;
					qCtrl.newDragDrop = "";
					break;
				}				
			}
		})
		}
	}
	
	qCtrl.checkButton = function(option){
		if (option.correct == 1) {
			return true;
		}else{
			return false;
		}
	}

	/*
	 * Here I am attempting to call the rest controller where I update the options with the new correct answer.
	 */
	qCtrl.multiCorrect = function(option){
		if(document.getElementById("msrad").checked == false){
	    	$http.post("question/markAllIncorrect/" + qCtrl.currentQuestion.questionId)
	    	qCtrl.optionCorrectChanger(option)
	    	document.getElementById("msrad").checked == true;
	    }
	}
	
	qCtrl.multiSelectCorrect = function(option){
		console.log(option)
		qCtrl.optionCorrectChanger(option)
		
	}
	
	qCtrl.optionCorrectChanger = function(option){
		$http.post("question/changeCorrect/" + option.optionId)
		.then(function(response){
			qCtrl.currentQuestion = response.data;
			qCtrl.getQuestion(qCtrl.currentQuestion)
			var length = qCtrl.qList.length;
			for(let i=0;i<length;i++){
				if(qCtrl.currentQuestion.questionId == qCtrl.qList[i].questionId){
					qCtrl.qList[i]=qCtrl.currentQuestion;
					break;
				}
			}
		})
	}
	
	angular.element(document).ready(function() {
		qCtrl.getQuestionList();
		qCtrl.loadCategories();
		qCtrl.loadTags();
	}); // angular.element end

}); // QuestionController end