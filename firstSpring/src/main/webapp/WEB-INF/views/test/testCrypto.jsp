<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testCrypto</title>
</head>
<body>
<h1>spring security 패스워드 암호화 처리 테스트 페이지</h1>
<fieldset>
	<legend>패스워드 암호화 확인용</legend>
	<form action="bcrypto.do" method="post">
		전송 암호 : <input type="password" name="userpwd"><br>
		<input type="submit" value="전송">
	</form>
</fieldset>
<br>
<hr>

</body>
</html>