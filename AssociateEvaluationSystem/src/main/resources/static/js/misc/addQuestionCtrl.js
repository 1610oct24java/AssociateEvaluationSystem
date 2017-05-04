angular
		.module('bankApp')
		.controller(
				'AddQuestionCtrl',
				function($http, $scope) {

					// storage for GET requests
					$scope.categories = null;
					$scope.formats = null;
					$scope.tags = null;

					// storage for selected value(s)
					// $scope.category;
					// $scope.type;
					// $scope.tag;
					// $scope.question;
					// $scope.answers;

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

					$scope.test = 0;

					// rest call to retrieve categories
					$scope.getCategories = function() {
						$http.get("category").then(function(response) {
							$scope.categories = response.data;
						})
					}

					// rest call to retrieve types/formats
					$scope.getTypes = function() {
						$http.get("rest/format").then(function(response) {
							$scope.formats = response.data;
						})
					}

					// rest call to retrieve tags
					$scope.getTags = function() {
						$http.get("tag").then(function(response) {
							$scope.tags = response.data;
						});
					}

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

					$scope.addAnswer = function() {

					};

					$scope.removeAnswer = function() {

					}

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
					}

					$scope.get = function() {
						console.log($scope.question);
					}

					// $scope.inc = function() {
					// return $scope.test++;
					// }

					$scope.getCategories();
				});