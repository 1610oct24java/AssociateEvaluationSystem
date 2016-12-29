'use strict';

var app = angular.module('AesApp',['ui.bootstrap', 'ngAnimate']);

app.controller('AccordionController', function($scope, $rootScope) {

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
	  
	  $rootScope.states = new Array();
	  
	  for (var i=0; i < $scope.questions.length; i++)
	  {
		  var temp = {
				  id : i,
				  flagged : false,
				  saved : false,
				  open : false
		  };
		  $rootScope.states.push(temp);
	  }
	  
	  $scope.collapseQuestion = function(index) {
		  console.log("Entered collapse: glyph id=" + index);
		  $rootScope.states[index].open = !$rootScope.states[index].open;
	  };
	  
	  $scope.saveQuestion = function(index) {
		  console.log("Entered save: glyph id=" + index);
		  $rootScope.states[index].saved = !$rootScope.states[index].saved;
	  };
	  
	  $scope.flagQuestion = function(index) {
		  
		  console.log("Entered flag: glyph id=" + index);
		  $rootScope.states[index].flagged = !$rootScope.states[index].flagged;
	  };
	  
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

/* Set the width of the side navigation to 250px and the right margin of the page content to 250px */
function openNav() {
    document.getElementById("sidenav").style.width = "250px";
    document.getElementById("page-container").style.marginRight = "250px";
}

/* Set the width of the side navigation to 0 and the right margin of the page content to 0 */
function closeNav() {
    document.getElementById("sidenav").style.width = "0";
    document.getElementById("page-container").style.marginRight = "0";
}