package com.luxurycity.clc.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.ModelAndView;

import com.luxurycity.clc.vo.MemberVO;



@Service
@Aspect
public class LogginAop {

	private static Logger log1 = LoggerFactory.getLogger(LogginAop.class);

	@Pointcut("execution(* com.luxurycity.clc.controller.Member.loginProc(..))")
	public void loginAspect() {}

	@After("loginAspect()")
	public void recordLog(JoinPoint join) {
		HttpSession session = (HttpSession) join.getArgs()[1];
		String sid = (String) session.getAttribute("SID");

		if(sid == null) {
			sid = (String) session.getAttribute("MID");
		}

		if(sid != null ) {
			log1.info(sid + " ] - ***** Login *****");
		}
	}

	@Pointcut("execution(* com.luxurycity.clc.controller.Member.logout(..))")
	public void logoutAspect() {}

	@Before("logoutAspect()")
	public void recordOut(JoinPoint join) {

		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String url = req.getRequestURL().toString();
		url = url.substring(url.lastIndexOf('/') + 1);

		HttpSession session = req.getSession();
		String sid = (String) session.getAttribute("SID");
		if(sid == null) {
			sid = (String) session.getAttribute("MID");
		}
		if(sid != null ) {
			switch(url) {
			case "logout.clc":
					log1.info(sid + " ] - ##### Logout #####");
				break;
			}
		}
	}
}