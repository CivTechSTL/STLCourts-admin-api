package svc.mangers;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import io.jsonwebtoken.Jwts;
import svc.exceptions.NotAuthorizedException;
import svc.models.NewTokenResponse;
import svc.models.User;
import svc.repositories.UserRepository;
import svc.security.config.JwtSettings;
import svc.security.jwt.factory.JwtTokenFactory;

@Component
public class LoginManager {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenFactory jwtTokenFactory;
	
	private final JwtSettings settings;
	
	private static final HttpTransport transport = new NetHttpTransport();
	private static final JsonFactory jsonFactory = new JacksonFactory();
	
	@Autowired
    public LoginManager(JwtSettings settings) {
        this.settings = settings;
    }
	
	/* see: ttps://developers.google.com/identity/sign-in/web/backend-auth */
	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
		    // Specify the CLIENT_ID of the app that accesses the backend:
		    .setAudience(Collections.singletonList("586136797966-6gdr7aslkoa16l23klro33dkic52dpvp.apps.googleusercontent.com"))
		    .build();

	public NewTokenResponse verifyGoogleTokenAndGetSecurityTokens(String idTokenString) throws GeneralSecurityException, NotAuthorizedException, IOException{
		GoogleIdToken idToken = verifier.verify(idTokenString);
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
		String email = Jwts.parser().setSigningKey(this.settings.getTokenSigningKey()).parseClaimsJws(refreshToken).getBody().getSubject();
		
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
