/**
 * @namespace AES.bankApp
 * @memberOf AES
 */

var app = angular.module("bankApp", ['ui.router', 'ngFileUpload','ngProgress', 'ngMaterial', 'ngMessages']);

app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider) {

    $stateProvider
        .state('category', {
            url:'category',
            templateUrl: 'templates/misc/bank/categoryTemplate.html',
            controller: 'CategoryCtrl',
            controllerAs: 'ctrl'
        })
        .state('tag', {
            url:'tag',
            templateUrl: 'templates/misc/bank/tagTemplate.html',
            controller: 'TagCtrl'
        })
        .state('question', {
            url:'question',
            templateUrl: 'templates/misc/bank/questionTemplate.html',
            controller: 'QuestionCtrl',
            controllerAs: 'qc'
        })
        .state('upload', {
            url:'upload',
            templateUrl: 'templates/misc/bank/uploadTemplate.html',
            controller: 'MasterCtrl'
        })
        .state('fullQuestion', {
            url:'fullQuestion',
            templateUrl: 'templates/misc/bank/addQuestionTemplate.html',
            controller: 'QuestionCtrl'
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

app.filter('startFrom', function() {
    return function(userInput, start) {
        start = +start;
        return userInput.slice(start);
    }
});

app.filter('unique', function () {

    return function (items, filterOn) {

        if (filterOn === false) {
            return items;
        }

        if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
            var newItems = [];

            
           
            
            var extractValueToCompare = function (item) {
                if (angular.isObject(item) && angular.isString(filterOn)) {
                    return item[filterOn];
                } else {
                    return item;
                }
            };

            angular.forEach(items, function (item) {
                var valueToCheck, isDuplicate = false;

                for (var i = 0; i < newItems.length; i++) {
                    if (angular.equals(extractValueToCompare(newItems[i]), extractValueToCompare(item))) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    newItems.push(item);
                }

            });
            items = newItems;
        }
        return items;
    };
});