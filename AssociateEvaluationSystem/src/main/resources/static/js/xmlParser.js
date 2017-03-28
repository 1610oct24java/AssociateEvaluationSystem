//var app = angular.module('adminApp', ['ngMaterial', 'ngMessages']);
var reader;

angular.module('adminApp').controller("parserCtrl", function ($scope, $http, SITE_URL, API_URL) {

    var removeHTML = function(str){
        var ogStr = str;
        str = str.replace("<p>", "");
        str = str.replace("</p>", "");
        str = str.replace("<br>", "");
        str = str.replace("<em>", "");
        str = str.replace("</em>", "");
        str = str.replace("&nbsp;", " ");
        str = str.replace("</span>", "");
        str = str.replace("</code>", "");
        str = str.replace("</pre>", "");
        str = str.replace(new RegExp("<span .*;\">"), "");
        str = str.replace(new RegExp("<p .*;\">"), "");
        str = str.replace(new RegExp("\\<pre style=\"margin: 0px; .*;\">"), " ");
        str = str.replace(new RegExp("<pre style=\"margin: 0px 0px .*;\">"), " ");
        //makes recursive calls
        if(ogStr != str){
            str = removeHTML(str);
        }

        str = str.trim();
        return str;
    };

    //return an array of category objects(just one at the moment)
    var getCategory = function(category){
        var out = {};
        var name;
        var cat = [];
        if(category.includes("JavaScript")){ //must go before Java
            name = "JavaScript";
        } else if (category.includes("Collections")) { //must go before Java
            name ="Data Structures";
        } else if (category.includes("Java")) {
            name ="Java";
        } else if (category.includes("SQL")) {
            name = "SQL";
        } else if (category.includes("Critical Thinking")) {
            name = "Critical Thinking";
        } else if (category.includes(".NET")){
            name = "C#";
        }
        angular.forEach($scope.categories,function (category) {
            if(category.name == name){
                out = category;
            }
        });
        cat.push(out);
        return cat;
    };

    //returns a format object
    var getFormat = function (multiChoice, hasSingle) {
        var out = {};
        var name;
        if(hasSingle){
            if(multiChoice == "true"){
                name = "Multiple Choice";
            } else {
                name = "Multiple Select";
            }
        } else {
            name = "Multiple Choice";
        }
        angular.forEach($scope.formats,function (format) {
            if(format.formatName == name){
                out = format;
            }
        });
        return out;
    };

    //handles error(throws error if file was not selected)
    var throwError = function (variable, message) {
        if(variable == undefined){
            $scope.error = true;
            $scope.messageError = message;
            return true;
        }
        $scope.error = false;
        return false;
    };

    $scope.extractXml = function(){
        if(throwError(reader, "Please select a file")){
            return;
        }
        $scope.questions = [];

        //create a XML DOM
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(reader.result, "text/xml");
        var qList = xmlDoc.getElementsByTagName("question");
        var category = getCategory(qList[0].textContent);

        for(var i = 1; i < qList.length; i++){ //questions start at 1 in the qList
            var question = {};
            question.option = [];

            question.questionText = removeHTML(qList[i].childNodes[3].textContent); //set string question text
            question.questionCategory = category; //set the category for each question

            //sets question format
            if(qList[i].attributes[0].textContent == "multichoice"){
                question.format = getFormat(qList[i].childNodes[13].textContent, true);
            } else if(qList[i].attributes[0].textContent == "truefalse"){
                question.format = getFormat("", false);
            } else{
                continue;
            }

            for(var j = 0; j < qList[i].childNodes.length; j++){
                if(qList[i].childNodes[j].nodeName == "answer"){
                    var answer = {};
                    answer.optionText = removeHTML(qList[i].childNodes[j].childNodes[1].textContent);
                    if(parseInt(qList[i].childNodes[j].attributes[0].textContent) > 0){
                        answer.correct = 1;
                    } else {
                        answer.correct = 0;
                    }
                    question.option.push(answer);
                }
            }
            $scope.questions.push(question);
        }
    };

    $scope.createQuestions = function () {
        $http({
            method: "POST",
            url: "questions",
            data: $scope.questions
        }).then(function (resp) {
            console.log(resp);
        })
    };

    //initialize data
    $http({
        method: "GET",
        url: "category"
    }).then(function (response) {
        $scope.categories = response.data;
    });

    $http({
        method: "GET",
        url: "format"
    }).then(function (response) {
        $scope.formats = response.data;
    });

    //data
    $scope.questions = [];
    $scope.options = [];
    $scope.error;
    $scope.messageError;

    $scope.logout = function() {
        $http.post(SITE_URL.BASE + API_URL.BASE + API_URL.LOGOUT)
            .then(function(response) {
                window.location = SITE_URL.LOGIN;
            });
    };

});


var openFile = function(event){
    var input = event.target;
    reader = new FileReader();
    reader.onload = function(){
        // var text = reader.result;
        // console.log(reader);
    };
    reader.readAsText(input.files[0]);
};

adminApp.controller("menuCtrl", function($scope, $location, $timeout, $mdSidenav, $log) {
    var mc = this;

    // functions
    // sets navbar to current page even on refresh
    mc.findCurrentPage = function() {

        // var path = $location.path().replace("/", "");
        var path = window.location.pathname.substr(1);

        switch(path) {
            case "aes/registerEmployee" : return "employees";
            case "aes/updateEmployee" : return "employees";
            case "aes/createAssessment" : return "assessments";
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

    $mdThemingProvider.theme("default")
        .primaryPalette("revBlue")
        .accentPalette("revOrange");
});
