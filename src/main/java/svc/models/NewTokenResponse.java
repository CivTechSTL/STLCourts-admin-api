package svc.models;

public class NewTokenResponse {
	public String token;
	public String refreshToken;
	
	public NewTokenResponse(String token, String refreshToken){
		this.token = token;
		this.refreshToken = refreshToken;
	}
}