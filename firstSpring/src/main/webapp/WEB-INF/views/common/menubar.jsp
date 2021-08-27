<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>firstspring</title>
<style type="text/css">
header h1#logo {
	font-size: 36pt;
	font-style: italic;
	color : navy;
	text-shadow: 2px 2px 2px gray;
}
ul#menubar {
	list-style: none;
	position: relative;
	left: 150px;
	/* top: -30px; */
}
ul#menubar li {
	float: left;
	width: 120px;
	height: 30px;
	margin-right: 5px;
	padding: 0;
}
ul#menubar li a {
	text-decoration: none;
	width: 120px;
	height: 30px;
	display: block;
	background: orange;
	color: navy;
	text-align: center;
	font-weight: bold;
	margin: 0;
	text-shadow: 1px 1px 2px white;
	padding-top: 5px;
}
ul#menubar li a:hover {
	text-decoration: none;
	width: 120px;
	height: 30px;
	display: block;
	background: navy;
	color: white;
	text-align: center;
	font-weight: bold;
	margin: 0;
	text-shadow: 1px 1px 2px navy;
	padding-top: 5px;
}
hr { clear: both; }
</style>
</head>
<body>
<header>
	<h1 id="logo">first spring project</h1>
</header>
<%-- <% if(loginMember == null){ //로그인하지 않았을 때 %> --%>
<c:if test="${empty sessionScope.loginMember }">
<ul id="menubar">
	<li><a href="${pageContext.servletContext.contextPath }/nlist.do">공지사항</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/blist.do?page=1">게시글</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/moveCrypto.do">암호화테스트</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/moveTest.do">테스트</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/main.do">홈</a></li>
</ul>
</c:if>
<%-- <% }else if(loginMember.getAdmin().equals("Y")){ //관리자가 로그인했을 때 %> --%>
<c:if test="${!empty sessionScope.loginMember and loginMember.admin eq 'Y'}">
<ul id="menubar">
	<li><a href="${pageContext.servletContext.contextPath }/mlist.do">회원관리</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/nlist.do">공지사항관리</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/blist.do?page=1">게시글관리</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/views/test_api/testList.html">테스트</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/main.do">홈</a></li>
</ul>
</c:if>
<%-- <% }else { //일반 회원이 로그인했을 때 %> --%>
<c:if test="${!empty loginMember and loginMember.admin ne 'Y'}">
<ul id="menubar">
	<li><a href="${pageContext.servletContext.contextPath }/nlist.do">공지사항</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/blist.do?page=1">게시글</a></li>
	<li><a href="#">QnA</a></li>
	<li><a href="#">암호화회원가입</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/views/test_api/testList.html">테스트</a></li>
	<li><a href="${pageContext.servletContext.contextPath }/main.do">홈</a></li>
</ul>
</c:if>
<%-- <% } %> --%>
</body>
</html>






