package com.luxurycity.clc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.service.*;
import com.luxurycity.clc.vo.*;
import java.util.*;
import com.luxurycity.clc.util.*;

@Controller
@RequestMapping("/member")
public class Member {
	@Autowired
	MemberDao mDao;
	@Autowired
	FindDao fDao;
	@Autowired
	BookmarkDao bmDao;
	@Autowired
	MemberService mService;

//	private static final Logger membLog = LoggerFactory.getLogger(Member.class);
	
	@RequestMapping("/login.clc")
	public ModelAndView login(ModelAndView mv) {
		mv.setViewName("member/Login");
		return mv;
	}
	
	
	// 관리자페이지 수정
	@RequestMapping("/loginProc.clc")
	public ModelAndView loginProc(ModelAndView mv, HttpSession session, MemberVO mVO) {
		int cnt = 0;
		int mCnt = 0;
		// 계정 검사
		cnt = mDao.loginCnt(mVO);
		mCnt = mDao.managerCnt(mVO);
		
		// 존재하지 않는 계정이라면
		if(cnt == 0) {
			// 로그인 페이지로 다시 리다이렉트
			mv.setViewName("redirect:/member/login.clc");
			return mv;
		}
		// 존재하는 계정이면
		// 계정에 해당하는 아바타 가져오고
		AvatarVO aVO = mDao.getAvt(mVO.getId());
		if(mCnt == 0) {
			// 세션에 아이디 부여하고
			session.setAttribute("SID", mVO.getId());
		} else if(mCnt == 1) {
			session.setAttribute("MID", mVO.getId());
		}
		// 세션에 아바타 부여하고
		session.setAttribute("AVT", aVO);
//		membLog.info(mVO.getId() + " ] login");
		// 메인페이지로 리다이렉트
		mv.setViewName("redirect:/main.clc");
		return mv;
	}
	
	@RequestMapping("/logout.clc")
	public ModelAndView logout(ModelAndView mv, HttpSession session) {
		// 아바타와 아이디 세션에서 삭제하고
		String sid = (String)session.getAttribute("SID");
		if(sid == null) {
			sid = (String)session.getAttribute("MID");
		}
//		membLog.info(sid + " ] logout");
		session.removeAttribute("SID");
		session.removeAttribute("MID");
		session.removeAttribute("AVT");
		
		// 뷰 설정하고
		mv.setViewName("redirect:/main.clc");
		return mv;
	}
	@RequestMapping("/idCheck.clc")
	@ResponseBody
	public String idCheck(ModelAndView mv, String id) {
		String result = "OK";
		int cnt = 0 ; 
		try {
			cnt = mDao.idCheck(id);
		}catch(Exception e) {}
		System.out.println("나오나요?" + cnt);
		if(cnt != 0) {
			//이경우 이용중인 id가 이미 있는거임
			result  = "NO";
		}
		return result;
	}
	@RequestMapping("/join.clc")
	public ModelAndView join(ModelAndView mv ) {
		//아바타랑 질문 리스트 넘기기
		mv.addObject("LIST", mDao.getAvtList());
		mv.addObject("QUE", mDao.getQuest());
		
		// 뷰 설정하고
		mv.setViewName("member/Join");
		return mv;
	}
	@RequestMapping("/joinProc.clc")
	public ModelAndView joinProc(ModelAndView mv, HttpSession session, FindVO fVO, MemberVO mVO) {
		int cnt = 0;
		//멤버 테이블 및 파인드 테이블에 인서트하기
		try {
			cnt = mDao.addMemb(mVO, fVO);
		}catch(Exception e) {}
		
		if(cnt == 2) {
			//이경우 정상적으로 두 테이블에 다들어감
			session.setAttribute("SID", mVO.getId());
			mv.setViewName("redirect:/main.clc");
		}else {
			System.out.println("인서트 잘못됨..");
			mv.setViewName("member/Join");
		}
		// 뷰 설정하고
		return mv;
	}
	
	@RequestMapping("/findpage.clc")
	public ModelAndView findPage(ModelAndView mv, String findType) {
		mv.addObject("TYPE", findType);
		
		if(findType.equals("pwCk")) {
			// 비밀번호 확인 질문 리스트
			List<FindVO> list = fDao.getQuestList();
			mv.addObject("LIST", list);
		}
		mv.setViewName("member/FindPage");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/findidpageproc.clc")
	public HashMap<String, String> findIdPageProc(String mail) {
		HashMap<String, String> map = new HashMap<String, String>();

		// 메일로 아이디 여부 검사하고
		int cnt = fDao.idCount(mail);
		if(cnt == 0) {
			// 일치하는 아이디가 없을 경우
			map.put("result", "NO");
		} else {
			// 일치하는 아이디가 있을 경우
			String id = fDao.findId(mail);
			map.put("result", "OK");
			map.put("id", id);
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/findpwpageproc.clc")
	public String findPwPageProc(@RequestBody FindVO fVO) {
		String result = "NO";
		int cnt = 0;
		cnt = fDao.findPw(fVO);
		if(cnt != 0) {
			result = "OK";
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/editpwproc.clc")
	public String editPwProc(@RequestBody FindVO fVO) {
		String result = "NO";
		int cnt = 0;
		cnt = fDao.editPw(fVO);
		if(cnt != 0) {
			result = "OK";
		}
		return result;
	}
	
	@RequestMapping("/mypage.clc")
	public ModelAndView myPage(ModelAndView mv, HttpSession session, PageUtil page, BookmarkVO bmVO) {
		// 세션 검사
		String id = (String) session.getAttribute("SID");
		Object obj = session.getAttribute("AVT");
		if(id == null || obj == null) {
			session.removeAttribute("SID");
			session.removeAttribute("AVT");
			mv.setViewName("redirect:/member/login.clc");
			return mv;
		}
		// id vo에 셋팅
		bmVO.setId(id);
		// 즐겨찾기 갯수(갯수가 더 많은 쪽) 계산하고 pageutil에 셋팅
		int total = bmDao.getTotal(id);
		page.setTotalCount(total);
		// pageutil 페이지 설정하고 vo에 셋팅
		page.setPage();
		bmVO.setPage(page);
		// dao 처리하고 결과 받고
		List<BookmarkVO> slist = bmDao.getStationList(bmVO);
		List<BookmarkVO> blist = bmDao.getBusList(bmVO);
		// 데이터 뷰에 심고
		mv.addObject("BLIST", blist);
		mv.addObject("SLIST", slist);
		mv.addObject("PAGE", page);
		
		mv.setViewName("member/MyPage");
		return mv;
	}
	
	@RequestMapping("/bookdelproc.clc")
	public ModelAndView bookDelProc(ModelAndView mv, HttpSession session, HttpServletRequest req, int nowPage) {
		// 세션 검사
		String id = (String) session.getAttribute("SID");
		Object obj = session.getAttribute("AVT");
		if(id == null || obj == null) {
			session.removeAttribute("SID");
			session.removeAttribute("AVT");
			mv.setViewName("redirect:/member/login.clc");
			return mv;
		}
		// 파라미터 받아오고 정수형으로 변환하고
		String[] sbmno = req.getParameterValues("dellist");
		int[] bmno = new int[sbmno.length];
		for(int i = 0; i < bmno.length; i++) {
			bmno[i] = Integer.parseInt(sbmno[i]);
		}
		
		// dao 처리하고 결과 받고
		int cnt = 0;
		cnt = bmDao.delBookmark(bmno);
		mv.setViewName("redirect:/member/mypage.clc?nowPage=" + nowPage);
		return mv;
	}
	
	
	@RequestMapping("/myinfo.clc")
	public ModelAndView myInfo(ModelAndView mv, MemberVO mVO, AvatarVO aVO, HttpSession session) {
		mService.setMyInfo(mv, mVO, aVO, session);
		return mv;
	}
	
	
	 
	@RequestMapping("/myinfoedit.clc")
	public ModelAndView myInfoEdit(ModelAndView mv, MemberVO mVO, HttpSession session) {
		mService.setMyInfoEdit(mv, mVO, session);
		return mv;
	}
  @RequestMapping("/myinfodel.clc")
	public ModelAndView myInfoDel(ModelAndView mv, MemberVO mVO, HttpSession session) {
		mService.setMyInfoDel(mv, mVO, session);
		return mv;
	}
	@ResponseBody
	@RequestMapping("bookaddproc.clc")
	public HashMap<String, String> bookAddproc(@RequestBody HashMap<String, Object> map, HttpSession session, BookmarkVO bmVO) {
		HashMap<String, String> map2 = mService.setBookAdd(map, session, bmVO);
		return map2;
	}
	
	@ResponseBody
	@RequestMapping("/bookdelprocAjax.clc")
	public HashMap<String, String> bookDelProcAjax(@RequestBody HashMap<String, Integer> map, HttpSession session) {
		HashMap<String, String> map2 = new HashMap<String, String>();
		
		String id = (String) session.getAttribute("SID");
		if(id == null) {
			map2.put("result", "LOGIN");
			return map2;
		}
		
		int cnt = 0;
		cnt = bmDao.delBookmark(map.get("bmno"));
		if(cnt == 0) {
			map2.put("result", "NO");
		} else {
			map2.put("result", "OK");
		}
		return map2;

	}

}