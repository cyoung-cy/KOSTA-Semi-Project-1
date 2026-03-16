package vo;

import java.sql.Timestamp;
import java.util.List;

import dto.Seat;

public class Ticket {

	/*
	 * 0313
	 * 이동혁
	 * TODO:영화 예매 정보 담는 VO
	 */
	private int reservationId; // 예약 번호
	private String userName; // 예약자 이름
	private String movieTitle; // 예약한 영화 이름
	private int totalPrice; // 총 가격
	private int count; // 예약 좌석 수
	private List<Seat> seats; // 예약한 좌석 정보
	private String roomName; // 몇 관인지
	private Timestamp startTime; // 상영 시작 시간
	private Timestamp endTime; // 상영 종료 시간
	
	// VO 따로 만들어서 좌석, 스케줄, 영화 정보까지 담기.

	public Ticket() {
		
	}

	public Ticket(int reservationId, String userName, String movieTitle, int totalPrice, int count, String roomName, List<Seat> seats, Timestamp startTime, Timestamp endTime) {
		this.reservationId = reservationId;
		this.userName = userName;
		this.movieTitle = movieTitle;
		this.totalPrice = totalPrice;
		this.count = count;
		this.roomName = roomName;
		this.seats = seats;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
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

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
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

}

