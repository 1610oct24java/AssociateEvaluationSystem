'use strict';

var app = angular.module('AesApp',['ui.bootstrap', 'ngAnimate']);

app.controller('AccordionController', function($scope) {

	  $scope.questions = [
	    {
	      id : 0,
	      title: 'Question 1',
	      content: 'What is your name?'
	    },
	    {
	      id : 1,
	      title : 'Question 2',
	      content : 'What is your favorite color?'
	    },
	    {
		  id : 2,
	      title : 'Question 3',
		  content : 'What is the airspeed velocity of an unladen swallow?'
		}
	  ];

//	  var ql = $scope.questions.length;
	  
	  $scope.states = new Array();
	  
	  for (var i=0; i < $scope.questions.length; i++)
	  {
		  var temp = {
				  flagged : false,
				  saved : false,
				  open : false
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
	  
	});

app.controller('QuizNavController', function($scope, $rootscope) {
	
	
	
});

app.controller('AjaxController', function($scope, $http) {

    $scope.user;
    $scope.pass;

    $scope.getAssessment = function() {
        var assessmentData = {username: this.user, password: this.pass};
        
        getAssessmentData(assessmentData);
    }
    
    $scope.submitAssessment = function(){
        var assessmentResults = {username:this.registerUser,password:this.registerPass};
        
        postAssessmentData(assessmentResults);
    }
    
    // postData takes in JSON, sends with HTTP POST to Spring LoginController
    function getAssessmentData(data){
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
    
    // postData takes in JSON, sends with HTTP POST to Spring LoginController
    function postAssessmentData(data){
        $http({
            method: 'POST',
            url: '/Assessment/submitAssessment',
            headers: {'Content-Type': 'application/json'},
            data: data
        })
        .success(function (data){
            console.log("Posted results: ");
        })
        .error(function (response){
            console.log("Error while submitting assessment");
        });
    }
});
