package dto;
/**
 * 2026-03-11
 * 이동혁
 * MemberDTO 구현
 */
public class Member {
	private String userID;
	private String password;
	private String name;
	private String phone;
	private String address;
	private String birthDate;
	private String [] preferredGenre;
	private String cardInfo;
	private String role;


    public String getRole() {
        return role;
    }
}
