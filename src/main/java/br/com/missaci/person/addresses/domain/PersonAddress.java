package br.com.missaci.person.addresses.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import br.com.missaci.person.addresses.infrastructure.exceptions.InvalidCepException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Entity representing an address.
 * 
 * For simplicity, I decided not creating
 * entities for street, city and state, because
 * creating them would just create things that I really don't
 * need now. If a reuse case appears for that entities,
 * it could be easily created and than the class expanded using or not
 * the same database model. 
 * 
 * @author Mateus <mateus.missaci@gmail.com>
 *
 */

@Entity
@Table(name="PERSON_ADDRESSES")
public class PersonAddress {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CEP", length=8, nullable=false)
	@NotNull
	@NotEmpty
	private String cep;
	
	@Column(name="STREET", length=255, nullable=false)
	@NotNull
	@NotEmpty
	private String street;
	
	@Column(name="NUMBER", length=6, nullable=false)
	@NotNull
	@NotEmpty
	private String number;
	
	@Column(name="CITY", length=60, nullable=false)
	@NotNull
	@NotEmpty
	private String city;
	
	@Column(name="STATE", length=2, nullable=false)
	@NotNull
	@NotEmpty
	private String state;
	
	@Column(name="NEIGHBORHOOD", length=60, nullable=true)
	private String neighborhood;
	
	@Column(name="COMPLEMENT", length=255, nullable=true)
	private String complement;
	
	@JsonCreator
	public PersonAddress(@JsonProperty("id") Long id){
		this.id = id;
	}
	
	@JsonCreator
	public PersonAddress(){}

	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		if(cep == null){
			throw new InvalidCepException("Given CEP is not valid");
		}
		
		this.cep = cep.replaceAll("-", "");
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getComplement() {
		return complement;
	}
	public void setComplement(String complement) {
		this.complement = complement;
	}
	public Long getId() {
		return id;
	}
	
}
