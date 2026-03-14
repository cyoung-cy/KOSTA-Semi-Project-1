package view;

import dao.MovieDAO;
import dao.impl.MovieDAOImpl;
import dto.Inquiry;
import dto.Member;
import dto.Movie;
import dto.Reservation;
import util.PagingUtil;
import java.util.List;
import java.util.Scanner;

public class EndView {

    /*
     * 0311
     * 김채영
     * TODO: 전체 사용자 상세목록 조회 View 형식 개발
     * */
    public static void printUserList(List<Member> list) {
        for(Member member : list) {
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
        System.out.println("-----------< 사용자 "+ list.size() +"명 >-----------");
        for(Member member : list) {
            System.out.print("회원 번호 : " + member.getMemberId()+
                    " | 회원 아이디 : " + member.getUserId() +
                    " | 이름 : " + member.getName() + "\n" );
            System.out.println("--------------------------------------------------------------");

        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 전체 문의 조회 View
     * */
    public static void printInquiryShort(List<Inquiry> list) {
        Member member = new Member();
        System.out.println("-------------< 문의 "+ list.size() +"개 >-------------");
        for(Inquiry inquiry : list) {
            String processed = null;
            if(inquiry.getProcessed() == true){
                processed = "resolved";
            }else{
                processed = "pending";
            }
            System.out.println("문의 번호 : " + inquiry.getInquiryId()+
                    " | 회원 아이디 : " + inquiry.getMemberId() +
                    " | 제목 : " + inquiry.getTitle()+
                    " | 회원 아이디 : " + inquiry.getMemberId() +
                    " | 구분 : " + inquiry.getCategory() +
                    " | 처리여부 : " + processed +"\n" );
            System.out.println("----------------------------------------------------------------------------");

        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 문의 상세 조회 View
     * */
    public static void printInquiryDetail(List<Inquiry> list) {
        for(Inquiry inquiry : list) {
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
     * 0314
     * 김채영
     * TODO: 전체 영화 조회 View - 페이징 + 한글 정렬 버전
     * */
    public static void printAllMovies(List<Movie> list) {
        final int PAGE_SIZE = 15; // 한 페이지당 출력할 영화 수
        int totalPages = (int) Math.ceil((double) list.size() / PAGE_SIZE);
        int currentPage = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 헤더 출력
            System.out.println("\n[전체 영화 목록]  " + (currentPage + 1) + " / " + totalPages + " 페이지");

            // 컬럼 너비 (출력 기준 너비 = 화면에서 차지하는 칸 수)
            // 한글 1자 = 2칸, 영문/숫자 1자 = 1칸
            int idW     = 10;
            int titleW  = 36;  // 한글 최대 약 18자 → 36칸
            int genreW  = 14;  // 한글 최대 약 7자  → 14칸
            int timeW   = 10;
            int statusW = 12;

            String separator = PagingUtil.makeSeparator(idW, titleW, genreW, timeW, statusW);

            System.out.println(separator);
            System.out.println(
                    PagingUtil.padRight("ID",     idW)    + " | " +
                            PagingUtil.padRight("제목",   titleW) + " | " +
                            PagingUtil.padRight("장르",   genreW) + " | " +
                            PagingUtil.padRight("상영시간", timeW) + " | " +
                            PagingUtil.padRight("상영여부", statusW)
            );
            System.out.println(separator);

            // ── 현재 페이지 데이터 출력 ────────────────────────────────
            int from = currentPage * PAGE_SIZE;
            int to   = Math.min(from + PAGE_SIZE, list.size());

            for (int i = from; i < to; i++) {
                Movie m = list.get(i);
                String status = m.getIsScreening() ? "상영중" : "상영종료";

                System.out.println(
                        PagingUtil.padRight(String.valueOf(m.getMovieId()), idW)   + " | " +
                                PagingUtil.padRight(m.getMovieTitle(),              titleW) + " | " +
                                PagingUtil.padRight(m.getGenre(),                   genreW) + " | " +
                                PagingUtil.padRight(m.getScreeningTime() + "분",    timeW)  + " | " +
                                PagingUtil.padRight(status,                         statusW)
                );
            }

            System.out.println(separator);

            //페이지 이동
            System.out.print("[ < 이전 | > 다음 | Q 종료 ] 입력: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("목록을 종료합니다.");
                break;
            } else if (input.equals(">")) {
                if (currentPage < totalPages - 1) {
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
     * 0312
     * 김채영
     * TODO: 영화 상세 조회 View
     * */
    public static void printMovieDetail(List<Movie> list) {
        for(Movie movie : list) {
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
     * 0314
     * 김채영
     * TODO: 예매된 영화 전체 조회 (영화 제목은 MovieDAO의 selectMovieDetail()을 이용해 조회)
     * */
    public static void selectReservationsByMemberId(List<Reservation> reservationList, int memberId) {
        MovieDAO movieDAO = new MovieDAOImpl();

        final int reservIdW = 12;
        final int memberIdW = 10;
        final int movieIdW  = 10;
        final int titleW    = 36;

        String separator = "-".repeat(reservIdW) + "-+-" +
                "-".repeat(memberIdW)  + "-+-" +
                "-".repeat(movieIdW)   + "-+-" +
                "-".repeat(titleW);

        System.out.println("\n["+memberId+" 예약 목록]  총 " + reservationList.size() + "건");
        System.out.println(separator);
        System.out.println(
                PagingUtil.padRight("예약 ID", reservIdW) + " | " +
                        PagingUtil.padRight("회원 ID", memberIdW) + " | " +
                        PagingUtil.padRight("영화 ID", movieIdW)  + " | " +
                        PagingUtil.padRight("영화 제목", titleW)
        );
        System.out.println(separator);

        for (Reservation r : reservationList) {
            // selectMovieDetail()은 List로 반환하므로 get(0)으로 꺼냄
            List<Movie> movieDetail = movieDAO.selectMovieDetail(r.getMovieId());
            String title = (!movieDetail.isEmpty()) ? movieDetail.get(0).getMovieTitle() : "정보 없음";

            System.out.println(
                    PagingUtil.padRight(String.valueOf(r.getReservationId()), reservIdW) + " | " +
                            PagingUtil.padRight(String.valueOf(r.getMemberId()),      memberIdW) + " | " +
                            PagingUtil.padRight(String.valueOf(r.getMovieId()),       movieIdW)  + " | " +
                            PagingUtil.padRight(title,                                titleW)
            );
        }
        System.out.println(separator);
    }
}
