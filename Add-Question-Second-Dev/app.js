var app = angular.module("putInQuestion",['ui.router','multipleSelectApp','multipleChoiceApp',]);

var select = angular.module('multipleSelectApp',[]);
var choice = angular.module('multipleChoiceApp',[]);

app.controller('QuestionCtrl', 
	function($scope,$location,$state,$rootScope,questionFactory) {

	$scope.question = {				//question json
			format:$scope.format,
    		questionText: $scope.questionText,
    		category:$scope.categoryList,
    		tags:$scope.tagList,
    		choices:$rootScope.answerList
	};

	$scope.categories = questionFactory.getCategories();
	$scope.tags =		questionFactory.getTags();
	$scope.formats =	questionFactory.getFormats();			

	$scope.format = 		'';		//format of question
	$scope.questionText = 	'';		//text of question	
	$scope.categoryList = 	[]; 	//list of categories
	$scope.tagList = 		[]; 	//list of tags
	$rootScope.optionList = []; 	//list of options for question
	$rootScope.answerList = [];		//list of answer for question

	$scope.go = function go(page){//change page based off of format dropdown

		console.log(page);

		if(page =='')// if no format is chosen then clears the question
		{
			$scope.option = '';
			$scope.tag = '';
			$scope.category = '';
			$scope.questionText = '';

			$state.go('home');
			//debuging
			console.log(page);					
		}
		else{

			
			
			$scope.question 		= {};	//question json
			$scope.categoryList 	= []; 	//list of categories
			$scope.tagList 			= []; 	//list of tags
			$rootScope.optionList 	= []; 	//list of options for question
			$rootScope.answerList 	= [];	//list of answer for question
			$scope.categories 		= questionFactory.getCategories();
			$scope.tags 			= questionFactory.getTags();

			//clear out page selections
			$scope.option 			= '';
			$scope.tag 				= '';
			$scope.category 		= '';
			
			$state.go(page);
			//debuging
			console.log($scope.format);
			console.log(page);
		}
		
		
	};
	

	$scope.addCategory = function(){//add categories to question

		if($scope.category !=null && $scope.category !='')
			if(!$scope.categoryList.includes($scope.category))//checks for duplicates

		{
            $scope.categoryList.push($scope.category);
           	var indexForRemoval = $scope.categories.indexOf($scope.category);//gets index of category to remove
            $scope.categories.splice(indexForRemoval,1);//removes from master list
            $scope.category = '';//clears the checkbox
            
        }
        //debuging
        console.log(!$scope.categoryList.includes($scope.category));      
        console.log($scope.categoryList);
        console.log("/");
        console.log($scope.categories);


	};

	$scope.addTag = function(){//add tags to question

		if($scope.tag !=null && $scope.tag !='')
			if(!$scope.tagList.includes($scope.tag))//checks for duplicates

		{
            $scope.tagList.push($scope.tag);
            var indexForRemoval = $scope.tags.indexOf($scope.tag);
            $scope.tags.splice(indexForRemoval,1);
            $scope.tag = '';//clears the check box
            
        }
        //debuging
       	console.log(!$scope.tagList.includes($scope.tag));
        console.log($scope.tagList);


	};

	$scope.addOption = function(){//add an option to question

		if($scope.option !=null && $scope.option !='')
			if(!$rootScope.optionList.includes($scope.option))//checks for duplicates

		{
            $rootScope.optionList.push($scope.option);
            $rootScope.answerList.push({option:$scope.option, correct:false});//maps the option to the question
            $scope.option = '';
            
        }
        //debuging
       	console.log(!$scope.optionList.includes($scope.option));
        console.log($rootScope.optionList);
        console.log($rootScope.answerList);


	};

	$scope.removeCategory = function(index)//removes category from question
        {
        	
         	var toReturnToMainList = $scope.categoryList.splice(index,1);
         	//debugging
         	console.log(toReturnToMainList);

            $scope.categories.push(toReturnToMainList[0]); //since the splice returns and array, just get index 0      	
    };

    $scope.removeTag = function(index)//removes tag from question
        {

            var toReturnToMainList = $scope.tagList.splice(index,1);
            $scope.tags.push(toReturnToMainList[0]);       	
    };

    $scope.removeOption = function(index)//removes option from question
        {

            $rootScope.optionList.splice(index,1);
            $rootScope.answerList.splice(index,1); 
            console.log($rootScope.answerList);     	
    };

    $scope.clearQuestion = function()//clears all parameters on the question
        {

           	$scope.format 			= '';
			$scope.questionText 	= '';	//text of question
			$scope.question 		= {};	//question json
			$scope.categoryList 	= []; 	//list of categories
			$scope.tagList 			= []; 	//list of tags
			$rootScope.optionList 	= []; 	//list of options for question
			$rootScope.answerList 	= [];	//list of answer for question
			$scope.categories 		= questionFactory.getCategories();
			$scope.tags 			= questionFactory.getTags();

			//clear out page selections
			$scope.option 			= '';
			$scope.tag 				= '';
			$scope.category 		= '';
			$scope.questionText 	= '';

			$scope.go('');
		      	
    };

    //sets answer to multiple select question
    $scope.selectedAnswer = function(index,answer){
    	
    	$rootScope.answerList[index].correct = answer;
    	//debuging
    	console.log(answer);
    	console.log ($rootScope.answerList[index].correct); 
    	console.log ($rootScope.answerList[index]);   	
    };

    //sets answers for multiple choice question
    $scope.setChoiceForQuestion = function (question, selectedCorrect) {
        angular.forEach(question, function (selectedCorrect) {
            selectedCorrect.correct = false;
            console.log($rootScope.answerList);
        });
        
        selectedCorrect.correct = true;
        //debuging
        console.log($rootScope.answerList);

    };

    //save question to db
    $scope.saveQuestion = function (){
    	//debuging
    	console.log($rootScope.answerList);

    	$scope.question.format = $scope.format;
    	$scope.question.questionText = $scope.questionText;
    	$scope.question.category = $scope.categoryList;
    	$scope.question.tags = $scope.tagList;
    	$scope.question.choices = $rootScope.answerList;
    
    	//debuging
    	console.log($scope.question);

    };
 
});


app.config(function($stateProvider,$urlRouterProvider){

	$urlRouterProvider.otherwise('');
	
	$stateProvider
		.state('home', {
			url: '/',
			templateUrl: 'noSelectOptionView.htm',
			controller: 'QuestionCtrl'
		})
		.state('multipleChoice', {
			url: '/multipleChoice',
			templateUrl: 'MultipleChoiceView.htm',
			controller: 'QuestionCtrl'
		})
		.state('multipleSelect', {
			url: '/multipleSelect',
			templateUrl: 'MultipleSelectView.htm',
			controller: 'QuestionCtrl'
		});

});


