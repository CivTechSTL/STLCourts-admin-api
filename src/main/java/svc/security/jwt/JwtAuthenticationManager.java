package svc.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import svc.models.JwtAuthenticationToken;
import svc.security.UserAuthority;
import svc.security.config.JwtSettings;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {
	private final JwtSettings settings;

	public JwtAuthenticationManager(JwtSettings settings) {
		this.settings = settings;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authentication.getCredentials();
		String token = ((JwtAuthenticationToken) authentication).getToken();
		
		UserAuthority scope = new UserAuthority((String)Jwts.parser().setSigningKey(this.settings.getTokenSigningKey()).parseClaimsJws(token).getBody().get("scopes"));
		String email = Jwts.parser().setSigningKey(this.settings.getTokenSigningKey()).parseClaimsJws(token).getBody().getSubject();
		
		List<GrantedAuthority> authorities = new ArrayList();
		authorities.add(scope);
       
		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token, authorities, email);
		
		System.out.println("In Authenticate, authorityCount = "+jwtAuthenticationToken.getAuthorities().size());
        
        return jwtAuthenticationToken;
	}

}
