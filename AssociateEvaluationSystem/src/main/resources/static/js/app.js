var app = angular.module('AESCoreApp',[]);

app.constant("SITE_URL", {
	"HTTP" : "http://",
	"HTTPS": "https://",
	"BASE" : "",
	"PORT" : ":8080",
	
	"LOGIN": "index",
	"VIEW_CANDIDATES" : "view",
	"REGISTER_CANDIDATE" : "",
	"VIEW_EMPLOYEES" : "viewEmployees",

	"TRAINER_HOME" : "http://sports.cbsimg.net/images/nhl/blog/Ryan_Reaves_Kiss.JPG"
});

app.constant("API_URL", {
	"BASE"      : "/aes",
	"LOGIN"     : "/login",
	"LOGOUT"    : "/logout",
	"AUTH"      : "/security/auth",
	"CANDIDATE" : "/candidate/",
	"RECRUITER" : "/recruiter/",
	"LINK"      : "/link",
	"CANDIDATES": "/candidates"
});

app.constant("ROLE", {
	"RECRUITER" : "ROLE_RECRUITER",
	"TRAINER"   : "ROLE_TRAINER",
	"CANDIDATE" : "ROLE_CANDIDATE",
	"ADMIN"		: "ROLE_ADMIN"
});


