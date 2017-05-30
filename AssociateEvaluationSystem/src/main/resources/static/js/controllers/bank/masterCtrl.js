/**
 * @class AES.bankApp.MasterCtrl
 */

app.controller('MasterCtrl', [
		'$scope',
		'$rootScope',
		'$log',
		'$state',
		'$http',
		'Upload',
		'questionBuilderService',
		function($scope, $rootScope, $log, $state, $http, Upload,
				questionBuilderService) {

			$scope.templateName = "";
			$scope.snippetError = false;
			$scope.submittingSnippet = false;
			$scope.snippetFileSuccess = false;
			$scope.snippetTextSuccess = false;
			$scope.busy = true;
			$scope.ready = false;
			$scope.files = [];

			$scope.addCategories = function() {
				$state.go("category")
				$scope.templateName = "Add Categories";
			};

			$scope.addTags = function() {
				$state.go("tag");
				$scope.templateName = "Add Tags";
			};

			$scope.modifyQuestions = function() {
				$state.go("question");
				$scope.templateName = "Modify Questions";
			};

			$scope.uploadAssessment = function() {
				$state.go("upload");
				$scope.templateName = "Upload Assessment";
			};
			$scope.addQuestion = function() {
				$state.go("fullQuestion");
				$scope.templateName = "Add Question";
			};

			$scope.uploadFile = function() {
				// Start the progressbar
				$scope.progressbar.start;
				// File uploaded by the user
				var file = $scope.myFile;
				// URL to Spring Controller
				var uploadUrl = "/aes/parseAiken";
				fileUpload.uploadFileToUrl(file, uploadUrl).then(
						$scope.completeProgressbar);
			};

			$scope.$watch('files', function() {
				angular.forEach($scope.files, function(file) {
					var fileArr = [ file ];
					$scope.upload(fileArr, file.type);
				})

			});

			$scope.submitSnippetFiles = function(isValid) {
				if (!isValid) {
					return;
				}

				$scope.submittingSnippet = true;

				// Upload the files to the S3 container
				$scope.files.push($scope.snippetTemplateFile);
				$scope.files.push($scope.snippetSolutionFile);
				var folderNames = [ 'SnippetTemplates', 'SnippetSolutions' ]
				$scope.upload($scope.files, [ 'SnippetTemplates',
						'SnippetSolutions' ]);

				// Upload question complete with URLs to the database
				var questionText = $scope.questionText;
				var snippetTemplateUrl = folderNames[0] + '/'
						+ $scope.snippetTemplateFile.name;
				var snippetSolutionUrl = folderNames[1] + '/'
						+ $scope.snippetSolutionFile.name;
				var fileType = $scope.fileType;
				var builder = new questionBuilderService.questionBuilder();
				builder.createSnippetQuestionBuilder(questionText, fileType,
						snippetTemplateUrl, snippetSolutionUrl);
				builder.addQuestionCategory(1, $scope.textSnippetFileType);
				question = builder.build();

				questionJSON = JSON.stringify(question);
				$http({
					method : "POST",
					url : "question",
					data : questionJSON
				}).then(function(response) { // success
					$scope.submittingSnippet = false;
					$scope.snippetFileSuccess = true;
				}, function(reason) { // error
					$scope.snippetError = true;
				});

			}

			$scope.submitSnippetText = function(isValid) {
				if (!isValid) {
					return;
				}

				$scope.submittingSnippet = true;

				var folderNames = [ 'SnippetTemplates', 'SnippetSolutions' ]
				var snippetTemplateUrl = folderNames[0] + '/'
						+ $scope.textSnippetFileName + 'Template.'
						+ $scope.textSnippetFileType;
				var snippetSolutionUrl = folderNames[1] + '/'
						+ $scope.textSnippetFileName + 'Solution.'
						+ $scope.textSnippetFileType;

				var builder = new questionBuilderService.questionBuilder();
				builder.createSnippetQuestionBuilder(
						$scope.textSnippetQuestionText,
						$scope.textSnippetFileType, snippetTemplateUrl,
						snippetSolutionUrl);
				builder.addQuestionCategory(1, $scope.textSnippetFileType);
				question = builder.build();

				// Upload template to the S3 container

				$http(
						{
							method : "POST",
							url : "rest/s3uploadTextAsFile/"
									+ question.snippetTemplates[0].templateUrl,
							data : $scope.textSnippetTemplate
						}).then(function(response) { // success
				}, function(reason) { // error
					$scope.snippetError = true;
				});

				// Upload solution to the S3 container
				$http(
						{
							method : "POST",
							url : "rest/s3uploadTextAsFile/"
									+ question.snippetTemplates[0].solutionUrl,
							data : $scope.textSnippetSolution
						}).then(function(response) { // success
				}, function(reason) { // error
					$scope.snippetError = true;
				});

				// Update database with question, complete with URLs
				questionJSON = JSON.stringify(question);
				$http({
					method : "POST",
					url : "question",
					data : questionJSON
				}).then(function(response) { // success
					$scope.submittingSnippet = false;
					$scope.snippetTextSuccess = !$scope.snippetError;
				}, function(reason) { // error
					$scope.snippetError = true;
				});

			}

			$scope.progressUpdater = function(evt) {
				return parseInt(100.0 * evt.loaded / evt.total);
			};

			$scope.someFunction = function(data, status, headers, config) {
				// keeping it to shut up sonarQube. This is that hot fix doe.
			};
			
			
			
			$scope.upload = function(files, folderNames) {
				
				var funcEVT = function(evt){
					parseInt(100.0 * evt.loaded / evt.total);
				};
				var funcDSHC = function(data, status, headers, config){			
					
				};
			
				if (files && files.length) {
					for (var i = 0; i < files.length; i++) {
						var file = files[i];
						Upload.upload({
							url : 'rest/s3uploadFile/' + folderNames[i],
							method : 'POST',
							file : file
						}).progress(funcEVT) 
						.success(funcDSHC);
					}
				}
			};
		} ]);

