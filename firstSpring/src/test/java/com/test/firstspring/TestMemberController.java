package com.test.firstspring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//SpringJUnit4ClassRunner : 스프링 테스트를 위한 Runner 클래스 지정
//WebAppConfiguration : Controller 및 web 환경에 사용되는 bean 들을 자동으로 생성하여 등록
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={
		"classpath:root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/spring-security.xml"})
public class TestMemberController {
	// 로그 출력을 위한 객체 
	private static final Logger LOGGER = LoggerFactory.getLogger(TestMemberController.class);
	
	// 테스트용 클래스에서 사용할 필드 선언
	@Autowired
	private WebApplicationContext wac; // 현재 실행중인 애플리케이션의 구성을 제공하는 인터페이스임
	private MockMvc mockMvc; // client 요청 내용을 controller 에서 받아 처리하는 것과 같은 테스트를 진행할 수 있는 클래스임
	
	@Before	//JUnit 테스트 진행 전 먼저 실행하는 것을 지정하는 어노테이션
	public void setup() {
		// 테스트에 사용할 MockMvc 객체 생성 처리
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		LOGGER.info("setup() 메소드 실행");
	}
	
	@Test	//테스트용 메소드임을 명시하는 어노테이션
	public void testLogin() throws Exception{
		try {
			this.mockMvc.perform(post("login.do").param("userid","admin02").param("userpwd","admin02")).andDo(print()).andExpect(status().isOk());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
