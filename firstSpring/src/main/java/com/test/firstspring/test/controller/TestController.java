package com.test.firstspring.test.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.test.firstspring.member.controller.MemberController;
import com.test.firstspring.test.model.vo.Sample;
import com.test.firstspring.test.model.vo.User;

@Controller
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	// 뷰 페이지 이동 처리용 -----------------------------------------------------
	@RequestMapping("moveCrypto.do")
	public String moveCryptoPage() {
		return "test/testCrypto";
	}

	@RequestMapping("moveTest.do")
	public String moveTestPage() {
		return "test/testPage";
	}

	// 패스워드 암호화 테스트-------------------------------------------------------
	@RequestMapping(value = "bcrypto.do", method = RequestMethod.POST)
	public String testBCryptMethod(@RequestParam("userpwd") String userpwd) {
		logger.info("전송온 암호 : " + userpwd);
		// 암호화처리
		String bcryptPwd = bcryptPasswordEncoder.encode(userpwd);
		logger.info("암호화된 패스워드 : " + bcryptPwd + ", 글자수 : " + bcryptPwd.length());
		return "common/main";
	}

	// 파일 업로드 테스트 메소드------------------------------------------------------
	@RequestMapping(value = "tinsert.do", method = RequestMethod.POST)
	public String testFileUpload(Sample sample, HttpServletRequest request,
			@RequestParam(name = "upfile", required = false) MultipartFile file) {
		logger.info("sample : " + sample);
		logger.info("file : " + file.getOriginalFilename());

		// 파일 저장 경로 지정
		String savePath = request.getSession().getServletContext().getRealPath("resources/test_files");

		// 업로드된 파일을 지정한 폴더로 옮기기
		try {
			file.transferTo(new File(savePath + "\\" + file.getOriginalFilename()));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return "common/error";
		}
		return "common/main";
	}

	// 파일 다운로드 테스트 메소드------------------------------------------------------
	// 파일 다운은 return type 무조건 modelandview 사용
	@RequestMapping("tdown.do")
	public ModelAndView testFileDownload(HttpServletRequest request, @RequestParam("fname") String fileName) {
		logger.info("filename : " + fileName);
		// 다운받을 파일 경로 지정
		String savePath = request.getSession().getServletContext().getRealPath("resources/test_files");
		
		// 다운파일을 File 객체로 생성
		File downFile = new File(savePath+"\\"+fileName);
		
		/* ModelAndView(String viewName, String modelName, Object modelObject)
		 * Model == request + response
		 * modelName == 저장이름 
		 * modelObject == 저장객체
		 * request.setAttribute("저장이름", 저장객체)
		 * */
		//스프링에서는 파일다운하려면, View 클래스를 상속받은 파일다운처리용 뷰클래스를 별도로 작성해야 함
		//DispatcherServlet 에 뷰클래스를 실행시키는 뷰리졸버를 추가로 등록해야 함
		return new ModelAndView("filedown", "downFile", downFile);
	}
	
	//ajax test method ------------------------------------------------------------
	@RequestMapping(value="test1.do", method=RequestMethod.POST)
	public void testAjaxMethod1(Sample sample, HttpServletResponse response) throws IOException {
		logger.info("test1.do run...");
		logger.info("sample : "+sample);
		
		//서비스로 보내고 결과받기 : 생략..
		
		//요청한 클라인언트에게 결과를 전송함 : 출력스트림을 이용
		//1. 응답하는 정보에 대한 MimiType 지정
		response.setContentType("text/html; charest=utf-8");
		//2. 출력에 사용할 스트림 생성
		PrintWriter out = response.getWriter();
		
		if(sample.getName().equals("홍길동")) {
			out.append("ok");
			out.flush();
		}else {
			out.append("fail");
			out.flush();
		}
		
		out.close();
	}
	
	//client에게서 요청을 받으면, json 객체를 응답하는 메소드
	@RequestMapping(value="test2.do", method=RequestMethod.POST)
	@ResponseBody	//return 하는 json 문자열을 response 객체에 담아서 보내라는 의미의 어노테이션
	public String testAjaxMethod2(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("test2.do run...");
		response.setContentType("application/json; charset=utf-8");
		
		JSONObject job = new JSONObject();
		job.put("no", 123);
		job.put("title", "test return json object");
		job.put("writer", URLEncoder.encode("홍길동", "utf-8"));
		job.put("content", URLEncoder.encode("json 객체를 뷰리졸버를 통해 리턴하는 테스트 글", "utf-8"));
		
		return job.toJSONString();	//디스패처 서블릿의 뷰리졸버로 리턴함
	}
	
	//클라이언트로부터 요청시, json 배열을 jsonView 로 리턴하는 메소드
	//jsonView 로 배열을 담은 json 문자열을 리턴
	@RequestMapping(value="test3.do", method=RequestMethod.POST)
	@ResponseBody
	public String testAjaxMethod3(HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("test3.do run...");
		
		response.setContentType("application/json; charset=utf-8");
		
		//샘플 List 준비 : 서비스로부터 리턴된 List 로 가정함
		ArrayList<User> list = new ArrayList<User>();
		
		list.add(new User("u111", "p111", "홍길동", 25, "hong111@test.org", "010-1234-5677", new java.sql.Date(new GregorianCalendar(1990,4,17).getTimeInMillis())));
		list.add(new User("u112", "p112", "임꺽정", 23, "lim111@test.org", "010-1234-5117", new java.sql.Date(new GregorianCalendar(1990,10,11).getTimeInMillis())));
		list.add(new User("u113", "p113", "신사임당", 40, "hon5111@test.org", "010-1074-5677", new java.sql.Date(new GregorianCalendar(1998,7,8).getTimeInMillis())));
		list.add(new User("u114", "p114", "강감찬", 25, "ho1@test.org", "010-1234-1597", new java.sql.Date(new GregorianCalendar(1990,6,8).getTimeInMillis())));
		list.add(new User("u115", "p115", "유관순", 19, "ho31@test.org", "010-4568-5677", new java.sql.Date(new GregorianCalendar(1919,2,1).getTimeInMillis())));

		//전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();
		//json 배열 준비
		JSONArray jarr = new JSONArray();
		
		//list 를 jarr로 옮기기
		for(User user : list) {
			//user 저장용 json 객체 생성
			JSONObject job = new JSONObject();
			
			job.put("userid", user.getUserid());
			job.put("userpwd", user.getUserpwd());
			job.put("username", URLEncoder.encode(user.getUsername(), "utf-8"));
			job.put("age", user.getAge());
			job.put("email", user.getEmail());
			job.put("phone", user.getPhone());
			job.put("birth", user.getBirth().toString());	//날짜는 반드시 String 으로 변환
			
			jarr.add(job);			
		}
		sendJson.put("list", jarr);
		return sendJson.toJSONString();
	}
	
	//클라이언트부터 요청이 오면, Map 객체를 json 객체에 담아서 jsonView 로 리턴함
	//(방법2. ModelAndView 로 리턴)
	@RequestMapping(value="test4.do", method=RequestMethod.POST)
	
	public ModelAndView testAjaxMethod4(ModelAndView mv) throws UnsupportedEncodingException {
		logger.info("test4.do run...");
		
		//Map 객체를 ModelAndView 에 담아서 JsonView 로 보냄
		//그러면 json 객체로 클라이언트에게 보내짐
		Sample samp = new Sample("이 율곡", 45);
		samp.setName(URLEncoder.encode(samp.getName(), "utf-8"));
		
		Map<String, Sample> map = new HashMap<String, Sample>();
		map.put("samp", samp);
		
		mv.addObject(map);
		//servlet-context.xml 에 등록된 JsonView 의 id 명을 뷰 이름으로 지정함
		mv.setViewName("jsonView");
		
		return mv;
	}
	
	//클라이언트가 보낸 json 객체를 받아서 처리하는 메소드
	@RequestMapping(value="test5.do", method=RequestMethod.POST)
	public ResponseEntity<String> testAjaxMethod5(@RequestBody String param) throws ParseException{
		//post 방식으로 전송온 request body 에 기록된 json 문자열을 꺼내서 param 변수에 기록 저장함
		
		logger.info("test5.do run ...");
		
		//param 변수에 저장된 전송온 json 문자열을 json 객체로 바꿈
		JSONParser jparser = new JSONParser();
		JSONObject job = (JSONObject)jparser.parse(param);
		
		//json 객체가 가진 각 필드값을 추출해서 Sample 객체로 복사
		Sample samp = new Sample();
		samp.setName((String)job.get("name"));
		samp.setAge(((Long)job.get("age")).intValue());
		logger.info("확인 : "+samp.toString());
		
		//서비스 메소드로 전달하고 결과받기 : 생략...
		
		//ResponseEntity<T> : 클라이언트에게 응답하는 용도릐 객체
		//뷰리졸버가 아닌 출력스트림으로 나감
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	//클라이언트가 보낸 json배열 문자열을 받아서 처리하는 메소드
	@RequestMapping(value="test6.do", method=RequestMethod.POST)
	public ResponseEntity<String> testAjaxMethod6(@RequestBody String param) throws ParseException{
		//post 전송은 request body 에 전송값을 인코딩해서 기록함
		//get 전송은 request body 에 전송값을 기록함 (보여짐)
		//param : 전송온 json 배열문자열을 가지고 있음
		
		logger.info("test6.do run...");
		
		//전송온 문자열을 json array 객체로 변환 처리
		JSONParser jparser = new JSONParser();
		JSONArray jarr = (JSONArray)jparser.parse(param);
		
		//jarr 의 정보를 ArrayList<Sample>로 옮겨 저장
		ArrayList<Sample> list = new ArrayList<Sample>();
		
		for(int i = 0; i<jarr.size(); i++) {
			JSONObject job = (JSONObject)jarr.get(i);
			Sample sample = new Sample((String)job.get("name"), ((Long)job.get("age")).intValue());
			list.add(sample);
		}
		logger.info("list : "+list);
		
		//서비스의 insert...() 를 for 문으로 반복 실행함
		
		//뷰리졸버로 리턴되지 않고 별도의 출력스트림을 만들어서 내보냄
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
}
