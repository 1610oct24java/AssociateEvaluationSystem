angular.module('bankApp').controller('MasterCtrl', ['$scope', '$rootScope','$log', '$state', '$http', 'Upload', 'questionBuilderService',function($scope, $rootScope, $log, $state, $http, Upload, questionBuilderService){
	
	
	
	$scope.templateName = "";
	
	$scope.addCategories = function(){
		$state.go("category")
		$scope.templateName = "Add Categories";
	};
	
	$scope.addTags = function(){
		$state.go("tag");
		$scope.templateName = "Add Tags";
	};
	
	$scope.modifyQuestions = function(){
		$state.go("question");
		$scope.templateName = "Modify Questions";
	};
	
	$scope.uploadAssessment = function(){
		$state.go("upload");
		$scope.templateName = "Upload Assessment";
	};
	
	// ngProgress bar initialization
//	$scope.progressbar = ngProgressFactory.createInstance;
	
    $scope.uploadFile = function(){
    	// Start the progressbar
    	$scope.progressbar.start;
        // File uploaded by the user
    	var file = $scope.myFile;
        // URL to Spring Controller
        var uploadUrl = "/aes/parseAiken";
        fileUpload.uploadFileToUrl(file, uploadUrl)
        	.then($scope.completeProgressbar);
    };
    
    // Stops the progressbar from.. progressing
//    $scope.completeProgressbar = function(){
//    	$scope.progressbar.stop;
//        $scope.progressbar.complete;
//    }
    
    $scope.busy = true;
    $scope.ready = false;

    $scope.files = [];

    $scope.$watch('files', function () { 
    	console.log("WATCH called");
    	angular.forEach($scope.files, function(file){
    		var fileArr = [file];    		
    		$scope.upload(fileArr, file.type);
    	})
      
    });
    
   $scope.submitSnippetFiles = function(){
	   if($scope.snippetTemplateFile != undefined && $scope.answerSnippetFile != undefined && $scope.questionText != undefined && $scope.fileType != undefined){
		   //Upload the files to the s3 container
		   console.log("Adding Snippet Files");
		   $scope.files.push($scope.snippetTemplateFile);
		   $scope.files.push($scope.answerSnippetFile);
		   console.log($scope.files);
		   var folderNames = ['SnippetTemplates', 'SnippetSolutions']
		   $scope.upload($scope.files, ['SnippetTemplates', 'SnippetSolutions']);
		   console.log("Building snippet question from text");
		   
		   //Upload the files to the database
		   var questionText = $scope.questionText;
		   var snippetTemplateUrl = folderNames[0] + '/' + $scope.snippetTemplateFile.name;
		   var snippetSolutionUrl = folderNames[1] + '/' +  $scope.answerSnippetFile.name;
		   var fileType = $scope.fileType;
		   var builder = new questionBuilderService.questionBuilder();
		   builder.createSnippetQuestionBuilder(questionText,fileType,snippetTemplateUrl,snippetSolutionUrl);
		   builder.addQuestionCategory(1,'Java');
		   question = builder.build();
		   console.log(question);
		   
		   questionJSON = JSON.stringify(question);
		   console.log(question);
		    $http({
		        method: "POST",
		        url: "question",
		        data: questionJSON
		    }).then(function (response) {
		        console.log(response.data)
		    });
	   }
	   
   }
   
   $scope.submitSnippetText = function(){
	   console.log("Building snippet question from text");
	   var builder = new questionBuilderService.questionBuilder();
	   builder.createSnippetQuestionBuilder('I am a new code snippet question.','Java','SnippetTemplates/HiNick.java','SnippetSolutions/s3Tester.java');
	   builder.addQuestionCategory(1,'Java');
	   question = builder.build();
	   console.log(question);
	   /*
	   questionJSON = JSON.stringify(question);
	   console.log(question);
	    $http({
	        method: "POST",
	        url: "question",
	        data: questionJSON
	    }).then(function (response) {
	        console.log(response.data)
	    });
	    */
	    console.log("/s3uploadTextAsFile/" + question.snippetTemplates[0].templateUrl);
	    var url = String();
	    var contents = JSON.stringify({contents: "This is test template text"});
	   // var contents2 = ;
	    console.log(contents);
	    $http({
	        method: "POST",
	        url: "rest/s3uploadTextAsFile/" + question.snippetTemplates[0].templateUrl, 
	        data: "This is test template text"
	    }).then(function (response) {
	        console.log(response.data)
	    });
	    
   }
   

   
//
//    $scope.$watch('snippetTemplateFile', function () { 
//    	console.log("WATCH snippetTemplateFile");
//      $scope.upload($scope.snippetTemplateFile);
//    });
//    
//    $scope.$watch('answerSnippetFile', function () { 
//    	console.log("WATCH answerSnippetFile");
//      $scope.upload($scope.answerSnippetFile);
//    });
    
	$scope.progressUpdater = function(evt){
		return parseInt(100.0 * evt.loaded / evt.total);
	};
	
	$scope.someFunction = function(data, status, headers, config){
    // keeping it to shut up sonarQube. This is that hot fix doe.
		
	};
	
    $scope.upload = function (files, folderNames) {
    		console.log("uploading");
    		console.log(files);
    		console.log(folderNames);
          if (files && files.length) {
              for (var i = 0; i < files.length; i++) { 
            	  var file = files[i];
            	  Upload.upload({
            	        url: 'rest/s3uploadFile/' + folderNames[i],
            	        method: 'POST',
            	        file: file
            	    }).progress(function (evt) {
            	        var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            	        console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
            	    }).success(function (data, status, headers, config) {
            	        console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
            	    });
            	  
//                  Upload.upload({
//                      url: 'rest/s3uploadFile/',
//                      method: 'POST',
////                      fields: {
////                        'filecontext': 'product',
////                      },
//                     file: file
//                  }).then(function(data, status, headers, config){
//                	  //$scope.progressUpdater(evt);
//                	  console.log("Hi from then!")
//                	  $scope.someFunction(data, status, headers, config);
//                  });
                  
              }
          }
      };
}]);