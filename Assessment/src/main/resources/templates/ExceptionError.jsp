<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="resources/css/bootstrap.css" rel="stylesheet"
	type="text/css">
<meta http-equiv="refresh" content="5;url=/Assessment/" />
<title>Error</title>
</head>
<body>

	<div class="alert alert-danger" role="alert">
		<strong>Oops!</strong> Looks like your network is having difficulties.
	</div>

	<h3>Error</h3>
	Please make a note of this error and contact support...
	<div>
		<p>
			<strong>Cause:</strong><br>
			<%=exception.getCause()%>
		</p>
		<p>
			<strong>Message:</strong><br>
			<%=exception.getMessage()%>
		</p>
	</div>
	<!--
    Failed URL: ${url}
    
    Exception:  ${exception.message}
    
        <c:forEach items="${exception.stackTrace}" var="ste">
            ${ste}   
    </c:forEach>
  -->
</body>
</html>