var app = angular.module('AESCoreApp');
app.controller("parserCtrl", function ($scope, $http) {
    var pCtrl = this;
    $scope.reader;

    $http.get("category").then(function(response) {
            $scope.cat = response.data;
    });

    pCtrl.openFile = function(event){
        var input = event.target;
        $scope.reader = new FileReader();
        $scope.reader.onload = function(){
            var text = reader.result;
            console.log($scope.reader);
        };
        $scope.reader.readAsText(input.files[0]);
    };

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
        if(category.includes("JavaScript")){
            return "JavaScript";
        } else if (category.includes("Java")) {
            return "Java";
        } else if (category.includes("SQL")) {
            return "SQL";
        } else if (category.includes("Critical Thinking")) {
            return "Critical Thinking";
        }
    };


    pCtrl.print = function(){
        var text = $scope.reader.result;
        var questions = [];
        var parser = new DOMParser();
        var xmlDoc = parser.parseFromString(text,"text/xml");
        var qList = xmlDoc.getElementsByTagName("question");
        var  category = {};
        category.name = getCategory(qList[0].textContent);

        for(var i = 1; i < qList.length; i++){
            var question = {};
            var answer;
            var qText;
            var aText;
            question.answers = [];

            question.questionCategory = [];
            question.format = {};
            question.questionCategory.push(category);
            question.format.formatName = "MultipleChoice";

            for(var j = 0; j < qList[i].childNodes.length; j++){
                if(qList[i].childNodes[j].nodeName == 'questiontext'){
                    qText = qList[i].childNodes[j].textContent;
                    qText = removeHTML(qText);
                    question.questionText = qText;
                }

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
                    question.answers.push(answer);
                }
            }
            questions.push(question);
        }
        console.log(questions);
    }
});