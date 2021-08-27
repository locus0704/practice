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
<c:import url="../common/menubar.jsp" />
<hr>
<h1 align="center">${ notice.noticeno } 번 공지글 수정 페이지</h1>
<%-- form 에서 입력값들과 파일을 같이 전송하려면, 반드시 enctype="multipart/form-data"
  속성 추가해야 함 --%>
<form action="nupdate.do" method="post" enctype="multipart/form-data">
<input type="hidden" name="noticeno" value="${ notice.noticeno }">
<c:if test="${ !empty notice.original_filepath }">
	<input type="hidden" name="original_filepath" value="${ notice.original_filepath }">
	<input type="hidden" name="rename_filepath" value="${ notice.rename_filepath }">
</c:if>
<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
<tr><th>제 목</th>
<td><input type="text" name="noticetitle" value="${ notice.noticetitle }"></td></tr>
<tr><th>작성자</th>
<td><input type="text" name="noticewriter" readonly value="${ notice.noticewriter }"></td></tr>
<tr><th>파일선택</th>
<td>
	<%-- 원래 첨부파일이 있는 경우 --%>
	<c:if test="${ !empty notice.original_filepath }">
		${ notice.original_filepath } &nbsp; 
		<input type="checkbox" name="delFlag" value="yes"> 파일삭제 <br>
	</c:if>
	<%-- 원래 첨부파일이 없는 경우 --%>
	<br>
	새로 첨부 : <input type="file" name="upfile">
</td></tr>
<tr><th>내 용</th>
<td><textarea name="noticecontent" rows="5" cols="50">${ notice.noticecontent }</textarea></td></tr>
<tr><th colspan="2">
<input type="submit" value="수정하기"> &nbsp; 
<input type="reset" value="작성취소"> &nbsp; 
<button onclick="javascript:history.go(-1); return false;">이전 페이지로 이동</button></th></tr>
</table>
</form>  
</body>
</html>