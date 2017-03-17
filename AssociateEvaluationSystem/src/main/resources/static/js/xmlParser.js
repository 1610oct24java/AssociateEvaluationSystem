var app = angular.module('AESCoreApp');
var reader;

app.controller("parserCtrl", function ($scope, $http) {

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