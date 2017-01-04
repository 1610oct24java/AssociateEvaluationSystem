var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable',
		'ngAnimate', '$http']);

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

app.controller("quizController", function($scope, $rootScope, $http, $location) {
	$rootScope.quiz = tstQuiz; //getQuizQuestions();
	$scope.answers = new Array();
	$rootScope.states = new Array();
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.editors = new Array();

	var incrementEditorIfNeeded = function(i) {
		if ($rootScope.quiz.questions[i].type === 3) {
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
		if($rootScope.quiz.questions[ndx].type === 0) {
			$scope.answers.push(-1);
		} else if ($rootScope.quiz.questions[ndx].type === 1 ) {
			$scope.answers.push([-1]);
		} else {
			$scope.answers.push(-1);
		};
	}
	var initSetup = function() {
		for (var i = 0; i < $rootScope.quiz.questions.length; i++) {
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
		if($rootScope.quiz.questions[ndxQuestion].type === 0) {
			console.log("1) Select Option: " + ndxOption + " in Question: " + ndxQuestion);
			$scope.answers[ndxQuestion] = ndxOption;
		} else if ($rootScope.quiz.questions[ndxQuestion].type === 1 ) {
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
		
		if($rootScope.quiz.questions[ndxQuestion].type === 0) {
			if ($scope.answers[ndxQuestion] === ndxOption) {
				output = true;
			}
		} else if ($rootScope.quiz.questions[ndxQuestion].type === 1 ) {
			if ($scope.answers[ndxQuestion].indexOf(ndxOption) != -1){
				output = true;
			}
		};
		
		return output;
	}

	// EDITORS
	$scope.checkNeedEditor2 = function(questionIndex) {
		// console.log("Checking if q " + questionIndex + " needs an editor.");
		if ($scope.editors.length < $scope.numEditors) {
			var currQ = $rootScope.quiz.questions[questionIndex];

			if (currQ.type === 3) {
				// If this question is a coding question
				// Create an editor for it
				var newEditor = ace.edit("editor" + questionIndex);
				newEditor.setTheme("ace/theme/monokai");
				newEditor.getSession().setMode("ace/mode/" + "java");
				$scope.editors.push(newEditor);
			}
		}
	};
	
	$scope.checkNeedEditor = function(questionIndex) {
		var currQ = $rootScope.quiz.questions[questionIndex];
		if(currQ.type === 3) {
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
		
	function getQuizQuestions() {
		$http({
			method: 'GET',
			url: '/Assessment/getAssessmentData',
			headers: {'Content-Type': 'application/json'}
		})
		.then(function(response) {
		    //First function handles success
		    console.log("Received Quiz Object= " + response.data);
		    $rootScope.quiz = response.data;
		        
		}, function(response) {
		    //Second function handles error
		    console.log("ERROR: status code: " + response.status);
		});
	}
}

	/*
	 * var initEditors = function () { for (var i = 0; i <
	 * $scope.quiz.questions.length; i++) { console.log("Does " + i + " need an
	 * editor?"); checkNeedEditor(i); }; };
	 * 
	 * initEditors();
	 */

	// PAGINATION
	$scope.filteredQuestions = [];
	$scope.currentPage = 1;
	$scope.numPerPage = 5;
	$scope.maxSize = 5;

	$scope.$watch('currentPage + numPerPage', function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

		$scope.filteredQuestions = $rootScope.quiz.questions.slice(begin, end);
	});
});

/* COUNTDOWN TIMER LOGIC */
app.controller('CountdownController', function($scope, $rootScope, $interval) {
	
	var startTime = 15;
	$scope.minutes = 0;
	$scope.seconds = startTime;
	$scope.barUpdate = getBarUpdate();
	$scope.submitModal = document.getElementById("submitModal");
	
//    m = checkTime(m);
//    s = checkTime(s);
    
	var timer = $interval(function(){
		$scope.barUpdate = {width:getBarUpdate()+'%'};
		
		//WHEN THE TIMER REACHES ZERO,
		//OPEN THE SUBMIT MODAL
		if ($scope.minutes < 0 && $scope.seconds < 0)
		{
			submitAssessment();
		}
	}, 1000);
	
	function getBarUpdate() {
		$scope.seconds = $scope.seconds - 1;
		
		if ($scope.seconds < 0)
		{
			$scope.seconds = 59;
			$scope.minutes = $scope.minutes - 1;
		}

		console.log($scope.minutes + "m " + $scope.seconds + "s");
		
		if ($scope.minutes < 0) {
			$interval.cancel(timer);
			showSubmitModal();
			console.log("time over");
		}
		
		return ((($scope.minutes*60)+$scope.seconds) / startTime) * 100;
	}
	
	function checkTime(i) {
	    if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
	    return i;
	}
	
	function showSubmitModal() {
		submitModal.style.display = "block";
	}
});

app.controller('SideNavController', function($scope, $rootScope, $interval, $http) {
	
	function submitAssessment(){
		answerData = {"id":1};
		
		postAssessment(answerData);
	}
	
	function postAssessment(answerData){
		$http({
			method: 'POST',
			url: '/Assessment/submitAssessment',
			headers: {'Content-Type': 'application/json'},
			data: answerData
		})
		.then(function(response) {
			//First function handles success
			console.log("Answers sent: " + response.data);
		}, function(response) {
			//Second function handles error
			console.log("status code: " + response.status);
		});
	}
});

app.controller('ModalController', function($scope, $rootScope, $interval, $http) {
	
	function submitAssessment(){
		answerData = {"id":1};
		
		postAssessment(answerData);
	}
	
	function postAssessment(answerData){
		$http({
			method: 'POST',
			url: '/Assessment/submitAssessment',
			headers: {'Content-Type': 'application/json'},
			data: answerData
		})
		.then(function(response) {
			//First function handles success
			console.log("Answers sent: " + response.data);
		}, function(response) {
			//Second function handles error
			console.log("status code: " + response.status);
		});
	}
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
