package com.test.firstspring.common;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service	//서비스 클래스에 적용, aspect 사용을 위해서 반드시 같이 붙여야함
@Aspect	//aop 를 의미함
public class AfterAdvice {
	private Logger logger = LoggerFactory.getLogger(getClass());
//	private Logger logger = LoggerFactory.getLogger(AfterAdvice.class);
	
	//포인트컷 설정에 사용되는 메소드 작성 : 내용없이 작성해야 함
	@Pointcut("execution(* com.test.firstspring..*Impl.*(..))")
	public void implPointcut() {}
	
	//포인트컷이 설정한 위치에 위빙될 어드바이스 코드 작성용 메소드
	@After("implPointcut()")
	public void finallyLog() {
		//예외발생 상관없이 무조건 실해되는 어드바이스임
		logger.info("*Impl 클래스 메소드 실행 후 작동 : 서비스 로직 수행이 종료된 후 무조건 작동됨");
	}
	
}
