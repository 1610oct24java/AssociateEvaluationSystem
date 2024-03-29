var app = angular.module("quizApp", [ 'ui.bootstrap', 'as.sortable',
		'ngAnimate', 'ui.ace']);

app.config(['$locationProvider', function($locationProvider) {
    $locationProvider.html5Mode(true);
}]);

var QUIZ_REST_URL = "/aes/rest/";
var QUIZ_SUBMIT_REST_URL = "/aes/rest/submitAssessment";

/* Set the width of the side navigation to 250px and the right margin of the page content to 250px */
function openSideNav() {
    document.getElementById("sidenav").style.width = "250px";
    document.getElementById("page-container").style.marginRight = "250px";
}

/* Set the width of the side navigation to 0 and the right margin of the page content to 0 */
function closeSideNav() {
    document.getElementById("sidenav").style.width = "0";
    document.getElementById("page-container").style.marginRight = "0";
}

function openAboutNav(){
	closeSideNav();
	document.getElementById("aboutnav").style.width = "250px";
	document.getElementById("page-container").style.marginRight = "250px";
}

function closeAboutNav(){
	openSideNav();
	document.getElementById("aboutnav").style.width = "0";
	document.getElementById("page-container").style.marginRight = "0";
}

function openHelpNav(){
	closeSideNav();
	document.getElementById("helpnav").style.width = "250px";
	document.getElementById("page-container").style.marginRight = "250px";
}

function closeHelpNav(){
	openSideNav();
	document.getElementById("helpnav").style.width = "0";
	document.getElementById("page-container").style.marginRight = "0";
}