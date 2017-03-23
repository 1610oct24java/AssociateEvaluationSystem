app.service("questionBuilderService", function(){
	function questionBuilder() {

		question = null;
		snippet = createQuestionSnippet;
		question = createQuestion;
		format = createFormat;
		category = {};		

		createQuestionSnippet = function(){
			
			snippet.fileType = "";
			snippet.snippetTemplateURL = "";
			snippet.solutionURL = "";
			return snippet;
		}

		createQuestion = function(){
			
			question.questionText = "";
			question.questionFormatID = 0;
			return question;
		}

		createFormat = function(){		    
			format.formatID = 0;
			format.formatName = "";
		}
		
		build = function(){
			return question;
		}
	}
	
	
	
	setFormat = function(name){
	    $http({
	        method: "GET",
	        url: "format"
	    }).then(function (response) {
	        formats = response.data;
	    });
	    
	    angular.foreach(formats, function(value,key){
	    	console.log(key + ': ' + value);
	    	
	    });
	}
	
	setQuestionCategory =  function(name){
		 $http({
		        method: "GET",
		        url: "category"
		    }).then(function (response) {
		        categories = response.data;
		    });
		 angular.foreach(categories, function(value,key){
		    	console.log(key + ': ' + value);
		    	
		    });
	}
	
	setSnippetTemplateURL = function(folderName, fileName) {
		snippet.snippetTemplateURL = folderName + '/' + fileName;
	}
	
	setSolutionURL = function(folderName, fileName) {
		snippet.solutionURL= folderName + '/' + fileName;
	}
	
	setQuestionSnippet = function(fileType){
		
		snippet.fileType = fileType;
		snippet.snippetTemplateURL = snippet.snippetTemplateURL;
		snippet.solutionURL = snippet.solutionURL;
		snippet= snippet;
	}
	
	setQuestion = function(questionText, questionFormatID){
		
		question.questionText = questionText;
		question.questionFormatID = FormatID;
		question = question;
	}
});
