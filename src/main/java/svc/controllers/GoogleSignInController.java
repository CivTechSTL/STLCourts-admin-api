package svc.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import svc.mangers.LoginManager;
import svc.models.SignInToken;

@RestController
public class GoogleSignInController {
	
	@Autowired
	LoginManager loginManager;
	
	@PostMapping(value = "googleSignin")
	public String googleSignIn(@RequestBody final SignInToken signInToken) throws GeneralSecurityException, IOException{
		System.out.println("Received Token: " + signInToken.getToken());
		
//		String result = loginManager.verifyGoogleToken(signInToken.getToken());
//		return result;
		return "hello";
	}

}


