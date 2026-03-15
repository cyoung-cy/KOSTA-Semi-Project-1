package view;

import dto.*;
import util.PagingUtil;
import vo.ReviewVO;
import vo.Ticket;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EndView {

    /*
     * 0311
     * 김채영
     * TODO: 전체 사용자 상세목록 조회 View 형식 개발
     * */
    public static void printUserList(List<Member> list) {
        for (Member member : list) {
            System.out.println(member);
        }
    }

    /*
     * 0311
     * 김채영
     * TODO: 삭제 된 사용자 조회 View
     * */
    public static void deleteUser(String name) {
        System.out.println(name + " 사용자가 삭제되었습니다.");
        //System.out.println("현재 사용자 수"+  +"명");
        System.out.println();
    }

    /*
     * 0311
     * 김채영
     * TODO: 전체 사용자 목록 조회 View
     * */
    public static void printUserShort(List<Member> list) {
        System.out.println("-----------< 사용자 " + list.size() + "명 >-----------");
        for (Member member : list) {
            System.out.println("회원 번호 : " + member.getMemberId() +
                    " | 회원 아이디 : " + member.getUserId() +
                    " | 이름 : " + member.getName() + "\n");
            System.out.print("--------------------------------------------------------------");

        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 전체 문의 조회 View
     * */
    public static void printInquiryShort(List<Inquiry> list) {
        Member member = new Member();
        System.out.println("-------------< 문의 " + list.size() + "개 >-------------");
        for (Inquiry inquiry : list) {
            String processed = null;
            if (inquiry.getProcessed() == true) {
                processed = "resolved";
            } else {
                processed = "pending";
            }
            System.out.println("문의 번호 : " + inquiry.getInquiryId() +
                    " | 회원 아이디 : " + inquiry.getMemberId() +
                    " | 제목 : " + inquiry.getTitle() +
                    " | 회원 아이디 : " + inquiry.getMemberId() +
                    " | 구분 : " + inquiry.getCategory() +
                    " | 처리여부 : " + processed + "\n");
            System.out.println("----------------------------------------------------------------------------");

        }
    }

    /*
     * 0314
     * 이동혁
     * TODO: 사용자 문의 조회 View
     * */
    public static void printUserInquiryShort(List<Inquiry> list) {
        Member member = new Member();
        System.out.println("-------------< 문의 " + list.size() + "개 >-------------");
        for (Inquiry inquiry : list) {
            String processed = null;
            if (inquiry.getProcessed() == true) {
                processed = "resolved";
            } else {
                processed = "pending";
            }
            System.out.println("문의 번호 : " + inquiry.getInquiryId() +
                    " | 제목 : " + inquiry.getTitle() +
                    " | 구분 : " + inquiry.getCategory() +
                    " | 처리여부 : " + processed + "\n");
            System.out.println("----------------------------------------------------------------------------");

        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 문의 상세 조회 View
     * */
    public static void printInquiryDetail(List<Inquiry> list) {
        for (Inquiry inquiry : list) {
            System.out.println(inquiry);
        }
    }

    /*
     * 회원 탈퇴 여부 View
     */
    public static void deleteUserByMemberId() {
        System.out.println("회원 탈퇴 되었습니다!");
    }

    /*
     * 사용자 자신 정보 업데이트 View
     */
    public static void updateUser() {
        System.out.println("사용자 정보가 수정되었습니다!");
    }


    /*
     * 0312
     * 김채영
     * TODO: 전체 영화 조회 View
     * */
    public static void printAllMovies(List<Movie> list) {
        System.out.printf("%-5s | %-20s | %-10s | %-10s | %-10s\n", "ID", "제목", "장르", "상영시간", "상영여부");
        System.out.println("-----------------------------------------------------------------------");
        for (Movie m : list) {
            String status = null;
            if (m.getIsScreening() == true) {
                status = "상영중";
            } else {
                status = "상영종료";
            }
            System.out.printf("%-5d | %-20s | %-10s | %-10s | %-10s\n",
                    m.getMovieId(), m.getMovieTitle(), m.getGenre(), m.getScreeningTime() + "분", status);
        }
    }

    /*
    * 0315
    * 이동혁
    * TODO: 추천 영화 조회 View
     */
    public static void printRecommendationMovies(List<Movie> list) {
        System.out.printf("%-5s | %-20s | %-10s | %-10s | %-10s\n", "ID", "제목", "장르", "상영시간");
        System.out.println("-----------------------------------------------------------------------");
        for (Movie m : list) {
            System.out.printf("%-5d | %-20s | %-10s | %-10s | %-10s\n",
                    m.getMovieId(), m.getMovieTitle(), m.getGenre(), m.getScreeningTime() + "분");
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 영화 상세 조회 View
     * */
    public static void printMovieDetail(List<Movie> list) {
        for (Movie movie : list) {
            System.out.println(movie);
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 성공 메시지
     * */
    public static void successMessage(String s) {
        System.out.println(s);

    }

    /*
     * 0313
     * 이동혁
     * TODO: 예약 리스트 조회 View
     */
    public static void printTickets(List<Ticket> list) {
        final int PAGE_SIZE = 5; // 한 페이지 당 표시할 티켓 수
        int totalPage = (int) Math.ceil((double) list.size() / PAGE_SIZE);
        int currentPage = 0;
        Scanner scanner = new Scanner(System.in);

        final int reservIdW = 12;
        final int userNameW = 12;
        final int movieTitleW = 20;
        final int totalPriceW = 10;
        final int countW = 5;
        final int roomNameW = 10;
        final int startTimeW = 10;
        final int endTimeW = 10;
        final int seatNameW = 20;

        String separator = "-".repeat(reservIdW) + "-+-" +
                "-".repeat(userNameW) + "-+-" + "-".repeat(movieTitleW) + "-+-" +
                "-".repeat(totalPriceW) + "-+-" + "-".repeat(countW) + "-+-" +
                "-".repeat(roomNameW) + "-+-" + "-".repeat(startTimeW) + "-+-" +
                "-".repeat(endTimeW) + "-+-" + "-".repeat(seatNameW);

        while(true) {

            System.out.println("\n[티켓 목록]  총 " + (currentPage + 1) + " / " + totalPage + " 페이지");
            System.out.println(separator);
            System.out.println(
                    PagingUtil.padRight("예약 번호", reservIdW) + " | " +
                            PagingUtil.padRight("예약자 이름", userNameW) + " | " +
                            PagingUtil.padRight("영화 제목", movieTitleW) + " | " +
                            PagingUtil.padRight("총 가격", totalPriceW) + " | " +
                            PagingUtil.padRight("예약 좌석 수", countW) + " | " +
                            PagingUtil.padRight("상영관", roomNameW) + " | " +
                            PagingUtil.padRight("상영 시작 시간", startTimeW) + " | " +
                            PagingUtil.padRight("상영 종료 시간", endTimeW) + " | " +
                            PagingUtil.padRight("좌석 번호", seatNameW)
            );

            System.out.println(separator);

            // 현재 페이지 데이터 출력
            int from = currentPage * PAGE_SIZE;
            int to = Math.min(from + PAGE_SIZE, list.size());

            for (int i = from; i < to; i++) {
                Ticket ticket = list.get(i);
                String seatNames = ticket.getSeats().stream()
                        .map(Seat::getName)
                        .collect(Collectors.joining(", "));
                System.out.println(
                        PagingUtil.padRight(String.valueOf(ticket.getReservationId()), reservIdW) + " | " +
                                PagingUtil.padRight(ticket.getUserName(), userNameW) +" | " +
                                PagingUtil.padRight(ticket.getMovieTitle(), movieTitleW) +" | " +
                                PagingUtil.padRight(String.valueOf(ticket.getTotalPrice()), totalPriceW) +" | " +
                                PagingUtil.padRight(String.valueOf(ticket.getCount()), countW) +" | " +
                                PagingUtil.padRight(ticket.getRoomName(), roomNameW) +" | " +
                                PagingUtil.padRight(ticket.getStartTime().toString(), startTimeW) +" | " +
                                PagingUtil.padRight(ticket.getEndTime().toString(), endTimeW) +" | " +
                                PagingUtil.padRight(seatNames, seatNameW)
                );

            }

            System.out.println(separator);

            System.out.print("[ < 이전 | > 다음 | Q 종료 ] 입력: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("목록을 종료합니다.");
                break;
            } else if (input.equals(">")) {
                if (currentPage < totalPage - 1) {
                    currentPage++;
                } else {
                    System.out.println("마지막 페이지입니다.");
                }
            } else if (input.equals("<")) {
                if (currentPage > 0) {
                    currentPage--;
                } else {
                    System.out.println("첫 번째 페이지입니다.");
                }
            } else {
                System.out.println("올바른 입력이 아닙니다. >, <, Q 중 하나를 입력하세요.");
            }
        }



    }

    /*
     * 0314
     * 이동혁
     * TODO: 리뷰 리스트 조회 View
     */
    public static void reviewList(List<ReviewVO> list) {


        System.out.println("-------------< 리뷰 " + list.size() + "개 >-------------");
        for (ReviewVO review : list) {
            System.out.println("리뷰 번호 : " + review.getReviewId() +
                    " | 영화 제목 : " + review.getMovieTitle() +
                    " | 평점 : " + review.getRating() +
                    " | 내용 : " + review.getContent());
            System.out.println("----------------------------------------------------------------------------");
        }
    }

}
