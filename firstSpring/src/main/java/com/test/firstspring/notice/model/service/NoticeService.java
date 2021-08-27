package com.test.firstspring.notice.model.service;

import java.util.ArrayList;

import com.test.firstspring.common.SearchDate;
import com.test.firstspring.notice.model.vo.Notice;

public interface NoticeService {
	ArrayList<Notice> selectAll();
	Notice selectNotice(int nid);
	int insertNotice(Notice notice);
	int updateNotice(Notice notice);
	int deleteNotice(int nid);
	ArrayList<Notice> selectNewTop3();
	ArrayList<Notice> selectSearchTitle(String keyword);
	ArrayList<Notice> selectSearchWriter(String keyword);
	ArrayList<Notice> selectSearchDate(SearchDate dates);

}
