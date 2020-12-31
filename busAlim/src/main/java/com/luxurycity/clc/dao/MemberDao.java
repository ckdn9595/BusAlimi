package com.luxurycity.clc.dao;

import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.luxurycity.clc.vo.*;

public class MemberDao {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	// 로그인 검사 전담 처리 함수(영선)
	public int loginCnt(MemberVO mVO) {
		return sqlSession.selectOne("mSQL.loginCnt", mVO);
	}
	
	// 로그인시 관리자인지 확인 처리 함수(진우)
	public int managerCnt(MemberVO mVO) {
		return sqlSession.selectOne("mSQL.manager", mVO);
	}
	
	// 메인에 표시되는 아바타 데이터 가져오기 전담 처리 함수(영선)
	public AvatarVO getAvt(String id) {
		return sqlSession.selectOne("mSQL.getMembAvt", id);
	}
	// 조인아이디체크(지우)
	public int idCheck(String id) {
		return sqlSession.selectOne("mSQL.idCheck", id);
	}
	// 조인아바타(지우)
	public List<AvatarVO> getAvtList() {
		return sqlSession.selectList("mSQL.getAvt");
	}
	// 조인질문(지우)
	public List<FindVO> getQuest() {
		return sqlSession.selectList("mSQL.getQuest");
	}
	// 조인proc_member add(지우)
	@Transactional
	public int addMemb(MemberVO mVO, FindVO fVO) {
		int cnt = 0;
		//멤버 add할떄 find도 add해야하므로 트랜잭션 처리함(동시 조인할 경우 꼬임 방지)
		cnt += sqlSession.insert("mSQL.addMemb", mVO);
		cnt += addFind(fVO);
		
		return cnt;
	}
	// 조인proc_find add(지우)
	public int addFind(FindVO fVO) {
		return sqlSession.insert("mSQL.addFind", fVO);
	}
	
	// 회원정보 페이지 폼 요청 아바타부분(성환)
	public AvatarVO myInfo1(String id) {
		return sqlSession.selectOne("mSQL.getMembAvt", id);
	}
	
	// 회원정보 페이지 폼 요청 회원부분(성환)
	public MemberVO myInfo2(String id) {
		return sqlSession.selectOne("mSQL.getMembInfo", id);
	}
	
	// 회원정보 변겅 전담 처리함수(성환)
	public int myInfoEdit(MemberVO mVO) {
		return sqlSession.update("mSQL.editMemb", mVO);
	}
	
	// 회원정보 삭제 전담 처리함수(성환)
	public int myInfoDel(MemberVO mVO) {
		return sqlSession.update("mSQL.delMemb", mVO);
	}
	 
}
