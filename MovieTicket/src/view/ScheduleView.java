package view;

import cache.CinemaCache;
import dto.Movie;
import dto.Room;
import dto.Schedules;
import service.SchedulesService;

import java.util.*;

public class ScheduleView {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * 영화 등록 후 스케줄 등록 여부 확인 후 진행
     */
    public static void askAndInsertSchedule(Movie movie) {
        System.out.print("\n스케줄도 등록하시겠습니까? (Y/N): ");
        String ans = sc.nextLine().trim();
        if (ans.equalsIgnoreCase("Y")) {
            insertScheduleForMovie(movie);
        }
    }

    /**
     * 영화 기준 상영관별 가용 시간 슬롯 출력 및 스케줄 등록
     */
    public static void insertScheduleForMovie(Movie movie) {
        SchedulesService schedulesService = SchedulesService.getInstance();
        CinemaCache cache = CinemaCache.getInstance();

        // 1. 날짜 입력
        String date = promptDate();

        // 2. 러닝타임 기준 시간 슬롯 생성
        List<String[]> allSlots = schedulesService.generateTimeSlots(movie.getScreeningTime());

        // 3. 상영관 목록
        List<Room> roomList = new ArrayList<>(cache.getRoomMap().values());

        System.out.println("\n====== 상영관별 가용 시간 선택 ======");
        System.out.println("* 영화: " + movie.getMovieTitle() + " (" + movie.getScreeningTime() + "분)");
        System.out.println("* 날짜: " + date);
        System.out.println();

        // 4. 상영관별 루프
        for (Room room : roomList) {
            List<String[]> availableSlots = getAvailableSlots(
                    schedulesService, room, date, allSlots);

            printRoomSlots(room, availableSlots);

            if (availableSlots.isEmpty()) continue;

            // 5. 사용자 선택
            System.out.print("[" + room.getName() + "] 등록할 시간 번호 선택 (여러 개: 1,3 / 건너뛰기: 0): ");
            String input = sc.nextLine().trim();

            if (input.equals("0") || input.isEmpty()) {
                System.out.println("  → 건너뜁니다.\n");
                continue;
            }

            // 6. 선택한 슬롯 등록
            registerSelectedSlots(schedulesService, movie, room, availableSlots, date, input);
            System.out.println();
        }

        ConsoleUI.success("스케줄 등록이 완료되었습니다!");
    }

    /**
     * 날짜 입력 (형식 검증 포함)
     */
    private static String promptDate() {
        while (true) {
            System.out.print("상영 날짜를 입력하세요 (예: 2026-03-25): ");
            String date = sc.nextLine().trim();
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return date;
            }
            System.out.println("❌ 날짜 형식이 올바르지 않습니다. (예: 2026-03-25)");
        }
    }

    /**
     * 해당 상영관의 가용 슬롯 계산 (이미 사용 중인 시간 제외)
     */
    private static List<String[]> getAvailableSlots(
            SchedulesService schedulesService, Room room,
            String date, List<String[]> allSlots) {

        List<Schedules> existing = schedulesService.getSchedulesByRoomAndDate(room.getRoomId(), date);

        Set<String> usedStartTimes = new HashSet<>();
        for (Schedules s : existing) {
            // "HH:mm" 형태로 추출
            String usedTime = s.getStartTime().toString().substring(11, 16);
            usedStartTimes.add(usedTime);
        }

        List<String[]> available = new ArrayList<>();
        for (String[] slot : allSlots) {
            if (!usedStartTimes.contains(slot[0])) {
                available.add(slot);
            }
        }
        return available;
    }

    /**
     * 상영관별 가용 슬롯 출력
     */
    private static void printRoomSlots(Room room, List<String[]> availableSlots) {
        System.out.println("┌─ [" + room.getName() + "] ─────────────────────");
        if (availableSlots.isEmpty()) {
            System.out.println("│  ❌ 해당 날짜에 가용 시간대가 없습니다.");
        } else {
            for (int i = 0; i < availableSlots.size(); i++) {
                System.out.printf("│  [%d] %s ~ %s%n",
                        i + 1,
                        availableSlots.get(i)[0],
                        availableSlots.get(i)[1]);
            }
        }
        System.out.println("└────────────────────────────────────");
    }

    /**
     * 선택한 슬롯을 DB에 등록
     */
    private static void registerSelectedSlots(
            SchedulesService schedulesService, Movie movie, Room room,
            List<String[]> availableSlots, String date, String input) {

        String[] choices = input.split(",");
        for (String choice : choices) {
            try {
                int idx = Integer.parseInt(choice.trim()) - 1;
                if (idx < 0 || idx >= availableSlots.size()) {
                    System.out.println("  ❌ 잘못된 번호: " + (idx + 1));
                    continue;
                }

                String startTimeStr = date + " " + availableSlots.get(idx)[0] + ":00";

                Schedules schedule = new Schedules();
                schedule.setMovieId(movie.getMovieId());
                schedule.setRoomId(room.getRoomId());
                schedule.setStartTime(java.sql.Timestamp.valueOf(startTimeStr));

                schedulesService.registerSchedule(schedule);
                System.out.println("  ✅ [" + room.getName() + "] "
                        + availableSlots.get(idx)[0] + " 등록 완료!");

            } catch (NumberFormatException e) {
                System.out.println("  ❌ 숫자만 입력하세요: " + choice);
            } catch (Exception e) {
                System.out.println("  ❌ 등록 실패: " + e.getMessage());
            }
        }
    }
}