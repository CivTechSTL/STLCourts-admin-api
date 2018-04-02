package svc.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import svc.exceptions.NotAuthorizedException;
import svc.models.JwtAuthenticationToken;
import svc.security.config.WebSecurityConfig;

public class JwtTokenAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	public JwtTokenAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
		super(requiresAuthenticationRequestMatcher);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, NotAuthorizedException {
		
		String tokenPayload = request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME);
		if (tokenPayload == null || !tokenPayload.startsWith("Bearer ")) {
			throw new NotAuthorizedException("JWT Token is missing");
		}
		
		String authenticationToken = tokenPayload.substring(7);
		JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
		return getAuthenticationManager().authenticate(token);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
	}

}
