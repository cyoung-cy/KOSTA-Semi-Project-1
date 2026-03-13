package vo;

import java.sql.Timestamp;
import java.util.List;

import dto.Seat;

public class Ticket {

	/*
	 * 0313
	 * 이동혁
	 * 예약 조회 시 
	 */
	private int reservationId; // 예약 번호
	private String userName; // 예약자 이름
	private int movieName; // 예약한 영화 이름
	private int totalPrice; // 총 가격
	private int count; // 예약 좌석 수
	private List<Seat> seats; // 예약한 좌석 정보
	private Timestamp startTime; 
	private Timestamp endTime;
	
	// VO 따로 만들어서 좌석, 스케줄, 영화 정보까지 담기.

	public Ticket() {
		
	}
	
	public Ticket(int reservationId, int memberId, int movieId, int scheduleId, int totalPrice, int count,
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

