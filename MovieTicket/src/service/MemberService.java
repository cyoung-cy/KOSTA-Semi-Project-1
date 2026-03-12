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
     * 20260312
     * 김채영
     * TODO: 전체 사용자 목록 조회
     * */
    public List<Member> selectUsers() throws NotFoundException, SQLException{
        List<Member> list = memberDao.selectUsers();
        if(list.size()==0)throw new NotFoundException("현재 상품이 없습니다.");
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
     * 김채영
     * TODO: 사용자 삭제 서비스
     * */
    public String deleteUserByName(String name) throws SQLException, NotFoundException{
        String user = memberDao.deleteUserByName(name);
        if(user == null){
            throw new NotFoundException("사용자가 삭제되지 않았습니다.");
        }
        return null;

    }

    /*
     * 20260312
     * 김채영
     * TODO: 전체 사용자 상세목록 조회
     * */
    public List<Member> selectUserDetail(String name) throws NotFoundException {
        List<Member> list = memberDao.selectUserDetail(name);
        if(list.size()==0) throw new NotFoundException("현재 상품이 없습니다.");
        return list;
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
