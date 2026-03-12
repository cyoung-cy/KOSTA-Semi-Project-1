package controller;

import dto.Member;
import service.MemberService;
import view.StartView;

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
			// 총 관리자는 AdminView 보이도록
			String verifiedUserRole = member.getRole();
			if("admin".equals(verifiedUserRole)) StartView.printAdminMenu(verifiedUserRole);
			// 일반 유저는 UserMenuView 보이도록
			else StartView.printUserMenu(verifiedUserRole);
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

}
