package br.com.missaci.person.addresses.infrastructure.exceptions;

/**
 * 
 * Exception used to inform when a parse error has occured
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
public class FailedToParseContentException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FailedToParseContentException(String message) {
		super(message);
	}
	
}
