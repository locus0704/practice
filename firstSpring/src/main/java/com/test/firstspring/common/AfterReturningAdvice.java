package com.test.firstspring.common;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.test.firstspring.member.model.vo.Member;

@Repository	//dao에 적용하는
@Aspect		//aop 를 의미
public class AfterReturningAdvice {
	//로그인 메소드에 대한 로그 처리를 목적으로 하는 어드바이스
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Pointcut("execution(* com.test.firstspring.member.model.dao.MemberDao.selectLogin(..))")
	public void loginPointcut() {}
	
	@AfterReturning(pointcut="loginPointcut()", returning="returnObj")
	public void loginLogging(JoinPoint jp, Object returnObj) {
		//비즈니스 메소드가 리턴할 결과 데이터를 다른 용도로 처리할 때 사용할 수 있음
		if(returnObj instanceof Member) {
			Member member = (Member)returnObj;
			logger.info(new Date()+"$"+member.getUserid()+"$님이 접속하였습니다.");
		}
	}
}
