package view;

import java.util.Scanner;

/**
 * 2060317
 * TODO: 공통 콘솔 UI 출력 전용 클래스
 * */
public class ConsoleUI {
    // 콘솔 전체 너비
    public static final int WIDTH = 78;

    // ANSI 색상 코드
    public static final String RESET  = "\u001B[0m";
    public static final String BOLD   = "\u001B[1m";
    public static final String RED    = "\u001B[31m";
    public static final String GREEN  = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE   = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN   = "\u001B[36m";
    public static final String BRIGHT_MAGENTA = "\u001B[95m";
    public static final String GRAY = "\u001B[90m";

    private ConsoleUI() {
    }

    public static void blank() {
        System.out.println();
    }

    public static void blank(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println();
        }
    }

    // 기본 구분선(빨간색)
    public static void printLine() {
        System.out.println(RED + "=".repeat(WIDTH) + RESET);
    }

    // 색상을 인수로 받는 구분선
    public static void printLine(String color) {
        System.out.println(color + "=".repeat(WIDTH) + RESET);
    }

    public static void printDoubleLine() {
        System.out.println("■".repeat(WIDTH / 2));
    }

    // 중앙 정렬 관련
    public static void printlnCenter(String text) {
        System.out.println(center(text, WIDTH));
    }

    // 영어 텍스트 보정 중앙정렬
    public static void printlnCenterTight(String text) {
        System.out.println(center(text, WIDTH - 1));
    }

    public static String center(String text, int width) {
        int textWidth = getDisplayWidth(removeAnsi(text));
        int padding = Math.max(0, (width - textWidth) / 2);
        return " ".repeat(padding) + text;
    }



    private static int getDisplayWidth(String text) {
        int width = 0;
        for (char c : text.toCharArray()) {
            // 한글 폭 대략 2칸 처리
            if (c >= 0xAC00 && c <= 0xD7A3) {
                width += 2;
            } else {
                width += 1;
            }
        }
        return width;
    }

    private static String removeAnsi(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    // 배너 / 헤더
    public static void printMainBanner() {
        printLine(RED);
        printlnCenter(RED + BOLD + "███╗   ███╗ ██████╗ ██╗   ██╗██╗███████╗" + RESET);
        printlnCenter(RED + BOLD + "████╗ ████║██╔═══██╗██║   ██║██║██╔════╝" + RESET);
        printlnCenter(RED + BOLD + "██╔████╔██║██║   ██║██║   ██║██║█████╗  " + RESET);
        printlnCenter(RED + BOLD + "██║╚██╔╝██║██║   ██║╚██╗ ██╔╝██║██╔══╝  " + RESET);
        printlnCenter(RED + BOLD + "██║ ╚═╝ ██║╚██████╔╝ ╚████╔╝ ██║███████╗" + RESET);
        printlnCenter(RED + BOLD + "╚═╝     ╚═╝ ╚═════╝   ╚═══╝  ╚═╝╚══════╝" + RESET);
        printlnCenter(YELLOW + BOLD + "T I C K E T   C O U N T E R" + RESET);
        printLine(RED);
        printlnCenterOffset("영화처럼 시작되는 당신의 콘솔 시네마", 3);
        printLine(RED);
    }

    public static void printHeader(String title, String subtitle, String lineColor, String titleColor) {
        printLine(lineColor);
        printlnHeaderTitle(titleColor + BOLD + "《 " + title + " 》" + RESET);

        if (subtitle != null && !subtitle.isBlank()) {
            printlnSubtitle(subtitle);   // 디폴트 글씨색 유지
        }

        printLine(lineColor);
    }

    // 오버로딩 (subtitle 보정값 받는 메소드)
    public static void printHeader(String title, String subtitle, String lineColor, String titleColor, int subtitleAdjust) {
        printLine(lineColor);
        printlnHeaderTitle(titleColor + BOLD + "《 " + title + " 》" + RESET);

        if (subtitle != null && !subtitle.isBlank()) {
            printlnSubtitle(subtitle, subtitleAdjust);
        }

        printLine(lineColor);
    }

    // 헤더 제목 전용 중앙 정렬
    private static void printlnHeaderTitle(String text) {
        int textWidth = getDisplayWidth(removeAnsi(text));
        int padding = Math.max(0, (WIDTH - textWidth) / 2 - 1); // 1칸 왼쪽 보정
        System.out.println(" ".repeat(padding) + text);
    }

    // subtitle 중앙정렬 (기본값)
    public static void printlnSubtitle(String subtitle) {
        printlnCenter(subtitle);
    }

    // subtitle 중앙정렬 (직접 조정)
    public static void printlnSubtitle(String subtitle, int adjust) {
        int leftPadding = Math.max(0, ((WIDTH - getDisplayWidth(removeAnsi(subtitle))) / 2) + adjust);
        System.out.println(" ".repeat(leftPadding) + subtitle);
    }

    /**
     * TODO: 메뉴 숫자 기준으로 정렬
     * */
    // 메뉴 정렬 방식 (번호 기준 정렬)
    public static void printMenu(String[] menus, String color) {
        int leftPadding = (WIDTH / 2) - 7;   // 메뉴 시작 위치 고정

        for (String menu : menus) {
            if (color == null) {
                // 기본 글자색(디폴트)
                System.out.println(" ".repeat(leftPadding) + menu);
            } else {
                System.out.println(" ".repeat(leftPadding) + color + menu + RESET);
            }
        }

        // 구분선 색상 통일
        if (color == null) {
            printLine(RED);
        } else {
            printLine(color);
        }
    }

    // leftPadding 값을 직접 지정
    public static void printMenu(String[] menus, String color, int adjust) {
        int leftPadding = (WIDTH / 2) - adjust;

        for (String menu : menus) {
            if (color == null) {
                System.out.println(" ".repeat(leftPadding) + menu);
            } else {
                System.out.println(" ".repeat(leftPadding) + color + menu + RESET);
            }
        }

        if (color == null) {
            printLine(RED);
        } else {
            printLine(color);
        }
    }

    // 중앙 정렬 후 오른쪽으로 offset만큼 이동
    public static void printlnCenterOffset(String text, int offset) {
        System.out.println(" ".repeat(offset) + center(text, WIDTH));
    }

    // 메시지 출력
    public static void info(String message) { System.out.println(CYAN + "[안내] " + message + RESET); }

    public static void success(String message) {
        System.out.println(CYAN + "[완료] " + message + RESET);
    }

    public static void error(String message) {
        System.out.println(RED + BOLD + "[오류] " + message + RESET);
    }

    public static void alert(String message) { System.out.println(RED + "[알림] " + message + RESET); }

    // 입력창 출력
    public static String prompt(Scanner sc, String label) {
        System.out.print(label + ": ");
        return sc.nextLine();
    }

    public static int promptInt(Scanner sc, String label) {
        while (true) {
            try {
                return Integer.parseInt(prompt(sc, label).trim());
            } catch (NumberFormatException e) {
                alert("숫자만 입력해주세요.");
            }
        }
    }
}
