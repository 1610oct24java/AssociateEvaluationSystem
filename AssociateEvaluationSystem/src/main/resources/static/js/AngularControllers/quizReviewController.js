app.controller("quizReviewController", function($scope, $rootScope, $http, 
		$location, $window, $timeout, $anchorScroll) {
	$rootScope.states = [];
	$scope.answers = [];
	$scope.numEditors = 0;
	$scope.oneAtATime = false;
	$scope.editors = [];
	$rootScope.protoTest;
	$scope.questions = [];
	$rootScope.snippetStarters = [];
	$rootScope.snippetSubmissions = [];
	$scope.protoTest2 = {};
	$scope.testtaker = "loading...";
	$scope.authUser;
	$scope.submitted = false;
	$scope.returning = false;
	getQuizQuestions();
	getSignedInUser();

	
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
		if ($scope.questions[ndx].question.format.formatName === "Multiple Choice" ) {
			$scope.answers.push([-1]);
		}
		else {
			$scope.answers.push(-1);
		}
	}
	var initSetup = function() {
		/*SA-CHANGES STARTED*/
		for (var i = 0; i < $scope.questions.length; i++) {
			var boolT = 0; var optionS=[];
			for (var z3=0; z3 < $rootScope.protoTest.options.length; z3++)
			{
					for(var k=0;k<$scope.questions[i].question.option.length;k++){
					if($scope.questions[i].question.option[k].optionId==$rootScope.protoTest.options[z3].optionId)
						{
						  boolT=1;
						  optionS.push(k);
						}
				}
			}
			
			if($scope.questions[i].question.format.formatName === "Drag and Drop") {
			 var oDragDrop=[];
			 for(var z4=0;z4<$rootScope.protoTest.assessmentDragDrop.length;z4++){
					var uOrder = $rootScope.protoTest.assessmentDragDrop[z4].userOrder;
					var dDrop = $rootScope.protoTest.assessmentDragDrop[z4].dragDrop;
					
					if(($scope.questions[i].question.dragdrop).indexOf(dDrop)){
						oDragDrop[uOrder-1]=dDrop;
					}
			 }
			 if(oDragDrop.length){
				 $scope.questions[i].question.dragdrop = oDragDrop;
				 boolT=1
			 }
			}
			
			makeState(i);
			if(boolT==1){
				
				if ($scope.questions[i].question.format.formatName === "Multiple Choice" ) {
					if(optionS.length){
						$scope.selectOption(optionS[0],i);
					}
				}
				else if($scope.questions[i].question.format.formatName === "Multiple Select")
				{
					for(var l=0;l<optionS.length;l++){
						$scope.selectOption(optionS[l],i);
					}
				}
				
				$scope.states[i].saved = true;
				
			}else{
			 makeAnswers(i);
			}
			/*SA-CHANGES ENDED*/
		}
		$scope.testtaker = $rootScope.protoTest.user.firstName + " " + $rootScope.protoTest.user.lastName;
	
		$timeout(function () {
			for (var i=0; i < $scope.filteredQuestions.length; i++)
			{
				if ($scope.filteredQuestions[i].question.format.formatName === "Code Snippet")
				{
					var editorId = "editor"+$scope.filteredQuestions[i].question.questionId;
					var aceEditor = ace.edit(editorId);
					aceEditor.getSession().setValue($rootScope.snippetStarters[0], -1);
				}
			}
	    }, 5000);
	};

	$scope.collapseQuestion = function(index) {
		$scope.states[index].open = !$scope.states[index].open;
	};
	
	var saveQuestion = function(index) {

		var q = $scope.questions[index];
		
		if(q.question.format.formatName === "Drag and Drop") {
			for (var i = 0; i < q.question.dragdrop.length; i++) {
				var assessmentDragDrop = {
						assessmentDragDropId : 0, //q.question.questionId,
						userOrder : i+1,
						assessmentId : $scope.protoTest.assessmentId,
						dragDrop : q.question.dragdrop[i]
				};
				$rootScope.protoTest.assessmentDragDrop.push(assessmentDragDrop);
			}
		}
		
		$scope.states[index].saved = true;
	};

	$scope.handleSaveClick = function(index) {
		//If question is not already saved, save it
		saveQuestion(index);
		
		$rootScope.protoTest.assessmentDragDrop.forEach(function(entry){
			delete entry.assessmentId;
			entry.assessment = {"assessmentId" : $rootScope.protoTest.assessmentId,};
		});

		var answerData = {
				assessment : $rootScope.protoTest,
				snippetUploads : $rootScope.snippetSubmissions
		};

		$http({
			method: 'POST',
			url: "aes/rest/quickSaveAssessment",
			headers: {'Content-Type': 'application/json'},
			data: answerData
		}).then(function(response) {
			//Removed console logs for sonar cube.
		});
	}

	$scope.flagQuestion = function(index) {
		$scope.states[index].flagged = !$scope.states[index].flagged;
	};
	
	$scope.selectOption = function (ndxOption, ndxQuestion) {
		// Handles putting the answer into the javascript object when the uesr
		// selects an option
		var answer;
		
		if($scope.questions[ndxQuestion].question.format.formatName === "Multiple Choice") {
			// Handles multiple choice questions
			$scope.answers[ndxQuestion] = ndxOption;
			answer = $scope.questions[ndxQuestion].question.option[ndxOption]; // Store selected answer
			
			var questionOptionIdArray = []; // Get options from question
			
			// Gets options from current selecting question into an array.
			for (var z=0; z < $scope.questions[ndxQuestion].question.option.length; z++)
			{
				questionOptionIdArray.push($scope.questions[ndxQuestion].question.option[z].optionId);
			}
			
			// Checks the answer selection array for previous multiple choice selections and deletes
			// them from the array since we only want ONE radial selection kept in the answer array
			for (var z2=0; z2 < questionOptionIdArray.length; z2++)
			{
				for (var z3=0; z3 < $rootScope.protoTest.options.length; z3++)
				{
					if ($rootScope.protoTest.options[z3].optionId === questionOptionIdArray[z2])
					{
						// Remove previously selected multiple choices from same question
						$rootScope.protoTest.options.splice(z3,1);
					}
				}
			}
			
			// Finally, add current selected multiple choice option to the answers array
			$rootScope.protoTest.options.push(answer);
			
		}else if ($scope.questions[ndxQuestion].question.format.formatName === "Multiple Select" ) {
			// Handles multiple select questions
			answer = $scope.questions[ndxQuestion].question.option[ndxOption];
			var foundAt = $rootScope.protoTest.options.indexOf(answer);
			
			if (foundAt === -1) {
				// User has not selected this previously, add it
				$rootScope.protoTest.options.push(answer);
			} else {
				// User has selected this previously, delete it.
				$rootScope.protoTest.options.splice(foundAt, 1);
			}
		}
		
		if ($scope.states[ndxQuestion].saved) {
			$scope.states[ndxQuestion].saved = false;
		}
	}
	
	$scope.checkChecked = function (ndxOption, ndxQuestion) {
		var output = false;
		
		if($scope.questions[ndxQuestion].question.format.formatName === "Multiple Choice") {
			// Handle multiple choice
			if ($scope.answers[ndxQuestion] === ndxOption) {
				output = true;
			}
		} else if ($scope.questions[ndxQuestion].question.format.formatName === "Multiple Select") {
			// Handle multiple select
			var answer = $scope.questions[ndxQuestion].question.option[ndxOption];
			var foundAt = $rootScope.protoTest.options.indexOf(answer);
			if (foundAt === -1){
				// This answer has not been previously selected
				output = false;
			} else {
				// This answer has been previously selected, should be marked
				output = true;
			}
		}
		return output;
	}
	
	// EDITORS	
	$scope.getType = function(filetype) {
		var output;
		switch(filetype) {
		case "java" :
			output = "java";
			break;
		case "cpp" :
        case "c++" :
        case "c" :
			output = "c_cpp";
			break;
		case "cs" :
			output = "csharp";
			break;
		default : 
			output = "java";
			break;
		}
		return output;
	}
	
	$scope.aceChanged = function(e) {
		var id2 = e[1].container.id;
		var editor = e[1];
		var SnippetUpload = function(_code, _questionId, _fileType){
			this.code = _code;
			this.questionId = _questionId;
			this.fileType = _fileType;
		};
		var snippetQuestionIndex;
		var q = {};
		for (var i = 0; i < $scope.questions.length; i++ ) {
			if ($scope.questions[i].question.questionId == id2.substr(6, id2.length)){
				q = $scope.questions[i];
				snippetQuestionIndex = i;
			}
		}
		
		var incFileType = q.question.snippetTemplates[0].fileType;
		var newSnippet = new SnippetUpload(editor.getValue(), id2.substr(6, id2.length), incFileType);
		
		for (i = 0; i < $rootScope.snippetSubmissions.length; i++){
			if ($rootScope.snippetSubmissions[i].questionId = newSnippet.questionId){
				$rootScope.snippetSubmissions.splice(i, 1);
			}
		}
		$rootScope.snippetSubmissions.push(newSnippet);
		saveQuestion(snippetQuestionIndex);
	};

	// PAGINATION
 	$scope.filteredQuestions = [];
	$scope.currentPage = 1;
	$scope.numPerPage = 5;
	$scope.maxSize = 100;
	
	//pagination goes to top of the page
	$scope.pageChanged = function() {
	    $location.hash('top');
	    $anchorScroll();
	};
	
	//code to jump to page and question 
	$scope.jumpPage = function (index) {
		$scope.pageChanged();
		numPage=index/$scope.numPerPage;
		$scope.currentPage =1+ Math.floor(numPage);
//		
		//$timeout(1000);
		var old = $location.hash();
		//code to jump to the question
		 var newHash = 'anchor' + index;
	      if ($location.hash() !== newHash) {
	        // set the $location.hash to `newHash` and
	        // $anchorScroll will automatically scroll to it
	        $location.hash('anchor' + index);
	        $anchorScroll(newHash);
	      } else {
	        // call $anchorScroll() explicitly,
//	        // since $location.hash hasn't changed
	        $anchorScroll(newHash);
	      }
	      //location.hash(old);
		
	};

	$scope.$watch('currentPage + numPerPage', function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

		$scope.filteredQuestions = $scope.questions.slice(begin, end);

		$timeout(function () {
			for (var i=0; i < $scope.filteredQuestions.length; i++)
			{
				if ($scope.filteredQuestions[i].question.format.formatName === "Code Snippet")
				{
					var editorId = "editor"+$scope.filteredQuestions[i].question.questionId;
					var aceEditor = ace.edit(editorId);
					//aceEditor.getSession().setValue($rootScope.snippetSubmissions[0].code, -1);
					
					/*SA-CHANGES STARTED*/
					//To init ace editor if other than first page
					if(aceEditor.length!=1){
						if(aceEditor.getSession().getValue()==="Enter code here"){
							aceEditor.getSession().setValue($rootScope.snippetStarters[0], -1);
						}
					}

					//To keep changes on the ace editor if pages are switched
					if($rootScope.snippetSubmissions.length){
						aceEditor.getSession().setValue($rootScope.snippetSubmissions[0].code, -1);
					}
					/*SA-CHANGES ENDED*/
				}
			}
	    }, 2000);
		
	});
	
	$scope.$watch('questions', function() {
		var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
				+ $scope.numPerPage;

		$scope.filteredQuestions = $scope.questions.slice(begin, end);
	});
	
	// AJAX
	function getQuizQuestions() {
		$http({
			method: 'GET',
			url: QUIZ_REST_URL + $location.search().asmt,
			headers: {'Content-Type': 'application/json'}
		})
		.then(function(response) {
			// Check response for assessment availability
			if (response.data.msg != "allow"){
				// check if the assessment has the assessment object
				if (!response.data.assessment){
					// redirect to a page that says page not available
					$window.location.href = '/aes/missing';
				} else {
					// Assessment ready to take
					$rootScope.protoTest = response.data.assessment;
				
					$scope.questions = $rootScope.protoTest.template.templateQuestion;
					//$rootScope.protoTest.options = [];
					$rootScope.snippetStarters = response.data.snippets;
					initSetup();
					$rootScope.initQuizNav();
				}
			}else {
				// Assessment was taken or time expired, redirecting to expired page
				$window.location.href = '/aes/expired';
			}
		});
	}
	
	// get signed in user
	function getSignedInUser() {
		
		$http.get('/aes/security/auth')
		.then(function(response) {
			if (response.data.authenticated) {
				var authUser = {
					username : response.data.principal.username,
					authority: response.data.principal.authorities[0].authority
				}
				$scope.authUser = authUser;
				if($scope.authUser.authority != 'ROLE_ADMIN' &&
						$scope.authUser.authority != 'ROLE_TRAINER' &&
						$scope.authUser.authority != 'ROLE_RECRUITER' && 
						$scope.authUser.authority != 'ROLE_CANDIDATE') {
					window.location = '/login';
				}
			} else {
				window.location = '/login';
			}
		});
		
	}
	
	$rootScope.submitAssessment = function(){

		$scope.submitted = true;

		$rootScope.protoTest.assessmentDragDrop.forEach(function(entry){

			delete entry.assessmentId;
			entry.assessment = {"assessmentId" : $rootScope.protoTest.assessmentId,};
		});

		var answerData = {
				assessment : $rootScope.protoTest,
				snippetUploads : $rootScope.snippetSubmissions
		};
		
	}
	
	// return to the user's homepage after they have finishing reviewing the assessment.
	$rootScope.quitReview = function() {
		$scope.returning = true;
		
		if ($scope.authUser.authority == 'ROLE_ADMIN') {
			//console.log('admin home');
			
			window.location = '/aes/viewEmployees';
		}
		else if ($scope.authUser.authority == 'ROLE_RECRUITER') {
			window.location = '/aes/view';
		}
		else if ($scope.authUser.authority == 'ROLE_CANDIDATE') {
			console.log('candidate home todo');
			
			// TODO: navigate to candidate's home page.
		}
	}
	
	// check whether the user is an admin
	$scope.isCandidate = function() {
		if ($scope.authUser) {
			if ($scope.authUser.authority == 'ROLE_CANDIDATE') {
				return true;
			}
		}
		return false;
	}
	
});




