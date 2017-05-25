/**
 * Created by Mehrab on 5/22/2017.
 */

userApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
    var mc = this;

    // functions
    // sets navbar to current page even on refresh
    mc.findCurrentPage = function() {

        // var path = $location.path().replace("/", "");
        var path = window.location.pathname.substr(1);
        console.log("in user/menuctrl");
        switch(path) {
            case "aes/recruit" : return "register";
            case "aes/updateUser" : return "updateRecruiter";
            default : return "overview"
        }
    };

    mc.currentPage = mc.findCurrentPage();

});