package view;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import controller.*;
import dto.Member;
import dto.Movie;
import util.ValidateUtil;

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
			ConsoleUI.blank(1);
			ConsoleUI.printHeader("MY PAGE", null, ConsoleUI.RED, ConsoleUI.YELLOW);

			ConsoleUI.printMenu(new String[]{
					"[1] 내 정보 수정",
					"[2] 예매 내역 조회",
					"[3] 리뷰 내역 조회",
					"[0] 이전으로 돌아가기"
			}, null);

			int menu = ConsoleUI.promptInt(sc, "마이 페이지 메뉴를 선택하세요");
            switch(menu) {
            	case 1:
            		//내 정보 수정
            		UserView.updateUserInfo(member);
            		break;
            	case 2:
            		//예매 내역 조회
					ConsoleUI.info("예매 내역을 불러옵니다.");
					TicketController.getTicketsInfo(member.getMemberId());
            		break;
            	case 3:
            		//리뷰 내역 조회
					ConsoleUI.info("리뷰 내역을 불러옵니다.");
					ReviewController.selectReviews(member);
            		break;
            	case 0:
            		StartView.printUserMenu(member);
				default:
					ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
            }
		}
	}
	
	private static void updateUserInfo(Member member) {
		while(true) {
			ConsoleUI.blank(1);
			ConsoleUI.printHeader("EDIT PROFILE", null, ConsoleUI.RED, ConsoleUI.YELLOW);

			ConsoleUI.printMenu(new String[]{
					"[1] 비밀번호 변경",
					"[2] 카드번호 변경",
					"[3] 전화번호 변경",
					"[4] 전체 변경 사항 반영",
					"[0] 뒤로가기"
			}, null);

			int menu = 0;
			while(true) {
				try {
					menu = ConsoleUI.promptInt(sc, "메뉴를 선택하세요");
					break;
				}catch(NumberFormatException e) {
					ConsoleUI.alert("잘못된 입력입니다. 숫자를 입력해주세요.");
				}
			}
			
			switch(menu) {
			case 1:
				while(true) {
					String currentPassword = ConsoleUI.prompt(sc, "현재 비밀번호를 입력해주세요");
					if(!currentPassword.equals(member.getPassword())) {
						ConsoleUI.alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
						continue;
					}
					String password = ConsoleUI.prompt(sc, "변경할 비밀번호를 입력해주세요 (8자 이상)");
					if(!ValidateUtil.isValidPassword(password)) {
						ConsoleUI.alert("비밀번호는 8자 이상이어야 합니다. 다시 입력해주세요.");
						continue;
					}
					member.setPassword(password);
					ConsoleUI.info("비밀번호가 변경되었습니다. 실제 반영하려면 [4] 전체 변경 사항 반영을 선택해주세요.");
					break;
				}
				break;
			case 2:
				while(true) {
					String cardInfo = ConsoleUI.prompt(sc, "변경할 카드번호를 입력해주세요 예) 1234-5678-9012-3456");
					if(!ValidateUtil.isValidCardInfo(cardInfo)) {
						ConsoleUI.alert("잘못된 카드번호 양식입니다. 입력 예) 1234-5678-9012-3456");
						continue;
					}
					member.setCardInfo(cardInfo);
					ConsoleUI.info("카드번호가 변경되었습니다. 실제 반영하려면 [4] 전체 변경 사항 반영을 선택해주세요.");
					break;
				}
				break;
			case 3:
				while(true) {
					String phone = ConsoleUI.prompt(sc, "변경할 전화번호를 입력해주세요 예) 010-1234-5678");
					if(!ValidateUtil.isValidPhone(phone)) {
						ConsoleUI.alert("잘못된 전화번호 양식입니다. 입력 예) 010-1234-5678");
						continue;
					}
					member.setPhone(phone);
					ConsoleUI.info("전화번호가 변경되었습니다. 실제 반영하려면 [4] 전체 변경 사항 반영을 선택해주세요.");
					break;
				}
				break;
			case 4: 
				MemberController.updateUser(member);
				return;
			default:
				ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
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
			ConsoleUI.blank(1);
			ConsoleUI.printHeader("INQUIRY", null, ConsoleUI.RED, ConsoleUI.YELLOW);

			ConsoleUI.printMenu(new String[]{
					"[1] 문의하기",
					"[2] 문의 내역 조회",
					"[3] 뒤로가기"
			}, null);

			int menu = ConsoleUI.promptInt(sc, "메뉴를 선택하세요");
			switch(menu) {
				case 1:
					String content = ConsoleUI.prompt(sc, "문의 내용을 입력해주세요");
					String category = ConsoleUI.prompt(sc, "문의 구분을 입력해주세요. (예: MOVIE, SYSTEM, PAYMENT, ETC)");
					String title = ConsoleUI.prompt(sc, "문의 제목을 입력해주세요");
					InquiryController.insertInquiry(member, content, category, title);
					break;
				case 2:
					ConsoleUI.info("문의 내역을 불러옵니다.");
					InquiryController.selectInquiryByMember(member);
					int inquiryId = ConsoleUI.promptInt(sc, "상세 조회할 문의 번호를 입력해주세요.(처리된 문의만 가능)");
					InquiryController.selectInquiryDetailByMember(inquiryId, member);
					break;
				case 3:
					StartView.printUserMenu(member);
					return;
				default:
					ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
			}
		}
	}

	/*
	* 20260315
	* 이동혁
	* 영화 추천 후 선택 View
	 */
	public static void recommendationMovie(List<Movie> list, Member member) {
		while(true) {
			EndView.printAllMovies(list); // 간략한 목록 출력용

			ConsoleUI.blank(1);
			ConsoleUI.printHeader("MOVIE RECOMMENDATION", null, ConsoleUI.RED, ConsoleUI.YELLOW);
			ConsoleUI.printMenu(new String[]{
					"[1] 영화 예매하기",
					"[2] 영화 리뷰보기",
					"[3] 뒤로가기"
			}, null);

			int menu = ConsoleUI.promptInt(sc, "메뉴를 선택하세요");
			switch(menu) {
				case 1:
					ReservationController.getInstance().manageReservation(member);
					break;
				case 2:
					int movieId = ConsoleUI.promptInt(sc, "리뷰를 볼 영화를 선택해주세요");
					// 영화	리뷰 조회 컨트롤러 호출
					ReviewController.selectReviewsByMovie(movieId);
					break;
				case 3:
					return;
				default:
					ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
			}
		}
	}
	
}
