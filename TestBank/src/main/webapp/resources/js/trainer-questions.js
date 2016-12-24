'use strict';

var app;
var port = "8090";
var host = "http://192.168.0.15:" + port + "/"; 
(() => {
	app = angular.module('app', []);
	app = angular.controller('FormatController', ['$scope', 'FeedService', ($scope, Feed) => {
		$scope.loadFeeds = () => {
			Feed.parseFeed(host +"")
		};
		$scope.loadFeeds();
	}]);
})();