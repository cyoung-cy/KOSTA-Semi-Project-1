package dto;

import java.sql.Timestamp;

public class Reservation {
	
	private int reservationId;
	private int memberId;
	private int movieId;
	private int scheduleId;
	private int totalPrice;
	private int count;
	private Timestamp startTime;
	private Timestamp endTime;
	
	public Reservation() {
		
	}
	
	public Reservation(int reservationId, int memberId, int movieId, int scheduleId, int totalPrice, int count,
			Timestamp startTime, Timestamp endTime) {
		this.reservationId = reservationId;
		this.memberId = memberId;
		this.movieId = movieId;
		this.scheduleId = scheduleId;
		this.totalPrice = totalPrice;
		this.count = count;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reservation [reservationId=");
		builder.append(reservationId);
		builder.append(", memberId=");
		builder.append(memberId);
		builder.append(", movieId=");
		builder.append(movieId);
		builder.append(", scheduleId=");
		builder.append(scheduleId);
		builder.append(", totalPrice=");
		builder.append(totalPrice);
		builder.append(", count=");
		builder.append(count);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append("]");
		return builder.toString();
	}
	
}
