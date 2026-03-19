package dto;

import java.sql.Timestamp;

import cache.CinemaCache;

public class Schedules {
	
	private int scheduleId;
	private Timestamp startTime;
	private int roomId;
	private int movieId;
	
	public Schedules() {}
	
	public Schedules(int scheduleId, Timestamp startTime, int roomId, int movieId) {
		this.scheduleId = scheduleId;
		this.startTime = startTime;
		this.roomId = roomId;
		this.movieId = movieId;
	}
	
	public String getRoomName() {
        Room room = CinemaCache.getInstance().getRoomById(this.roomId);
        return (room != null) ? room.getName() : "정보 없음";
    }

	public String toDisplayString(int duration) {
		String start = formatTime(startTime);
		String end = startTime.toLocalDateTime()
				.plusMinutes(duration)
				.toLocalTime()
				.toString()
				.substring(0, 5);

		return "[#" + scheduleId + "] "
				+ getRoomName() + " | "
				+ start + " ~ " + end;
	}

	private String formatTime(Timestamp ts) {
		if (ts == null) return "-";

		return ts.toLocalDateTime()
				.toLocalTime()
				.toString()
				.substring(0, 5);
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

}
