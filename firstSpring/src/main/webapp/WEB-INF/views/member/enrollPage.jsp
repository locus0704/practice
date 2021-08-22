<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="views/common/error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>fisrtspring</title>
<style type="text/css">
table th { background-color : #99ffff; }
table#outer { border : 2px solid navy;  }
</style>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<script>
	function validate(){
		//유효성 검사 코드 작성함
		//서버 컨트롤러로 전송할 값들이 요구한 조건을 모두 만족하였는지 검사함

		//암호와 암호 확인이 일치하지 않는지 확인함
		var pwdValue = document.getElementById("userpwd").value;
		var pwdValue2 = document.getElementById("userpwd2").value;

		if(pwdValue !== pwdValue2){
			alert("암호와 암호 확인의 값이 일치하지 않습니다.");
			document.getElementById("userpwd").select();
			return false;  //전송 취소함
		}

		return true;  //전송함
	}
	
	//아이디 중복 체크 확인을 위한 ajax 실행 처리용 함수
	function dupIdCheck(){
		$.ajax({
			url: "idchk.do",
			type: "post",
			data: {userid: $("#userid").val()},
			success: function(data){
				console.log("success : " + data);
				if(data == "ok"){
					alert("사용 가능한 아이디입니다.");
					$("#userpwd").focus();
				}else{
					alert("이미 사용중인 아이디입니다.\n다시 입력하세요.");
					$("#userid").select();
				}
			},
			error: function(jqXHR, textstatus, errorthrown){
				console.log("error : " + jqXHR + ", " + textstatus
						+ ", " + errorthrown);
			}
		});
		
		return false;  //클릭 이벤트가 전달되어서 submit 이 동작되지 않게 함
	}
</script>
</head>
<body>
<center>
<h1 align="center">회원 가입 페이지</h1>
<br>
<form method="post" action="enroll.do" onsubmit="return validate();">
<table id="outer" align="center" width="500" cellspacing="5" cellpadding="0">
<tr>
	<th colspan="2">회원 정보를 입력해 주세요. (* 표시는 필수입력 항목입니다.)</th>	
</tr>
<tr>
	<th width="120">*이 름</th>
	<td><input type="text" name="username" required></td>
</tr>
<tr>
	<th>*아이디</th>
	<td><input type="text" name="userid" id="userid" required> &nbsp; 
	<input type="button" value="중복체크" onclick="return dupIdCheck();"></td>
</tr>
<tr>
	<th>*암 호</th>
	<td><input type="password" name="userpwd" id="userpwd" required></td>
</tr>
<tr>
	<th>*암호확인</th>
	<td><input type="password" id="userpwd2"></td>
</tr>
<tr>
	<th>*성 별</th>
	<td><input type="radio" name="gender" value="M" checked> 남자 &nbsp; 
	    <input type="radio" name="gender" value="F"> 여자</td>
</tr>
<tr>
	<th>*나 이</th>
	<td><input type="number" name="age" min="19" max="200" value="20"></td>
</tr>
<tr>
	<th>*전화번호</th>
	<td><input type="tel" name="phone" required></td>
</tr>
<tr>
	<th>*이메일</th>
	<td><input type="email" name="email" required></td>
</tr>
<tr>
	<th>취 미</th>
	<td>
		<table width="350">
		<tr>
			<td><input type="checkbox" name="hobby" value="game"> 게임</td>
			<td><input type="checkbox" name="hobby" value="reading"> 독서</td>
			<td><input type="checkbox" name="hobby" value="climb"> 등산</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="hobby" value="sport"> 운동</td>
			<td><input type="checkbox" name="hobby" value="music"> 음악듣기</td>
			<td><input type="checkbox" name="hobby" value="movie"> 영화보기</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="hobby" value="travel"> 여행</td>
			<td><input type="checkbox" name="hobby" value="cook"> 요리</td>
			<td><input type="checkbox" name="hobby" value="etc" checked> 기타</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<th>하고싶은 말</th>
	<td><textarea name="etc" rows="5" cols="50"></textarea></td>
</tr>
<tr>
	<th colspan="2">
		<input type="submit" value="가입하기"> &nbsp; 
		<input type="reset" value="작성취소"> &nbsp; 
		<a href="main.do">시작 페이지로</a>
	</th>	
</tr>
</table>
</form>
</center>

</body>
</html>