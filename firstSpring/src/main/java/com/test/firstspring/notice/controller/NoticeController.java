package com.test.firstspring.notice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.test.firstspring.common.SearchDate;
import com.test.firstspring.member.model.vo.Member;
import com.test.firstspring.notice.model.service.NoticeService;
import com.test.firstspring.notice.model.vo.Notice;

@Controller
public class NoticeController {
	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;

	// ajax 로 최신 공지글 조회 요청 처리용
	@RequestMapping(value = "ntop3.do", method = RequestMethod.POST)
	@ResponseBody
	public String noticeNewTop3Method(HttpServletResponse response) throws UnsupportedEncodingException {

		// 최신 공지글 3개 조회해 옴
		ArrayList<Notice> list = noticeService.selectNewTop3();

		// 전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();

		// list 옮길 json 배열 준비
		JSONArray jarr = new JSONArray();

		// list 를 jarr 로 옮기기(복사)
		for (Notice notice : list) {
			// notice 저장용 json 객체 생성
			JSONObject job = new JSONObject();

			job.put("noticeno", notice.getNoticeno());
			job.put("noticetitle", URLEncoder.encode(notice.getNoticetitle(), "utf-8"));
			job.put("noticedate", notice.getNoticedate().toString());

			// job 를 jarr 에 저장
			jarr.add(job);
		}
		// 전송용 json 객체에 jarr 담음
		sendJson.put("list", jarr);

		return sendJson.toJSONString(); // jsonView 가 리턴됨
	}

	// 공지사항 전체 목록보기 요청 처리용
	@RequestMapping("nlist.do")
	public String noticeListMethod(Model model) {
		ArrayList<Notice> list = noticeService.selectAll();

		if (list.size() > 0) {
			model.addAttribute("list", list);
			return "notice/noticeListView";
		} else {
			model.addAttribute("message", "등록된 공지사항 정보가 없습니다.");
			return "common/error";
		}
	}

	// 공지사항 검색
	@RequestMapping(value = "nsearchTitle.do", method = RequestMethod.POST)
	public String noticeSearchTitleMethod(@RequestParam("keyword") String keyword, Model model) {
		ArrayList<Notice> list = noticeService.selectSearchTitle(keyword);

		if (list.size() > 0) {
			model.addAttribute("list", list);
			return "notice/noticeListView";
		} else {
			model.addAttribute("message", keyword + "로 검색된 공지사항 정보가 없습니다.");
			return "common/error";
		}
	}

	@RequestMapping(value = "nsearchWriter.do", method = RequestMethod.POST)
	public String noticeSearchWriterMethod(@RequestParam("keyword") String keyword, Model model) {
		ArrayList<Notice> list = noticeService.selectSearchWriter(keyword);

		if (list.size() > 0) {
			model.addAttribute("list", list);
			return "notice/noticeListView";
		} else {
			model.addAttribute("message", keyword + "로 검색된 공지사항 정보가 없습니다.");
			return "common/error";
		}
	}

	@RequestMapping(value = "nsearchDate.do", method = RequestMethod.POST)
	public String noticeSearchDateMethod(SearchDate dates, Model model) {
		ArrayList<Notice> list = noticeService.selectSearchDate(dates);

		if (list.size() > 0) {
			model.addAttribute("list", list);
			return "notice/noticeListView";
		} else {
			model.addAttribute("message", "해당 날짜로 검색된 공지사항 정보가 없습니다.");
			return "common/error";
		}
	}

	// 공지글 등록 페이지 요청 처리용
	@RequestMapping("nwform.do")
	public String noticeWriteForm() {
		return "notice/noticeWriteForm";
	}

	// 파일업로드 기능이 있는 공지글 등록 요청 처리용
	@RequestMapping(value = "ninsert.do", method = RequestMethod.POST)
	public String noticeInsertMethod(Notice notice, HttpServletRequest request, Model model,
			@RequestParam(name = "upfile", required = false) MultipartFile mfile) {
		// 업로드된 파일 저장 폴더 지정하기
		String savePath = request.getSession().getServletContext().getRealPath("resources/notice_upfiles");

		// 첨부파일이 있을때만 업로드된 파일을 지정 폴더로 옮기기
		if (!mfile.isEmpty()) {
			String fileName = mfile.getOriginalFilename();
			if (fileName != null && fileName.length() > 0) {
				try {
					mfile.transferTo(new File(savePath + "\\" + fileName));

					// 저장된 원본 파일의 이름 바꾸기 하려면...
					// 저장 폴더에 같은 이름의 파일이 있을 경우를 대비하기 위함
					// "년월일시분초.확장자" 형식으로 변경해 봄

					// 바꿀 파일명에 대한 문자열 만들기
					// 공지글 등록 요청 시점의 날짜정보를 이용함
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

					// 변경할 파일명 만들기
					String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));
					// 원본 파일의 확장자를 추출해서, 변경 파일명에 붙여줌
					renameFileName += "." + fileName.substring(fileName.lastIndexOf(".") + 1);

					// 파일명 바꾸기 실행함 : java.io.File 을 이용함
					File originFile = new File(savePath + "\\" + fileName);
					File renameFile = new File(savePath + "\\" + renameFileName);

					if (!originFile.renameTo(renameFile)) {
						// renameTo() 메소드가 실패한 경우(false)
						// 직접 수동으로 바꾸기함
						// 원본 파일 읽어서 파일복사하고,
						// 원본 파일 삭제로 처리함

						FileInputStream fin = new FileInputStream(originFile);
						FileOutputStream fout = new FileOutputStream(renameFile);

						int data = -1;
						byte[] buffer = new byte[1024];

						while ((data = fin.read(buffer, 0, buffer.length)) != -1) {
							fout.write(buffer, 0, buffer.length);
						}

						fin.close();
						fout.close();
						originFile.delete(); // 원본 파일 삭제함
					} // 직접 이름바꾸기

					notice.setRename_filepath(renameFileName);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "전송파일 저장 실패.");
					return "common/error";
				}

			} // 업로드된 파일이 있다면...

			notice.setOriginal_filepath(mfile.getOriginalFilename());
			logger.info("ninsert.do : " + notice);
		} // 첨부 파일이 있을 때

		if (noticeService.insertNotice(notice) > 0) {
			return "redirect:nlist.do";
		} else {
			model.addAttribute("message", "공지글 등록 실패.");
			return "common/error";
		}
	}

	// 공지사항 상세보기 요청 처리용
	@RequestMapping("ndetail.do")
	public String noticeDetailMethod(@RequestParam("noticeno") int noticeno, Model model, HttpSession session) {
		Notice notice = noticeService.selectNotice(noticeno);

		if (notice != null) {
			model.addAttribute("notice", notice);
			Member loginUser = (Member) session.getAttribute("loginMember");
			if (loginUser != null && loginUser.getAdmin().equals("Y")) {
				// 관리자가 상세보기 요청했을 때
				return "notice/noticeAdminDetailView";
			} else {
				// 관리자가 아닌 고객이 상세보기 요청했을 때
				return "notice/noticeDetailView";
			}
		} else {
			model.addAttribute("message", noticeno + "번 공지 상세보기 실패");
			return "common/error";
		}
	}

	// 첨부파일 다운로드 요청 처리용
	@RequestMapping("nfdown.do")
	public ModelAndView fileDownMethod(HttpServletRequest request, @RequestParam("ofile") String originFileName,
			@RequestParam("rfile") String renameFileName, ModelAndView mv) {
		String savePath = request.getSession().getServletContext().getRealPath("resources/notice_upfiles");

		// 저장 폴더에서 파일 읽기 위해 경로 포함
		File renameFile = new File(savePath + "\\" + renameFileName);
		// response에 다운파일명 등록을 위한 파일 (경로 제외)
		File originalFile = new File(originFileName);

		/*
		 * ModelAndView(String viewName, String modelName, Object modelObject) Model 클래스
		 * 객체 = request + response modelName == 이름, modelObject == 객체
		 * request.setAttribute("이름", 객체) 와 같은 의미임
		 */

		mv.setViewName("filedown2");
		mv.addObject("renameFile", renameFile);
		mv.addObject("originalFile", originalFile);

		// 스프링에서는 파일다운하려면, 스프링이 제공하는 View 클래스를 상속받은
		// 파일다운처리용 뷰클래스를 별도로 작성하고, DispatcherServlet 에
		// 파일다운로드용 뷰클래스를 실행시키는 뷰리졸버를 등록해야 함
		return mv;
	}

	// 공지글 삭제 요청 처리용
	@RequestMapping("ndel.do")
	public String noticeDeleteMethod(@RequestParam("noticeno") int noticeno,
			@RequestParam(name = "rfile", required = false) String renameFileName, HttpServletRequest request,
			Model model) {
		if (noticeService.deleteNotice(noticeno) > 0) {
			// 첨부파일이 있는 글일 때, 저장폴더에 있는 파일도 삭제함
			if (renameFileName != null) {
				new File(request.getSession().getServletContext().getRealPath("resources/notice_upfiles") + "\\"
						+ renameFileName).delete();
			}
			return "redirect:nlist.do";
		} else {
			model.addAttribute("message", noticeno + "번 공지글 삭제 실패.");
			return "common/error";
		}
	}

	// 공지글 수정 페이지 요청 처리용
	@RequestMapping("upmove.do")
	public String noticeUpdateForm(@RequestParam("noticeno") int noticeno, Model model) {
		Notice notice = noticeService.selectNotice(noticeno);
		if (notice != null) {
			model.addAttribute("notice", notice);
			return "notice/noticeUpdateForm";
		} else {
			model.addAttribute("message", noticeno + "번 공지 수정페이지로 이동 요청 실패.");
			return "common/error";
		}
	}

	// 공지글 수정 요청 처리용
	@RequestMapping(value = "nupdate.do", method = RequestMethod.POST)
	public String noticeUpdateMethod(Notice notice, HttpServletRequest request, Model model,
			@RequestParam(name = "delFlag", required = false) String delFlag,
			@RequestParam(name = "upfile", required = false) MultipartFile mfile) {

		// 업로드된 파일 저장 폴더 지정하기
		String savePath = request.getSession().getServletContext().getRealPath("resources/notice_upfiles");

		// 원래 첨부파일이 있는데, 삭제를 선택한 경우
		if (notice.getOriginal_filepath() != null && delFlag != null && delFlag.equals("yes")) {
			// 저장 폴더에서 파일을 삭제함
			new File(savePath + "\\" + notice.getRename_filepath()).delete();
			notice.setOriginal_filepath(null);
			notice.setRename_filepath(null);
		}

		// 새로운 첨부파일이 있을때
		if (!mfile.isEmpty()) {
			// 저장폴더의 이전 파일은 삭제
			if (notice.getOriginal_filepath() != null) {
				// 저장 폴더에서 파일을 삭제함
				new File(savePath + "\\" + notice.getRename_filepath()).delete();
				notice.setOriginal_filepath(null);
				notice.setRename_filepath(null);
			}
			
			String fileName = mfile.getOriginalFilename();
			if (fileName != null && fileName.length() > 0) {
				try {
					mfile.transferTo(new File(savePath + "\\" + fileName));

					// 저장된 첨부파일 이름 바꾸기
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

					// 변경할 파일명 만들기
					String renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));
					// 원본 파일의 확장자를 추출해서, 변경 파일명에 붙여줌
					renameFileName += "." + fileName.substring(fileName.lastIndexOf(".") + 1);

					// 파일명 바꾸기 실행함 : java.io.File 을 이용함
					File originFile = new File(savePath + "\\" + fileName);
					File renameFile = new File(savePath + "\\" + renameFileName);

					if (!originFile.renameTo(renameFile)) {
						// 파일 이름바꾸기 실패시 직접 바꾸기

						FileInputStream fin = new FileInputStream(originFile);
						FileOutputStream fout = new FileOutputStream(renameFile);

						int data = -1;
						byte[] buffer = new byte[1024];

						while ((data = fin.read(buffer, 0, buffer.length)) != -1) {
							fout.write(buffer, 0, buffer.length);
						}

						fin.close();
						fout.close();
						originFile.delete(); // 저장된 원본 파일 삭제함
					} // 직접 이름바꾸기

					notice.setRename_filepath(renameFileName);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "전송파일 저장 실패.");
					return "common/error";
				}

			} // 업로드된 파일이 있다면...

			notice.setOriginal_filepath(mfile.getOriginalFilename());
			logger.info("nupdate.do : " + notice);
		}

		if (noticeService.updateNotice(notice) > 0) {
			return "redirect:nlist.do";
		} else {
			model.addAttribute("message", notice.getNoticeno() + "번 공지글 수정 실패.");
			return "common/error";
		}

	}

}
