app.controller('QuestionCtrl', function($http, $scope, $filter) {
    $scope.fList;
    /*
     * var getFormatList = function() { $http.get(url + "format") .then(
     * function(response { formatList = response.data; $scope.fList =
     * formatList; });}
     */
    $scope.getFormatList = function() {
        $http.get("format")
            .then(function(response) {
                $scope.fList = response.data;

            }); // $http end;
    } // getFormatList() end

    angular.element(document).ready(function() {
        $scope.getFormatList();
    }); // angular element end
    // ////////////////////////////////////////////////

    $scope.formatSet = false;
    $scope.questionTextChanged = false;
    $scope.optionTextChanged = false;
    $scope.correctValue = false;
    $scope.addButton = false;
    $scope.updatedQuestion;
    $scope.show = false;
    $scope.qList;
    $scope.deleteme = 0;
    $scope.selected = {};
    $scope.tagList = '';
    $scope.catList = '';
    $scope.options = [];
    $scope.isMultiChoiceOption = false;
    $scope.isMultiSelectOption = false;
    $scope.isDragDrop = false;
    $scope.questionBeingUpdated = '';
    $scope.format = {
        format : 0,
        formatName : ''
    };

    $scope.option = {
        optionId: 0,
        optionText: '',
        correct: -1
    };

    $scope.categoriesInDatabase = null;
    $scope.tagsInDatabase = null;

    $scope.question = {
        question: {
            questionId : 0,
            questionText : '',
            format : {
                formatId : 0,
                formatName : ''
            }
        },
        tags : null,
        category: null,
        multiChoice:null,
        dragDrops:null,
        snippetTemplate:null
    };


    $scope.currentPage = 0;
    $scope.pageSize = 10;
    $scope.q = '';
    $scope.filteredItems;


    // Retrieves the List of Questions from the Database
    $scope.getQuestionList = function() {
        $http.get("question")
            .then(function(response) {
                $scope.qList = response.data;
                return $filter('filter')($scope.qList, $scope.q)
            }); // $http end
    }; // getQuestionList() end

    $scope.getQuestion = function(question){
        $scope.isOption = false;
        $scope.isDragDrop = false;
        if(question.format.formatName == "Drag and Drop"){
            $scope.isDragDrop = true;
            $scope.isMultiSelectOption = false;
            $scope.isMultiChoiceOption = false;
        }
        else if(question.format.formatName == "Multiple Select"){
            $scope.isDragDrop = false;
            $scope.isMultiSelectOption = true;
            $scope.isMultiChoiceOption = false;
        }
        else{
            $scope.isDragDrop = false;
            $scope.isMultiSelectOption = false;
            $scope.isMultiChoiceOption = true;
        }
        $scope.currentQuestion = question;
    }


    // Adds an option to a Question being created
    $scope.addOption = function() {
        if($scope.question.multiChoice == null){
            $scope.question.multiChoice = [];
        }// end if
        if($scope.option.optionText == '' || $scope.option.correct == -1){

        } else {
            $scope.question.multiChoice.push($scope.option);
            $scope.option = {
                optionId: 0,
                optionText: '',
                correct: -1
            };
        }// end if
    };

    // Adds a tag to the Question being updated
    $scope.addTag = function(tagName) {
        if($scope.question.tags == null){
            $scope.question.tags = [];
        }
        // search for tag in tagsInDatabase
        for(var i=0;i<$scope.tagsInDatabase.length;i++){
            if(tagName==$scope.tagsInDatabase[i].tagName){
                $scope.question.tags.push($scope.tagsInDatabase[i]);
                return true;
            }
        }
        // tag not found
        $scope.question.tags=null;
        return false;
    };

    // Adds a category to the Question being updated
    $scope.addCategory = function(categoryName) {
        if($scope.question.category == null){
            $scope.question.category = [];
        }
        // search for category in categoriesInDatabase
        for(var i=0;i<$scope.categoriesInDatabase.length;i++){
            if(categoryName==$scope.categoriesInDatabase[i].name){
                $scope.question.category.push($scope.categoriesInDatabase[i]);
                return true;
            }
        }
        // category not found
        $scope.question.category=null;
        return false;
    };

    // This functions ensures a user populates all the necessary fields for
    // a question.
    $scope.addAddQuestionButton = function(x) {

        switch(x) {
            case 1:
                $scope.formatSet = true;
                break;
            case 2:
                $scope.questionTextChanged = true;
                break;
            case 3:
                $scope.optionTextChanged = true;
                break;
            case 4:
                $scope.correctValue = true;
                break;
            default:
        } // switch end

        if($scope.formatSet === true
            && $scope.questionTextChanged === true
            && $scope.optionTextChanged === true
            && $scope.correctValue === true){
            $scope.addButton = true;
        }
    }; // addAddQuestionButton end

    $scope.resetQuestion = function() {
        $scope.question = {
            question : {
                questionId : 0,
                questionText : '',
                format : {
                    formatId : 0,
                    formatName : ''
                }
            },
            tags : null,
            category: null,
            multiChoice:null,
            dragDrops:null,
            snippetTemplate:null
        }; // $scope.question end
    }; // $scope.requeQuestion() end

    $scope.addQuestion = function() {

        $scope.question.question.format = $scope.type;
        $scope.question.question.questionText =$scope.question.questionText;
        $scope.question.question.questionCategory = [
            {
                categoryId : $scope.category.categoryId
            }
        ];
        $scope.question.question.option = $scope.choiceSet;
        if ($scope.question.question.format.formatId === 0) {
        } else {
            if($scope.question.questionText != ''){
                $http.post("fullQuestion", $scope.question)
                    .success(function(response) {
                        $scope.question.question = response;
                        if ($scope.question.question == null) {
                            $scope.resetQuestion();
                        } else {
                            $scope.getQuestionList();
                            $scope.resetQuestion();
                        }// inner most if end
                    }); // $http end
            } else {
            }// mid if end
        }	// outer if end
    }; // addQuestion() end

    $scope.deleteQuestion = function() {
        $http.delete("/question/" + $scope.deleteme)
            .success(function() {
                $scope.getQuestionList();
            })
            .error(function() {
            }); // $http end
    }; // deleteQuestion end

    $scope.showUpdateQuestion = function(aQuestion) {
        if($scope.question == null){
            $scope.show=false;
        } else {
            $scope.updatedQuestion = aQuestion;
            $scope.show = true;
        }	// if end
    }; // showUpdateQuestion end

    $scope.updateQuestion = function() {
        $scope.question.question.format = $scope.format;
        if ($scope.format.formatId === 0) {

        } else {
            $http.put("question", $scope.currentQuestion)
                .success(function(response) {
                    $scope.question = response.data;
                    if ($scope.currentQuestion == null) {

                    } else {
                        $scope.getQuestionList();
                        $scope.resetQuestion();
                        $scope.show = false;
                    } // inner if end
                }); // $http end
        } // outer if end
    }; // updateQuestion() end

    $scope.addCategories = function() {
        // get categories into an array
        if($scope.catList != null && $scope.catList != ''){
            var selectedCategories = $scope.catList.split(',');
            for (var i=0;i<selectedCategories.length;i++){
                if(!$scope.addCategory(selectedCategories[i])){
                    return;
                }
            }
        }
    };

    $scope.addTags = function() {
        // get tags into an array
        if($scope.tagList != null && $scope.tagList != ''){
            var selectedTags = $scope.tagList.split(',');
            for (var j=0;j<selectedTags.length;j++){
                if(!$scope.addTag(selectedTags[j])){
                    return;
                }
            }
        }
    };

    $scope.addCategoriesAndTags = function() {
        // question must be selected
        if($scope.questionBeingUpdated ===''){
            return;
        }
        // a tag or category must be provided
        if($scope.tagList ==='' && $scope.catList ===''){
            return;
        }
        // initialize question
        $scope.question = $scope.qList[$scope.questionBeingUpdated-1];

        $scope.addCategories();
        $scope.addTags();

        // save the question
        $http.put(url + "question", $scope.question)
            .success(function(response) {
                if (response == null) {
                } else {
                    $scope.getQuestionList();
                    $scope.resetQuestion();
                    $scope.show = false;
                } // inner if end
            }); // $http end
    }; // addCategoriesAndTags() end

    // Load categories from database so that they can be added to questions
    $scope.loadCategories = function() {
        $http.get("category")
            .then(function(response) {
                $scope.categoriesInDatabase = response.data;
            });
    };

    $scope.loadTags = function() {
        $http.get("tag")
            .then(function(response) {
                $scope.tagsInDatabase = response.data;
            })
    };

    $scope.removeOption = function(option){
        $http.post("question/refresh/" + option.optionId, $scope.currentQuestion.questionId)
            .then(function(response){
                $scope.currentQuestion = response.data;
                $scope.getQuestion($scope.currentQuestion);
                var length = $scope.qList.length;
                for(var i=0;i<length;i++){
                    if($scope.currentQuestion.questionId == $scope.qList[i].questionId){
                        $scope.qList[i]=$scope.currentQuestion;
                        break;
                    }
                }
            })
    }

    $scope.removeDDOption = function(dragdrop){
        $http.post("question/deleteDragDrop/" + dragdrop.dragDropId, $scope.currentQuestion.questionId)
            .then(function(response){
                $scope.currentQuestion = response.data;
                $scope.getQuestion($scope.currentQuestion)
                var length = $scope.qList.length;
                for(var i=0;i<length;i++){
                    if($scope.currentQuestion.questionId == $scope.qList[i].questionId){
                        $scope.qList[i]=$scope.currentQuestion;
                        break;
                    }
                }
            })
    }

    $scope.addOption = function(newOption){
        newOption = {"optionText": newOption, "correct" : -1}
        if(!newOption && newOption != ""){
            $http.post('question/addOption/'+ $scope.currentQuestion.questionId,JSON.stringify(newOption)).then(function(response){
                window.response = response
                console.log(response)
                $scope.currentQuestion = response.data;
                $scope.getQuestion($scope.currentQuestion)
                var length = $scope.qList.length;
                for(var i=0;i<length;i++){
                    if($scope.currentQuestion.questionId == $scope.qList[i].questionId){
                        $scope.qList[i]=$scope.currentQuestion;
                        $scope.newOption = "";
                        break;
                    }
                }
            })
        }
    }
    $scope.addDragDrop = function(newDragDrop){
        if(newDragDrop != null && newDragDrop != ""){
            $http.post("question/addDragDrop/" + $scope.currentQuestion.questionId, newDragDrop)
                .then(function(response){
                    $scope.currentQuestion = response.data;
                    $scope.getQuestion($scope.currentQuestion)
                    var length = $scope.qList.length;
                    for(var i=0;i<length;i++){
                        if($scope.currentQuestion.questionId == $scope.qList[i].questionId){
                            $scope.qList[i]=$scope.currentQuestion;
                            $scope.newDragDrop = "";
                            break;
                        }
                    }
                })
        }
    }

    $scope.checkButton = function(option){
        if (option.correct == 1) {
            return true;
        }

        return false;
    }

    /*
     * Here I am attempting to call the rest controller where I update the
     * options with the new correct answer.
     */
    $scope.multiCorrect = function(option){
        if(document.getElementById("msrad").checked == false){
            $http.post("question/mcCorrect/" + $scope.currentQuestion.questionId, option.optionId)
                .then(function(response){
                    $scope.currentQuestion = response.data;
                    $scope.getQuestion($scope.currentQuestion)
                    var length = $scope.qList.length;
                    for(var i=0;i<length;i++){
                        if($scope.currentQuestion.questionId == $scope.qList[i].questionId){
                            $scope.qList[i]=$scope.currentQuestion;
                            break;
                        }
                    }
                });
            document.getElementById("msrad").checked == true;
        }
    }
    $scope.markCorrect = function(option) {
        $scope.choiceSet.forEach((choice) => choice.correct = 0);
        option.correct = 1;

        if(document.getElementById("msrad").checked == false)
        {
        	document.getElementById("msrad").checked = true;
        }
    }
    $scope.setCat =function (category) {
        $scope.category = category;

    }

    $scope.setType = function (type){
        $scope.format = type;
    }

    $http({
        method: "GET",
        url: "category"
    }).then(function (response) {
        $scope.categories = response.data;
    });

    $http({
        method: "GET",
        url: "rest/format"
    }).then(function (response) {
        $scope.formats = response.data;
    });
    $scope.multiSelectCorrect = function(option){
        $scope.optionCorrectChanger(option)

    }

    $scope.choiceSet = [];
    $scope.quest = {};

    $scope.choiceSet.choices = [];
    $scope.addNewChoice = function () {
        $scope.choiceSet.push({
            optionText: '',
            correct: 0
        });
    };

    $scope.removeChoice = function (z) {
        //var lastItem = $scope.choiceSet.choices.length - 1;
        $scope.choiceSet.choices.splice(z,1);
    };

    $scope.optionCorrectChanger = function(option){
        $http.post("question/changeCorrect/" + option.optionId)
            .then(function(response){
                $scope.currentQuestion = response.data;
                $scope.getQuestion($scope.currentQuestion)
                var length = $scope.qList.length;
                for(var i=0;i<length;i++){
                    if($scope.currentQuestion.questionId == $scope.qList[i].questionId){
                        $scope.qList[i]=$scope.currentQuestion;
                        break;
                    }
                }
            })
    }

    $scope.formatNumber = function(number){
        return Math.ceil(number);
    }

    $scope.numberOfPages = function(){
        return Math.ceil($scope.qList.length/$scope.pageSize);
    }

    $scope.currentPageOverLimit = function(){
        if($scope.currentPage > $scope.formatNumber($scope.filteredItems.length/$scope.pageSize) - 1)
        {
            $scope.currentPage = $scope.formatNumber($scope.filteredItems.length/$scope.pageSize - 1);
        }
    }

    $scope.catSearch;
    $scope.exactMatch = false;
    $scope.catHasChanged = function(){
        if($scope.catSearch === ''){
            $scope.exactMatch = false;
        }
        else{
            $scope.exactMatch = true;
        }
    };

    $scope.showCurrPage= function(){
        if($scope.currentPage === -1){
            $scope.currentPage = 0;
        }

        if($scope.filteredItems.length === 0){
            return "1/1";
        }

        else if($scope.q == '' && $scope.catSearch == ''){
            return $scope.currentPage + 1 + "/" + $scope.numberOfPages();
        }
        else{
            return $scope.currentPage + 1 + "/" + ($scope.formatNumber($scope.filteredItems.length/$scope.pageSize));
        }
    };

    var pr = document.querySelector( '.paginate.left' );
    var pl = document.querySelector( '.paginate.right' );

    angular.element(document).ready(function() {
        $scope.getQuestionList();
        $scope.loadCategories();
        $scope.loadTags();
    }); // angular.element end

}); // QuestionController end
