package com.test.firstspring.board.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.firstspring.board.model.vo.Board;
import com.test.firstspring.common.Paging;

@Repository("boardDao")
public class BoardDao {
	@Autowired
	SqlSessionTemplate sqlSession;

	public ArrayList<Board> selectTop3() {
		List<Board> list = sqlSession.selectList("boardMapper.selectTop3");
		return (ArrayList<Board>)list;
	}

	public int selectListCount() {
		return sqlSession.selectOne("boardMapper.getListCount");
	}

	public ArrayList<Board> selectList(Paging page) {
		List<Board> list = sqlSession.selectList("boardMapper.selectList", page);
		return (ArrayList<Board>)list;
	}

	public Board selectBoard(int board_num) {
		return sqlSession.selectOne("boardMapper.selectBoard", board_num);
	}

	public int updateAddReadCount(int board_num) {
		return sqlSession.update("boardMapper.updateReadCount", board_num);
	}

	public int insertOriginBoard(Board board) {
		return sqlSession.insert("boardMapper.insertOriginBoard", board);
	}

	public int insertReply(Board reply) {
		return sqlSession.insert("boardMapper.insertReplyBoard", reply);
		
//		int result =0;
//		if (reply.getBoard_reply_lev() == 1) { //댓글
//			result = sqlSession.insert("boardMapper.insertReply1", reply);
//		}
//		if (reply.getBoard_reply_lev() == 2) { //대댓글
//			result = sqlSession.insert("boardMapper.insertReply2", reply);
//		}
//		return result;
	}

	public int updateReplySeq(Board reply) {
		int result =0;
		if (reply.getBoard_reply_lev() == 1) { //댓글
			result = sqlSession.update("boardMapper.updateReplySeq1", reply);
		}
		if (reply.getBoard_reply_lev() == 2) { //대댓글
			result = sqlSession.update("boardMapper.updateReplySeq2", reply);
		}
		return result;
	}

	public int updateOrigin(Board board) {
		return sqlSession.update("boardMapper.updateOrigin", board);
	}

	public int updateReply(Board reply) {
		return sqlSession.update("boardMapper.updateReply", reply);
	}

	public int deleteBoard(Board board) {
		return sqlSession.delete("boardMapper.deleteBoard", board);
	}
}
