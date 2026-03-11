package service;

import dao.MemberDAO;
import dao.MemberDAOImpl;
import dto.Member;
import exception.NotFoundException;
import session.Session;
import session.SessionSet;

import java.sql.SQLException;

public class MemberService {
	MemberDAO memberDAO = new MemberDAOImpl();
	/*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 로그인 서비스
	 * */
	public Member login(String userId, String password) throws NotFoundException, SQLException {
		Member member = memberDAO.login(userId, password);
		if(member == null) {
			throw new NotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
		}

		// 로그인 된 정보 저장하기
		Session session = new Session(userId);
		SessionSet sessionSet = SessionSet.getInstance();
		sessionSet.add(session);

		return member;
	}

	/*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 회원가입 서비스
	 * */

	
}
