package br.com.missaci.person.addresses.infrastructure.exceptions;

public class InvalidAddressException extends RuntimeException{

	private static final long serialVersionUID = 4655364871734168519L;

	public InvalidAddressException(String message) {
		super(message);
	}
	
}