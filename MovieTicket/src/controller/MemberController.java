package controller;

import dto.Member;
import service.MemberService;
import view.EndView;
import view.FailView;
import view.StartView;

import java.sql.SQLException;
import java.util.List;

public class MemberController {
    static MemberService memberService = new MemberService();

    /*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 회원가입 컨트롤러
	 * */

	/*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 회원가입 컨트롤러
	 * */

    /*
     * 0311
     * 김채영
     * TODO: Member 로그인 컨트롤러
     * */
    public static void login(String userId, String password) {
        try{
            Member member = memberService.login(userId, password);
            if("admin".equals(member.getRole())){
                StartView.printAdminMenu(userId);
            }else {
                StartView.printUserMenu(userId);
            }
        }catch (Exception e){
            FailView.errorMessage(e.getMessage());
            //StartView 이동
        }
    }

    /*
     * 0311
     * 김채영
     * TODO: 전체 사용자 목록 조회
     * */
    public static void selectUsers() {
        try{
            List<Member> list = memberService.userSelect();
            EndView.printUserList(list);
        }catch (Exception e){
            FailView.errorMessage(e.getMessage());
            //stratView 이동
        }

    }
}
