package svc.security.jwt;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
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
			throws AuthenticationException, IOException, ServletException {
		
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()){
			System.out.println(headerNames.nextElement());
		}
		
		String tokenPayload = request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME);
		if (tokenPayload == null || !tokenPayload.startsWith("Bearer ")) {
			throw new NotAuthorizedException("JWT Token is missing");
		}
		
		String authenticationToken = tokenPayload.substring(7);
		JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
		return getAuthenticationManager().authenticate(token);
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		super.unsuccessfulAuthentication(request, response, failed);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		System.out.println("Authenticated");
		List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authResult.getAuthorities();
		System.out.println("Authority Count = "+grantedAuthorities.size());
		System.out.println("Authority = "+grantedAuthorities.get(0).getAuthority());
		SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
	}

}
