package dto.request;

public class RoomCreateRequest {
	
	private int roomId;
	private int movieId;
	private boolean isShowing;
	private String name;
	
	public RoomCreateRequest() {}
	
	public RoomCreateRequest(int roomId, int movieId, boolean isShowing, String name) {
		super();
		this.roomId = roomId;
		this.movieId = movieId;
		this.isShowing = isShowing;
		this.name = name;
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

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean isShowing) {
		this.isShowing = isShowing;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RoomCreateRequest [roomId=");
		builder.append(roomId);
		builder.append(", movieId=");
		builder.append(movieId);
		builder.append(", isShowing=");
		builder.append(isShowing);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
}
