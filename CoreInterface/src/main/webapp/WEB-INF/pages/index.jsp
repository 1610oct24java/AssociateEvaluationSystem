<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="resources/css/login.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="wpb_wrapper text-center">
		<div class="hero-hw-video">
			<video id="hero-video" autoplay="" class="hero-video" preload="auto"
				loop="" poster="../wp-content/themes/revature/imgs/hp-carousel.jpg">
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
</body>
</html>