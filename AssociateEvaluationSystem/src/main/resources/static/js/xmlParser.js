var app = angular.module('AESCoreApp');
var reader;

app.controller("parserCtrl", function ($scope, $http) {

    var removeHTML = function(str){
        str = str.replace("<p>", "");
        str = str.replace("</p>", "");
        str = str.replace("<br>", "");
        str = str.replace("&nbsp;", " ");
        str = str.replace("</span>", "");
        str = str.replace(new RegExp("\\<span .*;\">"), "");
        str = str.trim();
        return str;
    };

    var getCategory = function(category){
        var out = {};
        if(category.includes("JavaScript")){
            out.categoryId = 12;
            out.name = "JavaScript";
        } else if (category.includes("Java")) {
            out.categoryId = 1;
            out.name ="Java";
        } else if (category.includes("SQL")) {
            out.categoryId = 4;
            out.name = "SQL";
        } else if (category.includes("Critical Thinking")) {
            out.categoryId = 12;
            out.name = "Critical Thinking";
        }

        return out;
    };

    $scope.print = function(){
        var text = reader.result;
        var questions = [];

        //create a XML DOM
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(text,"text/xml");
        var qList = xmlDoc.getElementsByTagName("question");



        for(var i = 1; i < qList.length; i++){
            console.log(qList);
            var question = {};
            var answer;
            var qText;
            var aText;
            var option = [];
            question.format = {};

            question.format.formatId = 1;
            question.format.formatName = "Multiple Choice";

            // if (qList[i].childNodes[3].nodeName == 'questiontext') {
                qText = qList[i].childNodes[3].textContent;
                qText = removeHTML(qText);
                question.questionText = qText;

            for(var j = 0; j < qList[i].childNodes.length; j++){
                if(qList[i].childNodes[j].nodeName == "answer"){
                    answer = {};
                    aText = qList[i].childNodes[j].textContent;
                    aText = removeHTML(aText);
                    answer.optionText = aText;
                    if(parseInt(qList[i].childNodes[j].attributes[0].textContent) > 0){
                        answer.correct = 1;
                    } else {
                        answer.correct = 0;
                    }
                    option.push(answer);
                }
            }

            $http({
                method: 'POST',
                url: "question",
                headers: {'Content-Type': 'application/json'},
                data: question
            }).then(function (response) {
                console.log("finished upload");

                $http.get("questionMax").then(function (response) {
                    question.questionId = response.data;

                    var  category = getCategory(qList[0].textContent);
                    question.questionCategory = [];
                    question.questionCategory.push(category);
                    question.option = option;
                    console.log(question);
                    $http({
                        method: 'PUT',
                        url: "question",
                        headers: {'Content-Type': 'application/json'},
                        data: question
                    }).then(function (response) {
                        console.log("taco");
                    });
                });
            });


            // questions.push(question);
        }
        // console.log(questions);
    };
});

var openFile = function(event){
    var input = event.target;
    reader = new FileReader();
    reader.onload = function(){
        var text = reader.result;
        console.log(reader);
    };
    reader.readAsText(input.files[0]);
};