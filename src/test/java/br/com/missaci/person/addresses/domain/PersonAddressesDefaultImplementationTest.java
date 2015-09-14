package br.com.missaci.person.addresses.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.missaci.person.addresses.infrastructure.exceptions.InvalidAddressException;
import br.com.missaci.person.addresses.infrastructure.exceptions.InvalidCepException;

@RunWith(MockitoJUnitRunner.class)
public class PersonAddressesDefaultImplementationTest {
	
	private PersonAddresses addresses;
	private final static String VALID_CEP = "01001-001";
	private final static String INVALID_CEP = "00000000";
	
	@Mock private CepService cepService;
	@Mock private PersonAddressesRepository repository;
	
	@Before
	public void init(){
		mockDependencies();
		addresses = new PersonAddressesDefaultImplementation(repository, cepService);
	}
	
	@Test
	public void shouldCreateANewAddress(){
		PersonAddress address = createValidAddress();
		address = addresses.create(address);
		assertNotNull(address.getId());
		
	}
	
	@Test(expected=InvalidAddressException.class)
	public void shouldThrowAnExceptionWhileCreatingNewAddressDueNullAddress(){
		addresses.create(null);
		
	}
	
	@Test(expected=InvalidCepException.class)
	public void shouldThrowAnExceptionWhileCreatingNewAddressDueInvalidCEP(){
		addresses.create(createInvalidAddress());
		
	}
	
	@Test
	public void shouldUpdateAnAddress(){
		PersonAddress address = createValidAddressWithId(1l);
		address = addresses.update(address);
		assertNotNull(address.getId());
		
	}
	
	@Test(expected=InvalidAddressException.class)
	public void shouldThrowAnExceptionWhileUpdatingNewAddressDueNullAddress(){
		addresses.update(null);
		
	}
	
	@Test(expected=InvalidCepException.class)
	public void shouldThrowAnExceptionWhileUpdatingNewAddressDueInvalidCEP(){
		addresses.update(createInvalidAddress());
		
	}
	
	//---------- Aux and mock methods -------- 
	
	private PersonAddress createValidAddress(){
		return createValidAddressWithId(null);
	}
	
	private PersonAddress createInvalidAddress(){
		return createAddressUsing(null, INVALID_CEP);
	}
	
	private PersonAddress createValidAddressWithId(Long id){
		return createAddressUsing(id, VALID_CEP);
	}
	
	private PersonAddress createAddressUsing(Long id, String cep){
		PersonAddress address = new PersonAddress(id);
		address.setCep(cep);
		address.setCity("São Paulo");
		address.setState("SP");
		address.setStreet("Praça da Sé - lado par");
		address.setNeighborhood("Sé");
		address.setNumber("2");
		
		return address;
	}
	
	private void mockDependencies(){
		InvalidCepException exception = new InvalidCepException("Cep inválido");
		doThrow(exception).when(cepService).checkIfCepCanBeUsed(INVALID_CEP);
		when(repository.save(any(PersonAddress.class))).thenReturn(createValidAddressWithId(1l));
		

	}

}
