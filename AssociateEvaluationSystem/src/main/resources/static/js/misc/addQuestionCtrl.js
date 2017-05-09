angular
		.module('bankApp')
		.controller(
				'AddQuestionCtrl',
				function($http, $scope) {

					// storage for GET requests
					$scope.categories = null;
					$scope.formats = null;
					$scope.tags = null;

					// for inserting answers, if applicable
					$scope.answers = [];
					$scope.cs = false;
					$scope.dnd = false;
					$scope.mc = false;
					$scope.ms = false;

					// for tracking what type of alert should be showing
					$scope.csAlert = false;
					$scope.dndAlert = false;
					$scope.mcAlert = false;
					$scope.msAlert = false;

					// alert functions to display or hide alerts
					$scope.csAlertShow = function() {
						if ($scope.csAlert == false) {
							$scope.csAlert = true;
						} else {
							$scope.csAlert = false;
						}
					};
					$scope.dndAlertShow = function() {
						if ($scope.dndAlert == false) {
							$scope.dndAlert = true;
						} else {
							$scope.dndAlert = false;
						}
					};
					$scope.mcAlertShow = function() {
						if ($scope.mcAlert == false) {
							$scope.mcAlert = true;
						} else {
							$scope.mcAlert = false;
						}
					};
					$scope.msAlertShow = function() {
						if ($scope.msAlert == false) {
							$scope.msAlert = true;
						} else {
							$scope.msAlert = false;
						}
					};

					// information to be displayed for the tags
					$scope.tagAlert = false;
					$scope.tagAlertShow = function() {
						if ($scope.tagAlert == false) {
							$scope.tagAlert = true;
						} else {
							$scope.tagAlert = false;
						}
					}

					// question format
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
						category : null,
						multiChoice : null,
						dragDrops : null,
						snippetTemplate : null
					};

					// determines if inputs are disabled or not
					$scope.cDis = true;
					$scope.tDis = true;

					// performs a switch statement to determine answer format
					$scope.checkType = function() {
						switch ($scope.question.question.format.formatId) {
						case ('1'):
							// multiple choice
							$scope.setTypeMultChoice();
							break;
						case ('2'):
							// multiple select
							$scope.setTypeMultSelect();
							break;
						case ('3'):
							// drag and drop
							$scope.setTypeDragDrop();
							break;
						case ('4'):
							// code snippet
							$scope.setTypeCodeSnip();
							break;
						default:
							console.warn('Could not find matching type in $scope.checkType().');
							$scope.typeReset();
							break;
						}
					};

					// removes a single answer from the answer array
					$scope.removeOneAnswer = function() {
						$scope.answers.pop();
					};

					// sets up multiple choice answers
					$scope.setTypeMultChoice = function() {
						$scope.answers = [];
						for (var x = 0; x < 4; x++) {
							$scope.answers.push({
								'optionId' : x,
								'optionText' : '',
								'correct' : false
							});
						}

						$scope.cs = false;
						$scope.dnd = false;
						$scope.mc = true;
						$scope.ms = false;
					};

					// adds a single answer option to multiple choice
					$scope.addOneMultChoice = function() {
						var x = $scope.answers.length;
						$scope.answers.push({
							'optionId' : x,
							'optionText' : '',
							'correct' : false
						});
					};

					// sets up multiple select answers
					$scope.setTypeMultSelect = function() {
						$scope.answers = [];
						for (var x = 0; x < 4; x++) {
							$scope.answers.push({
								'optionId' : x,
								'optionText' : '',
								'correct' : false
							});
						}

						$scope.cs = false;
						$scope.dnd = false;
						$scope.mc = false;
						$scope.ms = true;
					};

					// adds a single answer option for multiple select questions
					$scope.addOneMultSelect = function() {
						var x = $scope.answers.length;
						$scope.answers.push({
							'optionId' : x,
							'optionText' : '',
							'correct' : false
						});
					};

					// sets up the drag and drop answers
					$scope.setTypeDragDrop = function() {
						$scope.answers = [];
						for (var x = 0; x < 4; x++) {
							$scope.answers.push({
								'dragDropId' : x,
								'dragDropText' : '',
								'correctOrder' : (x + 1)
							});
						}

						$scope.cs = false;
						$scope.dnd = true;
						$scope.mc = false;
						$scope.ms = false;
					};

					// adds a single answer for the drag and drop options
					$scope.addOneDragDrop = function() {
						var x = $scope.answers.length;
						$scope.answers.push({
							'dragDropId' : x,
							'dragDropText' : '',
							'correctOrder' : (x + 1)
						});
					};

					// sets up the code snippet to have no answer options
					$scope.setTypeCodeSnip = function() {
						$scope.answers = null;

						$scope.cs = false;
						$scope.dnd = true;
						$scope.mc = false;
						$scope.ms = false;
					};

					// resets all answers and question types
					$scope.typeReset = function() {
						$scope.answers = null;

						$scope.cs = false;
						$scope.dnd = false;
						$scope.mc = false;
						$scope.ms = false;
					}

					// rest call to retrieve categories
					$scope.getCategories = function() {
						$http.get("category").then(function(response) {
							$scope.categories = response.data;
						})
					};

					// rest call to retrieve types/formats
					$scope.getTypes = function() {
						$http.get("rest/format").then(function(response) {
							$scope.formats = response.data;
						})
					};

					// rest call to retrieve tags
					$scope.getTags = function() {
						$http.get("tag").then(function(response) {
							$scope.tags = response.data;
							$scope.checkType();
						});
					};

					// for selecting a category
					$scope.cSelect = function() {
						$scope.cDis = false;
					};

					// for selecting a type
					$scope.tSelect = function() {
						$scope.tDis = false;
						$scope.formats
								.forEach(function(format) {
									if (format.formatId == $scope.question.question.format.formatId) {
										$scope.question.question.format.formatName = format.formatName;
									}
								});
					};

					// adds a single answer determined by a switch case
					$scope.addAnswer = function() {
						switch ($scope.question.question.format.formatId) {
						case ('1'):
							// multiple choice
							$scope.addOneMultChoice();
							break;
						case ('2'):
							// multiple select
							$scope.addOneMultSelect();
							break;
						case ('3'):
							// drag and drop
							$scope.addOneDragDrop();
							break;
						case ('4'):
							// code snippet
							console
									.log('You should not be able to add an answer in a code snippet');
							break;
						default:
							console.warn('Could not find matching type in $scope.addAnswer().');
							break;
						}
					};

					// removes a single answer option from the answers array,
					// but only if there are more than 2 options
					$scope.removeAnswer = function() {
						if ($scope.answers.length > 2) {
							$scope.removeOneAnswer();
						} else {
							console.log('Cannot have 2 or fewer answers.');
						}
					};

					// returns the question to an empty state for a new
					// submission
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
							category : null,
							multiChoice : null,
							dragDrops : null,
							snippetTemplate : null
						};
					};
					
					// switch function to determine if the submit question button is disabled or not
					$scope.submitDisabled = function() {
						switch ($scope.question.question.format.formatId) {
						case ('1'):
							// multiple choice
							
							break;
						case ('2'):
							// multiple select
							
							break;
						case ('3'):
							// drag and drop
							
							break;
						case ('4'):
							// code snippet
							
							break;
						default:
							console.warn('Could not find matching type in $scope.submitDisabled().');
							break;
						}
					}

					// for testing the question object
					$scope.get = function() {
						console.log($scope.question);
					};

					// for testing the answer options
					$scope.getAnswers = function() {
						console.log($scope.answers);
					}

					// gets the categories on completion of angular statements
					$scope.getCategories();
				});