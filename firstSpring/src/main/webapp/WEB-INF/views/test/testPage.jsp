<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testPage</title>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//테스트 1 버튼을 클릭했을 때, 서버 컨트롤러 메소드로 값 보내기
	$('#test1').on("click", function(){
		$.ajax({
			url: "test1.do",
			data: {name: $("#name").val(), age: $("#age").val()},
			type: "post",
			success: function(result){
				if(result == "ok"){
					alert("컨트롤러로 값 보내기 성공");
					console.log("result : "+result);
					$("#d1").html("<h5>"+result+"</h5>");
				}else{
					//'ok'가 아닌 다른 문자열이면
					alert('값 전송 실패');
				}
			},
			error: function(request, status, errorData){
				console.log("error code : "+request.status
						+"\nMessage : "+request.responseText
						+"\nError : "+errorData);
			}
		});	//ajax
	});	//test1 click
	
	// 서버 컨트롤러에서 전송한 json 출력
	$('#test2').on("click", function(){
		$.ajax({
			url: "test2.do",
			type: "post",	//json 을 받을 때는 post로 꼭 지정해야함
			dataType: "json",	//전송받는 값의 종류 지정 (기본 : text)
			success: function(jsonData){
				//json 객체 한 개를 받을 때는 바로 출력 처리할 수 있음
				console.log("jsonData : "+jsonData);
				
				$("#d2").html("번호 : "+jsonData.no
						+"<br>제목 : "+jsonData.title
						+"<br>작성자 : "+decodeURIComponent(jsonData.writer)
						+"<br>내용 : "+decodeURIComponent(jsonData.content).replace(/\+/gi, " "));
			},
			error: function(request, status, errorData){
				console.log("error code : "+request.status
						+"\nMessage : "+request.responseText
						+"\nError : "+errorData);
			}
		});	//ajax
	});	//test2 click
	
	//서버 컨트롤러에서 전송한 json 배열 받기
	$('#test3').on("click", function(){
		$.ajax({
			url: "test3.do",
			type: "post",
			dataType: "json",
			success: function(obj){
				//json 배열이 담긴 객체를 받은 경우
				//object => string => parsing : json
				console.log(obj); 	//[object, Object]
				
				//받은 객체를 문자열로 바꾸기
				var objStr = JSON.stringify(obj);
				//객체 문자열을 다시 json 객체로 파싱함
				var jsonObj = JSON.parse(objStr);
				
				//출력용 문자열 준비 : d3 영역에 문자열 추가
				var output = $("#d3").html();
				
				//출력용 문자열 만들기
				for(var i in jsonObj.list){
					output += jsonObj.list[i].userid
						+ ", "+jsonObj.list[i].userpwd
						+ ", "+decodeURIComponent(jsonObj.list[i].username).replace(/\+/gi, " ")
						+ ", "+jsonObj.list[i].age
						+ ", "+jsonObj.list[i].email
						+ ", "+jsonObj.list[i].phone
						+ ", "+jsonObj.list[i].birth+"<br>";
				}
				
				$("#d3").html(output);
			},
			error: function(request, status, errorData){
				console.log("error code : "+request.status
						+"\nMessage : "+request.responseText
						+"\nError : "+errorData);
			}
		});	//ajax
	});	//test3 click
	
	// 서버 컨트롤러에서 Map 객체를
	$('#test4').on("click", function(){
		$.ajax({
			url: "test4.do",
			type: "post",
			dataType: "json",
			success: function(jsonObj){
				console.log(jsonObj);
				
				var output="받은 Map 안의 객체 정보 확인<Br>";
				output += "이름 : "+decodeURIComponent(jsonObj.hashMap.samp.name).replace(/\+/gi, " ")
						+", 나이 : "+jsonObj.hashMap.samp.age;
				
				$("#d4").html(output);
			},
			error: function(request, status, errorData){
				console.log("error code : "+request.status
						+"\nMessage : "+request.responseText
						+"\nError : "+errorData);
			}
		});	//ajax
	});	//test4 click
	
	// json 객체를 서버로 보내기
	$('#test5').on("click", function(){
		//자바스크립트 객체 (json) 만들기
		var job = new Object();
		job.name = "강감찬";	//job.name = $('#name').val();
		job.age = 33;		//job.age = $('#age').val();
		
		//var job = {name: "강감찬", age: 33};
		
		$.ajax({
			url: "test5.do",
			type: "post",
			data: JSON.stringify(job),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				alert("전송 성공 : "+result);
				
				$('#d5').html("전송한 json 객체 정보 : "+job.name+", "+job.age);
			},
			error: function(request, status, errorData){
				console.log("error code : "+request.status
						+"\nMessage : "+request.responseText
						+"\nError : "+errorData);
			}
		});	//ajax
	});	//test5 click
	
	$('#test6').on('click', function(){
		//var jarr = new Array(5);	//index 이용할 수 있음
		//jarr[0] = {name: '홍길동', age: 25};
		
		//var jarr = new Array();	//Stack 구조가 됨, index 없음
		//저장 : push(), 꺼내기 : pop() 사용
		//jarr.push({name:'홍길동', age:25});
		
		//배열 초기화
		var jarr=[{name:'홍길동', age:25},{name:'이순신', age:24}, {name:'신사임당', age:30}];
		
		$.ajax({
			url: "test6.do",
			type:"post",
			data: JSON.stringify(jarr),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				alert("전송 성공 : "+result);
				
				var values = $('#d6').html();
				for(var i in jarr){
					values += i +"번째 이름 : "+jarr[i].name
								+", 나이 : "+jarr[i].age
								+"<br>";
				}
				$('#d6').html(values);
			},
			error: function(request, status, errorData){
				console.log("error code : "+request.status
						+"\nMessage : "+request.responseText
						+"\nError : "+errorData);
			}
		});	//ajax
		
	});	//test6 click
	
});
</script>
</head>
<body>
<h1>테스트 페이지</h1>
<h3>Spring file upload test</h3>
<form action="tinsert.do" method="post" enctype="multipart/form-data">
	이름 : <input type="text" name="name" required><br>
	나이 : <input type="number" name="age" required><br>
	첨부파일 : <input type="file" name="upfile" ><br>
	<input type="submit" value="send">
</form>

<hr>
<h3>Spring file download test</h3>
<A href="tdown.do?fname=파이널프로젝트_진행일정.txt">파이널프로젝트_진행일정.txt</A>

<hr>
<h3>Spring ajax using test</h3>
	<ol>
		<li>서버측 컨트롤러로 메소드로 값 보내기
			<button id="test1">test1</button>
		</li>
		이름 : <input type="text" id="name"><Br>
		나이 : <input type="text" id="age"><br>
		<p><div id="d1"></div></p>
		
		<li>컨트롤러에서 보낸 json 객체 받아서 출력하기
			<button id="test2">test2</button>
		</li>
		<p><div id="d2"></div></p>
		
		<li>컨트롤러에서 보낸 json 배열 받아서 출력하기
			<button id="test3">test3</button>
		</li>
		<p><div id="d3"></div></p>
		
		<li>컨트롤러에서 보낸 Map 객체 받아서 출력하기
			<button id="test4">test4</button>
		</li>
		<p><div id="d4"></div></p>
		
		<li>서버측 컨트롤러로 json 객체 보내기
			<button id="test5">test5</button>
		</li>
		<p><div id="d5"></div></p>
		<li>서버측 컨트롤러로 json 배열 보내기
			<button id="test6">test6</button>
		</li>
		<p><div id="d6"></div></p>
	</ol>


</body>
</html>