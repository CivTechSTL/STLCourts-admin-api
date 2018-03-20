package svc.security.auth.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import svc.models.LoginUser;

public class LoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter{

	private final ObjectMapper objectMapper;
	
	public LoginAuthenticationProcessingFilter(String defaultFilterProcessesUrl, ObjectMapper mapper) {
		super(defaultFilterProcessesUrl);
		
		this.objectMapper = mapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		// LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
		LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
		System.out.println("Received in Authentication, Token: " + loginRequest.getTokenId());
		LoginUser loginUser = new LoginUser();
		loginUser.setTokenId(loginRequest.getTokenId());
		OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(loginUser, null, null);
		
		return null;
	}

}
