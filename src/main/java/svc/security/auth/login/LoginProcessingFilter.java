package svc.security.auth.login;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.util.StringUtils;


import com.fasterxml.jackson.databind.ObjectMapper;

import svc.exceptions.AuthMethodNotSupportedException;
import svc.util.WebUtil;

public class LoginProcessingFilter extends AbstractAuthenticationProcessingFilter{

	private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    private final ObjectMapper objectMapper;
    
	public LoginProcessingFilter(String defaultFilterProcessesUrl, AuthenticationSuccessHandler successHandler, 
            AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
		super(defaultFilterProcessesUrl);
		this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		if (!HttpMethod.POST.name().equals(request.getMethod()) || !WebUtil.isAjax(request)) {
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);

        if (StringUtils.isEmpty(loginRequest.getToken())) {
            throw new AuthenticationServiceException("Google token not provided");
        }

        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(null, null, loginRequest.getToken());

        return this.getAuthenticationManager().authenticate(token);

	}
	
	 @Override
	    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
	            Authentication authResult) throws IOException, ServletException {
	        successHandler.onAuthenticationSuccess(request, response, authResult);
	    }

	    @Override
	    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	            AuthenticationException failed) throws IOException, ServletException {
	        SecurityContextHolder.clearContext();
	        failureHandler.onAuthenticationFailure(request, response, failed);
	    }

}
