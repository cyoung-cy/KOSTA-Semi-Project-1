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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Schedules [scheduleId=");
		builder.append(scheduleId);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", roomId=");
		builder.append(roomId);
		builder.append(", movieId=");
		builder.append(movieId);
		builder.append("]");
		return builder.toString();
	}

}
