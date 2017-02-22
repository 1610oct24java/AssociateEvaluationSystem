angular.module("bankApp").controller("questionAddController", questionAddController);

function questionAddController($scope, $state, $filter){

	var qac = this;

	
	
	qac.question = {
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
	
	qac.format = {
			format : 0,
			formatName : ''
	};
	
	
	/* functions */
	qac.addQuestion = function(){
		
		
		
		
	}
	
	$scope.addQuestion = function() {
		$scope.question.question.format = $scope.format;
		if ($scope.question.question.format.formatId === 0) {
		} else {
			if($scope.question.questionText != ''){
			$http.post("fullQuestion", $scope.question)
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

	
	$scope.addDragDrop = function(newDragDrop){
		if(newDragDrop != null && newDragDrop != ""){
		$http.post("question/addDragDrop/" + $scope.currentQuestion.questionId, newDragDrop)
		.then(function(response){
			$scope.currentQuestion = response.data;
			$scope.getQuestion($scope.currentQuestion)
			var length = $scope.qList.length;
			for(let i=0;i<length;i++){
				if($scope.currentQuestion.questionId == $scope.qList[i].questionId){
					$scope.qList[i]=$scope.currentQuestion;
					$scope.newDragDrop = "";
					break;
				}				
			}
		})
		}
	}
	
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
}