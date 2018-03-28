package svc.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	/* 
	 * This handler was created for SQL queries that are out of bounds, could be used for other purposes later
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public String handleNotFoundException(NotFoundException e){
		System.out.println("In GlobalExceptionHandler");
		String msg = (e.getMessage() != "")?e.getMessage():"Result Not Found";
		return (msg);
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(NotAuthorizedException.class)
	public String handleNotAuthorizedException(NotAuthorizedException e){
		String msg = (e.getMessage() != "")?e.getMessage():"UnAuthorized";
		return (msg);
	}
}
