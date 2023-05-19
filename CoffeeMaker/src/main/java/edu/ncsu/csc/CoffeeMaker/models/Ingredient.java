package edu.ncsu.csc.CoffeeMaker.models;

import java.io.Serializable;

import javax.persistence.Entity;
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
	
	/**
	 * Blank Constructor
	 */
	public Ingredient() {
		 // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
	}
	
	/**
	 * Ingredient constructor
	 * @param name of an ingredient object
	 * @param amount of the ingredient
	 */
	public Ingredient(String name, int amount) {
		super();
		setName(name);
		setAmount(amount);
	}
	
	/**
	 * Gets the name of our ingredient
	 * @return the ingredient name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the ingredient name
	 * @param name to set for an ingredient
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the amount for the ingredient
	 * @return out ingredient amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Set the amount for the ingredient
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Serialize the id
	 */
	@Override
	public Serializable getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

}
