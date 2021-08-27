package com.test.firstspring.board.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.firstspring.board.model.dao.BoardDao;
import com.test.firstspring.board.model.vo.Board;
import com.test.firstspring.common.Paging;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	BoardDao boardDao;
	
	@Override
	public ArrayList<Board> selectTop3() {
		return boardDao.selectTop3();
	}

	@Override
	public int selectListCount() {
		return boardDao.selectListCount();
	}

	@Override
	public ArrayList<Board> selectList(Paging page) {
		return boardDao.selectList(page);
	}

	@Override
	public Board selectBoard(int board_num) {
		return boardDao.selectBoard(board_num);
	}

	@Override
	public int updateAddReadCount(int board_num) {
		return boardDao.updateAddReadCount(board_num);
	}

	@Override
	public int insertOriginBoard(Board board) {
		return boardDao.insertOriginBoard(board);
	}

	@Override
	public int insertReply(Board reply) {
		return boardDao.insertReply(reply);
	}

	@Override
	public int updateReplySeq(Board reply) {
		return boardDao.updateReplySeq(reply);
	}

	@Override
	public int updateOrigin(Board board) {
		return boardDao.updateOrigin(board);
	}

	@Override
	public int updateReply(Board reply) {
		return boardDao.updateReply(reply);
	}

	@Override
	public int deleteBoard(Board board) {
		return boardDao.deleteBoard(board);
	}

}
