package br.com.missaci.person.addresses.application;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.com.missaci.person.addresses.AppInitializer;
import br.com.missaci.person.addresses.application.handlers.Error;
import br.com.missaci.person.addresses.domain.PersonAddress;
import br.com.missaci.person.addresses.domain.PersonAddressesRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = AppInitializer.class)
public class PersonAddressControllerIntegrationTest {
	
	@Autowired private WebApplicationContext webAppContext;
	@Autowired private PersonAddressesRepository repository;
	@Rule public WireMockRule wireMockRule = new WireMockRule(8080);

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.webAppContext).build();
		loadData();
		mockCepServiceServer();
	}

	@Test
	public void shouldFindAPersonAddressById() throws Exception {
		String result = this.mockMvc
				.perform(get("/personAddresses/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		PersonAddress address = new ObjectMapper().readValue(result, PersonAddress.class);
		assertEquals("01001000", address.getCep());
		assertEquals("Praça da Sé - lado ímpar", address.getStreet());
		
	}
	
	@Test
	public void shouldUpdateAPersonAddress() throws Exception {
		String result = this.mockMvc
				.perform(put("/personAddresses")
						.accept(MediaType.APPLICATION_JSON)
						.content(getUpdateContent())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		PersonAddress address = new ObjectMapper().readValue(result, PersonAddress.class);
		assertEquals("21", address.getNumber());
		
	}
	
	@Test
	public void shouldCreateAPersonAddress() throws Exception {
		String result = this.mockMvc
				.perform(post("/personAddresses")
						.accept(MediaType.APPLICATION_JSON)
						.content(getCreateContent())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		PersonAddress address = new ObjectMapper().readValue(result, PersonAddress.class);
		assertEquals("31", address.getNumber());
		
	}
	
	@Test
	public void shouldFailToCreateAPersonAddressDueInvalidCEP() throws Exception {
		String result = this.mockMvc
				.perform(post("/personAddresses")
						.accept(MediaType.APPLICATION_JSON)
						.content(getInvalidCEPContent())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isPreconditionFailed())
				.andReturn().getResponse().getContentAsString();

		Error error = new ObjectMapper().readValue(result, Error.class);
		assertEquals("CEP invalido", error.getErrorMessage());
		
	}
	
	@Test
	public void shouldFailToCreateAPersonAddressStreetFieldNotSet() throws Exception {
		String result = this.mockMvc
				.perform(post("/personAddresses")
						.accept(MediaType.APPLICATION_JSON)
						.content(getInvalidStreetContent())
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isPreconditionFailed())
				.andReturn().getResponse().getContentAsString();

		Error error = new ObjectMapper().readValue(result, Error.class);
		assertEquals("Campos obrigatórios não preenchidos", error.getErrorMessage());
		
	}
	
	@Test
	public void shouldRemoveAPersonAddressById() throws Exception {
		this.mockMvc
				.perform(delete("/personAddresses/2").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
	
	//===== Aux and mock methods =====
	
	private void loadData(){
		PersonAddress address = new PersonAddress();
		address.setCep("01001000");
		address.setCity("São Paulo");
		address.setState("SP");
		address.setStreet("Praça da Sé - lado ímpar");
		address.setNeighborhood("Sé");
		address.setNumber("11");
		
		repository.save(address);
		
		PersonAddress address2 = new PersonAddress();
		address2.setCep("01001001");
		address2.setCity("São Paulo");
		address2.setState("SP");
		address2.setStreet("Praça da Sé - lado par");
		address2.setNeighborhood("Sé");
		address2.setNumber("10");
		
		repository.save(address2);
		
	}
	
	private String getUpdateContent() throws JsonProcessingException{
		PersonAddress address = new PersonAddress(1L);
		address.setCep("01001000");
		address.setCity("São Paulo");
		address.setState("SP");
		address.setStreet("Praça da Sé - lado ímpar");
		address.setNeighborhood("Sé");
		address.setNumber("21");
		
		return new ObjectMapper().writeValueAsString(address);
	}
	
	private String getCreateContent() throws JsonProcessingException{
		PersonAddress address = new PersonAddress();
		address.setCep("01001000");
		address.setCity("São Paulo");
		address.setState("SP");
		address.setStreet("Praça da Sé - lado ímpar");
		address.setNeighborhood("Sé");
		address.setNumber("31");
		
		return new ObjectMapper().writeValueAsString(address);
	}
	
	private String getInvalidCEPContent() throws JsonProcessingException{
		PersonAddress address = new PersonAddress();
		address.setCep("00000-000");
		address.setCity("São Paulo");
		address.setState("SP");
		address.setStreet("Praça da Sé - lado ímpar");
		address.setNeighborhood("Sé");
		address.setNumber("31");
		
		return new ObjectMapper().writeValueAsString(address);
	}
	
	private String getInvalidStreetContent() throws JsonProcessingException{
		PersonAddress address = new PersonAddress();
		address.setCep("01001000");
		address.setCity("São Paulo");
		address.setState("SP");
		address.setStreet(null);
		address.setNeighborhood("Sé");
		address.setNumber("31");
		
		return new ObjectMapper().writeValueAsString(address);
	}
	
	private void mockCepServiceServer(){
		stubFor(get(urlEqualTo("/addresses/01001000"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withHeader("charset", "UTF-8")
						.withBody("\"{ "+
								"\"cep\": \"01001000\", "+
								"\"street\": \"Praça da Sé - lado ímpar\", "+
								"\"neighborhood\": \"Sé\", "+
								"\"city\": \"São Paulo\", "+
								"\"state\": \"SP\" "+
								"}\"")));
		
		stubFor(get(urlEqualTo("/addresses/00000000"))
				.willReturn(aResponse()
						.withStatus(200)
						.withHeader("Content-Type", "application/json")
						.withHeader("charset", "UTF-8")
						.withBody("{\"errorMessage\": \"CEP invalido\"}")));
	}

}
