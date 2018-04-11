package svc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import svc.security.config.JwtSettings;

@Component
public class JwtsUtility {
	private final JwtSettings settings;
	
	@Autowired
    public JwtsUtility(JwtSettings settings) {
        this.settings = settings;
    }
	
	public String getSubject(String jwtToken){
		return Jwts.parser().setSigningKey(this.settings.getTokenSigningKey()).parseClaimsJws(jwtToken).getBody().getSubject();
	}
	
	public UserAuthority getScope(String jwtToken){
		return new UserAuthority((String)Jwts.parser().setSigningKey(this.settings.getTokenSigningKey()).parseClaimsJws(jwtToken).getBody().get("scopes"));
	}

}
