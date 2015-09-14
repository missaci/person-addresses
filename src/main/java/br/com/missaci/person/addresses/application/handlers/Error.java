package br.com.missaci.person.addresses.application.handlers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is a simple representation of
 * a message error.
 * This class can be improved to be more informative
 * when passing content to the requester, informing
 * things like the original exception for example.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
public class Error {
	
	private final String errorMessage;
	
	@JsonCreator
	public Error(@JsonProperty("errorMessage") String errorMessage){
		this.errorMessage = errorMessage;
		
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
}
