package svc.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class LoginUser implements OAuth2User {
	private HashMap<String, Object> map = new HashMap<String, Object>();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<? extends GrantedAuthority> grantedAuthorities = new ArrayList();
	
	@Override
	public String getName() {
		return (String) map.get("name");
	}
	
	public void setName(String name) {
		map.put("name", name);
	}
	
	public String getTokenId() {
		return (String) map.get("tokenId");
	}
	
	public void setTokenId(String tokenId) {
		map.put("tokenId", tokenId);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return map;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

}
