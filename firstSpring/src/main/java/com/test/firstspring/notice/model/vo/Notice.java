package com.test.firstspring.notice.model.vo;

import java.sql.Date;

//컨트롤러 클래스의 메소드에서 매개변수로 command 객체를 이용하려면,
//database table column 명 == vo field명 == form input 태그 name
public class Notice implements java.io.Serializable {
	private static final long serialVersionUID = 112L;
	
	private int noticeno;
	private String noticetitle;
	private java.sql.Date noticedate;
	private String noticewriter;
	private String noticecontent;
	private String original_filepath;
	private String rename_filepath;
	
	public Notice() {}

	public Notice(int noticeno, String noticetitle, Date noticedate, String noticewriter, String noticecontent,
			String originalfilepath, String renamefilepath) {
		super();
		this.noticeno = noticeno;
		this.noticetitle = noticetitle;
		this.noticedate = noticedate;
		this.noticewriter = noticewriter;
		this.noticecontent = noticecontent;
		this.original_filepath = originalfilepath;
		this.rename_filepath = renamefilepath;
	}

	public int getNoticeno() {
		return noticeno;
	}

	public void setNoticeno(int noticeno) {
		this.noticeno = noticeno;
	}

	public String getNoticetitle() {
		return noticetitle;
	}

	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle;
	}

	public java.sql.Date getNoticedate() {
		return noticedate;
	}

	public void setNoticedate(java.sql.Date noticedate) {
		this.noticedate = noticedate;
	}

	public String getNoticewriter() {
		return noticewriter;
	}

	public void setNoticewriter(String noticewriter) {
		this.noticewriter = noticewriter;
	}

	public String getNoticecontent() {
		return noticecontent;
	}

	public void setNoticecontent(String noticecontent) {
		this.noticecontent = noticecontent;
	}

	public String getOriginal_filepath() {
		return original_filepath;
	}

	public void setOriginal_filepath(String original_filepath) {
		this.original_filepath = original_filepath;
	}

	public String getRename_filepath() {
		return rename_filepath;
	}

	public void setRename_filepath(String rename_filepath) {
		this.rename_filepath = rename_filepath;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Notice [noticeno=" + noticeno + ", noticetitle=" + noticetitle + ", noticedate=" + noticedate
				+ ", noticewriter=" + noticewriter + ", noticecontent=" + noticecontent + ", originalfilepath="
				+ original_filepath + ", renamefilepath=" + rename_filepath + "]";
	}

	
}
