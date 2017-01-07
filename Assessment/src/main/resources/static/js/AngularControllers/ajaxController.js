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
	            url: 'getAssessment',
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
	            url: 'submitAssessment',
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
