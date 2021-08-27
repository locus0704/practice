package com.test.firstspring.notice.model.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.firstspring.common.SearchDate;
import com.test.firstspring.notice.model.vo.Notice;

@Repository("noticeDao")
public class NoticeDao {
	@Autowired
	private SqlSessionTemplate sqlSession; 
	
	//공지사항 전체조회 : 공지사항 목록보기용
	public ArrayList<Notice> selectList(){
		List<Notice> list = sqlSession.selectList("noticeMapper.selectList");
		return (ArrayList<Notice>)list;
	}
	
	//공지글번호로 한 개 조회 : 공지사항 상세보기용
	public Notice selectOne(int noticeNo) {
		return sqlSession.selectOne("noticeMapper.selectOne", noticeNo);
	}
	
	//새 공지글 등록
	public int insertNotice(Notice notice) {
		return sqlSession.insert("noticeMapper.insertNotice", notice);
	}
	
	//공지글 수정
	public int updateNotice(Notice notice) {
		return sqlSession.update("noticeMapper.updateNotice", notice);
	}
	
	//공지글 삭제
	public int deleteNotice(int noticeNo) {
		return sqlSession.delete("noticeMapper.deleteNotice", noticeNo);
	}
	
	//새 공지글 3개를 리턴 : 작성날짜로 top-3 분석 이용함
	public ArrayList<Notice> selectNewTop3(){
		List<Notice> list = sqlSession.selectList("noticeMapper.selectNewTop3");
		return (ArrayList<Notice>)list;
	}

	public ArrayList<Notice> selectSearchTitle(String keyword) {
		List<Notice> list = sqlSession.selectList("noticeMapper.selectSearchTitle", keyword);
		return (ArrayList<Notice>)list;
	}

	public ArrayList<Notice> selectSearchWriter(String keyword) {
		List<Notice> list = sqlSession.selectList("noticeMapper.selectSearchWriter", keyword);
		return (ArrayList<Notice>)list;
	}

	public ArrayList<Notice> selectSearchDate(SearchDate dates) {
		List<Notice> list = sqlSession.selectList("noticeMapper.selectSearchDate", dates);
		return (ArrayList<Notice>)list;
	}
}







