package dto;

public class Seat {
	
	private int seatId;
	private int roomId;
	private boolean isReserved;
	private String name;
	private int rowNum;
	private int colNum;
	
	public Seat() {}
	
	public Seat(int seatId, int roomId, boolean isReserved, String name, int rowNum, int colNum) {
		this.seatId = seatId;
		this.roomId = roomId;
		this.isReserved = isReserved;
		this.name = name;
		this.rowNum = rowNum;
		this.colNum = colNum;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat [seatId=");
		builder.append(seatId);
		builder.append(", roomId=");
		builder.append(roomId);
		builder.append(", isReserved=");
		builder.append(isReserved);
		builder.append(", name=");
		builder.append(name);
		builder.append(", rowNum=");
		builder.append(rowNum);
		builder.append(", colNum=");
		builder.append(colNum);
		builder.append("]");
		return builder.toString();
	}
		 
}
