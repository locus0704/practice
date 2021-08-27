package com.test.firstspring.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.firstspring.board.model.service.BoardService;
import com.test.firstspring.board.model.vo.Board;
import com.test.firstspring.notice.model.vo.Notice;

@Controller
public class BoardController {

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
			// notice 저장용 json 객체 생성
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
	}
}
