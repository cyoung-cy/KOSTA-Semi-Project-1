package view;

import dto.Member;
import dto.Movie;
import dto.Reservation;
import dto.WeeklyStat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashboardView {
    static Scanner sc = new Scanner(System.in);

    /*
     * 0316
     * 김채영
     * TODO: 신규 가입자 및 회원 증감 추이 그래프
     * */
    public static void user(List<Member> dataList) {

        // 날짜 -> 가입자 수 Map 변환
        Map<String, Integer> dataMap = new LinkedHashMap<>();
        for (Member m : dataList) {
            dataMap.put(m.getCreateAt(), m.getMemberId());
        }

        // 오늘부터 7일치 날짜 목록 생성
        DateTimeFormatter dbFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("MM-dd(E)", Locale.KOREAN);
        LocalDate today = LocalDate.now();

        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(today.minusDays(i).format(dbFmt));
        }

        // 전일 대비 증감율 계산
        int todayCnt = dataMap.getOrDefault(dates.get(0), 0);
        int yesterdayCnt = dataMap.getOrDefault(dates.get(1), 0);
        String changeStr;
        if (yesterdayCnt == 0) {
            changeStr = todayCnt > 0 ? "+100.0% (상승세)" : "0.0% (변동없음)";
        } else {
            double rate = ((double)(todayCnt - yesterdayCnt) / yesterdayCnt) * 100;
            String arrow = rate > 0 ? "+" : "";
            String trend = rate > 0 ? "(상승세)" : rate < 0 ? "(하락세)" : "(변동없음)";
            changeStr = String.format("%s%.1f%% %s", arrow, rate, trend);
        }

        // 출력
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("회원 유입 및 증감 리포트", null, ConsoleUI.GREEN, ConsoleUI.GREEN);
        System.out.printf(" %-10s | %-9s | %s%n", "기준일자", "가입자 수", "시각화 (1명당 ■)");
        System.out.println("-".repeat(61));

        for (String dateStr : dates) {
            int cnt = dataMap.getOrDefault(dateStr, 0);
            LocalDate date = LocalDate.parse(dateStr, dbFmt);
            String displayDate = date.format(displayFmt);
            String bar = "■".repeat(cnt);
            System.out.printf(" %-11s | %5d명    | %s%n", displayDate, cnt, bar);
        }

        System.out.println("-".repeat(61));
        System.out.println(" ▶ 전일 대비 증감율: " + changeStr);
        ConsoleUI.printLine(ConsoleUI.GREEN);
        System.out.print("[0] 돌아가기 : ");

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();
            if (input.equals("0")) {
                break;
            } else {
                System.out.println("[알림] 0을 입력하면 돌아갑니다.");
            }
        }
    }

    /*
     * 0316
     * 김채영
     * TODO: 영화 장르 선호도 그래프
     * */
    public static void preferGenre(List<Member> dataList) {

        int total = 0;
        for (Member m : dataList) {
            total += m.getMemberId();
        }

        ConsoleUI.blank(1);
        ConsoleUI.printHeader("선호 장르별 시장 점유율", null, ConsoleUI.GREEN, ConsoleUI.GREEN);
        System.out.println("  순위 |   장르     |  회원 수  |  비중 (%)  |  그래프");
        System.out.println("-".repeat(61));

        int rank = 1;
        int graphMax = 15;

        for (Member m : dataList) {
            String genre = m.getRole();
            int cnt = m.getMemberId();
            double ratio = total == 0 ? 0 : ((double) cnt / total) * 100;

            int filled = total == 0 ? 0 : (int) Math.round((double) cnt / total * graphMax);
            int empty = graphMax - filled;
            String bar = "[" + "#".repeat(filled) + " ".repeat(empty) + "]";

            // 한글 패딩 보정 메서드 사용
            String genrePadded = padKorean(genre, 10);

            System.out.printf("   %d   | %s | %5d명   | %6.1f%%   | %s%n",
                    rank, genrePadded, cnt, ratio, bar);
            rank++;
        }

        System.out.println("-".repeat(61));
        System.out.printf(" ▶ 전체 선호 장르 선택 수: %d건%n", total);
        ConsoleUI.printLine(ConsoleUI.GREEN);
        System.out.print("[0] 돌아가기 : ");

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();
            if (input.equals("0")) {
                break;
            } else {
                System.out.println("[알림] 0을 입력하면 돌아갑니다.");
            }
        }
    }

    // 한글 포함 문자열 패딩 보정
    private static String padKorean(String str, int targetWidth) {
        int width = 0;
        for (char c : str.toCharArray()) {
            width += (c >= '\uAC00' && c <= '\uD7A3') ? 2 : 1;
        }
        int pad = targetWidth - width;
        return str + " ".repeat(Math.max(pad, 0));
    }

    public static void movieTopten(List<Movie> list, int totalAudiAcc) {
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("누적 관객 순위 TOP 10", null, ConsoleUI.GREEN, ConsoleUI.GREEN);

        System.out.println(" 순위 | 영화 제목                    | 관객 수        | 상태");
        System.out.println("-".repeat(65));

        for (int i = 0; i < list.size(); i++) {
            Movie m = list.get(i);
            String rank  = String.format(" %-4d", i + 1);
            String title = padKorean(m.getMovieTitle(), 28);
            String audi  = String.format("%,10d명", m.getAudiAcc());
            String status = getStatus(m);

            System.out.printf("%s| %s| %s | %s%n", rank, title, audi, status);
        }

        System.out.println("-".repeat(65));
        System.out.printf(" ▶ 현재 전체 누적 관객 수: %,d명%n", totalAudiAcc);
        ConsoleUI.printLine(ConsoleUI.GREEN);
        System.out.print("[0] 돌아가기 : ");
        sc.nextLine();
    }

    private static String getStatus(Movie m) {
        if (m.getIsScreening()) {
            return "상영중";
        }
        try {
            LocalDate releaseDate = LocalDate.parse(m.getReleaseDate());
            if (releaseDate.isAfter(LocalDate.now())) {
                return "개봉예정";
            }
        } catch (Exception e) {
            System.out.println("날짜가 없습니다.");
        }
        return "상영종료";
    }

    public static void reservationMovie(List<WeeklyStat> list) {

        // 요일 인덱스 매핑 (DAYOFWEEK: 1=일, 2=월, ..., 7=토)
        String[] dayNames = {"일", "월", "화", "수", "목", "금", "토"};

        // DB 결과를 요일 인덱스(0=일~6=토)로 매핑
        int[] counts = new int[7];
        int[] sales  = new int[7];

        for (WeeklyStat stat : list) {
            int idx    = stat.getDayOfWeek() - 1; // 1=일 → 0, 7=토 → 6
            counts[idx] = stat.getCount();
            sales[idx]  = stat.getDailySales();
        }

        // 최대 매출 요일 계산
        int maxSales   = 0;
        int maxDayIdx  = 0;
        int totalSales = 0;
        for (int i = 0; i < 7; i++) {
            totalSales += sales[i];
            if (sales[i] > maxSales) {
                maxSales  = sales[i];
                maxDayIdx = i;
            }
        }

        ConsoleUI.blank(1);
        ConsoleUI.printHeader("주간 매출 및 예매 분석", null, ConsoleUI.GREEN, ConsoleUI.GREEN);
        System.out.println(" 요일 | 예매수 |     그래프 (5건당 *)      |   일일 매출액");
        System.out.println("-".repeat(61));

        for (int i = 0; i < 7; i++) {
            String day    = dayNames[i];
            int count     = counts[i];
            int sale      = sales[i];
            String stars  = "*".repeat(count / 5);
            String bar    = stars.isEmpty() ? "-" : stars;

            System.out.printf(" %s   | %4d건 | %-40s | ₩%,d%n",
                    day, count, bar, sale);
        }

        System.out.println("-------------------------------------------------------------");
        System.out.printf(" [결과 분석] 이번 주는 '%s요일'에 최대 매출액을 달성했습니다.%n",
                dayNames[maxDayIdx]);
        System.out.printf(" ▶ 주간 총 합산 매출: ₩%,d%n", totalSales);
        ConsoleUI.printLine(ConsoleUI.GREEN);
        System.out.print("[0] 돌아가기 : ");
        sc.nextLine();
    }

}
