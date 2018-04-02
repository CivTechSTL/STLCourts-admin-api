package svc.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import svc.mangers.LoginManager;
import svc.models.NewTokenResponse;
import svc.models.BasicToken;

@RestController
public class LoginController {
	
	@Autowired
	LoginManager loginManager;
	
	@PostMapping(value = "googleSignin")
	public NewTokenResponse googleSignIn(@RequestBody final BasicToken signInToken) throws GeneralSecurityException, IOException{
		NewTokenResponse result = loginManager.verifyGoogleTokenAndGetSecurityTokens(signInToken.getToken());
		return result;
	}
	
	@PostMapping(value = "refreshToken")
	public NewTokenResponse refreshToken(@RequestBody final BasicToken refreshToken) throws GeneralSecurityException, IOException{
		NewTokenResponse result = loginManager.verifyRefreshTokenAndGenerateNewSecurityTokens(refreshToken.getToken());
		return result;
	}

}


