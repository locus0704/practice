<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testel</title>
</head>
<body>
<!-- jstl 의 절대경로 표기 : / == /context-root 명 -->
<c:import url="/WEB-INF/views/common/menubar.jsp"/>
<hr>
<h1>오류발생 : </h1>
<c:set var="e" value="<%= exception %>"></c:set>
<c:if test="${ !empty e }">
	<h3>jsp 페이지 오류 : ${ message }</h3>	
</c:if>
<%-- <% }else{ %>  --%>
<c:if test="${ empty e }">
	<h3>servlet 메세지 : ${ message } </h3>
	<!-- requestScope.message == request.getAttribute("message") -->
</c:if>
<%-- <% } %> --%>
<br>
<c:url var="movemain" value="main.do"></c:url>
<a href="${movemain }">시작페이지 가기</a>
<hr>
<!-- 상대경로는 기존의 표기방식 그대로 사용 -->
<c:import url="footer.jsp"/>
</body>
</html>






