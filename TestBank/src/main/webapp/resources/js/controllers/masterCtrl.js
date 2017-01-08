app.controller('MasterCtrl', ['$scope', '$rootScope', 'fileUpload', 'ngProgressFactory','$log','Upload', '$state', function($scope, $rootScope, fileUpload, ngProgressFactory, $log, Upload, $state){
	
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
	$scope.progressbar = ngProgressFactory.createInstance;
	
    $scope.uploadFile = function(){
    	// Start the progressbar
    	$scope.progressbar.start;
        // File uploaded by the user
    	var file = $scope.myFile;
        // URL to Spring Controller
        var uploadUrl = "parseAiken";
        fileUpload.uploadFileToUrl(file, uploadUrl)
        	.then($scope.completeProgressbar);
    };
    
    // Stops the progressbar from.. progressing
    $scope.completeProgressbar = function(){
    	$scope.progressbar.stop;
        $scope.progressbar.complete;
    }
    
    $scope.busy = true;
    $scope.ready = false;

    $scope.files = [];

    $scope.$watch('files', function () { 
      $scope.upload($scope.files);
    });

    
	$scope.progressUpdater = function(evt){
		return parseInt(100.0 * evt.loaded / evt.total);
	};
	
	$scope.someFunction = function(data, status, headers, config){
    // keeping it to shut up sonarQube. This is that hot fix doe.
		
	};
	
    $scope.upload = function (files) {

          if (files && files.length) {
              for (var i = 0; i < files.length; i++) {
                  var file = files[i];
                  Upload.upload({
                      url: '/admin-api/files/upload',
                      fields: {
                        'filecontext': 'product',
                      },
                      file: file
                  }).progress(progressUpdater(evt))
                  .success(someFunction(data, status, headers, config));
              }
          }
      };
}]);