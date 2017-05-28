var adminApp = angular.module('adminApp',['ngMaterial', 'ngMessages', 'ngRoute']);

adminApp.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "",
	"PORT" : ":8080",

	"LOGIN": "index",
    "VIEW_EMPLOYEES" : "viewEmployees",
    "VIEW_CANDIDATES" : "view",
    "REGISTER_EMPLOYEE" : "",
	"ADMIN_DASHBOARD" : "adminDashboard",
    "ASSESSMENTS":"/chooseAssessment"
});

adminApp.constant("API_URL", {
	"BASE"      : "/aes",
	"LOGIN"     : "/login",
	"LOGOUT"    : "/logout",
	"AUTH"      : "/security/auth",
	"CANDIDATE" : "/candidate/",
	"RECRUITER" : "/recruiter",
	"TRAINER"	: "/trainer",
	"LINK"      : "/link",
	"EMPLOYEE"	: "/employee",
	"EMPLOYEES" : "/employees",
	"ADMIN"		: "/admin"
});

adminApp.constant("ROLE", {
	"RECRUITER" : "ROLE_RECRUITER",
	"TRAINER"   : "ROLE_TRAINER",
	"CANDIDATE" : "ROLE_CANDIDATE",
	"ADMIN"		: "ROLE_ADMIN"
});

adminApp.config(function($mdThemingProvider) {

    var revOrangeMap = $mdThemingProvider.extendPalette("deep-orange", {
        "A200": "#FB8C00",
        "100": "rgba(89, 116, 130, 0.2)"
    });

    var revBlueMap = $mdThemingProvider.extendPalette("blue-grey", {
        "500": "#37474F",
        "800": "#3E5360"
    });

    $mdThemingProvider.definePalette("revOrange", revOrangeMap);
    $mdThemingProvider.definePalette("revBlue", revBlueMap);

    $mdThemingProvider.theme("success-toast");
    $mdThemingProvider.theme("fail-toast");

    $mdThemingProvider.theme("default")
        .primaryPalette("revBlue")
        .accentPalette("revOrange");
});

adminApp.directive('stringToNumber', function() {
	  return {
		    require: 'ngModel',
		    link: function(scope, element, attrs, ngModel) {
		      ngModel.$parsers.push(function(value) {
		        return '' + value;
		      });
		      ngModel.$formatters.push(function(value) {
		        return parseFloat(value);
		      });
		    }
		  };
		});

function makeUser($scope) {
    var user = {
        username: $scope.username,
        password: $scope.password
    };

    $scope.user = user;
}

//directive used to allow only nubmer inputs for text inputs. 
adminApp.directive('customValidation', function(){
	   return {
	     require: 'ngModel',
	     link: function(scope, element, attrs, ChooseAssessmentCtrl) {

	    	 ChooseAssessmentCtrl.$parsers.push(function (inputValue) {  	
	    	
	         var transformedInput = inputValue.replace(/[^0-9]/g, ''); 

	         if (transformedInput!=inputValue) {
	        	 ChooseAssessmentCtrl.$setViewValue(transformedInput);
	        	 ChooseAssessmentCtrl.$render();
	         }         

	         return transformedInput;         
	       });
	     }
	   };
	});

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
