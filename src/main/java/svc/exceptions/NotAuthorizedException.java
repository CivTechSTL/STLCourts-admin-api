package svc.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NotAuthorizedException extends AuthenticationException {
	private static final long serialVersionUID = 1L;
	private String message = null;
	
	public NotAuthorizedException(String message){
		super(message);
		this.message = message;
	}
	
	@Override
	public String toString(){
		return message;
	}
	
	@Override
	public String getMessage(){
		return message;
	}
	
}