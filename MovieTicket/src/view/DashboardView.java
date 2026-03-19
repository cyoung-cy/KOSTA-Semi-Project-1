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

    // ── 추가 ANSI 코드 (ConsoleUI에 없는 것만) ──────────────────────
    private static final String RESET          = ConsoleUI.RESET;
    private static final String BOLD           = ConsoleUI.BOLD;
    private static final String GREEN          = ConsoleUI.GREEN;
    private static final String YELLOW         = ConsoleUI.YELLOW;
    private static final String CYAN           = ConsoleUI.CYAN;
    private static final String RED            = ConsoleUI.RED;
    private static final String BLUE           = ConsoleUI.BLUE;
    private static final String PURPLE         = ConsoleUI.PURPLE;

    private static final String BRIGHT_GREEN   = "\u001B[92m";
    private static final String BRIGHT_YELLOW  = "\u001B[93m";
    private static final String BRIGHT_CYAN    = "\u001B[96m";
    private static final String BRIGHT_RED     = "\u001B[91m";
    private static final String BRIGHT_BLUE    = "\u001B[94m";
    private static final String BRIGHT_MAGENTA = "\u001B[95m";
    private static final String DIM            = "\u001B[2m";

    // 그래프 색상 팔레트 (장르/요일별 순환 사용)
    private static final String[] BAR_COLORS = {
            BRIGHT_CYAN, BRIGHT_GREEN, BRIGHT_YELLOW,
            BRIGHT_MAGENTA, BRIGHT_BLUE, BRIGHT_RED, CYAN
    };

    // ── 공통 헬퍼 ───────────────────────────────────────────────────

    /** 한글 포함 문자열의 터미널 표시 너비 계산 (ANSI 코드 제거 후) */
    private static int visibleWidth(String s) {
        // ANSI 이스케이프 코드 제거
        String plain = s.replaceAll("\u001B\\[[;\\d]*m", "");
        int w = 0;
        for (char c : plain.toCharArray()) {
            // 한글 완성형: 2칸, 나머지: 1칸
            w += (c >= '\uAC00' && c <= '\uD7A3') ? 2 : 1;
        }
        return w;
    }

    /**
     * ANSI 색상 코드가 포함된 문자열을 targetWidth 기준으로 우측 패딩.
     * printf("%-Ns", str) 는 ANSI 길이까지 포함해 계산하므로 이 메서드로 대체.
     */
    private static String padRight(String str, int targetWidth) {
        int pad = targetWidth - visibleWidth(str);
        return str + " ".repeat(Math.max(pad, 0));
    }

    /** 한글 포함 순수 문자열 우측 패딩 (ANSI 없는 경우용) */
    private static String padKorean(String str, int targetWidth) {
        int width = 0;
        for (char c : str.toCharArray()) {
            width += (c >= '\uAC00' && c <= '\uD7A3') ? 2 : 1;
        }
        int pad = targetWidth - width;
        return str + " ".repeat(Math.max(pad, 0));
    }

    /** [0] 돌아가기 공통 대기 루프 */
    private static void waitForBack() {
        System.out.print(DIM + "\n  [0] 돌아가기 → " + RESET);
        Scanner sc = new Scanner(System.in);
        while (true) {
            if ("0".equals(sc.nextLine().trim())) break;
            ConsoleUI.alert("0을 입력하면 돌아갑니다.");
        }
    }

    // ── 1. 회원 유입 및 증감 리포트 ─────────────────────────────────
    /*
     * 0316 김채영
     * TODO: 신규 가입자 및 회원 증감 추이 그래프
     */
    public static void user(List<Member> dataList) {

        // 날짜 → 가입자 수 Map 변환
        Map<String, Integer> dataMap = new LinkedHashMap<>();
        for (Member m : dataList) {
            dataMap.put(m.getCreateAt(), m.getMemberId());
        }

        DateTimeFormatter dbFmt      = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("MM-dd(E)", Locale.KOREAN);
        LocalDate today = LocalDate.now();

        List<String> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(today.minusDays(i).format(dbFmt));
        }

        // 최대값 → 바 스케일 계산
        int maxCnt = dates.stream()
                .mapToInt(d -> dataMap.getOrDefault(d, 0))
                .max().orElse(1);
        int barMax = 30; // 최대 바 길이(칸)

        // 전일 대비 증감율 계산
        int todayCnt     = dataMap.getOrDefault(dates.get(0), 0);
        int yesterdayCnt = dataMap.getOrDefault(dates.get(1), 0);
        String changeColor;
        String changeStr;
        if (yesterdayCnt == 0) {
            changeStr   = todayCnt > 0 ? "+100.0%  상승세 ▲" : "0.0%  변동없음 ▬";
            changeColor = todayCnt > 0 ? BRIGHT_GREEN : YELLOW;
        } else {
            double rate = ((double)(todayCnt - yesterdayCnt) / yesterdayCnt) * 100;
            String arrow = rate > 0 ? "▲" : rate < 0 ? "▼" : "▬";
            String trend = rate > 0 ? "상승세" : rate < 0 ? "하락세" : "변동없음";
            changeStr   = String.format("%s%.1f%%  %s %s", rate > 0 ? "+" : "", rate, trend, arrow);
            changeColor = rate > 0 ? BRIGHT_GREEN : rate < 0 ? BRIGHT_RED : YELLOW;
        }

        // ── 출력 ──
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("회원 유입 및 증감 리포트", null, GREEN, GREEN);

        System.out.println(
                DIM + "  기준일자        가입자 수   시각화  (■ 1명)" + RESET
        );
        System.out.println(GREEN + "  " + "─".repeat(55) + RESET);

        for (int i = 0; i < dates.size(); i++) {
            String dateStr = dates.get(i);
            int cnt = dataMap.getOrDefault(dateStr, 0);
            LocalDate date = LocalDate.parse(dateStr, dbFmt);
            String displayDate = date.format(displayFmt);

            // 바 길이 비례 계산
            int barLen = (maxCnt == 0) ? 0 : (int) Math.round((double) cnt / maxCnt * barMax);

            // 오늘 = 밝은 색, 나머지 = 순환 색
            String barColor = (i == 0) ? BRIGHT_GREEN : BAR_COLORS[i % BAR_COLORS.length];
            String bar = barColor + "■".repeat(barLen) + RESET;

            // 오늘 강조 (ANSI 포함 문자열)
            String dateLabel = (i == 0)
                    ? BOLD + BRIGHT_GREEN + displayDate + RESET
                    : displayDate;

            // printf("%-Ns")는 ANSI 코드 길이까지 포함해 패딩 계산 → padRight()로 수동 처리
            // MM-dd(E) 형태: 한글 요일 1자=2칸 포함 → 가시 너비 12 고정
            String col1 = padRight(dateLabel, 12);
            String col2 = String.format("%4d명", cnt);
            System.out.printf("  %s  %s   %s%n", col1, col2, bar);
        }

        System.out.println(GREEN + "  " + "─".repeat(55) + RESET);
        System.out.printf("  ▶ 전일 대비 증감율: %s%s%s%n", changeColor + BOLD, changeStr, RESET);
        ConsoleUI.printLine(GREEN);
        waitForBack();
    }

    // ── 2. 선호 장르별 시장 점유율 ───────────────────────────────────
    /*
     * 0316 김채영
     * TODO: 영화 장르 선호도 그래프
     */
    public static void preferGenre(List<Member> dataList) {

        int total = 0;
        for (Member m : dataList) total += m.getMemberId();

        int graphMax = 20; // 바 최대 길이

        ConsoleUI.blank(1);
        ConsoleUI.printHeader("선호 장르별 시장 점유율", null, GREEN, GREEN);

        System.out.println(
                DIM + "  순위  장르              회원 수    비중      그래프" + RESET
        );
        System.out.println(GREEN + "  " + "─".repeat(58) + RESET);

        int rank = 1;
        for (Member m : dataList) {
            String genre = m.getRole();
            int    cnt   = m.getMemberId();
            double ratio = (total == 0) ? 0 : (double) cnt / total * 100;

            int filled = (total == 0) ? 0 : (int) Math.round((double) cnt / total * graphMax);
            int empty  = graphMax - filled;

            String barColor = BAR_COLORS[(rank - 1) % BAR_COLORS.length];
            String bar = barColor + "█".repeat(filled) + RESET
                    + DIM + "░".repeat(empty) + RESET;

            // 순위 문자열: 가시 너비 4 고정 ("1위 "~"8위 " 모두 동일)
            String rankRaw = (rank == 1)
                    ? BOLD + BRIGHT_YELLOW + rank + "위" + RESET
                    : rank + "위";
            String rankCol = padRight(rankRaw, 4);  // "1위"~"8위" = 가시4칸

            // 장르명: 가시 너비 12 고정
            String genreCol = padKorean(genre, 12);

            // 고정폭 숫자 컬럼
            String cntCol   = String.format("%5d명", cnt);
            String ratioCol = String.format("%5.1f%%", ratio);

            System.out.printf("  %s  %s  %s   %s   %s%n",
                    rankCol, genreCol, cntCol, ratioCol, bar);
            rank++;
        }

        System.out.println(GREEN + "  " + "─".repeat(58) + RESET);
        System.out.printf("  ▶ 전체 선호 장르 선택 수: %s%d건%s%n", BOLD + BRIGHT_CYAN, total, RESET);
        ConsoleUI.printLine(GREEN);
        waitForBack();
    }

    // ── 3. 누적 관객 순위 TOP 10 ─────────────────────────────────────
    public static void movieTopten(List<Movie> list, int totalAudiAcc) {

        // 최대 관객 수 (바 스케일용)
        int maxAudi = list.stream().mapToInt(Movie::getAudiAcc).max().orElse(1);
        int barMax  = 22;

        ConsoleUI.blank(1);
        ConsoleUI.printHeader("누적 관객 순위 TOP 10", null, GREEN, GREEN);

        System.out.println(
                DIM + "  순위  영화 제목                            관객 수          상태      그래프" + RESET
        );
        System.out.println(GREEN + "  " + "─".repeat(74) + RESET);

        for (int i = 0; i < list.size(); i++) {
            Movie m = list.get(i);

            // 순위 컬럼: 가시 너비 6 고정
            // 🥇🥈🥉 이모지는 터미널에서 2칸 차지 → 순수 텍스트 부분만 ANSI 포함
            String rankRaw;
            if      (i == 0) rankRaw = BOLD + BRIGHT_YELLOW + "1위" + RESET;
            else if (i == 1) rankRaw = BOLD + "\u001B[37m"  + "2위" + RESET;
            else if (i == 2) rankRaw = BOLD + "\u001B[33m"  + "3위" + RESET;
            else             rankRaw = (i + 1) + "위";
            String rankCol = padRight(rankRaw, 4); // "1위"~"10위" 최대 가시4칸

            // 영화 제목: 가시 너비 28 고정 (한글 2칸 보정)
            String titleCol = padKorean(m.getMovieTitle(), 28);

            // 관객 수: 우측 정렬 고정폭 (최대 13자리+명)
            String audiCol = String.format("%,12d명", m.getAudiAcc());

            // 상태: 가시 너비 6 고정 ("상영중" 6칸, "상영종료" 8칸 → padRight로 맞춤)
            String status    = getStatus(m);
            String statusRaw = switch (status) {
                case "상영중"   -> BRIGHT_GREEN  + BOLD + "상영중" + RESET;
                case "개봉예정" -> BRIGHT_YELLOW + BOLD + "개봉예정" + RESET;
                default         -> DIM + "상영종료" + RESET;
            };
            String statusCol = padRight(statusRaw, 8); // "상영종료"=8칸 기준

            // 바
            int barLen = (int) Math.round((double) m.getAudiAcc() / maxAudi * barMax);
            String barColor = (i < 3) ? BRIGHT_YELLOW : BAR_COLORS[i % BAR_COLORS.length];
            String bar = barColor + "▬".repeat(barLen) + RESET;

            System.out.printf("  %s  %s  %s  %s  %s%n",
                    rankCol, titleCol, audiCol, statusCol, bar);
        }

        System.out.println(GREEN + "  " + "─".repeat(74) + RESET);
        System.out.printf("  ▶ 전체 누적 관객 수: %s%,d명%s%n", BOLD + BRIGHT_CYAN, totalAudiAcc, RESET);
        ConsoleUI.printLine(GREEN);
        System.out.print(DIM + "\n  [0] 돌아가기 → " + RESET);
        sc.nextLine();
    }

    private static String getStatus(Movie m) {
        if (m.getIsScreening()) return "상영중";
        try {
            LocalDate releaseDate = LocalDate.parse(m.getReleaseDate());
            if (releaseDate.isAfter(LocalDate.now())) return "개봉예정";
        } catch (Exception e) {
            // 날짜 파싱 실패 시 종료로 처리
        }
        return "상영종료";
    }

    // ── 4. 주간 매출 및 예매 분석 ────────────────────────────────────
    public static void reservationMovie(List<WeeklyStat> list) {

        String[] dayNames  = {"일", "월", "화", "수", "목", "금", "토"};
        // 주말 색상 강조
        String[] dayColors = {
                BRIGHT_RED,   // 일 (빨강)
                CYAN,          // 월
                CYAN,          // 화
                CYAN,          // 수
                CYAN,          // 목
                BRIGHT_YELLOW, // 금
                BRIGHT_RED    // 토 (빨강)
        };

        int[] counts = new int[7];
        int[] sales  = new int[7];
        for (WeeklyStat stat : list) {
            int idx    = stat.getDayOfWeek() - 1;
            counts[idx] = stat.getCount();
            sales[idx]  = stat.getDailySales();
        }

        int maxSales   = 0, maxDayIdx  = 0, totalSales = 0;
        int maxCount   = 0;
        for (int i = 0; i < 7; i++) {
            totalSales += sales[i];
            if (sales[i] > maxSales) { maxSales = sales[i]; maxDayIdx = i; }
            if (counts[i] > maxCount)  maxCount  = counts[i];
        }

        int barMax = 24;

        ConsoleUI.blank(1);
        ConsoleUI.printHeader("주간 매출 및 예매 분석", null, GREEN, GREEN);

        System.out.println(
                DIM + "  요일     예매 수   그래프  (★ 5건)          일일 매출액" + RESET
        );
        System.out.println(GREEN + "  " + "─".repeat(58) + RESET);

        for (int i = 0; i < 7; i++) {
            int count = counts[i];
            int sale  = sales[i];

            // 그래프 바: ★ (5건당 1개), 고정 칸 수 = barMax/5
            int starCount  = count / 5;
            int dotCount   = Math.max(0, barMax / 5 - starCount);
            String barColor = (i == maxDayIdx) ? BRIGHT_YELLOW : dayColors[i];
            String bar = barColor + "★".repeat(starCount) + RESET
                    + DIM + "·".repeat(dotCount) + RESET;

            // 요일 레이블: 가시 너비 5 고정 ("일요일"~"토요일" = 한글3자=6칸)
            // → padRight로 ANSI 포함 안전하게 처리
            String dayRaw = (i == maxDayIdx)
                    ? BOLD + BRIGHT_YELLOW + dayNames[i] + "요일" + RESET
                    : dayColors[i] + dayNames[i] + "요일" + RESET;
            String dayCol = padRight(dayRaw, 6); // "일요일" 한글3자=가시6칸

            // 예매 수: 고정폭 우측 정렬
            String cntCol = String.format("%4d건", count);

            // 매출액: 고정폭 우측 정렬 (₩ + 최대 10자리 + 쉼표)
            String salesRaw  = String.format("₩%,11d", sale);
            String salesCol  = (i == maxDayIdx)
                    ? BOLD + BRIGHT_YELLOW + salesRaw + RESET
                    : salesRaw;

            // 컬럼 구분자 고정으로 모든 행 동일 레이아웃
            System.out.printf("  %s  %s   %-16s  %s%n",
                    dayCol, cntCol, bar, salesCol);
        }

        System.out.println(GREEN + "  " + "─".repeat(58) + RESET);
        System.out.printf("  ▶ 최대 매출: %s'%s요일'%s  |  주간 합산: %s₩%,d%s%n",
                BOLD + BRIGHT_YELLOW, dayNames[maxDayIdx], RESET,
                BOLD + BRIGHT_CYAN, totalSales, RESET);
        ConsoleUI.printLine(GREEN);
        System.out.print(DIM + "\n  [0] 돌아가기 → " + RESET);
        sc.nextLine();
    }
}