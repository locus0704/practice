package com.test.firstspring.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.firstspring.common.SearchDate;
import com.test.firstspring.member.model.vo.Member;

@Repository("memberDao")
public class MemberDao {

	@Autowired
	private SqlSessionTemplate session;	//Spring DI
	
	public Member selectLogin(Member member) {
		//System.out.println("dao : " + member);
		return session.selectOne("memberMapper.selectLogin", member);
	}
	
	public int insertMember(Member member) {
		return session.insert("memberMapper.insertMember", member);
	}
	
	public int updateMember(Member member) {
		return session.update("memberMapper.updateMember", member);
	}
	
	public int deleteMember(String userid) {
		return session.delete("memberMapper.deleteMember", userid);
	}

	public Member selectMember(String userid) {
		return session.selectOne("memberMapper.selectMember", userid);
	}
	
	public ArrayList<Member> selectList(){
		List<Member> list = session.selectList("memberMapper.selectList");
		return (ArrayList<Member>)list;
	}

	public int updateLoginOK(
			Member member) {
		return session.update("memberMapper.updateLoginOK", member);
	}

	public ArrayList<Member> selectSearchUserId(
			String keyword) {
		List<Member> list = session.selectList(
				"memberMapper.selectSearchUserId", keyword);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchGender(
			String keyword) {
		List<Member> list = session.selectList(
				"memberMapper.selectSearchGender", keyword);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchAge(
			int age) {
		List<Member> list = session.selectList(
				"memberMapper.selectSearchAge", age);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchEnrollDate(
			SearchDate sdate) {
		List<Member> list = session.selectList(
				"memberMapper.selectSearchEnrollDate", sdate);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchLoginOK(
			String keyword) {
		List<Member> list = session.selectList(
				"memberMapper.selectSearchLoginOK", keyword);
		return (ArrayList<Member>)list;
	}

	public int selectCheckId(String userid) {
		return session.selectOne(
				"memberMapper.selectCheckId", userid);
	}
}
