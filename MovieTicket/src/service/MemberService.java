package service;

import dao.MemberDAO;
import dao.impl.MemberDAOImpl;
import dto.Member;
import exception.ExistedException;
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
     * 
     * 20260313
     * 이동혁
     * SessionSet에 사용자 정보 저장 기능 구현
     * */
    public Member login(String userId, String password) throws SQLException, NotFoundException {
        Member member = memberDao.login(userId, password);
        if(member == null){
            throw new NotFoundException("등록된 정보가 없습니다.");
        }

        Session session = new Session(member.getMemberId(), member.getUserId());

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
            System.out.println("\'" + name +"\' 회원이 없습니다.");
            throw new NotFoundException("회원이 삭제되지 않았습니다.");
        }
        return null;

    }
    
    /*
     * 20260313
     * 이동혁
     * TODO: 사용자 회원 탈퇴 서비스
     */
    public void deleteMemberByMemberId(int memberId) throws SQLException, NotFoundException {
    	int result = memberDao.deleteByMemberId(memberId);
    	if(result == 0) throw new NotFoundException("찾을 수 없는 회원 정보입니다.");
    }

    /*
     * 20260312
     * 김채영
     * TODO: 전체 사용자 상세목록 조회
     * */
    public List<Member> selectUserDetail(String userId) throws NotFoundException {
        List<Member> list = memberDao.selectUserDetail(userId);
        if(list.size()==0) {
            System.out.println("\'" + userId +"\' 회원이 없습니다.");
            throw new NotFoundException("현재 회원이 없습니다.");
        }
        return list;
    }

    /*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 회원가입 서비스
	 * */
    public void register(
    		Member member
    	) throws ExistedException, SQLException {

    	int result = memberDao.register(member);
    	if(result == 0) throw new ExistedException("이미 존재하는 사용자 정보입니다.");

    }
    
    /*
     * 20260313
     * 이동혁
     * TODO: 사용자 정보 수정 서비스
     */
    public void updateUser(Member member) throws SQLException, NotFoundException{
    	int result = memberDao.updateMember(member);
    	if(result == 0) throw new NotFoundException("사용자 업데이트에 실패했습니다.");
    }

	
}
