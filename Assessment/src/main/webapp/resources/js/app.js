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
		// Handles putting the answer into the javascript object when the uesr
		// selects an option
		
		if($scope.questions[ndxQuestion].templateQuestion.format.formatId === 0) {
			// Handles multiple choice questions
			$scope.answers[ndxQuestion] = ndxOption;
			var answer = $scope.questions[ndxQuestion].templateQuestion.multiChoice[ndxOption];
			var question = $scope.questions[ndxQuestion].templateQuestion;
			var foundAt = $scope.protoTest.options.indexOf(answer);
			// If it's not already in the system
			if (foundAt === -1) {
				// Make sure there's only one option per questions
				for (var i = 0; i < $scope.protoTest.options.length; i++) {
					if ($scope.protoTest.options[i].questionId === question.questionId){
						$scope.protoTest.options.splice(foundAt, 1);
					}
				}
				$scope.protoTest.options.push(answer);
			}
		} else if ($scope.questions[ndxQuestion].templateQuestion.format.formatId === 1 ) {
			console.log("2) Select Option: " + ndxOption + " in Question: " + ndxQuestion);
			var foundAt = $scope.answers[ndxQuestion].indexOf(ndxOption)
			var answer = $scope.questions[ndxQuestion].templateQuestion.multiChoice[ndxOption];
			if (foundAt === -1){
				$scope.answers[ndxQuestion].push(ndxOption);
				$scope.protoTest.options.push(answer);
			} else {
				$scope.answers[ndxQuestion].splice(foundAt, 1);
				$scope.protoTest.options.splice(foundAt, 1);
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

app.controller('ModalController', function($scope, $interval, $http) {
	
	$scope.submitAssessment = function(){
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

/* COUNTDOWN TIMER LOGIC */
app.controller('CountdownController', function($scope, $rootScope, $interval) {
	
	var startTime = 120;
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