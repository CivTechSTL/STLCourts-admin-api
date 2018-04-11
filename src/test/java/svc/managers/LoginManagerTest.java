package svc.managers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import svc.mangers.LoginManager;
import svc.models.NewTokenResponse;
import svc.models.User;
import svc.repositories.UserRepository;

import svc.security.JwtsUtility;
import svc.security.jwt.factory.JwtTokenFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginManager.class})
public class LoginManagerTest {
	@InjectMocks
	LoginManager manager;
	
	@Mock
	JwtsUtility mockJwtsUtility;
	
	@Mock
	UserRepository mockUserRepository;
	@Mock
	JwtTokenFactory mockJwtTokenFactory;
	
	String JWTTOKEN = "123ABC";
	String JWTREFRESH = "123DEF";
	String EMAIL_STRING = "myEmail";
	
	@Before
	public void mockCreateUser() {
		User mockUser = new User();
		when(mockUserRepository.findByEmail(EMAIL_STRING)).thenReturn(mockUser);
		when(mockJwtTokenFactory.createAccessJwtToken(mockUser)).thenReturn(JWTTOKEN);
		when(mockJwtTokenFactory.createRefreshToken(mockUser)).thenReturn(JWTREFRESH);
	}
	
	@Test
	public void createsNewTokenResponseFromverifyRefreshTokenAndGenerateNewSecurityTokens() {
		String REFRESH_TOKEN = "123abc";
	
		when(mockJwtsUtility.getSubject(REFRESH_TOKEN)).thenReturn(EMAIL_STRING);
		NewTokenResponse ntr = manager.verifyRefreshTokenAndGenerateNewSecurityTokens(REFRESH_TOKEN);
		assertThat(ntr.token, equalTo(JWTTOKEN));	
	}

	
}
