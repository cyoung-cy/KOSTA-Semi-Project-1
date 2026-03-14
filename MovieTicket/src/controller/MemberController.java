package controller;

import dto.Member;
import service.MemberService;
import session.Session;
import session.SessionSet;
import view.AdminView;
import view.EndView;
import view.FailView;
import view.StartView;

import java.util.List;

public class MemberController {
	static MemberService memberService = new MemberService();
	/*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 로그인 컨트롤러
	 * */
	public static void login(String userId, String password) {
		try {
			// 로그인 로직
			Member member = memberService.login(userId, password);
			SessionSet sessionSet = SessionSet.getInstance();
			Session session = new Session(member.getMemberId(), member.getUserId());
			sessionSet.add(session);
			// 총 관리자는 AdminView 보이도록
			String verifiedUserRole = member.getRole();
			if("admin".equals(verifiedUserRole)) StartView.printAdminMenu(member);
			// 일반 유저는 UserMenuView 보이도록
			else StartView.printUserMenu(member);
		} catch(Exception e) { 
			e.printStackTrace();
			// 이후에 StartView로 가도록 처리
		}
	}


    /*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 회원가입 컨트롤러
	 * */
	public static void register(
			String userId,
			String password,
			String name,
			String phone,
			String address,
			String birth,
			List<String> preferredGenre,
			String cardInfo
	) {
		try {
			Member member = new Member(0,    			
				userId,
    			password,
    			name,
    			phone,
    			address,
    			birth,
    			preferredGenre,
    			cardInfo,
    			"user");
			memberService.register(member);
			
		} catch (Exception e) {
			e.printStackTrace();
//			FailView.errorMessage(e.getMessage());
		}
	}


	/*
	 * 20260312
	 * 김채영
	 * TODO: 사용자 목록 조회
	 * */
    public static void selectUsers(Member member) {
		try{
			List<Member> list = memberService.selectUsers();
			EndView.printUserShort(list);
			AdminView.userManage(member);
		}catch (Exception e){
			e.printStackTrace();
			//startview 이동
		}
    }

	/*
	 * 20260312
	 * 김채영
	 * TODO: 사용자 삭제
	 * */
	public static void deleteUserByName(String name) {
		try{
			String user  = memberService.deleteUserByName(name);
			EndView.deleteUser(name);
		}catch(Exception e){
			//e.printStackTrace();
			//에러 페이지이동...
			e.getMessage();
		}

	}

	/*
	 * 20260312
	 * 김채영
	 * TODO: 사용자 상세 목록 조회
	 * */
	public static void selectUserDetail(String userId) {
		try{
			List<Member> list = memberService.selectUserDetail(userId);
			EndView.printUserList(list);
		}catch (Exception e){
			e.printStackTrace();
			//AdminView로 이동
		}
	}
	
	/*
	 * 20260313
	 * 이동혁
	 * TODO: 사용자 탈퇴
	 */
	public static void deleteUserByMemberId(Member member) {
		try {
			memberService.deleteMemberByMemberId(member.getMemberId());
        	Session session = new Session(member.getMemberId(), member.getUserId());
            SessionSet ss = SessionSet.getInstance();
            ss.remove(session);
            EndView.deleteUserByMemberId();
			//처음 시작 View로 이동
			StartView.menu();
		} catch (Exception e) {
//			e.printStackTrace();
			FailView.errorMessage(e.getMessage());
			//StartView의 printUserMenu로 이동
			StartView.printUserMenu(member);
		}
	}
	
	/*
	 * 20260313
	 * 이동혁
	 * TODO: 사용자 정보 수정
	 */
	public static void updateUser(Member member) {
		try {
			memberService.updateUser(member);
			EndView.updateUser();
		} catch (Exception e) {
//			e.printStackTrace();
			FailView.errorMessage(e.getMessage());
		}
	}
}
