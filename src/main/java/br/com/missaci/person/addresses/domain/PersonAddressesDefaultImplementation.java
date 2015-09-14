package br.com.missaci.person.addresses.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.missaci.person.addresses.infrastructure.exceptions.InvalidAddressException;

/**
 * 
 * This is the default implementation for PersonAddresses.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
@Service
public class PersonAddressesDefaultImplementation implements PersonAddresses {

	private PersonAddressesRepository repository;
	private CepService cepService;

	@Autowired
	public PersonAddressesDefaultImplementation(PersonAddressesRepository repository, CepService cepService) {
		this.repository = repository;
		this.cepService = cepService;
	}

	@Override
	public PersonAddress findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public PersonAddress create(PersonAddress address) {
		checkIfWeCanProceedUsing(address);
		return repository.save(address);
	}

	@Override
	public PersonAddress update(PersonAddress address) {
		checkIfWeCanProceedUsing(address);
		return repository.save(address);
	}

	@Override
	public void delete(PersonAddress address) {
		repository.delete(address);
	}
	
	private void checkIfWeCanProceedUsing(PersonAddress address){
		if(address == null){
			throw new InvalidAddressException("Address cannot be null.");
		}
		
		cepService.checkIfCepCanBeUsed(address.getCep());
		
	}

}
