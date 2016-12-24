'use strict';

var app;
var port = ":8090";
var baseDirectory = "TestBank"
var host = "http://192.168.0.15";
var url = host + port + "/" + baseDirectory + "/"; 
(() => {
	app = angular.module('app', []);
	app = angular.controller('FormatController', ['$scope', 'FeedService', ($scope, Feed) => {
		$scope.loadFeeds = () => {
			Feed.parseFeed(url + "format")
		};
		$scope.loadFeeds();
	}]);
})();