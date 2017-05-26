adminApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
    var mc = this;

    //JADE edidted
    mc.findCurrentPage = function() {
        var path = window.location.pathname.substr(5);
        switch (path) {
            case "index.html":
                return "employees";
            case "viewEmployees":
                return "employees";
            case "update.html":
                return "employees";
            case "registerEmployee":
                return "employees";
            case "updateEmployee":
                return "employees";
            case "manageQuestions":
                return "questions";
            case "addQuestions":
                return "questions";
            case "chooseAssessment":
                return "assessments";
            case "createAssessment":
                return "assessments";
            case "New.html":
                return "assessments";
            case "parser":
                return "parser";
            case "globalSettings":
                return "globalSettings";
            default:
                return "overview"
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
    function debounce(func, wait, context) {
        var timer;

        return function debounced() {
            var context = $scope,
                args = Array.prototype.slice.call(arguments);
            $timeout.cancel(timer);
            timer = $timeout(function(context) {
                timer = undefined;
                func.apply(context, args);
            }, wait || 10);
        };
    }

    /**
     * Build handler to open/close a SideNav; when animation finishes
     * report completion in console
     */


    function buildToggler(navID) {
        return function() {
            // Component lookup should always be available since we are not using `ng-if`
            $mdSidenav(navID)
                .toggle()
        };
    }

});
