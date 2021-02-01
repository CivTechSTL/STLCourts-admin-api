package svc.security.jwt;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.AuthenticationException;

import io.jsonwebtoken.ExpiredJwtException;
import svc.models.JwtAuthenticationToken;
import svc.security.JwtsUtility;
import svc.security.UserAuthority;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationManagerTest {
	@InjectMocks
	JwtAuthenticationManager manager;
	
	@Mock
	JwtsUtility mockJwtsUtility;
	
	@Test
	public void correctlyReturnsAuntenticationToken() {
		String token = "123";
		JwtAuthenticationToken authentication = new JwtAuthenticationToken(token);
		UserAuthority ua = new UserAuthority("someRole");
		when(mockJwtsUtility.getScope(token)).thenReturn(ua);
		when(mockJwtsUtility.getSubject(token)).thenReturn("myEmail");
		JwtAuthenticationToken result = (JwtAuthenticationToken) manager.authenticate(authentication);
		
		assertThat(result.getAuthorities().get(0).getAuthority(), equalTo("someRole"));
		assertThat(result.getPrincipal(), equalTo("myEmail"));
		assertThat(result.getToken(), equalTo(token));
	}
	
	@Test(expected=AuthenticationException.class)
	public void correctlyThrowsAuntenticationExceptionFromExpiredJwt() {
		String token = "123";
		JwtAuthenticationToken authentication = new JwtAuthenticationToken(token);
		
		when(mockJwtsUtility.getScope(token)).thenThrow(new ExpiredJwtException(null, null, token));
		
		manager.authenticate(authentication);
	
	}
	
	@Test(expected=AuthenticationException.class)
	public void correctlyThrowsAuntenticationExceptionFromException() {
		String token = "123";
		JwtAuthenticationToken authentication = new JwtAuthenticationToken(token);
		
		when(mockJwtsUtility.getScope(token)).thenThrow(new MockitoException("something"));
		
		manager.authenticate(authentication);
	
	}

}
