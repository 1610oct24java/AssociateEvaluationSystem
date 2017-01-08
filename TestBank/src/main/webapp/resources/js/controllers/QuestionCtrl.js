app.controller('QuestionCtrl', function($http, $scope) {
	var url = "/TestBank/";
	$scope.fList;
	/*
	 * var getFormatList = function() { $http.get(url + "format") .then(
	 * function(response { formatList = response.data; $scope.fList =
	 * formatList; });}
	 */
	$scope.getFormatList = function() {
		$http.get(url + "format")
		.then(function(response) {
			$scope.fList = response.data;
			console.log($scope.fList);
		}); // $http end;
	} // getFormatList() end
	
	angular.element(document).ready(function() {
		$scope.getFormatList();
	}); // angular element end
	//////////////////////////////////////////////////
	
	$scope.formatSet = false;
	$scope.questionTextChanged = false;
	$scope.optionTextChanged = false;
	$scope.correctValue = false;
	$scope.addButton = false;
	$scope.updatedQuestion;
	$scope.show = false;
	$scope.qList;
	$scope.deleteme = 0;
	$scope.selected = {};
	$scope.tagList = '';
	$scope.catList = '';
	$scope.questionBeingUpdated = '';
	$scope.format = {
		format : 0,
		formatName : ''
	};
	
	$scope.option = {
			optionId: 0,
			optionText: '',
			correct: -1
	};
	
	$scope.categoriesInDatabase = null;
	$scope.tagsInDatabase = null;
	
	$scope.question = {
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
	$scope.getQuestionList = function() {
		$http.get(url + "question")
			.then(function(response) {	
				$scope.qList = response.data;
			}); // $http end
	}; // getQuestionList() end
	
	// Adds a option to a Question being created
	$scope.addOption = function() {
		if($scope.question.multiChoice == null){
			$scope.question.multiChoice = [];
		}// end if
		if($scope.option.optionText == ''){
		} else if($scope.option.correct == -1){
		} else {
			$scope.question.multiChoice.push($scope.option);
			$scope.option = {
					optionId: 0,
					optionText: '',
					correct: -1
			};
		}// end if
	};
	
	// Adds a tag to the Question being updated
	$scope.addTag = function(tagName) {
		if($scope.question.tags == null){
			$scope.question.tags = [];
		}
		// search for tag in tagsInDatabase
		for(var i=0;i<$scope.tagsInDatabase.length;i++){
			if(tagName==$scope.tagsInDatabase[i].tagName){
				$scope.question.tags.push($scope.tagsInDatabase[i]);
				return true;
			}
		}
		// tag not found
		$scope.question.tags=null;
		return false;
	};
	
	// Adds a category to the Question being updated
	$scope.addCategory = function(categoryName) {
		if($scope.question.category == null){
			$scope.question.category = [];
		}
		// search for category in categoriesInDatabase
		for(var i=0;i<$scope.categoriesInDatabase.length;i++){
			if(categoryName==$scope.categoriesInDatabase[i].name){
				$scope.question.category.push($scope.categoriesInDatabase[i]);
				return true;
			}
		}
		// category not found
		$scope.question.category=null;
		return false;
	};
	
	// This functions ensures a user populates all the necessary fields for
	// a question.
	$scope.addAddQuestionButton = function(x) {
		
		switch(x) {
		case 1:
			$scope.formatSet = true;
			break;
		case 2:
			$scope.questionTextChanged = true;
			break;
		case 3:
			$scope.optionTextChanged = true;
			break;
		case 4:
			$scope.correctValue = true;
			break;
		default:
		} // switch end
		
		if($scope.formatSet === true 
				&& $scope.questionTextChanged === true 
				&& $scope.optionTextChanged === true 
				&& $scope.correctValue === true){
			$scope.addButton = true;
		}
	}; // addAddQuestionButton end
	
	$scope.resetQuestion = function() {
		$scope.question = {
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
				}; // $scope.question end
	}; //$scope.requeQuestion() end
	
	$scope.addQuestion = function() {
		$scope.question.question.format = $scope.format;
		if ($scope.question.question.format.formatId === 0) {
		} else {
			if($scope.question.questionText != ''){
			$http.post(url + "fullQuestion", $scope.question)
				.success(function(response) {
					$scope.question.question = response;
					if ($scope.question.question == null) {
						$scope.resetQuestion();
					} else {
						$scope.getQuestionList();
						$scope.resetQuestion();
						}// inner most if end
					}); // $http end
				} else {
			}// mid if end
		}	// outer if end
	}; // addQuestion() end

	$scope.deleteQuestion = function() {
		$http.delete(url + "question/" + $scope.deleteme)
			.success(function() {
				$scope.getQuestionList();
			})
			.error(function() {
			}); // $http end
	}; // deleteQuestion end
	
	$scope.showUpdateQuestion = function(aQuestion) {
		if($scope.question == null){
			$scope.show=false;
		} else {
			$scope.updatedQuestion = aQuestion;
			$scope.show = true;
		}	// if end
	}; // showUpdateQuestion end

	$scope.updateQuestion = function() {
		$scope.question.question.format = $scope.format;
		if ($scope.format.formatId === 0) {
			alert("please choose a format type");
		} else {
			$http.put(url + "question", $scope.question)
				.success(function(response) {
					$scope.question = response.data;
					if ($scope.question == null) {
						alert("Error Saving Question Please Try Again");
					} else {
						$scope.getQuestionList();	
						$scope.resetQuestion();
						$scope.show = false;
					} // inner if end
				}); // $http end
		} // outer if end	
	}; // updateQuestion() end
	
	$scope.addCategories = function() {
		// get categories into an array
		if($scope.catList != null && $scope.catList != ''){	
			var selectedCategories = $scope.catList.split(',');
			for (var i=0;i<selectedCategories.length;i++){
				if(!$scope.addCategory(selectedCategories[i])){
					return;
				}
			}
		}
	};
	
	$scope.addTags = function() {
		// get tags into an array
		if($scope.tagList != null && $scope.tagList != ''){
			var selectedTags = $scope.tagList.split(',');
			for (var j=0;j<selectedTags.length;j++){
				if(!$scope.addTag(selectedTags[j])){
					return;
				}
			}
		}
	};
	
	$scope.addCategoriesAndTags = function() {
		// question must be selected
		if($scope.questionBeingUpdated ===''){
			return;
		}
		// a tag or category must be provided
		if($scope.tagList ==='' && $scope.catList ===''){
			return;
		}
		// initialize question
		$scope.question = $scope.qList[$scope.questionBeingUpdated-1];
		
		$scope.addCategories();
		$scope.addTags();

		// save the question
		$http.put(url + "question", $scope.question)
		.success(function(response) {
			if (response == null) {
			} else {
				$scope.getQuestionList();	
				$scope.resetQuestion();
				$scope.show = false;
			} // inner if end
		}); // $http end
	}; // addCategoriesAndTags() end
	
	// Load categories from database so that they can be added to questions
	$scope.loadCategories = function() {
		$http.get(url + "category")
		.then(function(response) {	
			$scope.categoriesInDatabase = response.data;
		});
	};
	
	$scope.loadTags = function() {
		$http.get(url + "tag")
		.then(function(response) {
			$scope.tagsInDatabase = response.data;
		})
	};
	
	angular.element(document).ready(function() {
		$scope.getQuestionList();
		$scope.loadCategories();
		$scope.loadTags();
	}); // angular.element end
}); // QuestionController end