package br.com.missaci.person.addresses.insfrastructure;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import br.com.missaci.person.addresses.domain.CepService;
import br.com.missaci.person.addresses.infrastructure.RestCepService;
import br.com.missaci.person.addresses.infrastructure.exceptions.InvalidCepException;

import com.github.tomakehurst.wiremock.junit.WireMockRule;


public class RestCepServiceTest{

	@Rule public WireMockRule wireMockRule = new WireMockRule(8080);
	private CepService cepService;


	@Before
	public void init(){
		cepService = new RestCepService("http://localhost:8080/addresses");
	}

	@Test
	public void shouldIdentifyCepAsValid(){
		stubFor(get(urlEqualTo("/addresses/01001-001"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("\"{ "+
								"\"cep\": \"01001001\", "+
								"\"street\": \"Praça da Sé - lado par\", "+
								"\"neighborhood\": \"Sé\", "+
								"\"city\": \"São Paulo\", "+
								"\"state\": \"SP\" "+
								"}\"")));
		
		cepService.checkIfCepCanBeUsed("01001-001");
	}

	@Test(expected=InvalidCepException.class)
	public void shouldIdentifyCepAsInvalid(){
		stubFor(get(urlEqualTo("/addresses/00000-000"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withBody("{\"errorMessage\": \"CEP inválido\"}")));
		
		cepService.checkIfCepCanBeUsed("00000-000");
	}
	
	@Test(expected=InvalidCepException.class)
	public void shouldThrowExceptionDueNullCEP(){
		cepService.checkIfCepCanBeUsed(null);
	}

}
