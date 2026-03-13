package dto.request;

public class RoomCreateRequest {
	
	private boolean isShowing;
	private String name;

	public RoomCreateRequest() {}

	public RoomCreateRequest(boolean isShowing, String name) {
		this.isShowing = isShowing;
		this.name = name;
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
		builder.append("RoomCreateRequest [isShowing=");
		builder.append(isShowing);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
}
