<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testel</title>
</head>
<body>
<c:import url="/views/common/header.jsp" />
<hr>
<h2 align="center">${ notice.noticeno }번 글 수정페이지</h2>
<br>
<form action="/nupdate.ad" method="post" enctype="multipart/form-data">
<input type="hidden" name="noticeno" value="${  notice.noticeno }">
<input type="hidden" name="original_filepath" value="${  notice.original_filepath }">
<input type="hidden" name="rename_filepath" value="${  notice.rename_filepath }">
<table align="center" width="500" border="1" cellspacing="0" 
cellpadding="5">
<tr><th>제 목</th><td><input type="text" name="noticetitle" size="50" value="${ notice.noticetitle }"></td></tr>
<tr><th>작성자</th>
   <td><input type="text" name="noticewriter" readonly value="${ notice.noticewriter }"></td>
</tr>
<tr>
	<th>첨부파일</th>
	<td>
		<c:if test="${ !empty notice.original_filepath }">		
		${ notice.original_filepath } &nbsp; 
		<input type="checkbox" name="delflag" value="yes"> 파일삭제<br>
		</c:if>
		<c:if test="${ empty notice.originalFilepath }">	
			첨부파일 없음<br>
			<input type="file" name="upfile">		
		</c:if>
	</td>
</tr>
<tr><th>내 용</th><td><textarea rows="5" cols="50" name="noticecontent">${ notice.noticecontent }</textarea></td></tr>
<tr><th colspan="2">
<input type="submit" value="수정하기"> &nbsp; 
<input type="reset" value="수정취소"> &nbsp;
<input type="button" value="이전 페이지로" onclick="javascript:history.go(-1); return false;">
</th></tr>
</table>
</form>
</body>
</html>








