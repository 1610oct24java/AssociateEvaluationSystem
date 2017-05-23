var userApp = angular.module('userApp',['ngMaterial', 'ngMessages']);

userApp.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "",
	"PORT" : ":8080",
	
	"LOGIN": "index",
	"TRAINER_HOME" : "",
	"VIEW_CANDIDATES" : "view",
	"VIEW_EMPLOYEES" : "viewEmployees",
	"REGISTER_CANDIDATE" : "",
    "REGISTER_EMPLOYEE" : "",
    "ASSESSMENT_LANDING" : "assessmentLandingPage"
});

userApp.constant("API_URL", {
	"BASE"      : "/aes",
	"LOGIN"     : "/login",
	"LOGOUT"    : "/logout",
	"AUTH"      : "/security/auth",
	"CANDIDATE" : "/candidate/",
	"RECRUITER" : "/recruiter/",
	"LINK"      : "/link",
	"CANDIDATES": "/candidates"
});

userApp.constant("ROLE", {
	"RECRUITER" : "ROLE_RECRUITER",
	"TRAINER"   : "ROLE_TRAINER",
	"CANDIDATE" : "ROLE_CANDIDATE",
	"ADMIN"		: "ROLE_ADMIN"
});

userApp.config(function($mdThemingProvider) {

    var revOrangeMap = $mdThemingProvider.extendPalette("deep-orange", {
        "A200": "#F26925",
        "100": "rgba(242, 105, 37, 0.2)"
    });

    var revBlueMap = $mdThemingProvider.extendPalette("blue-grey", {
        "500": "#37474F",
        "800": "#3E5360"
    });

    $mdThemingProvider.definePalette("revOrange", revOrangeMap);
    $mdThemingProvider.definePalette("revBlue", revBlueMap);

    $mdThemingProvider.theme("default")
        .primaryPalette("revBlue")
        .accentPalette("revOrange");
});

//On enter event
userApp.directive('onEnter', function() {
    return function(scope, elm, attr) {
        elm.bind('keypress', function(e) {
            if (e.keyCode === 13) {
                scope.$apply(attr.onEnter);
            }
        });
    };
});

// Inline edit directive
userApp.directive('inlineEdit', function($timeout) {
    return {
        scope: {
            model: '=inlineEdit',
            handleSave: '&onSave',
            handleCancel: '&onCancel'
        },
        link: function(scope, elm, attr) {
            var previousValue;

            scope.edit = function() {
                scope.editMode = true;
                previousValue = scope.model;

                $timeout(function() {
                    elm.find('input')[0].focus();
                }, 0, false);
            };
            scope.save = function() {
                scope.editMode = false;

            };
            scope.cancel = function() {
                scope.editMode = false;
                scope.model = previousValue;
                scope.handleCancel({value: scope.model});
            };
        },
        template: '<div><input type="text" on-enter="save()" on-esc="cancel()" ng-model="model" ng-show="editMode">'
        + '<button ng-click="cancel()" ng-show="editMode"><span class="glyphicon glyphicon-remove"></span></button>'
        + '<button ng-click="save()" ng-show="editMode"><span class="glyphicon glyphicon-ok"></span></button>'
        + '<span ng-mouseenter="showEdit = true" ng-mouseleave="showEdit = false">'
        + '<span ng-hide="editMode" ng-click="edit()">{{model}}</span>'
        + '<a ng-show="showEdit" ng-click="edit()"><span class="glyphicon glyphicon-pencil"></span></a>'
        + '</span></div>'
    };
});

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
            break;
        }
    }
}

// formats dates from being in total-milliseconds-from-epoch format into a human-readable, presentable format.
function formatDate(date) {
    if (date == null) {
        return "";
    }
    var d = new Date(date);
    var min = d.getMinutes() < 10 ? '0' + d.getMinutes() : d.getMinutes();
    return (d.getMonth() + 1) + "/" + d.getDate() + "/" + d.getFullYear() + " " + d.getHours() + ":" + min;
}

// reformats dates formatted by formatDate() from (M/D/YYYY H:MM) to an iso format (YYYY-MM-DDTHH:mm:ss.SSSZ)
function reformatDate(date) {
    if (!date) {
        return "";
    }

    // split up the date and time components to make easier to change.
    var datetime = date.split(' ');

    // reformat the calendar-date components.
    var oldDate = datetime[0];
    var oldDateComponents = oldDate.split('/');
    var YYYY = oldDateComponents[2];
    var MM = oldDateComponents[0].length === 1 ? '0' + oldDateComponents[0] : oldDateComponents[0]; // month should be in the form 'MM'.
    var DD = oldDateComponents[1];

    // reformat the clock-time components.
    var oldTime = datetime[1];
    var oldTimeComponents = oldTime.split(':');
    var hh = oldTimeComponents[0].length === 1 ? '0' + oldTimeComponents[0] : oldTimeComponents[0]; // hours should be in the form 'hh'.
    var mm = oldTimeComponents[1];
    var ss = '00'; // seconds aren't preserved in formatDate() from the original TimeStamp, so here just hardcoding 0...
    var sss = '000'; // same situation as seconds, so here just hardcoding 0 for milliseconds...

    // assemble the iso date from the reformatted date and time components.
    var isoDate = YYYY + '-' + MM + '-' + DD + 'T' + // add the date components.
        hh + ':' + mm + ':' + ss + '.' + sss + 'Z'; // add the time components.

    // return the iso-formatted version of the date.
    return isoDate;
}

// formats dates from being in total-milliseconds-from-epoch format into an iso format (YYYY-MM-DDThh:mm:ss.sssZ).
function formatDateIso(date) {
    if (!date) {
        return "";
    }

    var jsDate = new Date(date);

    // get the calendar-date components of the iso date.
    var YYYY = jsDate.getFullYear();
    var MM = (jsDate.getMonth() + 1) < 10 ? '0' + (jsDate.getMonth() + 1) : (jsDate.getMonth() + 1); // month should be in the form 'MM'.
    var DD = jsDate.getDate() < 10 ? '0' + jsDate.getDate() : jsDate.getDate(); // day should be in the form 'DD'.

    // get the clock-time components of the iso date.
    var hh = jsDate.getHours() < 10 ? '0' + jsDate.getHours() : jsDate.getHours(); // hours should be in the form 'hh'.
    var mm = jsDate.getMinutes() < 10 ? '0' + jsDate.getMinutes() : jsDate.getMinutes(); // minutes should be in the form 'mm'.
    var ss = jsDate.getSeconds() < 10 ? '0' + jsDate.getSeconds() : jsDate.getSeconds(); // seconds should be in the form 'ss'.
    var sss = '000'; //just hardcoded 000 for milliseconds...

    // assemble the iso date from the date and time components.
    var isoDate = YYYY + '-' + MM + '-' + DD + 'T' + // add the date components.
        hh + ':' + mm + ':' + ss + '.' + sss + 'Z'; // add the time components.

    // return the iso-formatted version of the date.
    return isoDate;
}