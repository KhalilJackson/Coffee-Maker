package edu.ncsu.csc.CoffeeMaker.models;

import java.io.Serializable;

import java.security.PublicKey;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;


@Entity
public class Ingredient extends DomainObject{
	
	@Id
	@GeneratedValue
	private Long id;
	

	@Min ( 0 )

	private Integer amount;
	
	private String name;
	
	
	public Ingredient() {
		 // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
	}
	
	
	public Ingredient(String name, int amount) {
		super();
		setName(name);
		setAmount(amount);
	}
	
	

	public String getName() {
		return this.name;
	}


	public void setName(final String name) {
		this.name = name;
	}



	public int getAmount() {
		return amount;
	}



	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public Serializable getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
