package svc.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

// this class was created so the LoginManager could be unit tested by injecting this class

@Component
public class GoogleTokenVerifier {
	
	
	public GoogleIdToken verify(String idTokenString) throws GeneralSecurityException, IOException{
		HttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
			    // Specify the CLIENT_ID of the app that accesses the backend:
			    .setAudience(Collections.singletonList("473553693292-s73tvaovrej8gfiijto1mk8ag30g8ck9.apps.googleusercontent.com"))
			    .build();
		
		return verifier.verify(idTokenString);
	}

}
