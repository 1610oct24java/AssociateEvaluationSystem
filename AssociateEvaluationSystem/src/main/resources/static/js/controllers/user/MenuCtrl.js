/**
 * @class AES.userApp.menuCtrl
 */

userApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
    var mc = this;

    // functions
    // sets navbar to current page even on refresh
    mc.findCurrentPage = function() {

        // var path = $location.path().replace("/", "");
        var path = window.location.pathname.substr(1);
        switch(path) {
            case "aes/recruiterDash" : return "dashboard";
            case "aes/view" : return "overview";
            case "aes/recruit" : return "register";
            case "aes/updateUser" : return "updateRecruiter";
            default : return "dashboard";
        }
    };

    mc.currentPage = mc.findCurrentPage();

});