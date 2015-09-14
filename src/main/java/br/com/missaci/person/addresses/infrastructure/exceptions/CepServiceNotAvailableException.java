package br.com.missaci.person.addresses.infrastructure.exceptions;

/**
 * 
 * Exception used to inform when the CEP service is not available.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
public class CepServiceNotAvailableException extends RuntimeException{

	private static final long serialVersionUID = -4165768430318325649L;

	public CepServiceNotAvailableException(String message) {
		super(message);
	}
	
}
