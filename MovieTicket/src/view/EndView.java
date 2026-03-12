package view;

import dto.Member;

import java.util.List;

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

        System.out.println();
    }

    /*
     * 0311
     * 김채영
     * TODO: 삭제 된 사용자 조회 View 형식 개발
     * */
    public static void deleteUser(String name) {
        System.out.println(name + " 사용자가 삭제되었습니다.");
        //System.out.println("현재 사용자 수"+  +"명");
        System.out.println();
    }

    /*
     * 0311
     * 김채영
     * TODO: 전체 사용자 목록 조회 View 형식 개발
     * */
    public static void printUserShort(List<Member> list) {
        System.out.println("-----사용자 "+ list.size() +"명 -------------");
        for(Member member : list) {
            System.out.println("회원 번호 : " + member.getMemberId()+
                    " | 회원 아이디 : " + member.getUserId() +
                    " | 이름 : " + member.getName() + "\n" );
            System.out.println("------------------------------------------");

        }

        System.out.println();
    }
}
