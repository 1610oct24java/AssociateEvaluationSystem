var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable', 'ngAnimate' ]);

app.controller("dragController"), function($scope) {
	// DRAG AND DROP
	$scope.dragControlListeners = {
			accept: function (sourceItemHandleScope, destSortableScope) {return boolean},//override to determine drag is allowed or not. default is true.
		    itemMoved: function (event) {},
		    orderChanged: function(event) {}
		    /*containment: '#board'//optional param.
		    clone: true //optional param for clone feature.
		    allowDuplicates: false //optional param allows duplicates to be dropped.*/
	};

	$scope.dragControlListeners1 = {
			/*containment: '#board'//optional param.
	        allowDuplicates: true //optional param allows duplicates to be dropped.*/
	};
}

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
				  open : true
		  };
		  $scope.states.push(temp);
	  }
	
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
	
	$scope.status = {
		isCustomHeaderOpen : false,
		isFirstOpen : true,
		isFirstDisabled : false
	};
	
	
	
	// EDITORS
	$scope.checkNeedEditor = function (questionIndex) {
		var currQ = $scope.quiz.questions[questionIndex]
		
		if (currQ.type === 3) {
			// If this question is a coding question
			//Create an editor for it
			var newEditor = ace.edit("editor" + questionIndex);
			newEditor.setTheme("ace/theme/monokai");
			newEditor.getSession().setMode("ace/mode/" + "java");
			$scope.editors.push(newEditor);
		};
	};
	
	// PAGINATION
	$scope.totalItems = 64;
	$scope.currentPage = 4;
	$scope.currQuestions;
	
	var getCurrQuestions = function(numPage) {
		$scope.currQuestions = new Array();
		for (var i = 0; i < 10; i++ ){
			console.log("Push index: " + i + 10*(numPage-1));
			$scope.currQuestions.push($scope.quiz.questions[i + 10*(numPage-1)]);
		};
	}
	
	$scope.setPage = function (pageNo) {
	    $scope.currentPage = pageNo;
	    
	  };

	  $scope.pageChanged = function() {
	    $log.log('Page changed to: ' + $scope.currentPage);
	    getCurrQuestions();
	  };

	  $scope.maxSize = 5;
	  $scope.bigTotalItems = 175;
	  $scope.bigCurrentPage = 1;
});