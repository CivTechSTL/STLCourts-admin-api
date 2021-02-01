package svc.security;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthorityTest {
	
	@Test
	public void properlyInitializesRole() {
		UserAuthority ua = new UserAuthority("myRole");
		String role = ua.getAuthority();
		assertThat(role, equalTo("myRole"));
	}

}
