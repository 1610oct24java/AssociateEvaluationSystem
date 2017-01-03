app.controller('CreationCtrl', ['$scope', 'fileUpload', function($scope, fileUpload){
    
    $scope.uploadFile = function(){
        // File uploaded by the user
    	var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        // URL to Spring Controller
        var uploadUrl = "/TestBank/parseAiken";
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };
}]);