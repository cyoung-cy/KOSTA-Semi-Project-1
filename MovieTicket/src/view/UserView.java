package view;

import java.util.Scanner;

import controller.MemberController;
import dto.Member;

/*
 * 20260313
 * 이동혁
 * 사용자가 볼 수 있는 2차 뷰 모음
 */
public class UserView {
	private static Scanner sc = new Scanner(System.in);
	
	/*
	 * 20260313
	 * 이동혁
	 * 마이페이지 뷰
	 */
	public static void myPage(Member member) {
		while(true) {
            System.out.println("=============================================================");
            System.out.println("                       [마이 페이지]");
            System.out.println("=============================================================");
            System.out.println("                      [1] 내 정보 수정");
            System.out.println("                      [2] 예매 내역 조회");
            System.out.println("                      [3] 리뷰 내역 조회");
            System.out.println("                      [0] 이전으로 돌아가기");
            System.out.println("=============================================================");

            System.out.println("마이 페이지 메뉴를 선택하세요 : ");
            int menu = Integer.parseInt(sc.nextLine());
            switch(menu) {
            	case 1:
            		//내 정보 수정
            		UserView.updateUserInfo(member);
            		break;
            	case 2:
            		//예매 내역 조회
            		
            		break;
            	case 3:
            		//리뷰 내역 조회
            		break;
            	case 0:
            		StartView.printUserMenu(member);
            }
		}
	}
	
	private static void updateUserInfo(Member member) {
		while(true) {
			System.out.println("=============================================================");
	        System.out.println("                       [회원 정보 수정]");
	        System.out.println("=============================================================");
	        System.out.println("                      [1] 비밀번호 변경");
	        System.out.println("                      [2] 카드번호 변경");
	        System.out.println("                      [3] 전화번호 변경");
	        System.out.println("                      [4] 전체 변경 사항 반영");
	        System.out.println("=============================================================");

	        System.out.println("메뉴를 선택하세요 : ");
			int menu = Integer.parseInt(sc.nextLine());
			
			switch(menu) {
			case 1:
				System.out.println("변경할 비밀번호를 입력해주세요.");
				String password = sc.nextLine();
				member.setPassword(password);
				break;
			case 2:
				System.out.println("변경할 카드번호를 입력해주세요.");
				String cardInfo = sc.nextLine();
				member.setCardInfo(cardInfo);
				break;
			case 3:
				System.out.println("변경할 전화번호를 입력해주세요.");
				String phone = sc.nextLine();
				member.setPhone(phone);
				break;
			case 4: 
				MemberController.updateUser(member);
				return;
			}
		}
	}
	
	
}
