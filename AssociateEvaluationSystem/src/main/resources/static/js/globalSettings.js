/**
 * 
 */

angular.module('adminApp').controller("SettingsViewCtrl", function($scope, $http, SITE_URL, API_URL, ROLE){
	
var settingsUrl = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + "/globalSettings"
	
	$scope.getSettings = function(){
		$scope.getSettingsUnsuccessful = false;
		$http({
			method  : 'GET',
			url		: settingsUrl
		}).success(function(data){
            if (!data){
                $scope.getSettingsUnsuccessful = true;
            } else {
            	$scope.settings = {};
            	data.forEach(function (s){
            		$scope.settings[s.propertyId] = s;
            	});
            }
		}).error( function() {
			 $scope.getSettingsUnsuccessful = true;
		});
	}

});

adminApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
    var mc = this;

    // functions
    // sets navbar to current page even on refresh
    mc.findCurrentPage = function() {

        // var path = $location.path().replace("/", "");
        var path = window.location.pathname.substr(1);

        switch(path) {
            case "aes/registerEmployee" : return "employees";
            case "aes/updateEmployee" : return "employees";
            case "aes/createAssessment" : return "assessments";
            case "aes/globalSettings" : return "globalSettings";
            default : return "overview"
        }
    };

    mc.buildToggler = function(navID) {
        return function() {
            $mdSidenav(navID)
                .toggle()
                .then(function() {
                    $log.debug("toggle " + navID + " is done");
                });
        };
    };
    $scope.isOpenLeft = function() {
        return $mdSidenav('left').isOpen();
    };


    // data
    mc.currentPage = mc.findCurrentPage();
    $scope.toggleLeft = mc.buildToggler('left');

 // $scope.toggleLeft = buildDelayedToggler('left');
    $scope.toggleRight = buildToggler('right');
    $scope.isOpenRight = function() {
        return $mdSidenav('right').isOpen();
    };

    $scope.toggleAss = buildToggler('ass');
    $scope.isOpenAss = function(){
    	return $mdSidenav('ass').isOpen();
    };
    
    function buildToggler(navID) {
        return function() {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav(navID)
                .toggle()
        };
    }

});