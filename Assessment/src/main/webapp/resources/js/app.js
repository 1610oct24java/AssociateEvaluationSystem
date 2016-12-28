var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable', 'ngAnimate' ]);

app.controller("quizController", function($scope, $http, $location) {
	$scope.quiz = tstQuiz;
	$scope.oneAtATime = false;
	$scope.states = new Array();
	$scope.editors = new Array();
	
	// Initialize states
	for (var i=0; i < $scope.quiz.questions.length; i++)
	  {
		  var temp = {
				  flagged : false,
				  saved : false,
				  open : false
		  };
		  $scope.states.push(temp);
	  }
	
	/*$scope.editor = ace.edit("editor1");
	$scope.editor.setTheme("ace/theme/monokai");
	$scope.editor.getSession().setMode("ace/mode/java");*/

/*	var editor2 = ace.edit("editor2");
	editor2.getSession().setMode("ace/mode/java");*/
	
	 $scope.collapseQuestion = function(index) {
		  console.log("Entered collapse: glyph id=" + index);
		  $scope.states[index].open = !$scope.states[index].open;
	  };
	  
	  $scope.saveQuestion = function(index) {
		  console.log("Entered save: glyph id=" + index);
		  $scope.states[index].saved = !$scope.states[index].saved;
	  };
	  
	  $scope.flagQuestion = function(index) {
		  
		  console.log("Entered flag: glyph id=" + index);
		  $scope.states[index].flagged = !$scope.states[index].flagged;
	  };

	$scope.test = function () {
		console.log("Testing");
		var out = $scope.editor.getValue();
		console.log(out);
	};
	
	$scope.status = {
		isCustomHeaderOpen : false,
		isFirstOpen : true,
		isFirstDisabled : false
	};
	
	$scope.checkNeedEditor = function (questionIndex) {
		console.log("checkNeedEditor(" + questionIndex + ")");
		var currQ = $scope.quiz.questions[questionIndex]
		console.log(currQ.type);
		
		if (currQ.type === 3) {
			// If this question is a coding question
			console.log("Code question found");
			//Create an editor for it
			/*var newEditor = ace.edit("editor" + questionIndex);
			newEditor.setTheme("ace/theme/monokai");
			newEditor.getSession().setMode("ace/mode/java");
			//$scope.editors.push(newEditor);
*/		}
	}
});