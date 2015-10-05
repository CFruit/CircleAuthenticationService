package circleAuthenticationServiceDataModel;

public class CircleSignInResponse {
	Boolean succeed;
	String circleAccessToken;
	int code;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Boolean getSucceed() {
		return succeed;
	}
	public void setSucceed(Boolean succeed) {
		this.succeed = succeed;
	}
	public String getCircleAccessToken() {
		return circleAccessToken;
	}
	public void setCircleAccessToken(String circleAccessToken) {
		this.circleAccessToken = circleAccessToken;
	}
}
