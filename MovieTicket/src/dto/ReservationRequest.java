package dto;

import java.util.List;

public class ReservationRequest {

	private int memberId; 
	private int scheduleId; 
	private int adultCount;
	private int teenCount;
	private int babyCount;
	private int movieId;

	private List<String> selectSeats;

	private String cardInfo;
	
	public ReservationRequest() {}

	public ReservationRequest(int memberId, int scheduleId, int adultCount, int teenCount, int babyCount,
			int movieId ,List<String> selectSeats, String cardInfo) {
		this.memberId = memberId;
		this.scheduleId = scheduleId;
		this.adultCount = adultCount;
		this.teenCount = teenCount;
		this.babyCount = babyCount;
		this.movieId = movieId;
		this.selectSeats = selectSeats;
		this.cardInfo = cardInfo;
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

	public int getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public int getTeenCount() {
		return teenCount;
	}

	public void setTeenCount(int teenCount) {
		this.teenCount = teenCount;
	}

	public int getBabyCount() {
		return babyCount;
	}

	public void setBabyCount(int babyCount) {
		this.babyCount = babyCount;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public List<String> getSelectSeats() {
		return selectSeats;
	}

	public void setSelectSeats(List<String> selectSeats) {
		this.selectSeats = selectSeats;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReservationRequest [memberId=");
		builder.append(memberId);
		builder.append(", scheduleId=");
		builder.append(scheduleId);
		builder.append(", adultCount=");
		builder.append(adultCount);
		builder.append(", teenCount=");
		builder.append(teenCount);
		builder.append(", babyCount=");
		builder.append(babyCount);
		builder.append(", movieId=");
		builder.append(movieId);
		builder.append(", selectSeats=");
		builder.append(selectSeats);
		builder.append(", cardInfo=");
		builder.append(cardInfo);
		builder.append("]");
		return builder.toString();
	} 

}