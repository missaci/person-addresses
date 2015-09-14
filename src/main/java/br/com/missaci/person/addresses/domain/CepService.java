package br.com.missaci.person.addresses.domain;

/**
 * 
 * CepService interface is used to abstract the communication
 * with a CEP Service server.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
public interface CepService {
	
	/**
	 * 
	 * Must check if a CEP is valid.
	 * If it identifies that the given CEP is not valid,
	 * this must throw an Exception
	 * 
	 * @param cep to validate
	 */
	public void checkIfCepCanBeUsed(String cep);

}
