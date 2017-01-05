var app;
(()=>{
	app = angular.module('app',[]);
	
	angular.element("document").ready( () => {
		console.log( $http.get("http://localhost:8080/TestBank/categoriestest/Multiple%20Choice"));
	});
})();