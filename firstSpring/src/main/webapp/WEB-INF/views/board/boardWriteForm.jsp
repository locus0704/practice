<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<c:import url="/WEB-INF/views/common/menubar.jsp" />
<hr>
<h1 align="center">게시글 등록 페이지</h1>
<!-- form 에서 입력값들과 파일을 같이 전송하려면 
    반드시 enctype="multipart/form-data" 속성을 추가해야 함 -->
<form action="binsert.do" method="post" enctype="multipart/form-data">
<table align="center" width="500" border="1" cellspacing="0" 
cellpadding="5">
<tr><th>제 목</th><td><input type="text" name="board_title" size="50"></td></tr>
<tr><th>작성자</th>
   <td><input type="text" name="board_writer" readonly value="${ loginMember.userid }"></td>
</tr>
<tr>
	<th>파일 선택 : </th>
	<td><input type="file" name="upfile">		
	</td>
</tr>
<tr><th>내 용</th><td><textarea rows="5" cols="50" name="board_content"></textarea></td></tr>
<tr><th colspan="2">
<input type="submit" value="등록하기"> &nbsp; 
<input type="reset" value="작성취소"> &nbsp;
<input type="button" value="목록" onclick="javascript:history.go(-1); return false;">
</th></tr>
</table>
</form>
</body>
</html>