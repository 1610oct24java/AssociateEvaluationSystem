<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.8/angular.min.js"></script>

<title>Login</title>
</head>
<body>
   <h1>Login</h1>
   <form name='f' action="login" method='POST'>
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
       <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
  </form>

</body>

<%-- <body ng-app="myApp" ng-controller="myController">
	<h1>Login</h1>
	<form ng-submit="login()">
	<br>Email: <input type="email" ng-model="email">
	<br>Passw: <input type="password" ng-model="password">
	<input type="submit">
	</form>
	
	<script src="resources/js/login.js"></script>
</body> --%>
</html>