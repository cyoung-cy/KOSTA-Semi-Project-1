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
        System.out.println(center(text, WIDTH - 6));
    }

    public static String center(String text, int width) {
        int textWidth = getDisplayWidth(removeAnsi(text));
        int padding = Math.max(0, (width - textWidth) / 2);
        return " ".repeat(padding) + text;
    }

    // 헤더 제목 전용 중앙 정렬
    public static void printlnHeaderCenter(String text) {
        // 일반 center보다 살짝 왼쪽으로 보정
        System.out.println(center(text, WIDTH - 2));
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
        printlnCenter("영화처럼 시작되는 당신의 콘솔 시네마" + RESET);
        printLine(RED);
    }

    public static void printHeader(String title, String subtitle, String color) {
        printLine(color);
        printlnHeaderCenter(color + BOLD + "《 " + title + " 》" + RESET);
        // subtitle은 기본색으로 출력
        if (subtitle != null && !subtitle.isBlank()) {
            printlnCenterTight(subtitle);
        }
        printLine(color);
    }

    public static void printHeader(String title, String subtitle, String lineColor, String titleColor) {
        printLine(lineColor);
        printlnCenter(titleColor + BOLD + "《 " + title + " 》" + RESET);

        if (subtitle != null && !subtitle.isBlank()) {
            printlnCenterTight(subtitle);   // 디폴트 글씨색 유지
        }

        printLine(lineColor);
    }

    public static void printSectionTitle(String title, String color) {
        printLine(color);
        printlnCenter(color + BOLD + title + RESET);
        printLine(color);
    }

    /**
     * TODO: 메뉴 숫자 기준으로 정렬
     * */
    // 메뉴 정렬 방식 (번호 기준 정렬)
    public static void printMenu(String[] menus, String color) {
        int leftPadding = (WIDTH / 2) - 10;   // 메뉴 시작 위치 고정

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

    // 메시지 출력
    public static void info(String message) {
        System.out.println(CYAN + "[안내] " + message + RESET);
    }

    public static void warn(String message) {
        System.out.println(RED + "[경고] " + message + RESET);
    }

    public static void success(String message) {
        System.out.println(GREEN + "[완료] " + message + RESET);
    }

    public static void error(String message) {
        System.out.println(RED + BOLD + "[오류] " + message + RESET);
    }

    // 입력창 출력
    public static String prompt(Scanner sc, String label) {
        System.out.printf(label + ": ");
        return sc.nextLine();
    }

    public static int promptInt(Scanner sc, String label) {
        while (true) {
            try {
                return Integer.parseInt(prompt(sc, label).trim());
            } catch (NumberFormatException e) {
                warn("숫자만 입력해주세요.");
            }
        }
    }

    // 표 출력용
    /**
     * 20260317
     * TODO: 영화 목록을 "|" 기준으로 간격 맞출 때 사용.
     * */
    public static void printRow(int w1, int w2, int w3, int w4,
                                String c1, String c2, String c3, String c4) {
        String format = "%-" + w1 + "s | %-" + w2 + "s | %-" + w3 + "s | %-" + w4 + "s%n";
        System.out.printf(format, safe(c1), safe(c2), safe(c3), safe(c4));
    }

    /**
     * TODO: 열이 5개일 때 사용
     * */
    public static void printRow(int w1, int w2, int w3, int w4, int w5,
                                String c1, String c2, String c3, String c4, String c5) {
        String format = "%-" + w1 + "s | %-" + w2 + "s | %-" + w3 + "s | %-" + w4 + "s | %-" + w5 + "s%n";
        System.out.printf(format, safe(c1), safe(c2), safe(c3), safe(c4), safe(c5));
    }

    /**
     * TODO: 표 구분선
     * */
    public static void printTableLine(int length, String color) {
        if (color == null) {
            System.out.println("-".repeat(length));
        } else {
            System.out.println(color + "-".repeat(length) + RESET);
        }
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

}
