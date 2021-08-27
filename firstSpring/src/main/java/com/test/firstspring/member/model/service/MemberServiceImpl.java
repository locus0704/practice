package com.test.firstspring.member.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.firstspring.common.SearchDate;
import com.test.firstspring.member.model.dao.MemberDao;
import com.test.firstspring.member.model.vo.Member;

@Service("memberService")	//MemberService memberService = new MemberService()
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao memberDao;

	@Override
	public Member selectLogin(Member member) {
		return memberDao.selectLogin(member);
	}

	@Override
	public int insertMember(Member member) {
		return memberDao.insertMember(member);
	}

	@Override
	public int updateMember(Member member) {
		return memberDao.updateMember(member);
	}

	@Override
	public int deleteMember(String userid) {
		return memberDao.deleteMember(userid);
	}

	@Override
	public ArrayList<Member> selectList() {
		return memberDao.selectList();
	}

	@Override
	public Member selectMember(String userid) {
		return memberDao.selectMember(userid);
	}

	@Override
	public int updateLoginOk(Member member) {
		return memberDao.updateLoginOK(member);
	}

	@Override
	public ArrayList<Member> selectSearchUserid(String keyword) {
		return memberDao.selectSearchUserId(keyword);
	}

	@Override
	public ArrayList<Member> selectSearchGender(String keyword) {
		return memberDao.selectSearchGender(keyword);
	}

	@Override
	public ArrayList<Member> selectSearchAge(int age) {
		return memberDao.selectSearchAge(age);
	}

	@Override
	public ArrayList<Member> selectSearchEnrollDate(SearchDate searchdate) {
		return memberDao.selectSearchEnrollDate(searchdate);
	}

	@Override
	public ArrayList<Member> selectSearchLoginOk(String keyword) {
		return memberDao.selectSearchLoginOK(keyword);
	}

	@Override
	public int selectCheckId(String userid) {
		return memberDao.selectCheckId(userid);
	}
	
	

}
