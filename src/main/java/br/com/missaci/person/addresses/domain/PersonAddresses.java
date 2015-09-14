package br.com.missaci.person.addresses.domain;

/**
 * 
 * PersonAddresses is an abstraction to work like 
 * a Repository. But implementations of this
 * can implement more complex logic.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
public interface PersonAddresses {
	
	/**
	 * @param id
	 * @return the PersonAddress that matches the given id
	 */
	PersonAddress findById(Long id);
	
	/**
	 * @param address
	 * @return the created address with its id
	 */
	PersonAddress create(PersonAddress address);
	
	/**
	 * @param address
	 * @return the updated address
	 */
	PersonAddress update(PersonAddress address);
	
	/**
	 * @param the address to be removed
	 */
	void delete(PersonAddress address);

}
