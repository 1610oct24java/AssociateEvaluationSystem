var tstQuiz2 = {
		assessmentId : 2,
		user : {
			// USER OBJECT
		},
		grade : 0,
		timeLimit : 60,
		createdTimeStamp : {
			//TIMESTAMP OBJECT
		},
		finishedTimeStamp : {
			//TIMESTAMP OBJECT
		},
		template : {
			//TEMPLATE OBJECT
		},
		options : [
				//OPTION OBJECTS
		],
		assessmentDragDrop : [
				//ASSESSMENT DRAG DROP OBJECTS
		],
		fileUpload : [
				//FILE UPLOAD OBJECTS
		],
		snippedTemplate : [
				//SNIPPED TEMPLATE OBJECTS
		]
}

var tstUser = {
		userId : 134,
		firstName : "Steve",
		lastName : "Carlsburg",
		salesforce : 0,
		recruiterId : -1,
		role : {
			// ROLE object
		},
		datePassIssues : "Tuesday"
}
tstQuiz2.user = tstUser;

function template(_templateId, _createTimeStamp, _creator, _templateQuestion) {
	this.templateId = _templateId;
	this.createTimeStamp = _createTimeStamp;
	this.creator = _creator;
	this.templateQuestion = _templateQuestion;
}

function question(_questionId, _questionText, _format, _tags, _category, _multiChoice, _dragDrops, _snippetTemplate) {
	this.questionId = _questionId;
	this.questionText = _questionText;
	this.format = _format;
	this.tags = _tags;
	this.category = _category;
	this.multiChoice = _multiChoice;
	this.dragDrops = _dragDrops;
	this.snippetTemplate = _snippetTemplate;
}

function format(_formatId, _formatName) {
	this.formatId = _formatId;
	this.formatName = _formatName;
}

function option(_optionId, _optionText, _correct, _questionId) {
	this.optionId = _optionId;
	this.optionText = _optionText;
	this.correct = _correct;
	this.questionId = _questionId;
}

function dragDrop(_dragDropId, _dragDropText, _correctOrder, _questionId) {
	this.dragDropId = _dragDropId;
	this.dragDropText = _dragDropText;
	this.correctOrder = _correctOrder;
	this.questionId = _questionId;
}

function snippetTemplate(_snippetTemplateId, _fileType, _templateUrl, _solutionUrl, _questionId){
	this.snippetTemplateId = _snippetTemplateId;
	this.fileType = _fileType;
	this.templateUrl = _templateUrl;
	this.solutionUrl = _solutionUrl;
	this.questionId = _questionId;
}

function templateQuestion(_weight, _templateQuestion, _template) {
	this.weight = _weight;
	this.templateQuestion = _templateQuestion;
	this.template = _template;
}

var template1 = new template(34, {}, {}, []);

for (var i = 0; i < 10*4; i+=4 ) {
	var opt1 = new option((100+i), "Obi-Wan Kenobi", 0, i);
	var opt2 = new option((200+i), "Ahsoka Tano", 1, i);
	var opt3 = new option((300+i), "Luke Skywalker", 0, i);
	var opts = [opt1, opt2, opt3];
	var f1 = new format(0, "Multiple Choice");
	var q1 = new question(i, "Who was Anakin's apprentice?", f1, [], [], opts, [], {});
	var tq1 = new templateQuestion(1, q1, 34);
	template1.templateQuestion.push(tq1);
}

for (var j = 1; j < 10*4; j+=4 ) {
	var opt1 = new option((100+j), "Mace Windu", 0, j);
	var opt2 = new option((200+j), "Plo Koon", 0, j);
	var opt3 = new option((300+j), "Obi-Wan Kenobi", 1, j);
	var opt4 = new option((300+j), "Kit Fisto", 0, j);
	var opt5 = new option((300+j), "Yoda", 1, j);
	var opts = [opt1, opt2, opt3, opt4, opt5];
	var f1 = new format(1, "Multiple Select");
	var q1 = new question(j, "At the end of Revenge of the Sith, which Jedi were confirmed to survive?", f1, [], [], opts, [], {});
	var tq1 = new templateQuestion(1, q1, 34);
	template1.templateQuestion.push(tq1);
}

{
	var dd1 = new dragDrop((102), "The Phantom Menace", 3, 2);
	var dd2 = new dragDrop((202), "Attack of the Clones", 4, 2);
	var dd3 = new dragDrop((302), "Revenge of the Sith", 5, 2);
	var dd4 = new dragDrop((402), "A New Hope", 1, 2);
	var dd5 = new dragDrop((502), "The Empire Strikes Back", 2, 2);
	var dd6 = new dragDrop((602), "Return of the Jedi", 6, 2);
	var dragDrops = [dd1, dd2, dd3, dd4, dd5, dd6];
	var f3 = new format(2, "Drag and Drop");
	var q3 = new question(2, "What is the proper viewing order of these films?", f3, [], [], [], dragDrops, {});
	var tq3 = new templateQuestion(1, q3, 34);
	template1.templateQuestion.push(tq3);
}

{
	var st = new snippetTemplate(103, "java", "/answers.temp", "/solutions.temp", 3);
	var f4 = new format(3, "Coding Snippet");
	var q4 = new question(3, "Coding sample 1", f4, [], [], [], st);
	var tq4 = new templateQuestion(1, q4, 34);
	template1.templateQuestion.push(tq4);
}

{
	var st2 = new snippetTemplate(107, "java", "/answers.temp", "/solutions.temp", 7);
	var f4 = new format(3, "Coding Snippet");
	var q5 = new question(7, "Coding sample 1", f4, [], [], [], st2);
	var tq5 = new templateQuestion(1, q5, 34);
	template1.templateQuestion.push(tq5);
}
tstQuiz2.template = template1;


var tstQuiz = {
	id : 1,
	questions : [
		{
			txtQ : "Who was Anakin Skywalker's apprentice?",
			id : 0,
			type : 0,
			options : [
				{
					txtA : "Luke Skywalker"
				},
				{
					txtA : "Ahsoka Tano"
				},
				{
					txtA : "Obi-Wan Kenobi"
				}
			]
		},
		{
			txtQ : "At the end of Revenge of the Sith, which Jedi are confirmed to have survived Order 66?",
			id : 1,
			type : 1,
			options : [
				{
					txtA : "Obi-Wan Kenobi"
				},
				{
					txtA : "Mace Windu"
				},
				{
					txtA : "The Younglings"
				},
				{
					txtA : "Yoda"
				},
				{
					txtA : "Kit Fisto"
				}
			]
		},
		{
			txtQ : "Who shot first?",
			id : 2,
			type : 0,
			options : [
				{
					txtA : "Han Solo"
				},
				{
					txtA : "Greedo"
				}
			]
		},
		{
			txtQ: "Write a program that prints 'May the Force be with you.' in Java",
			id : 3,
			type : 3,
			options : [
				{
					txtA : "private static void main(String[] args){\n\n}"
				}
			]
		},
		{
			txtQ : "What is the proper viewing order of these films?",
			id: 4,
			type : 2,
			options : [
				{
					txtA : "Empire Strikes Back"
				},
				{
					txtA : "The Phantom Menace"
				},
				{
					txtA : "Return of The Jedi"
				},
				{
					txtA : "Revenge of the Sith"
				},
				{
					txtA : "Attack of the Clones"
				},
				{
					txtA : "A New Hope"
				},
			]
		},
		{
			txtQ : "Test",
			id : 5,
			type : 3,
			options : [
				{
					txtA : "private static void main(String[] pirateNoises){\n\n}"
				}
			]
		},
		{
			txtQ : "Who was Anakin Skywalker's apprentice?",
			id : 6,
			type : 0,
			options : [
				{
					txtA : "Luke Skywalker"
				},
				{
					txtA : "Ahsoka Tano"
				},
				{
					txtA : "Obi-Wan Kenobi"
				}
			]
		},
		{
			txtQ : "At the end of Revenge of the Sith, which Jedi are confirmed to have survived Order 66?",
			id : 7,
			type : 1,
			options : [
				{
					txtA : "Obi-Wan Kenobi"
				},
				{
					txtA : "Mace Windu"
				},
				{
					txtA : "The Younglings"
				},
				{
					txtA : "Yoda"
				},
				{
					txtA : "Kit Fisto"
				}
			]
		},
		{
			txtQ : "Who shot first?",
			id : 8,
			type : 0,
			options : [
				{
					txtA : "Han Solo"
				},
				{
					txtA : "Greedo"
				}
			]
		},
		{
			txtQ: "Write a program that prints 'May the Force be with you.' in Java",
			id : 9,
			type : 3,
			options : [
				{
					txtA : "private static void main(String[] args){\n\n}"
				}
			]
		},
		{
			txtQ : "What is the proper viewing order of these films?",
			id: 10,
			type : 2,
			options : [
				{
					txtA : "Empire Strikes Back"
				},
				{
					txtA : "The Phantom Menace"
				},
				{
					txtA : "Return of The Jedi"
				},
				{
					txtA : "Revenge of the Sith"
				},
				{
					txtA : "Attack of the Clones"
				},
				{
					txtA : "A New Hope"
				},
			]
		},
		{
			txtQ : "Test",
			id : 11,
			type : 3,
			options : [
				{
					txtA : "private static void main(String[] pirateNoises){\n\n}"
				}
			]
		}
	]
};