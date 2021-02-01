package svc.models;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = 1L;
	private String token;
	private Object principal;
	private String credentials; //could possibly use the google token string  but for now we will leave null;
	private List<GrantedAuthority> authorities;
	
	public JwtAuthenticationToken(String token) {
		super(null, null);
		this.token = token;
		this.principal = null;
		this.credentials = null;
		this.authorities = null;
	}
	
	public JwtAuthenticationToken(String token, List<GrantedAuthority> authorities, String email){
		super(null, null);
		this.token = token;
		this.principal = email;
		this.authorities = authorities;
		this.credentials = null;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public Object getCredentials() {
		return credentials;
	}
	
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	
	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(Object principal) {
		this.principal = principal;
	}
	
	@Override
	public List<GrantedAuthority> getAuthorities(){
		return this.authorities;
	}
	
	public void setAuthorities(List<GrantedAuthority> authorities){
		this.authorities = authorities;
	}

}
