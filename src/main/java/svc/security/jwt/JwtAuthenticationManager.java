package svc.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import svc.exceptions.NotAuthorizedException;
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
		String token = ((JwtAuthenticationToken) authentication).getToken();
		UserAuthority scope = null;
		String email = null;
		try{
			scope = new UserAuthority((String)Jwts.parser().setSigningKey(this.settings.getTokenSigningKey()).parseClaimsJws(token).getBody().get("scopes"));
			email = Jwts.parser().setSigningKey(this.settings.getTokenSigningKey()).parseClaimsJws(token).getBody().getSubject();
		}catch(ExpiredJwtException expiredEx){
			throw new NotAuthorizedException("JWT is Expired");
		}catch(Exception e){
			throw new NotAuthorizedException("Error Authorizing JWT");
		}
		List<GrantedAuthority> authorities = new ArrayList();
		authorities.add(scope);
       
		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token, authorities, email);
        
        return jwtAuthenticationToken;
	}

}
