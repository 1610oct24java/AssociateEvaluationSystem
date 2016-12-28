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
		  console.log("Entered collapse");
		  console.log("glyph id=" + index);
		  $scope.states[index].open = !$scope.states[index].open;
	  };
	  
	  $scope.saveQuestion = function(index) {
		  console.log("Entered save");
		  console.log("glyph id=" + index);
		  $scope.states[index].saved = !$scope.states[index].saved;
	  };
	  
	  $scope.flagQuestion = function(index) {
		  console.log("Entered flag");
		  console.log("glyph id=" + index);
		  $scope.states[index].flagged = !$scope.states[index].flagged;
	  };
	  
	});
