package util;

import dto.Seat;
import vo.Ticket;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PrintTickets {
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";

    private static final int CARD_WIDTH = 76;
    private static final int LABEL_WIDTH = 10;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void print(List<Ticket> list) {
        if (list == null || list.isEmpty()) {
            System.out.println(CYAN + "[안내] 조회된 티켓이 없습니다." + RESET);
            return;
        }

        for (Ticket t : list) {
            drawTicketCard(t);
        }
    }

    private static void drawTicketCard(Ticket t) {
        String seats = (t.getSeats() == null || t.getSeats().isEmpty())
                ? "-"
                : t.getSeats().stream()
                .map(Seat::getName)
                .collect(Collectors.joining(", "));

        String title = safe(t.getMovieTitle());
        String room = safe(t.getRoomName());
        String timeRange = formatDateTime(t.getStartTime()) + " ~ " + formatDateTime(t.getEndTime());
        String branch = "오리역 CGV";

        System.out.println(CYAN + "┌" + "─".repeat(CARD_WIDTH) + "┐" + RESET);
        System.out.println("  [티켓 예약 번호 #" + YELLOW + t.getReservationId() + RESET + "]");
        System.out.println(CYAN + "─".repeat(CARD_WIDTH + 2) + RESET);

        printContentLine(" 영화 제목", GREEN + title + RESET);
        printContentLine(" 상영관", room);
        printContentLine(" 상영 시간", timeRange);
        printContentLine(" 좌석", seats);
        printContentLine(" 상영 지점", branch);

        System.out.println(CYAN + "└" + "─".repeat(CARD_WIDTH) + "┘" + RESET);
    }

    private static void printContentLine(String label, String value) {
        String line = " " + padLabel(label, LABEL_WIDTH) + " : " + value;
        System.out.println(fitToWidth(line, CARD_WIDTH + 2));
    }

    private static String padLabel(String text, int width) {
        int displayWidth = getDisplayWidth(text);

        if (displayWidth >= width) {
            return text;
        }

        return text + " ".repeat(width - displayWidth);
    }

    private static String fitToWidth(String text, int width) {
        if (text == null) text = "";

        int displayWidth = getDisplayWidth(text);

        if (displayWidth == width) {
            return text;
        }

        if (displayWidth < width) {
            return text + " ".repeat(width - displayWidth);
        }

        return truncateToWidth(text, width);
    }

    private static String truncateToWidth(String text, int width) {
        StringBuilder result = new StringBuilder();
        int currentWidth = 0;
        boolean inAnsi = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '\u001B') {
                inAnsi = true;
                result.append(c);
                continue;
            }

            if (inAnsi) {
                result.append(c);
                if (c == 'm') {
                    inAnsi = false;
                }
                continue;
            }

            int charWidth = getCharWidth(c);
            if (currentWidth + charWidth > width) {
                break;
            }

            result.append(c);
            currentWidth += charWidth;
        }

        if (currentWidth < width) {
            result.append(" ".repeat(width - currentWidth));
        }

        return result.toString();
    }

    private static int getDisplayWidth(String text) {
        if (text == null) return 0;

        String plainText = removeAnsi(text);
        int width = 0;

        for (char c : plainText.toCharArray()) {
            width += getCharWidth(c);
        }
        return width;
    }

    private static int getCharWidth(char c) {
        if ((c >= 0xAC00 && c <= 0xD7A3) ||
                (c >= 0x1100 && c <= 0x11FF) ||
                (c >= 0x3130 && c <= 0x318F) ||
                (c >= 0x4E00 && c <= 0x9FFF) ||
                (c >= 0xFF01 && c <= 0xFF60) ||
                (c >= 0xFFE0 && c <= 0xFFE6)) {
            return 2;
        }
        return 1;
    }

    private static String removeAnsi(String text) {
        return text.replaceAll("\u001B\\[[;\\d]*m", "");
    }

    private static String safe(String text) {
        return (text == null || text.isBlank()) ? "-" : text;
    }

    private static String formatDateTime(Timestamp ts) {
        if (ts == null) return "-";
        return ts.toLocalDateTime().format(TIME_FORMATTER);
    }
}