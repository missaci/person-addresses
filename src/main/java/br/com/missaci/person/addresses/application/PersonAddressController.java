package br.com.missaci.person.addresses.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.missaci.person.addresses.domain.PersonAddress;
import br.com.missaci.person.addresses.domain.PersonAddresses;
import br.com.missaci.person.addresses.infrastructure.exceptions.InvalidAddressException;

/**
 * 
 * Rest controller used to receive requests for
 * all CRUD operation on Person Addresses.
 * 
 * List is not available in this scenario.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
@RestController
public class PersonAddressController {

	@Autowired private PersonAddresses addresses;
	
	@RequestMapping(method=RequestMethod.GET)
	public String checkAvailability(){
		return "Up and running!";
	}
	
	@Transactional(readOnly=true)
	@RequestMapping(method=RequestMethod.GET, value="/personAddresses/{id}")
	public @ResponseBody PersonAddress findById(@PathVariable("id") Long id){
		return addresses.findById(id);
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/personAddresses")
	public @ResponseBody PersonAddress save(@RequestBody PersonAddress address){
		return addresses.create(address);
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.PUT, value="/personAddresses")
	public @ResponseBody PersonAddress update(@RequestBody PersonAddress address){
		return addresses.update(address);
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.DELETE, value="/personAddresses/{id}")
	public @ResponseBody void delete(@PathVariable("id") Long id){
		PersonAddress address = addresses.findById(id);
		
		if(address == null){
			throw new InvalidAddressException("Address not found.");
		}
		
		addresses.delete(address);
	}
	
}
