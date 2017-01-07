var app = angular.module("AESapp", [ 'ui.router', 'ngFileUpload','ngProgress']);

app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    //$urlRouterProvider.otherwise('resources/pages/trainerHome.html#/');
 
    $stateProvider
        .state('category', {
            url:'/category',
            templateUrl: 'categoryTemplate.html',
            controller: 'CategoryCtrl',
            controllerAs: 'ctrl'
        })
        .state('tag', {
            url:'/tag',
            templateUrl: 'tagTemplate.html',
            controller: 'TagCtrl'
        })
        .state('question', {
            url:'/question',
            templateUrl: 'questionTemplate.html',
            controller: 'QuestionCtrl',
            controllerAs: 'qc'
        })
        .state('upload', {
            url:'/upload',
            templateUrl: 'uploadTemplate.html',
            controller: 'MasterCtrl'
        })
 
}]);

app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

app.service('fileUpload', ['$http', function ($http) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        return $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
    }
}]);