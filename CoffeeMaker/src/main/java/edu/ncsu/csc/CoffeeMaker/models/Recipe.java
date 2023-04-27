package edu.ncsu.csc.CoffeeMaker.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import net.bytebuddy.asm.Advice.Thrown;

/**
 * Recipe for the coffee maker. Recipe is tied to the database using Hibernate
 * libraries. See RecipeRepository and RecipeService for the other two pieces
 * used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Recipe extends DomainObject {

	/** Recipe id */
	@Id
	@GeneratedValue
	private Long id;

	/** Recipe name */
	private String name;

	/** Recipe price */
	@Min(0)
	private Integer price;

//    /** Amount coffee */
//    @Min ( 0 )
//    private Integer coffee;
//
//    /** Amount milk */
//    @Min ( 0 )
//    private Integer milk;
//
//    /** Amount sugar */
//    @Min ( 0 )
//    private Integer sugar;
//
//    /** Amount chocolate */
//    @Min ( 0 )
//    private Integer chocolate;

	/**
	 * Ingredients List
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Ingredient> ingredients;

	/**
	 * Creates a default recipe for the coffee maker.
	 */
	public Recipe() {

		this.ingredients = new ArrayList<>();
	}

	/**
	 * Check if all ingredient fields in the recipe are 0
	 *
	 * @return true if all ingredient fields are 0, otherwise return false
	 */
	public boolean checkRecipe() {

		for (Ingredient ingredient : ingredients) {
			if (ingredient.getAmount() != 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Get the ID of the Recipe
	 *
	 * @return the ID
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Set the ID of the Recipe (Used by Hibernate)
	 *
	 * @param id the ID
	 */
	@SuppressWarnings("unused")
	private void setId(final Long id) {
		this.id = id;
	}

	/**
	 * adds ingredient to recipe
	 * 
	 * @author Ladi
	 * @param ingredient
	 */
	public void addIngredient(Ingredient ingredient) {

		ingredients.add(ingredient);
	}

	/**
	 * 
	 * @author Ladi
	 * @return ArrayList of Ingredients
	 */
	public List<Ingredient> getIngredients() {

		return this.ingredients;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Sets the recipe name.
	 *
	 * @param name The name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	public void setIngredient(Ingredient ingredient) {

		String newIngredientName = ingredient.getName();

		for (Ingredient i : ingredients) {
			if (i.getName().equals(newIngredientName)) {
				i.setAmount(ingredient.getAmount());
			}
		}

	}

	/**
	 * Returns the price of the recipe.
	 *
	 * @return Returns the price.
	 */
	public Integer getPrice() {
		return this.price;
	}

	/**
	 * Sets the recipe price.
	 * 
	 * @author Ladi
	 * @param price The price to set.
	 */
	public void setPrice(final Integer price) {

		if (price <= 0) {
			throw new IllegalArgumentException("Price can't be less than zero");
		} else {
			this.price = price;
		}
	}
	
	
	
	
	
	/**
	 * removes ingredient 
	 * 
	 * @author ladi
	 * @param ingredient
	 */
	public void removeIngredient(Ingredient ingredient) {
		
		String newIngredientName = ingredient.getName();
		
		for (Ingredient i : ingredients) {
			if (i.getName().equals(newIngredientName)) {
				ingredients.remove(i);
			}
			else {
				throw new IllegalArgumentException("Ingredient doesn't exist");
			}
		}
	}
	
//	
//	public void editRecipe(Integer newPrice, ArrayList<Ingredient> ingredientList, Integer unit) {
//		
//	}

	/**
	 * Returns the name of the recipe.
	 *
	 * @return String
	 */
	@Override
	public String toString() {
//        return name;

		String ingredientString = "";

		for (Ingredient e : ingredients) {
			ingredientString += " " + e;
		}

		return ingredientString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		Integer result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Recipe other = (Recipe) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
