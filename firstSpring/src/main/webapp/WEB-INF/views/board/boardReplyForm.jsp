<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
    
<c:set var="board_num" value="${ requestScope.board_num }" />    
<c:set var="currentPage" value="${ requestScope.currentPage }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp"/>
<h1 align="center">${ board_num } 번글 댓글 달기 페이지</h1>

<hr>
<form action="breply.do" method="post" >
<input type="hidden" name="board_ref" value="${ board_num }">
<input type="hidden" name="page" value="${ currentPage }">
<table align="center" width="500" border="1" cellspacing="0" 
cellpadding="5">
<tr><th>제 목</th><td><input type="text" name="board_title" size="50"></td></tr>
<tr><th>작성자</th>
   <td><input type="text" name="board_writer" readonly value="${ loginMember.userid }"></td>
</tr>
<tr><th>내 용</th><td><textarea rows="5" cols="50" name="board_content"></textarea></td></tr>
<tr><th colspan="2">
<input type="submit" value="등록하기"> &nbsp; 
<input type="reset" value="작성취소"> &nbsp;
<c:url var="ubl" value="/blist.do">  	
  	<c:param name="page" value="${ currentPage }"/>
</c:url>
<button onclick="javascript:location.href='${ ubl }'; return false;">목록</button>
</th></tr>
</table>
</form>
</body>
</html>






