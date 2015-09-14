package br.com.missaci.person.addresses.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * Abstraction for PersonAddressRepository.
 * No implementation is required for now, since it
 * uses the default implementation of Spring CrudRepository.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
public interface PersonAddressesRepository extends CrudRepository<PersonAddress, Long>{

}
