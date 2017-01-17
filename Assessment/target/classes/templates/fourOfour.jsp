
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<c:url value="/resources/css/bootstrap.css" />"
	rel="stylesheet" type="text/css">
<!-- <meta http-equiv="refresh" content="3;url=/Assessment/" /> -->
<title>Simple Error</title>
</head>
<body>
	<div class="alert alert-warning" role="alert">
		<strong>Warning!</strong> If you are seeing this page, something <strong>minor</strong>
		happened.
	</div>
	<img src="<c:url value="/resources/images/logo.png"/>"
		alt="revatureLogo" style="padding: 40px;">
	<br>
	<img src="<c:url value="/resources/images/404.png"/>" alt="404">
</body>
</html>