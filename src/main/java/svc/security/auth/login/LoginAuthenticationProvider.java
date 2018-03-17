package svc.security.auth.login;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationProvider implements AuthenticationProvider{

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String googleIdToken = (String) authentication.getDetails();
		return new OAuth2AuthenticationToken(null,null, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (OAuth2AuthenticationToken.class.isAssignableFrom(authentication));
	}

}
