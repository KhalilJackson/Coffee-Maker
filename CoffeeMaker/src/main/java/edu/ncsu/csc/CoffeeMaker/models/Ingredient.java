package edu.ncsu.csc.CoffeeMaker.models;

import java.io.Serializable;
import java.security.PublicKey;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import edu.ncsu.csc.CoffeeMaker.models.enums.IngredientType;


@Entity
public class Ingredient extends DomainObject{
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated ( EnumType.STRING )
	private IngredientType ingredient;
	
	private Integer amount;
	
	
	public Ingredient() {
		 // Intentionally empty so that Hibernate can instantiate
        // Inventory object.
	}
	
	
	public Ingredient(IngredientType ingredient, int amount) {
		super();
		this.ingredient = ingredient;
		this.amount = amount;
	}
	
	

	public IngredientType getIngredient() {
		return ingredient;
	}



	public void setIngredient(IngredientType ingredient) {
		this.ingredient = ingredient;
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
