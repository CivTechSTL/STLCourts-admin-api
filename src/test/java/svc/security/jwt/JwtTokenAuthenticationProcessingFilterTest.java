package svc.security.jwt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import svc.exceptions.NotAuthorizedException;
import svc.models.JwtAuthenticationToken;
import svc.security.JwtRequestMatcher;
import svc.security.UserAuthority;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenAuthenticationProcessingFilterTest {
	
	@Mock
	HttpServletRequest mockRequest;
	@Mock
	HttpServletResponse mockResponse;
	@Mock
	JwtAuthenticationManager mockAuthenticationManager;
	@Mock
	FilterChain mockFilterChain;
	
	private JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
	    JwtRequestMatcher requestMatcher = new JwtRequestMatcher(pathsToSkip, pattern);
		JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(requestMatcher);
	    filter.setAuthenticationManager(mockAuthenticationManager);
	    return filter;
	}
	
	@Test
	public void successfullyAuthenticates() throws Exception {
		when(mockRequest.getHeader(Mockito.anyString())).thenReturn("Bearer 12345");
		JwtAuthenticationToken authToken = new JwtAuthenticationToken("ABC");
		when(mockAuthenticationManager.authenticate(Mockito.any(JwtAuthenticationToken.class))).thenReturn(authToken);
		
		
		JwtTokenAuthenticationProcessingFilter filter = buildJwtTokenAuthenticationProcessingFilter(Arrays.asList("/skipMe"), "/**");
		
		JwtAuthenticationToken result = (JwtAuthenticationToken) filter.attemptAuthentication(mockRequest, mockResponse);
		
		assertThat(result, is(authToken));
	}
	
	@Test(expected=NotAuthorizedException.class)
	public void successfullyThrowsExceptionDuringAuthenticationWithNoBearer() throws Exception {
		when(mockRequest.getHeader(Mockito.anyString())).thenReturn("12345");
				
		JwtTokenAuthenticationProcessingFilter filter = buildJwtTokenAuthenticationProcessingFilter(Arrays.asList("/skipMe"), "/**");
		
		filter.attemptAuthentication(mockRequest, mockResponse);
	}
	
	@Test(expected=NotAuthorizedException.class)
	public void successfullyThrowsExceptionDuringAuthenticationWithNull() throws Exception {
		when(mockRequest.getHeader(Mockito.anyString())).thenReturn(null);
				
		JwtTokenAuthenticationProcessingFilter filter = buildJwtTokenAuthenticationProcessingFilter(Arrays.asList("/skipMe"), "/**");
		
		filter.attemptAuthentication(mockRequest, mockResponse);
	}
	
	@Test
	public void successfullySetsSecurityContext() throws Exception {
		UserAuthority ua = new UserAuthority("myRole");
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(ua);
		String email = "myEmail";
		String token = "123";
		
		JwtAuthenticationToken authToken = new JwtAuthenticationToken(token,authorities,email);
		
		doNothing().when(mockFilterChain).doFilter(mockRequest, mockResponse);
		
		JwtTokenAuthenticationProcessingFilter filter = buildJwtTokenAuthenticationProcessingFilter(Arrays.asList("/skipMe"), "/**");
		filter.successfulAuthentication(mockRequest, mockResponse, mockFilterChain, authToken);
		
		SecurityContext context = SecurityContextHolder.getContext();
		String principal = (String)context.getAuthentication().getPrincipal();
		assertThat(principal, equalTo(email));
		
		assertThat(context.getAuthentication(),equalTo(authToken));
	}

}
