app.controller('CreationCtrl', ['$scope', 'fileUpload', 'ngProgressFactory','$log','Upload', function($scope, fileUpload, ngProgressFactory, $log, Upload){
	// ngProgress bar initialization
	$scope.progressbar = ngProgressFactory.createInstance();
	
    $scope.uploadFile = function(){
    	// Start the progressbar
    	$scope.progressbar.start();
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
    
    var self = this;
    $scope.busy = true;
    $scope.ready = false;

    $scope.files = [];

    $scope.$watch('files', function () { 
      $scope.upload($scope.files);
    });

    $scope.upload = function (files) {
      $log.debug("upload... ", files);

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