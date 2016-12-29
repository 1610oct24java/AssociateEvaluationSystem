<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="resources/css/login.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<br>
	<div class="container">

		<div class="navbar-header">
				<img src="https://3g4d13k75x47q7v53surz1gi-wpengine.netdna-ssl.com/wp-content/themes/revature/imgs/logo.png" alt="Revature" title="">
		</div>

		<div id="navbar" class="navbar-collapse collapse rev-menu">
			<div class="menu-header-menu-container">
				
			</div>
		</div>
	</div><br>

	<div class="wpb_wrapper text-center">
		<div class="hero-hw-video">
			<video id="hero-video" autoplay="" class="hero-video" preload="auto"
				loop="">
			<source
				src="https://revature.com/wp-content/uploads/2016/11/Hero_Full_720P.m4v"
				type="video/mp4"></video>

			<div class="slidertext col-sm-3">
				<div class="well-special">
				<form name='f' action="login" method='POST'>
					<input type="text" name="username" placeholder="Username">
					<br> <br> <input type="text" name="password"
						placeholder="Password"> <br> <br>
					<input type="submit" name="submit" class="css3button">Login</button>
					<br> <br>
				</form>
				</div>
			</div>
		</div>
	</div>
	
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.9/angular.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.9/angular-route.js"></script>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js" integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
		crossorigin="anonymous"></script>
</body>
</html>