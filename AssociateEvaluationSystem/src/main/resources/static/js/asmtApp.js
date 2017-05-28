var asmt = angular.module("asmtApp", [ 'ui.bootstrap', 'as.sortable',
		'ngAnimate', 'ui.ace']);

asmt.constant("SITE_URL", {
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


asmt.constant("API_URL", {
    "BASE"      : "/aes",
    "LOGIN"     : "/login",
    "LOGOUT"    : "/logout",
    "AUTH"      : "/security/auth",
    "CANDIDATE" : "/candidate/",
    "RECRUITER" : "/recruiter/",
    "LINK"      : "/link",
    "CANDIDATES": "/candidates"
});


asmt.constant("ROLE", {
    "RECRUITER" : "ROLE_RECRUITER",
    "TRAINER"   : "ROLE_TRAINER",
    "CANDIDATE" : "ROLE_CANDIDATE",
    "ADMIN"		: "ROLE_ADMIN"
});

asmt.config(['$locationProvider', function($locationProvider) {
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

//where user makes decision to submit
function showSubmitAssesmentModal() {
    var submitAssessmentModal = document.getElementById("submitAssessmentModal");
    submitAssessmentModal.style.display = "block";
    $timeout(function () {
        $rootScope.submitAssessment();
    }, 3000);
}

function closeSubmitAssesmentModal() {
    var submitAssessmentModal = document.getElementById("submitAssessmentModal");
    submitAssessmentModal.style.display = "none";
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