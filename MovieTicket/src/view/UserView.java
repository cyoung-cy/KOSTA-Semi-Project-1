package view;

import java.util.List;
import java.util.Scanner;

import controller.InquiryController;
import controller.MemberController;
import controller.ReviewController;
import controller.TicketController;
import dto.Member;
import dto.Movie;

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
					TicketController.getTicketsInfo(member.getMemberId());
            		break;
            	case 3:
            		//리뷰 내역 조회
					ReviewController.selectReviews(member);
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


	/**
	 * 20260314
	 * 이동혁
	 * 문의 뷰
	 * @param member
	 */
	public static void inquiry(Member member) {
		while(true) {
			System.out.println("=============================================================");
			System.out.println("                       [문의]");
			System.out.println("=============================================================");
			System.out.println("                      [1] 문의하기");
			System.out.println("                      [2] 문의 내역 조회");
			System.out.println("                      [3] 뒤로가기");
			System.out.println("=============================================================");

			System.out.println("메뉴를 선택하세요 : ");
			int menu = Integer.parseInt(sc.nextLine());

			switch(menu) {
				case 1:
					System.out.println("문의 내용을 입력해주세요. : ");
					String content = sc.nextLine();
					System.out.println("문의 구분을 입력해주세요. (예: MOVIE, SYSTEM, PAYMENT, ETC) : ");
					String category = sc.nextLine();
					System.out.println("문의 제목을 입력해주세요. : ");
					String title = sc.nextLine();
					InquiryController.insertInquiry(member, content, category, title);
					break;
				case 2:
					InquiryController.selectInquiryByMember(member);
					System.out.println("상세 조회할 문의 번호를 입력해주세요.(처리된 문의만 가능) : ");
					int inquiryId = Integer.parseInt(sc.nextLine());
					InquiryController.selectInquiryDetailByMember(inquiryId, member);
					break;
				case 3:
					StartView.printUserMenu(member);
					return;
			}
		}
	}

	/*
	* 20260315
	* 이동혁
	* 영화 추천 후 선택 View
	 */
	public static void recommendationMovie(List<Movie> list) {
		while(true) {
			EndView.printAllMovies(list); // 간략한 목록 출력용
			System.out.println("=============================================================");
			System.out.println("                       [영화 추천]");
			System.out.println("=============================================================");
			System.out.println("                      [1] 영화 예매하기");
			System.out.println("                      [2] 영화 리뷰보기");
			System.out.println("                      [3] 뒤로가기");
			System.out.println("=============================================================");
			int menu = Integer.parseInt(sc.nextLine());
			switch(menu) {
				case 1:
					// TODO: 영화 예매 뷰 호출
					break;
				case 2:
					System.out.println("리뷰를 볼 영화를 선택해주세요. : ");
					int movieId = Integer.parseInt(sc.nextLine());
					// 영화	리뷰 조회 컨트롤러 호출
					ReviewController.selectReviewsByMovie(movieId);
					break;
				case 3:
					return;
			}
		}
	}
}
