<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="views/common/error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testspring</title>
<style type="text/css">
h1 {
	font-size : 48pt;
	color : navy;
}
div {
	width : 500px;
	height : 200px;
	border : 2px solid navy;
	position : relative; /* 본래 표시될 위치 기준 상대위치로 지정한다는 설정임 */
	left : 400px;
}

div form {
	font-size : 16pt;
	color : navy;
	font-weigth : bold;
	margin : 10px;
	padding : 10px;
}

div#loginForm form input.pos {
	position : absolute;  /*절대좌표로 위치 지정한다는 설정임 */
	left : 120px;
	width : 300px;
	height : 25px;	
}
div#loginForm form input[type=submit] {
	margin : 10px;
	width : 250px;
	height : 40px;
	position : absolute;
	left : 120px;
	background : navy;
	color : white;
	font-size : 16pt;
	font-weight : bold;
}
</style>
</head>
<body>
<h1 align="center">TEST1 로그인</h1>
<div id="loginForm">
<!-- <form action="../../login" method="post"> --><!-- 상대경로로 서블릿 실행 -->
<form action="login.do" method="post"> <!-- 절대경로로 서블릿 실행 -->
<label>아 이 디 : <input type="text" name="userid" id="uid" class="pos"></label> <br>
<label>비밀번호 : <input type="password" name="userpwd" id="upwd" class="pos"></label> <br>
<input type="submit" value="로그인">
</form>
</div>

</body>
</html>









