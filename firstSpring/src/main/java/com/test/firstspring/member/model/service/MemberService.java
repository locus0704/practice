package com.test.firstspring.member.model.service;

import java.util.ArrayList;

import com.test.firstspring.member.model.vo.Member;
import com.test.firstspring.member.model.vo.SearchDate;

public interface MemberService {
	Member selectLogin(Member member);
	int insertMember(Member member);
	int updateMember(Member member);
	int deleteMember(String userid);
	ArrayList<Member> selectList();
	Member selectMember(String userid);
	int updateLoginOk(Member member);
	ArrayList<Member> selectSearchUserid(String keyword);
	ArrayList<Member> selectSearchGender(String keyword);
	ArrayList<Member> selectSearchAge(int age);
	ArrayList<Member> selectSearchEnrollDate(SearchDate searchdate);
	ArrayList<Member> selectSearchLoginOk(String keyword);
	int selectCheckId(String userid);
}
