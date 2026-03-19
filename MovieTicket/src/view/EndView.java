package view;

import dao.MemberDAO;
import dao.MovieDAO;
import dao.impl.MemberDAOImpl;
import dao.impl.MovieDAOImpl;
import dto.*;

import dto.Inquiry;
import dto.Member;
import dto.Movie;
import util.PagingUtil;
import util.PrintTickets;
import vo.ReviewVO;
import vo.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class EndView {

    // EndView 공통 출력 보조 메서드
    private static String fit(String text, int width) {
        if (text == null) text = "";
        if (text.length() > width) {
            return text.substring(0, width);
        }
        return text + " ".repeat(width - text.length());
    }

    private static void printDashLine() {
        System.out.println("-".repeat(ConsoleUI.WIDTH));
    }

    /*
     * 0311
     * 김채영
     * TODO: 전체 사용자 상세목록 조회 View 형식 개발
     * */
//    public static void printUserList(List<Member> list) {
//        for (Member member : list) {
//            System.out.println(member);
//        }
//    }
    public static void printUserList(List<Member> list) {
        ConsoleUI.printHeader("회원 상세 목록", "총 " + list.size() + "명", ConsoleUI.GREEN, ConsoleUI.GREEN);
        for (Member member : list) {
            System.out.println(member);
            printDashLine();
        }
    }

    /*
     * 0311
     * 김채영
     * TODO: 삭제 된 사용자 조회 View
     * */
    public static void deleteUser(String name) {
        ConsoleUI.info(name + " 사용자가 삭제되었습니다.");
        //System.out.println("현재 사용자 수"+  +"명");
        ConsoleUI.blank(1);
    }

    /*
     * 0311
     * 김채영
     * TODO: 전체 사용자 목록 조회 View
     * */
//    public static void printUserShort(List<Member> list) {
//        System.out.println("-----------< 사용자 "+ list.size() +"명 >-----------");
//        for(Member member : list) {
//            System.out.print("회원 번호 : " + member.getMemberId()+
//                    " | 회원 아이디 : " + member.getUserId() +
//                    " | 이름 : " + member.getName() + "\n" );
//            System.out.println("--------------------------------------------------------------");
//
//        }
//    }
    public static void printUserShort(List<Member> list) {
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("회원 목록", "총 " + list.size() + "명", ConsoleUI.GREEN, ConsoleUI.GREEN);

        for (Member member : list) {
            String row =
                    "회원 번호 : " + fit(String.valueOf(member.getMemberId()), 4) +
                            " | 회원 아이디 : " + fit(member.getUserId(), 12) +
                            " | 이름 : " + fit(member.getName(), 10);

            System.out.println(row);
            printDashLine();
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 전체 문의 조회 View
     * */
//    public static void printInquiryShort(List<Inquiry> list) {
//        Member member = new Member();
//        System.out.println("-------------< 문의 "+ list.size() +"개 >-------------");
//        for(Inquiry inquiry : list) {
//            String processed = null;
//            if(inquiry.getProcessed() == true){
//                processed = "resolved";
//            }else{
//                processed = "pending";
//            }
//            System.out.println("문의 번호 : " + inquiry.getInquiryId()+
//                    " | 회원 아이디 : " + inquiry.getMemberId() +
//                    " | 제목 : " + inquiry.getTitle()+
//                    " | 회원 아이디 : " + inquiry.getMemberId() +
//                    " | 구분 : " + inquiry.getCategory() +
//                    " | 처리여부 : " + processed +"\n" );
//            System.out.println("----------------------------------------------------------------------------");
//
//        }
//    }
    public static void printInquiryShort(List<Inquiry> list) {
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("문의 목록", "총 " + list.size() + "개", ConsoleUI.GREEN, ConsoleUI.GREEN);

        for (Inquiry inquiry : list) {
            String processed = inquiry.getProcessed() ? "resolved" : "pending";

            String row =
                    "문의 번호 : " + fit(String.valueOf(inquiry.getInquiryId()), 4) +
                            " | 회원 ID : " + fit(String.valueOf(inquiry.getMemberId()), 6) +
                            " | 구분 : " + fit(String.valueOf(inquiry.getCategory()), 10) +
                            " | 처리여부 : " + fit(processed, 8);

            System.out.println(row);
            System.out.println("제목 : " + inquiry.getTitle());
            printDashLine();
        }
    }

    /*
     * 0314
     * 이동혁
     * TODO: 사용자 문의 조회 View
     * */
//    public static void printUserInquiryShort(List<Inquiry> list) {
//        Member member = new Member();
//        System.out.println("-------------< 문의 " + list.size() + "개 >-------------");
//        for (Inquiry inquiry : list) {
//            String processed = null;
//            if (inquiry.getProcessed() == true) {
//                processed = "resolved";
//            } else {
//                processed = "pending";
//            }
//            System.out.println("문의 번호 : " + inquiry.getInquiryId() +
//                    " | 제목 : " + inquiry.getTitle() +
//                    " | 구분 : " + inquiry.getCategory() +
//                    " | 처리여부 : " + processed + "\n");
//            System.out.println("----------------------------------------------------------------------------");
//
//        }
//    }
    public static void printUserInquiryShort(List<Inquiry> list) {
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("내 문의 목록", "총 " + list.size() + "개", ConsoleUI.RED, ConsoleUI.YELLOW);

        for (Inquiry inquiry : list) {
            String processed = inquiry.getProcessed() ? "resolved" : "pending";

            String row =
                    "문의 번호 : " + fit(String.valueOf(inquiry.getInquiryId()), 4) +
                            " | 구분 : " + fit(String.valueOf(inquiry.getCategory()), 10) +
                            " | 처리여부 : " + fit(processed, 8);

            System.out.println(row);
            System.out.println("제목 : " + inquiry.getTitle());
            printDashLine();
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 문의 상세 조회 View
     * */
    /*
     * 0312
     * 김채영
     * TODO: 문의 상세 조회 View
     * */
//    public static void printInquiryDetail(List<Inquiry> list) {
//        for(Inquiry inquiry : list) {
//            System.out.println(inquiry);
//        }
//    }
    public static void printInquiryDetail(List<Inquiry> list) {
        ConsoleUI.printHeader("문의 상세 조회", null, ConsoleUI.GREEN, ConsoleUI.GREEN);
        for (Inquiry inquiry : list) {
            System.out.println(inquiry);
            printDashLine();
        }
    }

    /*
     * 회원 탈퇴 여부 View
     */
    public static void deleteUserByMemberId() {
        ConsoleUI.info("회원 탈퇴 되었습니다!");
    }

    /*
     * 사용자 자신 정보 업데이트 View
     */
    public static void updateUser() {
        ConsoleUI.info("사용자 정보가 수정되었습니다.");
    }

    /*
     * 0314
     * 김채영
     * TODO: 전체 영화 조회 View - 페이징 + 한글 정렬 버전
     * */
    public static void printAllMovies(List<Movie> list) {
        final int PAGE_SIZE = 7;
        int totalPages = (int) Math.ceil((double) list.size() / PAGE_SIZE);
        int currentPage = 0;
        Scanner scanner = new Scanner(System.in);

        if (list == null || list.isEmpty()) {
            ConsoleUI.alert("조회할 영화가 없습니다.");
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("[전체 영화 목록]  " + (currentPage + 1) + " / " + totalPages + " 페이지");
            System.out.println("=".repeat(78));

            int from = currentPage * PAGE_SIZE;
            int to = Math.min(from + PAGE_SIZE, list.size());

            for (int i = from; i < to; i++) {
                Movie m = list.get(i);
                String status = m.getIsScreening() ? "상영중" : "상영종료";

                // 영화 1개를 카드처럼 2줄로 출력
                System.out.println("[" + m.getMovieId() + "] " + m.getMovieTitle());
                System.out.println(
                        "장르: " + m.getGenre() +
                                "   |   상영시간: " + m.getScreeningTime() + "분" +
                                "   |   상영여부: " + status
                );

                // 마지막 항목 뒤에는 구분선 생략 가능하지만, 통일감 위해 유지
                System.out.println("-".repeat(78));
            }

            System.out.print("[ < 이전 | > 다음 | Q 종료 ] 입력: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                ConsoleUI.info("목록을 종료합니다.");
                //System.out.println();
                break;
            } else if (input.equals(">")) {
                if (currentPage < totalPages - 1) {
                    currentPage++;
                } else {
                    ConsoleUI.alert("마지막 페이지입니다.");
                }
            } else if (input.equals("<")) {
                if (currentPage > 0) {
                    currentPage--;
                } else {
                    ConsoleUI.alert("첫 번째 페이지입니다.");
                }
            } else {
                ConsoleUI.alert("올바른 입력이 아닙니다. >, <, Q 중 하나를 입력하세요.");
            }
        }
    }

    /*
    * 0315
    * 이동혁
    * TODO: 추천 영화 조회 View
     */
    public static void printRecommendationMovies(List<Movie> list) {
        if (list == null || list.isEmpty()) {
            ConsoleUI.alert("추천할 영화가 없습니다.");
            return;
        }

        System.out.println("\n[추천 영화 목록]");
        System.out.println("=".repeat(78));

        for (Movie m : list) {
            System.out.println("[" + m.getMovieId() + "] " + m.getMovieTitle());
            System.out.println(
                    "장르: " + m.getGenre() +
                            "   |   상영시간: " + m.getScreeningTime() + "분"
            );
            System.out.println("-".repeat(78));
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 영화 상세 조회 View
     * */
//    public static void printMovieDetail(List<Movie> list) {
//        for(Movie movie : list) {
//            System.out.println(movie);
//        }
//    }
    public static void printMovieDetail(List<Movie> list) {
        ConsoleUI.printHeader("영화 상세 조회", null, ConsoleUI.GREEN, ConsoleUI.GREEN);
        for (Movie movie : list) {
            System.out.println(movie);
            printDashLine();
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 성공 메시지
     * */
    public static void successMessage(String s) {
        //System.out.println(s);
        ConsoleUI.info(s);
    }


    /*
     * 0314
     * 김채영
     * TODO: 예매된 영화 전체 조회
     *       영화 제목은 MovieDAO의 selectMovieDetail()을 이용해 조회
     *       사용자 이름은 MemberDAO의 selectUsers()를 이용해 조회
     * */
    public static void selectReservationsByMemberId(List<Reservation> reservationList, int memberId) {
        MovieDAO movieDAO = new MovieDAOImpl();
        MemberDAO memberDAO = new MemberDAOImpl();
        final int PAGE_SIZE = 15;
        int totalPages = (int) Math.ceil((double) reservationList.size() / PAGE_SIZE);
        int currentPage = 0;
        Scanner scanner = new Scanner(System.in);

        final int reservIdW = 12;
        final int memberIdW = 10;
        final int movieIdW = 10;
        final int titleW = 36;

        String separator = "-".repeat(reservIdW) + "-+-" +
                "-".repeat(memberIdW) + "-+-" +
                "-".repeat(movieIdW) + "-+-" +
                "-".repeat(titleW);

        // 회원 이름 조회
        List<Member> members = memberDAO.selectUsers();
        String name = members.stream()
                .filter(m -> m.getMemberId() == memberId)
                .map(Member::getName)
                .findFirst()
                .orElse("알 수 없음");

        // 페이징 루프
        while (true) {
            int from = currentPage * PAGE_SIZE;
            int to = Math.min(from + PAGE_SIZE, reservationList.size());

            System.out.println("\n[" + name + " 예약 목록]  총 " + reservationList.size() + "건");
            System.out.println(separator);
            System.out.println(
                    PagingUtil.padRight("예약 ID", reservIdW) + " | " +
                            PagingUtil.padRight("회원 ID", memberIdW) + " | " +
                            PagingUtil.padRight("영화 ID", movieIdW) + " | " +
                            PagingUtil.padRight("영화 제목", titleW)
            );
            System.out.println(separator);

            for (Reservation r : reservationList) {
                // selectMovieDetail()은 List로 반환하므로 get(0)으로 꺼냄
                List<Movie> movieDetail = movieDAO.selectMovieDetail(r.getMovieId());
                String title = (!movieDetail.isEmpty()) ? movieDetail.get(0).getMovieTitle() : "정보 없음";

                System.out.println(
                        PagingUtil.padRight(String.valueOf(r.getReservationId()), reservIdW) + " | " +
                                PagingUtil.padRight(String.valueOf(r.getMemberId()), memberIdW) + " | " +
                                PagingUtil.padRight(String.valueOf(r.getMovieId()), movieIdW) + " | " +
                                PagingUtil.padRight(title, titleW)
                );
            }

            System.out.println(separator);
            System.out.print("[ < 이전 | > 다음 | Q 종료 ] 입력: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                ConsoleUI.info("목록을 종료합니다.");
                break; //while 루프를 빠져나감
            } else if (input.equals(">")) {
                if (currentPage < totalPages - 1) {
                    currentPage++;
                } else {
                    ConsoleUI.alert("마지막 페이지입니다.");
                }
            } else if (input.equals("<")) {
                if (currentPage > 0) {
                    currentPage--;
                } else {
                    ConsoleUI.alert("첫 번째 페이지입니다.");
                }
            } else {
                ConsoleUI.alert("올바른 입력이 아닙니다. >, <, Q 중 하나를 입력하세요.");
            }
        }
    }

    /*
     * 0313
     * 이동혁
     * TODO: 예약 리스트 조회 View
     */
    public static void printTickets(List<Ticket> list) {
        final int PAGE_SIZE = 2; // 한 페이지 당 표시할 티켓 수
        int totalPage = (int) Math.ceil((double) list.size() / PAGE_SIZE);
        int currentPage = 0;
        while (true) {
            // 현재 페이지 데이터 출력
            List pageList = new ArrayList();
            int from = currentPage * PAGE_SIZE;
            int to = Math.min(from + PAGE_SIZE, list.size());
            for(int i = from; i < to; i++) {
                pageList.add(list.get(i));
            }
            PrintTickets.print(pageList);
            Scanner scanner = new Scanner(System.in);

            System.out.print("[ < 이전 | > 다음 | Q 종료 ] 입력: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                ConsoleUI.info("목록을 종료합니다.");
                break;
            } else if (input.equals(">")) {
                if (currentPage < totalPage - 1) {
                    currentPage++;
                } else {
                    ConsoleUI.alert("마지막 페이지입니다.");
                }
            } else if (input.equals("<")) {
                if (currentPage > 0) {
                    currentPage--;
                } else {
                    ConsoleUI.alert("첫 번째 페이지입니다.");
                }
            } else {
                ConsoleUI.alert("올바른 입력이 아닙니다. >, <, Q 중 하나를 입력하세요.");
            }
        }
    }

    /*
     * 0314
     * 이동혁
     * TODO: 리뷰 리스트 조회 View
     */
    public static void reviewList(List<ReviewVO> list) {

        final int PAGE_SIZE = 5; // 한 페이지에 표시할 리뷰 수
        int totalPage = (int) Math.ceil((double) list.size() / PAGE_SIZE);
        if(totalPage == 0) totalPage = 1;
        int currentPage = 0;

        Scanner scanner = new Scanner(System.in);

        final int reviewIdW = 12;
        final int movieTitleW = 12;
        final int ratingW = 12;
        final int contentW = 50;

        String separator = "-".repeat(reviewIdW) + "-+-"
                + "-".repeat(movieTitleW) + "-+-"
                + "-".repeat(ratingW) + "-+-"
                + "-".repeat(contentW);

        while(true) {
            System.out.println("\n[리뷰 목록] 총 " + (currentPage + 1) + " / " + totalPage + "페이지");
            System.out.println(separator);
            System.out.println(
                    PagingUtil.padRight("리뷰 번호", reviewIdW) + " | " +
                    PagingUtil.padRight("영화 제목", movieTitleW) + " | " +
                    PagingUtil.padRight("평점", ratingW) + " | " +
                    PagingUtil.padRight("내용", contentW)
            );
            System.out.println(separator);

            // 현재 페이지 데이터 출력
            int from = currentPage * PAGE_SIZE;
            int to = Math.min(from + PAGE_SIZE, list.size());
            for (int i = from; i < to; i++) {
                ReviewVO review = list.get(i);
                int starCount = review.getRating(); // 별점 개수

                System.out.println(
                        PagingUtil.padRight(String.valueOf(review.getReviewId()), reviewIdW) + " | " +
                        PagingUtil.padRight(review.getMovieTitle(), movieTitleW) + " | " +
                        PagingUtil.padRight("★".repeat(starCount) + "☆".repeat(5 - starCount), ratingW) + " | " +
                        PagingUtil.padRight(review.getContent(), contentW)
                );
            }
            System.out.println(separator);
            System.out.println("[ < 이전 | > 다음 | Q 종료 ] 입력: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                ConsoleUI.info("목록을 종료합니다.");
                return;
            } else if (input.equals(">")) {
                if (currentPage < totalPage - 1) {
                    currentPage++;
                } else {
                    ConsoleUI.alert("마지막 페이지입니다.");
                }
            } else if (input.equals("<")) {
                if (currentPage > 0) {
                    currentPage--;
                } else {
                    ConsoleUI.alert("첫 번째 페이지입니다.");
                }
            } else {
                ConsoleUI.alert("올바른 입력이 아닙니다. >, <, Q 중 하나를 입력하세요.");
            }
        }
    }
}
