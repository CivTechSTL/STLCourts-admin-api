package svc.security.auth.login;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

	private String tokenId;
	
	 @JsonCreator
	    public LoginRequest(@JsonProperty("token") String token) {
	        this.tokenId = token;
	    }

	public String getTokenId() {
		return tokenId;
	}

}
