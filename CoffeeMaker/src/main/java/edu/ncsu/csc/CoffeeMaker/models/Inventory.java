
package edu.ncsu.csc.CoffeeMaker.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Inventory for the coffee maker. Inventory is tied to the database using
 * Hibernate libraries. See InventoryRepository and InventoryService for the
 * other two pieces used for database support.
 *
 * @author Kai Presler-Marshall
 */
@Entity
public class Inventory extends DomainObject {

	/** id for inventory entry */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * Variable for the inventoryIngredients
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Ingredient> inventoryIngredients;

	/**
	 * Get the inventory ingredients
	 * @return the ingredients in the inventory
	 */
	public List<Ingredient> getInventoryIngredients() {
		return inventoryIngredients;
	}

	/**
	 * Set our inventory ingredients
	 * @param inventoryIngredients the inventory of ingredients we are going to set
	 */
	public void setInventoryIngredients(List<Ingredient> inventoryIngredients) {
		this.inventoryIngredients = inventoryIngredients;
	}

	/**
	 * Empty constructor for Hibernate
	 */
	public Inventory() {
		// Intentionally empty so that Hibernate can instantiate
		// Inventory object.
		this.inventoryIngredients = new ArrayList<>();

	}

	/**
	 * Add an indredient to our list of ingredients
	 * @param ingredient the ingredient to add
	 */
	public void addIngredient(final Ingredient ingredient) {
		inventoryIngredients.add(ingredient);
	}

	/**
	 * Add ingredients to our list of ingredients
	 * @param ingredients list of ingredients to add to
	 */
	public void addIngredientsToList(final List<Ingredient> ingredients) {

		this.inventoryIngredients = ingredients;
	}

	/**
	 * updates a single ingredients amount in inventory
	 * 
	 * @param ingredient that needs to have amount updated
	 */
	public void updateInventory(final Ingredient ingredient) {

		for (Ingredient e : inventoryIngredients) {

			if (e.getName().equals(ingredient.getName())) {
				e.setAmount(ingredient.getAmount() + e.getAmount());
				return;
			}
		}
	}

	/**
	 * Updates a single ingredients amount in inventory
	 * @param ingredient that needs to have its amount now set
	 */
	public void setAmount(final Ingredient ingredient) {

		for (Ingredient e : inventoryIngredients) {

			if (e.getName().equals(ingredient.getName())) {
				e.setAmount(ingredient.getAmount());
			} else {
				throw new IllegalArgumentException("Ingredient doesn't exist");
			}
		}
	}

	/**
	 * Returns the ID of the entry in the DB
	 *
	 * @return long the id in the inventory
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Set the ID of the Inventory (Used by Hibernate)
	 *
	 * @param id the ID that is set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * get our amount for the ingredient that we are passing in
	 * @param i the ingredient we need the amount od
	 * @return the amount of the ingredient
	 */
	public int getIngredient(Ingredient i) {
		return i.getAmount();

	}

	/**
	 * Returns true if there are enough ingredients to make the beverage.
	 *
	 * @param r recipe to check if there are enough ingredients
	 * @return true if enough ingredients to make the beverage
	 */
	public boolean enoughIngredients(final Recipe r) {

		boolean isEnough = true;

		if (!r.getIngredients().isEmpty() && !inventoryIngredients.isEmpty()) {

			for (Ingredient i : r.getIngredients()) {
				for (Ingredient e : inventoryIngredients) {

					if (i.getName().equals(e.getName())) {
						if (e.getAmount() < i.getAmount()) {
							isEnough = false;
						}
					}
				}
			}
			return isEnough;
		} else {
			return false;
		}

	}

	/**
	 * Removes the ingredients used to make the specified recipe. Assumes that the
	 * user has checked that there are enough ingredients to make
	 *
	 * @param r recipe to make
	 * @return true if recipe is made.
	 */
	public boolean useIngredients(final Recipe r) {

		List<Ingredient> ingredientList = r.getIngredients();

		if (enoughIngredients(r)) {

			for (Ingredient i : ingredientList) {
				for (Ingredient e : inventoryIngredients) {
					if (i.getName().equals(e.getName())) {
						e.setAmount(e.getAmount() - i.getAmount());
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}
}