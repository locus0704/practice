<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="views/common/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>firstspring</title>
<style type="text/css">
div.lineA {
	height: 100px;
	border: 1px solid gray;
	float: left;
	position: relative;
	left: 120px;
	margin: 5px;
	padding: 5px;
}

div#banner {
	width: 500px;
	padding: 0;
}

div#banner img {
	width: 450px;
	height: 80px;
	padding: 0;
}

div#loginBox {
	width: 300px;
	font-size: 10pt;
	text-align: left;
	padding-left: 20px;
}

div#loginBox button {
	width: 250px;
	height: 35px;
	background-color: navy;
	color: white;
	margin-top: 10px;
	margin-bottom: 15px;
	font-size: 14pt;
	font-weight: bold;
}
</style>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	function movePage() {
		location.href = "loginPage.do";
	}

	$(function() {
		//최근 등록된 공지글 3개 전송받아서 출력되게 함
		$.ajax({
			url : "ntop3.do",
			type : "post",
			dataType : "json",
			success : function(data) {
				console.log("success : " + data);

				//object --> string
				var str = JSON.stringify(data);
				//string --> json 
				var json = JSON.parse(str);

				values = "";
				for ( var i in json.list) {
					values += "<tr><td>"
							+ json.list[i].noticeno
							+ "</td><td><a href='ndetail.do?noticeno="
							+ json.list[i].noticeno
							+ "'>"
							+ decodeURIComponent(json.list[i].noticetitle).replace(
									/\+/gi, " ") + "</a></td><td>"
							+ json.list[i].noticedate + "</td></tr>";
				}

				$('#newnotice').html($('#newnotice').html() + values);
			},
			error : function(jqXHR, textstatus, errorthrown) {
				console.log("error : " + jqXHR + ", " + textstatus + ", "
						+ errorthrown);
			}
		}); //ajax (notice top3)

		//조회수 많은 인기 게시글 상위 3개 조회 출력
		$.ajax({
			url : "btop3.do",
			type : "post",
			dataType : "json",
			success : function(data) {
				console.log("success : " + data);

				//object --> string
				var str = JSON.stringify(data);
				//string --> json 
				var json = JSON.parse(str);

				values = "";
				for ( var i in json.list) {
					values += "<tr><td>"
							+ json.list[i].board_num
							+ "</td><td><a href='bdetail.do?board_num="
							+ json.list[i].board_num
							+ "'>"
							+ decodeURIComponent(json.list[i].board_title).replace(
									/\+/gi, " ") + "</a></td><td>"
							+ json.list[i].board_readcount + "</td></tr>";
				}

				$('#toplist').html($('#toplist').html() + values);
				//$('#toplist').append(values);

			},
			error : function(jqXHR, textstatus, errorthrown) {
				console.log("error : " + jqXHR + ", " + textstatus + ", "
						+ errorthrown);
			}
		});

	}); //document.ready
</script>
</head>
<body>
	<header>
		<h1></h1>
		<!-- 상대경로로 대상 파일을 지정한 경우 -->
		<%-- <%@ include file="views/common/menubar.jsp" %> --%>
		<%-- <jsp:include page="views/common/menubar.jsp" /> --%>
		<%-- <c:import url="views/common/menubar.jsp" /> --%>
		<!-- c:import 태그는 절대경로 사용할 수 있음 
			일반적인 절대경로는
			/context-root명 : 경로명 맨 앞에 표기함
				url 상에서의 content directory(webapp)까지의
				경로를 의미함
			JSTL 에서는 절대경로 표기법이 달라짐
			/context-root명 ==> / 로 바뀜
			즉, /testel ==> /
		-->
		<c:import url="/WEB-INF/views/common/menubar.jsp" />
		<hr style="clear: both">
		<center>
			<div id="banner" class="lineA">
				<img src="${pageContext.servletContext.contextPath }/resources/images/photo2.jpg">
			</div>
			<c:if test="${ empty sessionScope.loginMember }">
				<div id="loginBox" class="lineA">
					SPRING firstspring 사이트 방문을 환영합니다.<br>
					<button onclick="movePage()">로그인 하세요.</button>
					<br> <a>아이디/비밀번호 조회</a> &nbsp; &nbsp; 
						<a href="enrollPage.do">회원가입</a>
				</div>
			</c:if>
			<%-- <% }else{ %> --%>
			<c:if test="${ !empty sessionScope.loginMember }">
				<%-- 로그인했을 때 --%>
				<div id="loginBox" class="lineA">
					${ sessionScope.loginMember.username } 님.<br>
					<button onclick="javascript:location.href='logout.do';">로그아웃</button>
					<br> <a>쪽지</a> &nbsp; &nbsp; <a>메일</a> &nbsp; &nbsp;
					<c:url var="callMyinfo2" value="myinfo.do">
						<c:param name="userid" value="${loginMember.userid }"/>
					</c:url>
					<a href="${callMyinfo2 }">My Page</a>
				</div>
				<%-- <% } %> --%>
			</c:if>
		</center>
	</header>
	<hr style="clear: both">
	<section>
		<!-- 최근 등록된 공지글 3개 출력 : ajax -->
		<div
			style="float: left; border: 1px solid navy; padding: 5px; margin: 5px; margin-left: 150px;">
			<h4>최신 공지사항</h4>
			<table id="newnotice" border="1" cellspacing="0">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>날짜</th>
				</tr>
			</table>
		</div>

		<!-- 조회수 많은 인기게시글 3개 출력 : ajax -->
		<div
			style="float: left; border: 1px solid navy; padding: 5px; margin: 5px;">
			<h4>인기 게시글</h4>
			<table id="toplist" border="1" cellspacing="0">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>조회수</th>
				</tr>
			</table>
		</div>
	</section>
	<hr style="clear: both;">
	<%-- <%@ include file="views/common/footer.jsp" %> --%>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>



