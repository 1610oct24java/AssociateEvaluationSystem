'use strict';
var variable;
var app;
(()=>{
	app = angular.module('myapp', ['ui.bootstrap', 'ui.bootstrap.tpls' ]);
	
	app.controller('myctrl', function($http){
		
		this.getCSV = () => {
			$http.get('http://localhost:8090/TestBank/test')
				.success((response) => {
					variable = response.data;
					console.log(variable);
				});
		};
		angular.element(document).ready(() =>{
			this.getCSV();
		});
	});
});