package br.com.missaci.person.addresses.application.handlers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.missaci.person.addresses.infrastructure.exceptions.InvalidCepException;

/**
 * This handler is intended to be generic.
 * It has a simple mechanism to identify exceptions
 * and internationalized messages for each exception.
 * 
 * The error massages and each status can be managed trough 
 * the messages.properties file.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	@Autowired private MessageSource messageSource;

	/**
	 * Handle all Exceptions, except the InvalidCepException
	 * that has a specialized handler
	 * 
	 * @param exception to handle
	 * @param request
	 * @return a response entity with an Error object
	 */
	@ExceptionHandler({ Exception.class })
	protected ResponseEntity<Object> handleInvalidRequest(Exception exception, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		String message = getErrorMessageFor(exception);

		if(message.contains(";")){
			String[] messages = message.split(";");
			status = HttpStatus.valueOf(Integer.valueOf(messages[1]));
			message = messages[0];
			
		}

		return handleExceptionInternal(exception, new Error(message), headers, status, request);
	}
	
	/**
	 * Specialized handler to InvalidCepException.
	 * 
	 * This handler is used to retrieve the exact message of the exception.
	 * 
	 * @param exception to handle
	 * @param request
	 * @return a response entity with an Error object
	 */
	@ExceptionHandler({ InvalidCepException.class })
	protected ResponseEntity<Object> handleInvalidCepException(Exception exception, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));

		return handleExceptionInternal(exception, new Error(exception.getMessage()), headers, HttpStatus.PRECONDITION_FAILED, request);
	}

	private String getErrorMessageFor(Exception e) {
		try {
			e.printStackTrace();
			return messageSource.getMessage(e.getClass().getName(), new Object[]{}, new Locale("pt", "BR"));
		} catch (NoSuchMessageException e1) {
			return "Falha interna no sistema.";
		}
	}

}
