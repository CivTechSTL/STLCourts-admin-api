package svc.exceptions;

public class NotAuthorizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message = null;
	
	public NotAuthorizedException(){
		super();
	}
	
	public NotAuthorizedException(String message){
		super(message);
		this.message = message;
	}
	
	public NotAuthorizedException(Throwable cause){
		super(cause);
	}
	
	public NotAuthorizedException(String message, Throwable cause){
		super(message,cause);
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