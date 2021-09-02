<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    

<c:set var="listCount" value="${ requestScope.listCount }" />
<c:set var="startPage" value="${ requestScope.startPage }" />
<c:set var="endPage" value="${ requestScope.endPage }" />
<c:set var="maxPage" value="${ requestScope.maxPage }" />
<c:set var="currentPage" value="${ requestScope.currentPage }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>firstspring</title>
<script type="text/javascript">
function showWriteForm(){
	location.href = "${pageContext.servletContext.contextPath }/bwform.do";
}
</script>
</head>
<body>
<c:import url="../common/menubar.jsp" />
<hr>
<h2 align="center">게시글 목록 : 총 ${ listCount } 개</h2>
<!-- 게시글 쓰기(등록)은 로그인한 회원만 가능함 -->
<c:if test="${ !empty sessionScope.loginMember }">
	<div style="align:center;text-align:center;">
		<button onclick="showWriteForm();">글쓰기</button>
	</div>
</c:if>
<br>
<table align="center" border="1" cellspacing="0" width="700">
<tr><th>번호</th><th>제목</th><th>작성자</th><th>날짜</th>
<th>조회수</th><th>첨부파일</th></tr>
<c:forEach items="${ requestScope.list }" var="b">
<tr>
<td align="center">${ b.board_num }</td>
<td>
<!-- 댓글일때는 제목을 들여쓰기함 -->
<c:if test="${ b.board_reply_lev eq 2 }">
&nbsp; &nbsp; ▶
</c:if>
<c:if test="${ b.board_reply_lev eq 3 }">
&nbsp; &nbsp; &nbsp; &nbsp; ▶▶
</c:if>
<!-- 로그인한 사용자만(회원만) 상세보기할 수 있게 함 -->
<c:if test="${ !empty sessionScope.loginMember }">
	<c:url var="ubd" value="bdetail.do">
		<c:param name="board_num" value="${ b.board_num }"/>
		<c:param name="page" value="${ currentPage }" />
	</c:url>
	<a href="${ ubd }">${ b.board_title }</a>
</c:if>
<c:if test="${ empty sessionScope.loginMember }">
	${ b.board_title }
</c:if>
</td>
<td align="center">${  b.board_writer }</td>
<td align="center">
<fmt:formatDate value="${  b.board_date }" type="date" pattern="yyyy-MM-dd" /></td>
<td align="center">${  b.board_readcount }</td>
<td align="center">
<c:if test="${ !empty b.board_original_filename }">
◎
</c:if>
<c:if test="${ empty b.board_original_filename }">
&nbsp;
</c:if>
</td>
</tr>
</c:forEach>
</table>
<br>
<!-- 페이징 처리 -->
<div style="text-align:center;">
<c:if test="${ currentPage <= 1 }">
   [맨처음]&nbsp;
</c:if>
<c:if test="${ currentPage > 1 }">
   <c:url var="ubl" value="/blist.do">
      <c:param name="page" value="1" />
   </c:url>
   <a href="${ ubl }">[맨처음]</a>
</c:if>
<!-- 이전 그룹으로 이동 처리 -->
<c:if test="${ (currentPage - 10) < startPage and (currentPage - 10) > 1 }">
   <c:url var="ubl2" value="/blist.do">
      <c:param name="page" value="${ startPage - 10 }"/>
   </c:url>
   <a href="${ ubl2 }">[이전그룹]</a>
</c:if>
<c:if test="${ !((currentPage - 10) < startPage and (currentPage - 10) > 1) }">
   [이전그룹]&nbsp;
</c:if>
<!-- 현재 페이지가 속한 페이지그룹의 숫자 출력 처리 -->
<c:forEach var="p" begin="${ startPage }" end="${ endPage }" step="1">
   <c:if test="${ p eq currentPage }">   
      <font color="red" size="4"><b>[${ p }]</b></font>
   </c:if>
   <c:if test="${ p ne currentPage }">
      <c:url var="ubl3" value="/blist.do">
         <c:param name="page" value="${ p }"/>
      </c:url>
      <a href="${ ubl3 }">${ p }</a>
   </c:if>
</c:forEach>
<!-- 다음 그룹으로 이동 처리 -->
<c:if test="${ (currentPage + 10) > endPage && (currentPage + 10) < maxPage }">
   <c:url var="ubl4" value="/blist.do">
      <c:param name="page" value="${ endPage + 10 }"/>
   </c:url>
   <a href="${ ubl4 }">[다음그룹]</a>
</c:if>
<c:if test="${ !((currentPage + 10) > endPage && (currentPage + 10) < maxPage) }">
   [다음그룹]&nbsp;
</c:if>
<!-- 맨끝 페이지로 이동 처리 -->
<c:if test="${ currentPage >= maxPage }">
   [맨끝]&nbsp;
</c:if>   
<c:if test="${ currentPage < maxPage }">
   <c:url var="ubl5" value="/blist.do">
      <c:param name="page" value="${ maxPage }"/>
   </c:url>
   <a href="${ ubl5 }">[맨끝]</a>
</c:if>
</div>

<hr>
<c:import url="../common/footer.jsp" />
</body>
</html>





