package com.test.firstspring.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.test.firstspring.board.model.service.BoardService;
import com.test.firstspring.board.model.vo.Board;
import com.test.firstspring.common.Paging;
import com.test.firstspring.notice.model.vo.Notice;

@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	BoardService boardService;

	@RequestMapping(value = "btop3.do", method = RequestMethod.POST)
	@ResponseBody
	public String baordReadCountTop3(HttpServletResponse response) throws UnsupportedEncodingException {
		// 최신 공지글 3개 조회해 옴
		ArrayList<Board> list = boardService.selectTop3();

		// 전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();

		// list 옮길 json 배열 준비
		JSONArray jarr = new JSONArray();

		// list 를 jarr 로 옮기기(복사)
		for (Board board : list) {
			// board 저장용 json 객체 생성
			JSONObject job = new JSONObject();

			job.put("board_num", board.getBoard_num());
			job.put("board_title", URLEncoder.encode(board.getBoard_title(), "utf-8"));
			job.put("board_readcount", board.getBoard_readcount());

			// job 를 jarr 에 저장
			jarr.add(job);
		}
		// 전송용 json 객체에 jarr 담음
		sendJson.put("list", jarr);

		return sendJson.toJSONString(); // jsonView 가 리턴됨
		// jsonView 가 출력스트림 열어서 받은 json 정보를 내보냄
	}

	@RequestMapping(value = "blist.do")
	public ModelAndView boardListMethod(ModelAndView mv, @RequestParam(name = "page", required = false) String page) {
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		// 페이징 처리
		int limit = 10; // 한 페이지에 출력할 목록 개수
		// 페이지 계산을 위해 총 목록개수 조회
		int listCount = boardService.selectListCount();
		// 페이지 수 계산
		// 목록이 11개이면 총 2 페이지가 나오게 계산식 작성
		int maxPage = (int) ((double) listCount / limit + 0.9);
		// 현재 페이지가 포함된 페이지 그룹의 시작값
		// 뷰페이지에 페이지 숫자를 10개씩 보여지게 한다면
		int startPage = (int) ((double) currentPage / limit + 0.9);
		// 현재 페이지가 포함된 페이지 그룹의 끝값
		// 페이지 수가 10개이면
		int endPage = startPage + 10 - 1;

		if (maxPage < endPage) {
			endPage = maxPage;
		}
		// 쿼리문에 전달할 현재 페이지에 출력할 목록의 첫행과 끝행
		int startRow = (currentPage - 1) * limit + 1;
		int endRow = startRow + limit - 1;
		Paging paging = new Paging(startRow, endRow);

		ArrayList<Board> list = boardService.selectList(paging);

		if (list != null && list.size() > 0) {
			mv.addObject("list", list);
			mv.addObject("listCount", listCount);
			mv.addObject("maxPage", maxPage);
			mv.addObject("startPage", startPage);
			mv.addObject("currentPage", currentPage);
			mv.addObject("endPage", endPage);
			mv.addObject("limit", limit);

			mv.setViewName("board/boardListView");
		} else {
			mv.addObject("message", currentPage + "페이지 목록 조회 실패");
			mv.setViewName("common/error");
		}

		return mv;
	}

	// 게시글 쓰기 페이지로 이동
	@RequestMapping(value = "bwform.do")
	public String moveBoardWriteForm() {
		return "board/boardWriteForm";
	}

	// 파일업로드 기능이 있는 게시원글 등록 요청 처리용
	@RequestMapping(value = "binsert.do", method = RequestMethod.POST)
	public String boardInsertMethod(Board board, HttpServletRequest request, Model model,
			@RequestParam(name = "upfile", required = false) MultipartFile mfile) {
		// 업로드된 파일 저장 폴더 지정하기
		String savePath = request.getSession().getServletContext().getRealPath("resources/board_upfiles");

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

					board.setBoard_rename_filename(renameFileName);
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("message", "전송파일 저장 실패.");
					return "common/error";
				}

			} // 업로드된 파일이 있다면...

			board.setBoard_original_filename(mfile.getOriginalFilename());
			logger.info("ninsert.do : " + board);
		} // 첨부 파일이 있을 때

		if (boardService.insertOriginBoard(board) > 0) {
			return "redirect:blist.do";
		} else {
			model.addAttribute("message", "공지글 등록 실패.");
			return "common/error";
		}
	}
	
	//게시글 상세보기
	@RequestMapping("bdetail.do")
	public ModelAndView boardDetailMethod(ModelAndView mv, @RequestParam("board_num") int board_num, @RequestParam("page") int page) {
		// 조회수 1증가 처리
		boardService.updateAddReadCount(board_num);
		
		//해당 게시글 조회
		Board board = boardService.selectBoard(board_num);
		
		if(board != null) {
			mv.addObject("board", board);
			mv.addObject("currentPage", page);
			mv.setViewName("board/boardDetailView");
		}else {
			mv.addObject("message", board_num +"번 게시글 조회 실패");
			mv.setViewName("common/error");
		}
		return mv;
	}
	
	// 첨부파일 다운로드 요청 처리용
		@RequestMapping("bfdown.do")
		public ModelAndView fileDownMethod(HttpServletRequest request, 
				@RequestParam("ofile") String originFileName,
				@RequestParam("rfile") String renameFileName, ModelAndView mv) {
			String savePath = request.getSession().getServletContext().getRealPath("resources/board_upfiles");

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
		
		// 댓글달기 페이지로 이동
		@RequestMapping("breplyform.do")
		public String moveReplyForm(@RequestParam("bnum") int origin_num, @RequestParam("page") int currentPage, Model model) {
			model.addAttribute("board_num", origin_num);
			model.addAttribute("currentPage", currentPage);
			return "board/boardReplyForm";
		}
		
		// 댓글달기
		@RequestMapping(value="breply.do", method=RequestMethod.POST)
		public String replyInsertMethod(Board reply, @RequestParam("page") int page, Model model) {
			//해당 댓글의 원글 조회
			Board origin = boardService.selectBoard(reply.getBoard_ref());
			
			//레벨값 1증가 처리
			reply.setBoard_reply_lev(origin.getBoard_reply_lev() + 1);
			
			//대댓글일때 board_reply_ref 값 등록
			if(reply.getBoard_reply_lev() == 3) {
				reply.setBoard_ref(origin.getBoard_ref());
				reply.setBoard_reply_ref(origin.getBoard_reply_ref());
			}
			//댓글과 대댓글은 최신글을 순번 1로 함
			reply.setBoard_reply_seq(1);
			//기존의 댓글 또는 대댓글의 순번은 모두 1증가 처리함
			boardService.updateReplySeq(reply);
			
			if(boardService.insertReply(reply)>0) {
				return "redirect:blist.do?page="+page;
			}else {
				model.addAttribute("message", reply.getBoard_ref()+"번 게시글에 댓글달기 실패");
				return "common/error";
			}
		}
		
		// 삭제하기
		@RequestMapping("bdelete.do")
		public String boardDeleteMethod(Board board, HttpServletRequest request, Model model) {
			if(boardService.deleteBoard(board)>0) {
				//글 삭제 성공하면 저장폴더에 첨부파일도 삭제 처리
				if(board.getBoard_rename_filename() != null) {
					new File(request.getSession().getServletContext().getRealPath("resources/board_upfiles")+"\\"+board.getBoard_rename_filename()).delete();
				}
				return "redirect:blist.do?page=1";
			}else {
				model.addAttribute("message", board.getBoard_num()+"번 게시글 삭제 실패");
				return "common/error";
			}
		}
		
		// 글 수정페이지로 이동 처리용 컨트롤러
		@RequestMapping("bupview.do")
		public String moveBoardUpdateView(@RequestParam("board_num") int board_num, 
				@RequestParam("page") int currentPage, Model model) {
			Board board = boardService.selectBoard(board_num);
			
			if(board != null) {
				model.addAttribute("board", board);
				model.addAttribute("page", currentPage);
				return "board/boardUpdateView";
			}else {
				model.addAttribute("message", board_num+"번 게시글 수정페이지로 이동 실패");
				return "common/error";
			}
		}
		
		// 댓글, 대댓글 수정 처리용
		@RequestMapping(value="breplyup.do", method=RequestMethod.POST)
		public String replyUpdateMethod(Board reply, @RequestParam("page") int page, Model model) {
			if(boardService.updateReply(reply)>0) {
				model.addAttribute("board_num", reply.getBoard_num());
				model.addAttribute("page", page);
				return "redirect:bdetail.do";
			}else {
				model.addAttribute("message", reply.getBoard_num()+"번 글 수정 실패");
				return "common/error";
			}
		}
		
		// 게시원글 수정 요청 처리용
		@RequestMapping(value = "boriginup.do", method = RequestMethod.POST)
		public String boardUpdateMethod(Board origin, HttpServletRequest request, Model model,
				@RequestParam("page") int page,
				@RequestParam(name = "delflag", required = false) String delFlag,
				@RequestParam(name = "upfile", required = false) MultipartFile mfile) {

			// 업로드된 파일 저장 폴더 지정하기
			String savePath = request.getSession().getServletContext().getRealPath("resources/board_upfiles");

			// 원래 첨부파일이 있는데, 삭제를 선택한 경우
			if (origin.getBoard_original_filename() != null && delFlag != null && delFlag.equals("yes")) {
				// 저장 폴더에서 파일을 삭제함
				new File(savePath + "\\" + origin.getBoard_rename_filename()).delete();
				origin.setBoard_original_filename(null);
				origin.setBoard_rename_filename(null);
			}

			// 새로운 첨부파일이 있을때
			if (!mfile.isEmpty()) {
				// 저장폴더의 이전 파일은 삭제
				if (origin.getBoard_original_filename() != null) {
					// 저장 폴더에서 파일을 삭제함
					new File(savePath + "\\" + origin.getBoard_rename_filename()).delete();
					origin.setBoard_original_filename(null);
					origin.setBoard_rename_filename(null);
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

						origin.setBoard_rename_filename(renameFileName);
					} catch (Exception e) {
						e.printStackTrace();
						model.addAttribute("message", "전송파일 저장 실패.");
						return "common/error";
					}

				} // 업로드된 파일이 있다면...

				origin.setBoard_original_filename(mfile.getOriginalFilename());
				logger.info("boriginup.do : " + origin);
			}

			if (boardService.updateOrigin(origin) > 0) {
				model.addAttribute("page", page);
				model.addAttribute("board_num", origin.getBoard_num());
				return "redirect:bdetail.do?page="+page;
			} else {
				model.addAttribute("message", origin.getBoard_num() + "번 공지글 수정 실패.");
				return "common/error";
			}

		}

}
