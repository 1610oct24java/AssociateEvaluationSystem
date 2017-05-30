app.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
    var mc = this;

    // functions
    // sets navbar to current page even on refresh
    mc.findCurrentPage = function() {

        
        var path = window.location.pathname.substr(1);

        switch (path) {
            case "index.html" : 
            case "update.html" : 
            case "aes/registerEmployee" :
            case "aes/updateEmployee" : return "employees";
            case "New.html" :
            case "aes/createAssessment" : return "assessments";
            case "aes/globalSettings" : return "globalSettings";
            case "aes/parser" : return "parser";
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

  
    $scope.toggleRight = buildToggler('right');
    $scope.isOpenRight = function() {
        return $mdSidenav('right').isOpen();
    };

    $scope.toggleAss = buildToggler('ass');
    $scope.isOpenAss = function(){
        return $mdSidenav('ass').isOpen();
    };
    /**
     * Supplies a function that will continue to operate until the
     * time is up.
     */
    function debounce(func, wait) {
        var timer;

        return function debounced() {
            $timeout.cancel(timer);
            timer = $timeout(function(context) {
                timer = undefined;
                func.apply($scope, Array.prototype.slice.call(arguments));
            }, wait || 10);
        };
    }

    /**
     * Build handler to open/close a SideNav; when animation finishes
     * report completion in console
     */
//    function buildDelayedToggler(navID) {
//        return debounce(function() {
//            // Component lookup should always be available since we are not using `ng-if`
//            $mdSidenav(navID)
//                .toggle()
//        }, 200);
//    }

    function buildToggler(navID) {
        return function() {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav(navID)
                .toggle()
        };
    }

});