package svc.controllers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import svc.exceptions.NotAuthorizedException;
import svc.mangers.LoginManager;
import svc.models.BasicToken;
import svc.models.JwtAuthenticationToken;
import svc.models.NewTokenResponse;
import svc.security.UserAuthority;



@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginController.class, SecurityContextHolder.class})
public class LoginControllerTest {
	@InjectMocks
	LoginController controller;

	@Mock
	LoginManager mockLoginManager;
	
	@Mock
	SecurityContext mockSecurityContext;
	
	@Test
	public void returnsProperNewTokenFromGooglSignin() throws NotAuthorizedException, GeneralSecurityException, IOException{
		BasicToken bt = new BasicToken();
		bt.setToken("123");
		
		NewTokenResponse ntr = new NewTokenResponse("abc","def");
		
		when(mockLoginManager.verifyGoogleTokenAndGetSecurityTokens("123")).thenReturn(ntr);
		
		NewTokenResponse result = controller.googleSignIn(bt);
		
		assertThat(result, is(ntr));
	}
	
	@Test
	public void returnsProperNewTokenFromRefreshToken() {
		BasicToken bt = new BasicToken();
		bt.setToken("123");
		
		NewTokenResponse ntr = new NewTokenResponse("abc","def");
		
		when(mockLoginManager.verifyRefreshTokenAndGenerateNewSecurityTokens("123")).thenReturn(ntr);
		
		NewTokenResponse result = controller.refreshToken(bt);
		
		assertThat(result, is(ntr));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void returnsCorrectPrivileges() {
		PowerMockito.mockStatic(SecurityContextHolder.class);
		when(SecurityContextHolder.getContext()).thenReturn(mockSecurityContext);
		
		List<GrantedAuthority> authorities = new ArrayList();
		authorities.add(new UserAuthority("myAuth"));
		JwtAuthenticationToken jwtAuthToken = new JwtAuthenticationToken("123", authorities, "myemail");
		
		when(mockSecurityContext.getAuthentication()).thenReturn(jwtAuthToken);
		GrantedAuthority result = controller.getPrivilege();
		assertThat(result, is (jwtAuthToken.getAuthorities().get(0)));
	}
}
