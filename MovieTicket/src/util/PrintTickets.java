package util;
import dto.Seat;
import vo.Ticket;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PrintTickets {
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";

    public static void print(List<Ticket> list) {
        // (기본 루프 로직은 이전과 동일, drawTicketCard 호출부만 확인)
        for (Ticket t : list) {
            drawTicketCard(t);
        }
    }

    private static void drawTicketCard(Ticket t) {
        String seats = t.getSeats().stream().map(Seat::getName).collect(Collectors.joining(", "));
        int cardWidth = 70; // 티켓 전체 고정 폭

        System.out.println(CYAN + "┌" + "─".repeat(cardWidth) + "┐" + RESET);
        printLine("티켓 예약 번호: " + YELLOW + t.getReservationId() + RESET, cardWidth);
        System.out.println(CYAN + "├" + "─".repeat(cardWidth) + "┤" + RESET);
        printLine("영화 제목 : " + GREEN + t.getMovieTitle() + RESET, cardWidth);
        printLine("상영관  : " + t.getRoomName(), cardWidth);
        printLine("상영 시간대  : " + t.getStartTime() + " ~ " + t.getEndTime(), cardWidth);
        printLine("좌석 : " + seats, cardWidth);
        printLine("상영 지점 : 오리역 CGV", cardWidth);
        System.out.println(CYAN + "└" + "─".repeat(cardWidth) + "┘" + RESET);
    }

    // 한글 폭을 계산하여 우측 테두리를 고정시키는 메서드
    private static void printLine(String content, int maxWidth) {
        // ANSI 코드를 제거한 순수 텍스트 길이 계산 (정렬용)
        String plainText = content.replaceAll("\u001B\\[[;\\d]*m", "");
        int visualLength = 0;
        for (char c : plainText.toCharArray()) {
            visualLength += (c >= '\uAC00' && c <= '\uD7A3') ? 2 : 1; // 한글이면 2칸, 아니면 1칸
        }

        int padding = maxWidth - visualLength - 2; // 좌우 여백 고려
        System.out.print(CYAN + "│ " + RESET + content);
        if (padding > 0) {
            System.out.print(" ".repeat(padding));
        }
        System.out.println(CYAN + " │" + RESET);
    }
}