app.service("questionBuilderService", function($http){
	this.questionBuilder = function() {

		this.question = {};
		this.invalid = false;
		
		// required always
		this.question.format = {};
		this.question.questionText = "";
		
		// required if Multiple Choice or Multiple Select
		this.question.option = null;
		
		// required if Drag and Drop
		this.question.dragdrop = null;
		
		// required if Code Snippet
		this.question.snippetTemplates = null;
		
		// optional
		this.question.questionCategory = null;
		this.question.questionTags = null;
		
		this.createMultipleChoiceQuestionBuilder = function (questionText, options){
			this.question.format.formatId = 1;
			this.question.format.formatName = "Multiple Choice";
			this.question.questionText = questionText;
			this.question.option = options;
		}
		
		this.createMultipleSelectQuestionBuilder = function (questionText, options){
			this.question.format.formatId = 2;
			this.question.format.formatName = "Multiple Select";
			this.question.questionText = questionText;
			this.question.option = options;
		}
		
		this.createDragAndDropQuestionBuilder = function (questionText, dragdrop){
			this.question.format.formatId = 3;
			this.question.format.formatName = "Drag and Drop";
			this.question.questionText = questionText;
			this.question.dragdrop = dragdrop;
			
		}
		
		this.createSnippetQuestionBuilder = function (questionText, fileType, templateURL, solutionURL){
			this.question.format.formatId = 4;
			this.question.format.formatName = "Code Snippet";
			this.question.questionText = questionText;
			this.question.snippetTemplates = [{fileType, templateURL, solutionURL}];
		}
		
		this.addQuestionCategory = function(categoryId, name){
			if(this.question.questionCategory === null){
				this.question.questionCategory = [];
			}
			var category = {};
			category.categoryId = categoryId;
			category.name = name;
			this.question.questionCategory.push(category);
		}
		
		this.addQuestionTags = function(questionTags){
			if(this.question.questionTags === null){
				this.question.questionTags = [];
			}
			// TODO: Implement
		}
		
		this.build = function(){
			return this.question;
		}
	}	
});
