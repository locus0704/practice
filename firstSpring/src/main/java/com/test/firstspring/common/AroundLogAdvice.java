package com.test.firstspring.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service	//service 에 적용
@Aspect	//aop 를 의미
public class AroundLogAdvice {
	private Logger logger = LoggerFactory.getLogger(getClass());

	//포인트컷 설정
	@Pointcut("execution(* com.test.firstspring..*Impl.*(..))")
	public void servicePointcut() {}
	
	//target object 의 메소드가 실행되기 전부터 실행된 후까지의 처리를 하는 어드바이스
	@Around("servicePointcut()")
	public Object aroundLog(ProceedingJoinPoint pp) throws Throwable {
		String methodName = pp.getSignature().getName();
		
		//메소드 수행된 시간 등을 체크할 수 있음
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		//타겟 메소드가 구동되는 동안 기다림
		Object obj = pp.proceed();
		//타겟 메소드가 리턴될 때 스톱워치 멈추게 함
		stopWatch.stop();
		
		logger.info(methodName+"() 메소드 수행에 걸린 시간 : "+stopWatch.getTotalTimeMillis()+"(ms)초");
		return obj;
	}
}
