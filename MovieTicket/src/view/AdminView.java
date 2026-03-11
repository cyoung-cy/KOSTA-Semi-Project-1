package view;

import controller.MemberController;
import session.SessionSet;

import java.util.Scanner;

public class AdminView {
    private static Scanner sc = new Scanner(System.in);

    public static void userManage(String userId) {

    }

    /*
     * 기능 : userId에 길이에 따라 중간 정렬
     * @param : userId(String), 너비(int)
     * @return : String
     * */
    public static String center(String text, int width) {
        int padding = (width - text.length()) / 2;
        String format = "%" + (padding + text.length()) + "s";
        return String.format(format, text);
    }
}
