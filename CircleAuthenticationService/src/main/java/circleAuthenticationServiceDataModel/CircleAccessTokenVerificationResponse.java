package circleAuthenticationServiceDataModel;

public class CircleAccessTokenVerificationResponse {
	Boolean succeed;
	String username;
	int code;
	public Boolean getSucceed() {
		return succeed;
	}
	public void setSucceed(Boolean succeed) {
		this.succeed = succeed;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
}
