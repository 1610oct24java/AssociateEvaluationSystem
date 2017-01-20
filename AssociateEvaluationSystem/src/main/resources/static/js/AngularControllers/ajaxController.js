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
	        });
	    }
	    
	    function postAssessmentData(data){
	        $http({
	            method: 'POST',
	            url: 'http://localhost:8090/aes/rest/submitAssessment',
	            headers: {'Content-Type': 'application/json'},
	            data: data
	        });
	    }
});