package svc.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import svc.exceptions.NotAuthorizedException;
import svc.models.JwtAuthenticationToken;
import svc.security.JwtsUtility;
import svc.security.UserAuthority;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {
	@Autowired
	JwtsUtility jwtsUtility;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = ((JwtAuthenticationToken) authentication).getToken();
		UserAuthority scope = null;
		String email = null;
		try{
			scope = jwtsUtility.getScope(token);
			email = jwtsUtility.getSubject(token);
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
