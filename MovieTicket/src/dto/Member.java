package dto;


import java.util.List;


/**
 * 2026-03-11
 * 이동혁
 * MemberDTO 구현
 */
public class Member {
	private int memberId;
	private String userId;
	private String password;
	private String name;
	private String phone;
	private String address;
	private String birthDate;
    private String createAt;
	/**
	 * 20260312
	 * 이동혁
	 * 장르를 리스트 형태로 받아오도록 수정
	 */
	private List<String> preferredGenre;
	private String cardInfo;
	private String role;

	public Member() {

	}

	public Member(int memberId, String userId, String name) {
		this.memberId = memberId;
		this.userId = userId;
		this.name = name;
	}

    public Member(int memberId, String userId, String password, String name, String phone, String address, String birthDate, List<String> genreList, String cardInfo, String role, String createAt) {
        this.memberId = memberId;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.preferredGenre = genreList;
        this.cardInfo = cardInfo;
        this.role = role;
        this.createAt = createAt;
    }

    public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}



	public List<String> getPreferredGenre() {
		return preferredGenre;
	}

	public void setPreferredGenre(List<String> preferredGenre) {
		this.preferredGenre = preferredGenre;
	}

	public String getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Member(int memberId, String userId, String password, String name, String phone, 
			String address, String birthDate, List<String> preferredGenre, String cardInfo, String role) {
		this.memberId = memberId;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.birthDate = birthDate;
		this.preferredGenre = preferredGenre;
		this.cardInfo = cardInfo;
		this.role = role;
	}

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
