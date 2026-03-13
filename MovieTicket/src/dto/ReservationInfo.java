package dto;

public class ReservationInfo {
	
	private int reservationInfoId;
	private int reservationId;
	private int seatId;
	private String category;
	private int price;
	
	public ReservationInfo() {
		
	}
	
	public ReservationInfo(int reservationInfoId, int reservationId, int seatId, String category, int price) {
		this.reservationInfoId = reservationInfoId;
		this.reservationId = reservationId;
		this.seatId = seatId;
		this.category = category;
		this.price = price;
	}

	public int getReservationInfoId() {
		return reservationInfoId;
	}

	public void setReservationInfoId(int reservationInfoId) {
		this.reservationInfoId = reservationInfoId;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReservationInfo [reservationInfoId=");
		builder.append(reservationInfoId);
		builder.append(", reservationId=");
		builder.append(reservationId);
		builder.append(", seatId=");
		builder.append(seatId);
		builder.append(", category=");
		builder.append(category);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}
	
}
