var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable',
		'ngAnimate' ]);

app.controller("dragController", function($scope) {
	// DRAG AND DROP
	$scope.dragControlListeners = {
		accept : function(sourceItemHandleScope, destSortableScope) {
			return boolean
		},
		itemMoved : function(event) {
		},
		orderChanged : function(event) {
		}
	};

	$scope.dragControlListeners1 = {};
});

app.controller('QuizNavController', function($scope, $rootScope) {
	
	$scope.index = 0;
    $scope.array = [];
   for(var i=0; i < $rootScope.states.length/5; i++)
     $scope.array.push(i);
	
});

app.controller('AjaxController', function($scope, $http) {

	   getAssessmentData();
	 
	    $scope.submitAssessment = function(){
	        var assessmentResults = {username:this.registerUser,password:this.registerPass};
	        
	        postAssessmentData(assessmentResults);
	    }
	    
	    // postData takes in JSON, sends with HTTP POST to Spring LoginController
	    function getAssessmentData(data){
	    	
	    	// setup variables
	    	
	        $http({
	            method: 'GET',
	            url: '/Assessment/getAssessment',
	            headers: {'Content-Type': 'application/json'},
	            data: data
	        })
	        .success(function (data){
	            // SETUP QUESTIONS AND STATE ARRAYS
	            // START TIMER
	            console.log("GET ASSESSMENT SUCCESS");
	        })
	        .error(function (response){
	            console.log("Error Status: " + response);
	        });
	    }
	    
	    function postAssessmentData(data){
	        $http({
	            method: 'POST',
	            url: '/Assessment/submitAssessment',
	            headers: {'Content-Type': 'application/json'},
	            data: data
	        })
	        .success(function (data){
	            console.log("Posted results");
	        })
	        .error(function (response){
	            console.log("Error while submitting assessment");
	        });
	    }
	});

app.controller("quizController", function($scope, $rootScope, $http, $location) {
	$rootScope.states = new Array();
	$scope.answers = new Array();
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.editors = new Array();
	$scope.protoTest = tstQuiz2;
	$scope.questions = $scope.protoTest.template.templateQuestion

	var incrementEditorIfNeeded = function(i) {
		if ($scope.questions[i].templateQuestion.format.formatId === 3) {
			$scope.numEditors++;
		};
	};
	var makeState = function(input) {
		var temp = {
			id: input,
			flagged: false,
			saved: false,
			open: true
		};
		$scope.states.push(temp);
	};
	var makeAnswers = function(ndx) {
		if($scope.questions[ndx].templateQuestion.format.formatId === 0) {
			$scope.answers.push(-1);
		} else if ($scope.questions[ndx].templateQuestion.format.formatId === 1 ) {
			$scope.answers.push([-1]);
		} else {
			$scope.answers.push(-1);
		};
	}
	var initSetup = function() {
		for (var i = 0; i < $scope.questions.length; i++) {
			incrementEditorIfNeeded(i);
			makeState(i);
			makeAnswers(i);
		};
	};
	initSetup();

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
	
	$scope.selectOption = function (ndxOption, ndxQuestion) {
		if($scope.questions[ndxQuestion].templateQuestion.format.formatId === 0) {
			console.log("1) Select Option: " + ndxOption + " in Question: " + ndxQuestion);
			$scope.answers[ndxQuestion] = ndxOption;
		} else if ($scope.questions[ndxQuestion].templateQuestion.format.formatId === 1 ) {
			console.log("2) Select Option: " + ndxOption + " in Question: " + ndxQuestion);
			var foundAt = $scope.answers[ndxQuestion].indexOf(ndxOption)
			if (foundAt === -1){
				$scope.answers[ndxQuestion].push(ndxOption);
			} else {
				$scope.answers[ndxQuestion].splice(foundAt, 1);
			}
		};
	}
	
	$scope.checkChecked = function (ndxOption, ndxQuestion) {
		var output = false;
		
		if($scope.questions[ndxQuestion].templateQuestion.format.formatId === 0) {
			if ($scope.answers[ndxQuestion] === ndxOption) {
				output = true;
			}
		} else if ($scope.questions[ndxQuestion].templateQuestion.format.formatId === 1 ) {
			if ($scope.answers[ndxQuestion].indexOf(ndxOption) != -1){
				output = true;
			}
		};
		return output;
	}

	// EDITORS	
	$scope.checkNeedEditor = function(questionIndex) {
		var currQ = $scope.questions[questionIndex];
		if(currQ.templateQuestion.format.formatId === 3) {
			// If this question is a coding question
			var temp = ace.edit("editor" + questionIndex);
			temp.setTheme("ace/theme/monokai");
			temp.getSession().setMode("ace/mode/" + "java");
			
			var foundAt = $scope.editors.indexOf(temp);
			if (foundAt != -1) {
				$scope.editors[foundAt] = temp;
			} else {
				$scope.editors.push(temp);
			}
		}
	}

	// PAGINATION
	$scope.filteredQuestions = [];
	$scope.currentPage = 1;
	$scope.numPerPage = 5;
	$scope.maxSize = 5;
	
	$scope.jumpPage = function (numPage) {
		console.log("Jump to Page: " + numPage);
		$scope.currentPage = numPage;
	};

	$scope.$watch('currentPage + numPerPage', function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

		$scope.filteredQuestions = $scope.questions.slice(begin, end);
	});
});

/* Set the width of the side navigation to 250px and the right margin of the page content to 250px */
function openSideNav() {
    document.getElementById("sidenav").style.width = "250px";
    document.getElementById("page-container").style.marginRight = "250px";
}

/* Set the width of the side navigation to 0 and the right margin of the page content to 0 */
function closeSideNav() {
    document.getElementById("sidenav").style.width = "0";
    document.getElementById("page-container").style.marginRight = "0";
}