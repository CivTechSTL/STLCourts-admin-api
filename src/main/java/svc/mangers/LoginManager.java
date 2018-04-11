package svc.mangers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import svc.exceptions.NotAuthorizedException;
import svc.models.NewTokenResponse;
import svc.models.User;
import svc.repositories.UserRepository;
import svc.security.GoogleTokenVerifier;
import svc.security.JwtsUtility;
import svc.security.jwt.factory.JwtTokenFactory;

@Component
public class LoginManager {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenFactory jwtTokenFactory;
	 
	@Autowired
	GoogleTokenVerifier googleTokenVerifier;
	
	@Autowired
	JwtsUtility jwtsUtility;
	
	public NewTokenResponse verifyGoogleTokenAndGetSecurityTokens(String idTokenString) throws GeneralSecurityException, NotAuthorizedException, IOException{
		GoogleIdToken idToken = googleTokenVerifier.verify(idTokenString);
		if (idToken != null) {
		  Payload payload = idToken.getPayload();
	
		  // Get profile information from payload
		  String email = payload.getEmail();
		  return createUser(email);
	
		} else {
		  throw new NotAuthorizedException("Not properly signed in with Google");
		}
	}
	
	public NewTokenResponse verifyRefreshTokenAndGenerateNewSecurityTokens(String refreshToken){
		//get user from refreshToken
		String email = jwtsUtility.getSubject(refreshToken);
		
		//verify that user is still a valid user and get their role in case it has changed
		return createUser(email);
	}
	
	private NewTokenResponse createUser(String email){
		User user = userRepository.findByEmail(email);
		  
		  if (user != null){
			  String jwtToken = jwtTokenFactory.createAccessJwtToken(user);
			  String jwtRefreshToken = jwtTokenFactory.createRefreshToken(user);
			  return new NewTokenResponse(jwtToken, jwtRefreshToken);
		  }
		  else{
			  throw new NotAuthorizedException("User does not exist.");
		  }
	}
}
