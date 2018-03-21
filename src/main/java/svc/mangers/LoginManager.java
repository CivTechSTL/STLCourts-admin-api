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

import svc.exceptions.NotAuthorizedException;
import svc.jwt.factory.JwtTokenFactory;
import svc.models.NewTokenResponse;
import svc.models.User;
import svc.repositories.UserRepository;

@Component
public class LoginManager {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenFactory jwtTokenFactory;
	
	private static final HttpTransport transport = new NetHttpTransport();
	private static final JsonFactory jsonFactory = new JacksonFactory();
	
	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
		    // Specify the CLIENT_ID of the app that accesses the backend:
		    .setAudience(Collections.singletonList("586136797966-6gdr7aslkoa16l23klro33dkic52dpvp.apps.googleusercontent.com"))
		    // Or, if multiple clients access the backend:
		    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
		    .build();

	public NewTokenResponse verifyGoogleTokenAndGetSecurityTokens(String idTokenString) throws GeneralSecurityException, NotAuthorizedException, IOException{
		GoogleIdToken idToken = verifier.verify(idTokenString);
		if (idToken != null) {
		  Payload payload = idToken.getPayload();
	
		  // Print user identifier
		  String userId = payload.getSubject();
		  System.out.println("User ID: " + userId);
	
		  // Get profile information from payload
		  String email = payload.getEmail();
		  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		  String name = (String) payload.get("name");
		  String pictureUrl = (String) payload.get("picture");
		  String locale = (String) payload.get("locale");
		  String familyName = (String) payload.get("family_name");
		  String givenName = (String) payload.get("given_name");
	
		  // Use or store profile information
		  // ...
		  
		  User user = userRepository.findByEmail(email);
		  
		  if (user != null){
			  String jwtToken = jwtTokenFactory.createAccessJwtToken(user);
			  String jwtRefreshToken = jwtTokenFactory.createRefreshToken(user);
			  return new NewTokenResponse(jwtToken, jwtRefreshToken);
			  // return name + " has role: " + user.getRole().toString();
		  }
		  else{
			  throw new NotAuthorizedException("User does not exist.");
		  }
	
		} else {
		  throw new NotAuthorizedException();
		}
	}
}
