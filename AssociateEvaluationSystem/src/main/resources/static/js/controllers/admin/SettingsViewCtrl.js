adminApp.controller("SettingsViewCtrl", function($scope, $mdToast, $http, SITE_URL, API_URL){
	
	
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