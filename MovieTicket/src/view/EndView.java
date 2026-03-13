package view;

import dto.*;
import vo.Ticket;

import java.util.List;
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
        System.out.println("-------------< 예약 " + list.size() + "개 >-------------");

        for (Ticket ticket : list) {
            System.out.println("예약 번호 : " + ticket.getReservationId() +
                    " | 예약자 이름 : " + ticket.getUserName() +
                    " | 영화 제목 : " + ticket.getMovieTitle() +
                    " | 총 가격 : " + ticket.getTotalPrice() +
                    " | 예약 좌석 수 : " + ticket.getCount() +
                    "\n상영관 : " + ticket.getRoomName() +
                    " | 상영 시작 시간 : " + ticket.getStartTime() +
                    " | 상영 종료 시간 : " + ticket.getEndTime());
            String seatNames = ticket.getSeats().stream()
                    .map(Seat::getName)
                    .collect(Collectors.joining(", "));
            System.out.println("좌석 번호 : " + seatNames);

            System.out.println("----------------------------------------------------------------------------");
        }

    }
}
