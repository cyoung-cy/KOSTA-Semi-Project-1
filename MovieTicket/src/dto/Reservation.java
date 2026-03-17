package dto;

import java.sql.Timestamp;
import java.util.List;

public class Reservation {
	/*
	 * 0313
	 * 이동혁
	 * 예약 조회 시 
	 */
	private int reservationId;
	private int memberId;
	private int scheduleId;
	private int movieId;
	private String cardInfo;
	private Timestamp paidAt;
	
	// VO 따로 만들어서 좌석, 스케줄, 영화 정보까지 담기.

	public Reservation() {}
	
	public Reservation(int reservationId, int memberId, int scheduleId, int movieId, String cardInfo,
			Timestamp paidAt) {
		this.reservationId = reservationId;
		this.memberId = memberId;
		this.scheduleId = scheduleId;
		this.movieId = movieId;
		this.cardInfo = cardInfo;
		this.paidAt = paidAt;
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

	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public Timestamp getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(Timestamp paidAt) {
		this.paidAt = paidAt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Reservation [reservationId=");
		builder.append(reservationId);
		builder.append(", memberId=");
		builder.append(memberId);
		builder.append(", scheduleId=");
		builder.append(scheduleId);
		builder.append(", movieId=");
		builder.append(movieId);
		builder.append(", cardInfo=");
		builder.append(cardInfo);
		builder.append(", paidAt=");
		builder.append(paidAt);
		builder.append("]");
		return builder.toString();
	}

}
