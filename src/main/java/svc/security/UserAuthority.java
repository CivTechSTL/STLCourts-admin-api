package svc.security;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority{
	private static final long serialVersionUID = -5026138220272128961L;
	private  String role;
	
	public UserAuthority(String role){
		this.role = role;
	}
	
	@Override
	public String getAuthority() {
		return this.role;
	}

}
