package com.test.firstspring.board.model.service;

import java.util.ArrayList;

import com.test.firstspring.board.model.vo.Board;
import com.test.firstspring.common.Paging;

public interface BoardService {
	ArrayList<Board> selectTop3();
	int selectListCount();
	ArrayList<Board> selectList(Paging page);
	Board selectBoard(int board_num);		//글 상세보기
	int updateAddReadCount(int board_num);	//조회수 1증가
	int insertOriginBoard(Board board);
	int insertReply(Board reply);
	int updateReplySeq(Board reply);	//기존 댓글 순번 1증가 처리
	int updateOrigin(Board board);
	int updateReply(Board reply);
	int deleteBoard(Board board);
}
