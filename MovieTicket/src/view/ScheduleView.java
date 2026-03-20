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
        ConsoleUI.blank(1);
        String ans = ConsoleUI.prompt(sc, "상영 스케줄도 등록하시겠습니까? (Y/N)").trim();

        if (ans.equalsIgnoreCase("Y")) {
            insertScheduleForMovie(movie);
        } else {
            ConsoleUI.info("스케줄 등록을 건너뜁니다.");
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

        ConsoleUI.blank(1);
        ConsoleUI.printHeader(
                "SCHEDULE REGISTRATION",
                "상영관별 가용 시간을 선택하세요",
                ConsoleUI.GREEN,
                ConsoleUI.GREEN
        );

        System.out.println("영화 : " + ConsoleUI.CYAN + movie.getMovieTitle() + ConsoleUI.RESET
                + " (" + movie.getScreeningTime() + "분)");
        System.out.println("날짜 : " + ConsoleUI.CYAN + date + ConsoleUI.RESET);
        System.out.println("-".repeat(ConsoleUI.WIDTH));

        // 4. 상영관별 루프
        for (Room room : roomList) {
            List<String[]> availableSlots = getAvailableSlots(
                    schedulesService, room, date, allSlots
            );

            printRoomSlots(room, availableSlots);

            if (availableSlots.isEmpty()) {
                ConsoleUI.info(room.getName() + "에는 등록 가능한 시간대가 없습니다.");
                System.out.println("-".repeat(ConsoleUI.WIDTH));
                continue;
            }

            // 5. 사용자 선택
            String input = ConsoleUI.prompt(
                    sc,
                    room.getName() + " 등록 시간을 선택하세요 (예: 1,3 / 0: 건너뛰기)"
            ).trim();

            if (input.equals("0") || input.isEmpty()) {
                ConsoleUI.info(room.getName() + " 스케줄 등록을 건너뜁니다.");
                System.out.println("-".repeat(ConsoleUI.WIDTH));
                continue;
            }

            // 6. 선택한 슬롯 등록
            registerSelectedSlots(schedulesService, movie, room, availableSlots, date, input);
            System.out.println("-".repeat(ConsoleUI.WIDTH));
        }

        ConsoleUI.success("스케줄 등록이 완료되었습니다!");
    }

    /**
     * 날짜 입력 (형식 검증 포함)
     */
    private static String promptDate() {
        while (true) {
            String date = ConsoleUI.prompt(sc, "상영 날짜를 입력하세요 (예: 2026-03-25)").trim();

            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return date;
            }

            ConsoleUI.alert("날짜 형식이 올바르지 않습니다. (예: 2026-03-25)");
        }
    }

    /**
     * 해당 상영관의 가용 슬롯 계산 (이미 사용 중인 시간 제외)
     */
    private static List<String[]> getAvailableSlots(
            SchedulesService schedulesService,
            Room room,
            String date,
            List<String[]> allSlots
    ) {
        List<Schedules> existing = schedulesService.getSchedulesByRoomAndDate(room.getRoomId(), date);

        Set<String> usedStartTimes = new HashSet<>();
        for (Schedules s : existing) {
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
        System.out.println(ConsoleUI.GREEN + "[ " + room.getName() + " ]" + ConsoleUI.RESET);

        if (availableSlots.isEmpty()) {
            System.out.println("  등록 가능한 시간대가 없습니다.");
            return;
        }

        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.println(
                    "  [" + (i + 1) + "] "
                            + availableSlots.get(i)[0] + " ~ " + availableSlots.get(i)[1]
            );
        }

        System.out.println("-".repeat(ConsoleUI.WIDTH));
    }

    /**
     * 선택한 슬롯을 DB에 등록
     */
    private static void registerSelectedSlots(
            SchedulesService schedulesService,
            Movie movie,
            Room room,
            List<String[]> availableSlots,
            String date,
            String input
    ) {
        String[] choices = input.split(",");

        for (String choice : choices) {
            try {
                int idx = Integer.parseInt(choice.trim()) - 1;

                if (idx < 0 || idx >= availableSlots.size()) {
                    ConsoleUI.alert("잘못된 번호입니다: " + (idx + 1));
                    continue;
                }

                String startTimeStr = date + " " + availableSlots.get(idx)[0] + ":00";

                Schedules schedule = new Schedules();
                schedule.setMovieId(movie.getMovieId());
                schedule.setRoomId(room.getRoomId());
                schedule.setStartTime(java.sql.Timestamp.valueOf(startTimeStr));

                schedulesService.registerSchedule(schedule);

                ConsoleUI.success(
                        room.getName() + " " + availableSlots.get(idx)[0] + " 등록 완료"
                );

            } catch (NumberFormatException e) {
                ConsoleUI.alert("숫자만 입력하세요: " + choice);
            } catch (Exception e) {
                ConsoleUI.error("등록 실패: " + e.getMessage());
            }
        }
    }
}