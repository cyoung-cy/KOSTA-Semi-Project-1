package service;

import dao.MemberDAO;
import dao.MemberDAOImpl;
import dto.Member;
import exception.NotFoundException;
import session.Session;
import session.SessionSet;

import java.sql.SQLException;
import java.util.List;

public class MemberService {
    MemberDAO memberDao = new MemberDAOImpl();

    /*
     * 20260311
     * 김채영
     * TODO: 전체 사용자 목록 조회
     * */
    public List<Member> userSelect() throws SQLException, NotFoundException {
        List<Member> list = memberDao.goodsSelect();
        if(list.size() == 0)throw new NotFoundException("사용자가 없습니다.");
        return list;
    }

    /*
     * 20260311
     * 김채영
     * TODO:멤버 로그인 서비스
     * */
    public Member login(String userId, String password) throws SQLException, NotFoundException {
        Member member = memberDao.login(userId, password);
        if(member == null){
            throw new NotFoundException("등록된 정보가 없습니다.");
        }

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

	/*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 회원가입 서비스
	 * */

	
}
