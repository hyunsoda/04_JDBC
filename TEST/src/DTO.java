
public class DTO {

	private String memberId;
	private String memberPw;
	private String phone;
	private String gender;
	
	public DTO() {
	}

	public DTO(String memberId, String memberPw, String phone, String gender) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.phone = phone;
		this.gender = gender;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "DTO [memberId=" + memberId + ", memberPw=" + memberPw + ", phone=" + phone + ", gender=" + gender + "]";
	}
	
	
}
