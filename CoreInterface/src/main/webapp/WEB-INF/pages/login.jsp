<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>

<title>Login</title>
</head>
<body ng-app="myApp" ng-controller="myController">
	<h1>Login</h1>
	<br>Email: <input type="text" ng-model="email">
	<br>Passw: <input type="text" ng-model="password">
	<button ng-click="login()">login</button>
	<script src="resources/js/login.js" type="text/javascript"></script>
</body>
</html>