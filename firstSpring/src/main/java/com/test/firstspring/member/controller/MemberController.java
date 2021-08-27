package com.test.firstspring.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.test.firstspring.common.SearchDate;
import com.test.firstspring.member.model.service.MemberService;
import com.test.firstspring.member.model.vo.Member;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	// 의존성 주입 (Dependency Injection)
	// 사용할 클래스 레퍼런스 = new 생성자();

	@Autowired // 자동 DI 처리됨
	private MemberService memberService;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	// 뷰 페이지 이동 처리용 메소드 ----------------------------------------------
	@RequestMapping("loginPage.do")
	public String moveLoginPage() {
		return "member/loginPage";
	}

	@RequestMapping("enrollPage.do")
	public String moveEnrollPage() {
		return "member/enrollPage";
	}

	// --------------------------------------------------------------------

//	@RequestMapping(value="login.do", method=RequestMethod.POST)
//	public String loginMethod(HttpServletRequest request, HttpServletResponse response) {
//		String userid = request.getParameter("userid");
//		String userpwd = request.getParameter("userpwd");
//		return "main";
//	}

//	@RequestMapping(value="login.do", method=RequestMethod.POST)
//	public String loginMethod(
//			@RequestParam("userid") String userid,
//			@RequestParam("userpwd") String userpwd
//			){
//		// 매개변수 수가 적을 때
//		System.out.println("login.do : "+userid+", "+userpwd);
//		Member member = new Member();
//		member.serUserid(userid);
//		member.setUserpwd(userpwd);
//		return "main";
//	}

//	//command 객체 이용 
//	@RequestMapping(value="login.do", method=RequestMethod.POST)
//	public String loginMethod(Member member, HttpSession session, SessionStatus status, Model model){
//		//System.out.println("login.do : "+member);
//		logger.info("login.do : "+member);
//		//Member loginMember = memberService.selectLogin(member);
//		
//		//userid 가 일치하는 회원정보 조회해 옴
//				Member loginMember = memberService.selectMember(member.getUserid());
//				//조회해 온 회원정보의 암호화된 패스워드와 클라이언트가 보낸 암호 비교
//				//matches(일반글자 암호, 암호화된 패스워드)
//				if(loginMember != null && bcryptPasswordEncoder.matches(member.getUserpwd(), loginMember.getUserpwd()));
//
//		if(loginMember != null) {
//			//세션 객체 생성 > 세션 안에 회원정보 저장
//			session.setAttribute("loginMember", loginMember);
//			status.setComplete();	//요청 성공. 200 전송보냄
//			return "common/main";
//		}else {
//			model.addAttribute("message", "로그인 실패!");
//			return "common/error";
//		}
//
//	}
	// command 객체 이용
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String loginMethod(Member member, HttpSession session, SessionStatus status, Model model) {
		// System.out.println("login.do : "+member);
		logger.info("login.do : " + member);
		// 암호화 처리 전
		// Member loginMember = memberService.selectLogin(member);

		// userid 가 일치하는 회원정보 조회해 옴
		Member loginMember = memberService.selectMember(member.getUserid());
		// 조회해 온 회원정보의 암호화된 패스워드와 클라이언트가 보낸 암호 비교
		// matches(일반글자 암호, 암호화된 패스워드)

		if (loginMember != null && bcryptPasswordEncoder.matches(member.getUserpwd(), loginMember.getUserpwd())
				&& loginMember.getLogin_ok().equals("Y")) {
			// 세션 객체 생성 > 세션 안에 회원정보 저장
			session.setAttribute("loginMember", loginMember);
			status.setComplete(); // 요청 성공. 200 전송보냄
			return "common/main";
		} else {
			model.addAttribute("message", "로그인 실패!");
			return "common/error";
		}

	}

	@RequestMapping("logout.do")
	public String logoutMethod(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false); // 새로 생성되면 안되니까
		if (session != null) {
			session.invalidate();
			return "common/main";
		} else {
			model.addAttribute("message", "로그인 세션이 존재하지 않습니다.");
		}
		return "main";
	}

	// id 중복 체크 확인을 위한 ajax 요청 처리용 메소드
	// 전송방식이 get이 아니면 써줘야 함
	// ajax 통신은 뷰리졸버로 뷰파일을 리턴하면 안됨(뷰페이지가 바뀜)
	// 요청한 클라이언트와 출력스트림을 만들어서 값 전송함
	@RequestMapping(value = "idchk.do", method = RequestMethod.POST)
	public void idCheckMethod(@RequestParam("userid") String userid, HttpServletResponse response) throws IOException {
		// String userid = request.getParameter("userid");

		int idcount = memberService.selectCheckId(userid);

		String returnValue = null;
		if (idcount == 0) {
			returnValue = "ok";
		} else {
			returnValue = "dup";
		}
		// response 를 이용해서 클라이언트로 출력스트림 만들고 값 보내기
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.append(returnValue);
		out.flush();
		out.close();
	}

	@RequestMapping(value = "enroll.do", method = RequestMethod.POST)
	public String memberInsert(Member member, Model model) {
		logger.info("enroll.do : " + member);

		// 패스워드 암호화 처리
		member.setUserpwd(bcryptPasswordEncoder.encode(member.getUserpwd()));

		if (memberService.insertMember(member) > 0) {
			return "common/main";
		} else {
			model.addAttribute("message", "회원 가입 실패...");
			return "common/error";
		}
	}

	@RequestMapping("myinfo.do")
	public ModelAndView myInfoMethod(@RequestParam("userid") String userid, ModelAndView mv) {
		Member member = memberService.selectMember(userid);
		if (member != null) {
			mv.addObject("member", member);
			mv.setViewName("member/myInfoPage");
		} else {
			mv.addObject("message", userid + " 회원 조회 실패!");
			mv.setViewName("common/error");
		}
		return mv;
	}

	@RequestMapping("mupview.do")
	public ModelAndView memberUpdateViewMethod(@RequestParam("userid") String userid, ModelAndView mv) {
		Member member = memberService.selectMember(userid);
		if (member != null) {
			mv.addObject("member", member);
			mv.setViewName("member/memberUpdatePage");
		} else {
			mv.addObject("message", userid + " 회원 정보 수정페이지로 이동 실패!");
			mv.setViewName("common/error");
		}
		return mv;
	}

	@RequestMapping(value = "mupdate.do", method = RequestMethod.POST)
	public String memberUpdateMethod(Member member, Model model, @RequestParam("origin_userpwd") String originUserpwd) {
		logger.info("mupdate.do : " + member);
		logger.info("opwd : " + originUserpwd);

		// 새로운 암호가 전송이 왔다면
		String userpwd = member.getUserpwd().trim();
		if (userpwd != null && userpwd.length() > 0) {
			// 기존 암호와 다른 값이면 암호화 처리를 해라
			if (!bcryptPasswordEncoder.matches(userpwd, originUserpwd)) {
				member.setUserpwd(bcryptPasswordEncoder.encode(userpwd));
			}
		} else {
			// 새로운 암호값이 전송오지 않았다면 원래 암호를 기록
			member.setUserpwd(originUserpwd);
		}
		logger.info("after : " + member);

		if (memberService.updateMember(member) > 0) {
			// 컨트롤러의 메소드를 직접 호출할 수 있음
			return "redirect:myinfo.do?userid=" + member.getUserid();
		} else {
			model.addAttribute("message", member.getUserid() + "회원정보 수정 실패!");

			return "common/error";
		}

	}

	@RequestMapping("mdel.do")
	public String memberDeleteMethod(@RequestParam("userid") String userid, Model model) {
		if (memberService.deleteMember(userid) > 0) {
			return "redirect:logout.do";
		} else {
			model.addAttribute("message", userid + " 회원 삭제 실패");
			return "common/error";
		}
	}
	
	@RequestMapping("mlist.do")
	public String memberListViewMethod(Model model) {
		ArrayList<Member> list = memberService.selectList();
		
		if(list.size()>0) {
			model.addAttribute("list", list);
			return "member/memberListView";
		}else {
			model.addAttribute("message", "회원 목록이 존재하지 않습니다.");
			return "common/error";
		}
	}
	
	@RequestMapping(value="msearch.do", method=RequestMethod.POST)
	public String memberSearchMethod(HttpServletRequest request, Model model) {
		String action = request.getParameter("action");
		String keyword = null, beginDate = null, endDate= null;
		
		if(action.equals("enrolldate")) {
			beginDate = request.getParameter("begin");
			endDate = request.getParameter("end");
		}else {
			keyword = request.getParameter("keyword");
		}
		//서비스 메소드로 전송하고 결과받을 리스트 준비
		ArrayList<Member> list = null;
		
		switch(action) {
		case "id": list = memberService.selectSearchUserid(keyword); break;
		case "gender": list = memberService.selectSearchGender(keyword); break;
		case "age" : list = memberService.selectSearchAge(Integer.parseInt(keyword)); break;
		case "enrolldate": list = memberService.selectSearchEnrollDate(new SearchDate(Date.valueOf(beginDate), Date.valueOf(endDate))); break;
		case "loginok": list = memberService.selectSearchLoginOk(keyword); break;
		}
		
		if(list.size()>0) {
			model.addAttribute("list", list);
			return "member/memberListView";
		}else {
			model.addAttribute("message", action+" 검색에 대한 "+keyword+" 결과가 존재하지 않습니다.");
			return "common/error";
		}
		
	}
	
	@RequestMapping("loginOK.do")
	public String changeLoginOKMethod(Member member, Model model) {
		logger.info("loginOK.do : "+member.getUserid()+", "+member.getLogin_ok());
		
		if(memberService.updateLoginOk(member)>0) {
			return "redirect:mlist.do";
		}else {
			model.addAttribute("message", "로그인 제한/허용 처리 오류");
			return "common/error";
		}
	}
	

}
