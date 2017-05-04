/**
 * 
 */

angular.module('adminApp').controller("SettingsViewCtrl", function($scope, $mdToast, $http, SITE_URL, API_URL){
	
	
	var settingsUrl = SITE_URL.BASE + API_URL.BASE + API_URL.ADMIN + "/globalSettings"
	
	 $scope.showToast = function(message) {
    	$mdToast.show($mdToast.simple().textContent(message).parent(document.querySelectorAll('#toastContainer')).position("center center").action("OKAY").highlightAction(true));
    };
	
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
            	$scope.settings.keys = [];
            	data.forEach(function (s){
            		$scope.settings[s.propertyId] = s;
            		$scope.settings.keys.push(s.propertyId);
            	});
            }
		}).error( function() {
			 $scope.getSettingsUnsuccessful = true;
		});
	}
	
	$scope.setSettings = function(){
		//turns the settings map back into a settings array
		var settingsArray = [];
		$scope.settings.keys.forEach(function (s){
			settingsArray.push($scope.settings[s]); 
		});
		
		$http({
			method 	: 	'PUT',
			url		:	settingsUrl,
			data	:	settingsArray,
			headers : {'Content-Type' : 'application/json'}
		}).success(function (data){
			if (data){
				$scope.showToast("Settings Saved");
			} else {
				$scope.showToast("Settings Failed to Save");
			}
		}).error(function(){
			$scope.showToast("Settings Failed to Save");
		})
	}
	
	$scope.getSettings();
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