<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>firstspring</title>
<script type="text/javascript">
	function validate(){
		var pwd1 = document.getElementById("userpwd").value;
		var pwd2 = document.getElementById("userpwd2").value;
		
		if(pwd1 !== pwd2){
			alert("암호와 암호 확인이 일치하지 않습니다.\n다시 확인하고 입력하세요");
			document.getElementById("userpwd").select();
		}
	}
</script>
</head>
<body>
<%-- <%@ include file="../common/header.jsp" %> --%>
<c:import url="/WEB-INF/views/common/menubar.jsp" />
<hr>
<h1 align="center">회원 정보 수정 페이지</h1>
<br>
<form method="post" action="mupdate.do">
<input type="hidden" name="origin_userpwd" value="${requestScope.member.userpwd }">
<table id="outer" align="center" width="500" cellspacing="5" cellpadding="0">
<tr>
	<th width="120">이 름</th>
	<td><input type="text" name="username" 
	value="${ requestScope.member.username }" readonly></td>
</tr>
<tr>
	<th>아이디</th>
	<td><input type="text" name="userid" 
	value="${ requestScope.member.userid }" readonly></td>
</tr>
<tr>
	<th>암 호</th>
	<td><input type="password" name="userpwd" id="userpwd"></td>
</tr>
<tr>
	<th>암호확인</th>
	<td><input type="password" id="userpwd2" onblur="validate();"></td>
</tr>
<tr>
	<th>성 별</th>
	<td>
	<c:if test="${ member.gender eq 'M' }">	
		<input type="radio" name="gender" value="M" checked> 남자 &nbsp; 
	    <input type="radio" name="gender" value="F"> 여자
	</c:if>
	<c:if test="${ member.gender eq 'F' }">	  
		<input type="radio" name="gender" value="M"> 남자 &nbsp; 
	    <input type="radio" name="gender" value="F" checked> 여자
	</c:if>
	</td>
</tr>
<tr>
	<th>나 이</th>
	<td><input type="number" name="age" min="19" max="200" 
	value="${ requestScope.member.age }"></td>
</tr>
<tr>
	<th>전화번호</th>
	<td><input type="tel" name="phone" 
	value="${ requestScope.member.phone }"></td>
</tr>
<tr>
	<th>이메일</th>
	<td><input type="email" name="email" 
	value="${ requestScope.member.email }"></td>
</tr>
<tr>
	<th>취 미</th>
	<td>
	<%-- 취미 문자열을 각각의 문자열로 분리하면서, 취미에 적용할
	     변수 9개를 만듦 --%>
	<c:forTokens items="${ requestScope.member.hobby }" delims="," 
		var="hb">
		<c:if test="${ hb eq 'game' }">
			<c:set var="checked0" value="checked" />
		</c:if>
		<c:if test="${ hb eq 'reading' }">
			<c:set var="checked1" value="checked" />
		</c:if>
		<c:if test="${ hb eq 'climb' }">
			<c:set var="checked2" value="checked" />
		</c:if>
		<c:if test="${ hb eq 'sport' }">
			<c:set var="checked3" value="checked" />
		</c:if>
		<c:if test="${ hb eq 'music' }">
			<c:set var="checked4" value="checked" />
		</c:if>
		<c:if test="${ hb eq 'movie' }">
			<c:set var="checked5" value="checked" />
		</c:if>
		<c:if test="${ hb eq 'travel' }">
			<c:set var="checked6" value="checked" />
		</c:if>
		<c:if test="${ hb eq 'cook' }">
			<c:set var="checked7" value="checked" />
		</c:if>
		<c:if test="${ hb eq 'etc' }">
			<c:set var="checked8" value="checked" />
		</c:if>
	</c:forTokens>
		<table width="350">
		<tr>
			<td><input type="checkbox" name="hobby" value="game" ${ checked0 }> 게임</td>
			<td><input type="checkbox" name="hobby" value="reading" ${ checked1 }> 독서</td>
			<td><input type="checkbox" name="hobby" value="climb" ${ checked2 }> 등산</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="hobby" value="sport" ${ checked3 }> 운동</td>
			<td><input type="checkbox" name="hobby" value="music" ${ checked4 }> 음악듣기</td>
			<td><input type="checkbox" name="hobby" value="movie" ${ checked5 }> 영화보기</td>
		</tr>
		<tr>
			<td><input type="checkbox" name="hobby" value="travel" ${ checked6 }> 여행</td>
			<td><input type="checkbox" name="hobby" value="cook" ${ checked7 }> 요리</td>
			<td><input type="checkbox" name="hobby" value="etc" ${ checked8 }> 기타</td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<th>하고싶은 말</th>
	<td><textarea name="etc" rows="5" cols="50">${ member.etc }</textarea></td>
</tr>
<tr>
	<th colspan="2">
		<a href="javascript:history.go(-1);">이전 페이지로 이동</a> &nbsp; 
		<input type="submit" value="수정하기"> &nbsp; 
		<input type="reset" value="수정취소"> &nbsp; 		
		<a href="main.do">시작 페이지로</a>
	</th>	
</tr>
</table>
</form>
<hr>
<!-- 상대경로만 사용 가능함 -->
<%-- <%@ include file="../common/footer.jsp" %> --%>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>