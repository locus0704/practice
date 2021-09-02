<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
    
<c:set var="currentPage" value="${ requestScope.currentPage }" />
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp"/>
<hr>
<h2 align="center">${ requestScope.board.board_num } 번 게시글 상세보기</h2>
<br>
<table align="center" width="500" border="1" cellspacing="0" 
cellpadding="5">
<tr><th width="120">제 목</th><td>${ board.board_title }</td></tr>
<tr><th>작성자</th><td>${ board.board_writer }</td></tr>
<tr><th>등록날짜</th>
<td><fmt:formatDate value="${ board.board_date }" type="date" pattern="yyyy/MM/dd" /></td></tr>
<tr>
	<th>첨부파일</th>
	<td>
		<c:if test="${ !empty board.board_original_filename }">
			<c:url var="ubf" value="/bfdown.do">
				<c:param name="ofile" value="${ board.board_original_filename }"/>
				<c:param name="rfile" value="${ board.board_rename_filename }"/>
			</c:url>		
			<a href="${ ubf }">${ board.board_original_filename }</a>
		</c:if>
		<c:if test="${ empty board.board_original_filename }">
		&nbsp;
		</c:if>
	</td>
</tr>
<tr><th>내 용</th><td>${ board.board_content }</td></tr>
<tr><th colspan="2">
<c:if test="${ !empty sessionScope.loginMember }">
	<c:if test="${ loginMember.userid eq board.board_writer }">
	    <c:url var="ubup" value="/bupview.do">
	    	<c:param name="board_num" value="${ board.board_num }"/>
	    	<c:param name="page" value="${ currentPage }"/>
	    </c:url>
	    <a href="${ ubup }">[수정페이지로 이동]</a>
	    &nbsp; &nbsp; 
	    <c:url var="ubd" value="/bdelete.do">
	    	<c:param name="board_num" value="${ board.board_num }"/>
	    	<c:param name="board_reply_lev" value="${ board.board_reply_lev }"/>
	    	<c:param name="board_rename_filename" value="${ board.board_rename_filename }"/>
	    </c:url>
	    <a href="${ ubd }">[글삭제]</a>
	    &nbsp; &nbsp; 
    </c:if>
    <c:if test="${ loginMember.userid ne board.board_writer }">
   		<c:url var="brf" value="/breplyform.do">
   			<c:param name="bnum" value="${ board.board_num }"/>
   			<c:param name="page" value="${ currentPage }"/>
   		</c:url>
		<a href="${ brf }">[댓글달기]</a>
	</c:if>
</c:if> 
&nbsp; &nbsp; 
<c:url var="ubl" value="/blist.do">  	
  	<c:param name="page" value="${ currentPage }"/>
</c:url>
<button onclick="javascript:location.href='${ ubl }'">목록</button>
</th></tr>
</table>

</body>
</html>




