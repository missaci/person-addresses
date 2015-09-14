package br.com.missaci.person.addresses.infrastructure;

import java.io.IOException;
import java.net.ConnectException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.missaci.person.addresses.domain.CepService;
import br.com.missaci.person.addresses.infrastructure.exceptions.CepServiceNotAvailableException;
import br.com.missaci.person.addresses.infrastructure.exceptions.FailedToParseContentException;
import br.com.missaci.person.addresses.infrastructure.exceptions.InvalidCepException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Default implementation for CepService
 * constructed using the Spring RestTemplate utility.
 * 
 * The service url (cepUrl) can be changed in the application.yml
 * file.
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */
@Service
public class RestCepService implements CepService{
	
	private String cepUrl;
	
	@Autowired
	public RestCepService(@Value("${cep.url}") String cepUrl){
		if(cepUrl == null){
			throw new RuntimeException("Cep service URL not informed");
		}
		
		this.cepUrl = cepUrl.endsWith("/") ? cepUrl : cepUrl + "/"; 
		
	}

	@Override
	public void checkIfCepCanBeUsed(String cep) {
		if(cep == null || cep.trim().isEmpty()){
			throw new InvalidCepException("CEP cannot be null neither empty.");
		}
		
		try {
			callRemoteService(cep);

		} catch (RestClientException e) {
			e.printStackTrace();
			adequateToRightException(e);
			
		} 
	}

	private void adequateToRightException(RestClientException e) {
		if(e.getCause() instanceof ConnectException){
			throw new CepServiceNotAvailableException("CEP Service could not be found.");
		}

		parseErrorMessage(((HttpClientErrorException)e).getResponseBodyAsString());
	}

	private void callRemoteService(String cep) throws RestClientException{
		RestTemplate restTemplate = new RestTemplate();     

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> entity = new HttpEntity<Object>(headers);

		ResponseEntity<String> result = restTemplate.exchange(cepUrl+cep, HttpMethod.GET, entity, String.class);
		
		if(result.getBody().contains("errorMessage")){
			parseErrorMessage(result.getBody());
		}
	}

	private void parseErrorMessage(String result){
		try {
			br.com.missaci.person.addresses.application.handlers.Error error = new ObjectMapper().readValue(result, br.com.missaci.person.addresses.application.handlers.Error.class);
			throw new InvalidCepException(error.getErrorMessage());
			
		} catch (IOException e) {
			throw new FailedToParseContentException(e.getMessage());
		}
	}

}
