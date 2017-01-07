app.controller('MasterCtrl', ['$scope', '$rootScope', 'fileUpload', 'ngProgressFactory','$log','Upload', '$state', function($scope, $rootScope, fileUpload, ngProgressFactory, $log, Upload, $state){
	$rootScope.templateName = "";
	
	$scope.addCategories = function(){
		$state.go("category")
		$rootScope.templateName = "Add Categories";
	};
	
	$scope.addTags = function(){
		$rootScope.templateName = "Add Tags";
	};
	
	$scope.modifyQuestions = function(){
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
        var uploadUrl = "/TestBank/parseAiken";
        fileUpload.uploadFileToUrl(file, uploadUrl)
        	.then($scope.completeProgressbar);
    };
    
    // Stops the progressbar from.. progressing
    $scope.completeProgressbar = function(){
    	$scope.progressbar.stop();
        $scope.progressbar.complete();
    }
    
    $scope.busy = true;
    $scope.ready = false;

    $scope.files = [];

    $scope.$watch('files', function () { 
      $scope.upload($scope.files);
    });

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
                  }).progress(function (evt) {
                      var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                      $log.debug('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                  }).success(function (data, status, headers, config) {
                      $log.debug('file ' + config.file.name + 'uploaded. Response: ' + data);
                  });
              }
          }
      };
}]);