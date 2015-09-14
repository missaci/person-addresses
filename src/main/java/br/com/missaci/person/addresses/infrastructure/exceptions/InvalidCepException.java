package br.com.missaci.person.addresses.infrastructure.exceptions;

/**
 * 
 * Exception used to inform when a malformed CEP
 * is detected.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
public class InvalidCepException extends RuntimeException{

	private static final long serialVersionUID = 4655364871734168517L;

	public InvalidCepException(String message) {
		super(message);
	}
	
}
