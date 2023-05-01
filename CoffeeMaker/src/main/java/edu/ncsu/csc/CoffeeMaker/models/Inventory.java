
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
//
//	/** amount of incgredient */
//	@Min(0)
//	private Ingredient ingredient;
//
//	private Integer amount;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Ingredient> inventoryIngredients;

	/**
	 * Empty constructor for Hibernate
	 */
	public Inventory() {
		// Intentionally empty so that Hibernate can instantiate
		// Inventory object.
		this.inventoryIngredients = new ArrayList<>();
	}

	public void addIngredient(final Ingredient ingredient) {
		ingredient.setAmount(ingredient.getAmount());
		inventoryIngredients.add(ingredient);
	}

	public void addIngredientsToList(final List<Ingredient> ingredients) {

		this.inventoryIngredients = ingredients;
	}

	public List<Ingredient> getList() {
		return this.inventoryIngredients;
	}

	/**
	 * updates a single ingredients amount in inventory
	 * 
	 * @param ingredient
	 */
	public void updateInventory(final Ingredient ingredient) {

		for (Ingredient e : inventoryIngredients) {

			if (e.getName().equals(ingredient.getName())) {
				e.setAmount(ingredient.getAmount() + e.getAmount());
			} else {
				throw new IllegalArgumentException("Ingredient doesn't exist");
			}
		}
	}

	/**
	 * updates a single ingredients amount in inventory
	 * 
	 * @param ingredient
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
	 * @return long
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * Set the ID of the Inventory (Used by Hibernate)
	 *
	 * @param id the ID
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Returns the current number of ingredient units in the inventory.
	 *
	 * @return amount of chocolate
	 */
	public int getIngredient(Ingredient i) {
		return i.getAmount();

	}

//	/**
//	 * Add the number of sugar units in the inventory to the current amount of sugar
//	 * units.
//	 *
//	 * @param sugar amount of sugar
//	 * @return checked amount of sugar
//	 * @throws IllegalArgumentException if the parameter isn't a positive integer
//	 */
//	public Integer checkIngredient(final String name) throws IllegalArgumentException {
//		
//		
//		Integer amtSugar = 0;
//		try {
//			amtSugar = Integer.parseInt(sugar);
//		} catch (final NumberFormatException e) {
//			throw new IllegalArgumentException("Units of sugar must be a positive integer");
//		}
//		if (amtSugar < 0) {
//			throw new IllegalArgumentException("Units of sugar must be a positive integer");
//		}
//
//		return amtSugar;
//	}

//    /**
//     * Sets the number of chocolate units in the inventory to the specified
//     * amount.
//     *
//     * @param amtChocolate
//     *            amount of chocolate to set
//     */
//    public void setChocolate ( final Integer amtChocolate ) {
//        if ( amtChocolate >= 0 ) {
//            chocolate = amtChocolate;
//        }
//    }
//
//    /**
//     * Add the number of chocolate units in the inventory to the current amount
//     * of chocolate units.
//     *
//     * @param chocolate
//     *            amount of chocolate
//     * @return checked amount of chocolate
//     * @throws IllegalArgumentException
//     *             if the parameter isn't a positive integer
//     */
//    public Integer checkChocolate ( final String chocolate ) throws IllegalArgumentException {
//        Integer amtChocolate = 0;
//        try {
//            amtChocolate = Integer.parseInt( chocolate );
//        }
//        catch ( final NumberFormatException e ) {
//            throw new IllegalArgumentException( "Units of chocolate must be a positive integer" );
//        }
//        if ( amtChocolate < 0 ) {
//            throw new IllegalArgumentException( "Units of chocolate must be a positive integer" );
//        }
//
//        return amtChocolate;
//    }
//
//    /**
//     * Returns the current number of coffee units in the inventory.
//     *
//     * @return amount of coffee
//     */
//    public Integer getCoffee () {
//        return coffee;
//    }
//
//    /**
//     * Sets the number of coffee units in the inventory to the specified amount.
//     *
//     * @param amtCoffee
//     *            amount of coffee to set
//     */
//    public void setCoffee ( final Integer amtCoffee ) {
//        if ( amtCoffee >= 0 ) {
//            coffee = amtCoffee;
//        }
//    }
//
//    /**
//     * Add the number of coffee units in the inventory to the current amount of
//     * coffee units.
//     *
//     * @param coffee
//     *            amount of coffee
//     * @return checked amount of coffee
//     * @throws IllegalArgumentException
//     *             if the parameter isn't a positive integer
//     */
//    public Integer checkCoffee ( final String coffee ) throws IllegalArgumentException {
//        Integer amtCoffee = 0;
//        try {
//            amtCoffee = Integer.parseInt( coffee );
//        }
//        catch ( final NumberFormatException e ) {
//            throw new IllegalArgumentException( "Units of coffee must be a positive integer" );
//        }
//        if ( amtCoffee < 0 ) {
//            throw new IllegalArgumentException( "Units of coffee must be a positive integer" );
//        }
//
//        return amtCoffee;
//    }
//
//    /**
//     * Returns the current number of milk units in the inventory.
//     *
//     * @return int
//     */
//    public Integer getMilk () {
//        return milk;
//    }
//
//    /**
//     * Sets the number of milk units in the inventory to the specified amount.
//     *
//     * @param amtMilk
//     *            amount of milk to set
//     */
//    public void setMilk ( int amtMilk ) {
//        if ( amtMilk >= 0 ) {
//            milk = amtMilk;
//        }
//        
//        System.out.println("Unmodified setMilk "+milk);
//    }
//
//    /**
//     * Add the number of milk units in the inventory to the current amount of
//     * milk units.
//     *
//     * @param milk
//     *            amount of milk
//     * @return checked amount of milk
//     * @throws IllegalArgumentException
//     *             if the parameter isn't a positive integer
//     */
//    public Integer checkMilk ( final String milk ) throws IllegalArgumentException {
//        Integer amtMilk = 0;
//        try {
//            amtMilk = Integer.parseInt( milk );
//        }
//        catch ( final NumberFormatException e ) {
//            throw new IllegalArgumentException( "Units of milk must be a positive integer" );
//        }
//        if ( amtMilk < 0 ) {
//            throw new IllegalArgumentException( "Units of milk must be a positive integer" );
//        }
//
//        return amtMilk;
//    }
//
//    /**
//     * Returns the current number of sugar units in the inventory.
//     *
//     * @return int
//     */
//    public Integer getSugar () {
//        return sugar;
//    }
//
//    /**
//     * Sets the number of sugar units in the inventory to the specified amount.
//     *
//     * @param amtSugar
//     *            amount of sugar to set
//     */
//    public void setSugar ( final Integer amtSugar ) {
//        if ( amtSugar >= 0 ) {
//            sugar = amtSugar;
//        }
//    }
//
//    /**
//     * Add the number of sugar units in the inventory to the current amount of
//     * sugar units.
//     *
//     * @param sugar
//     *            amount of sugar
//     * @return checked amount of sugar
//     * @throws IllegalArgumentException
//     *             if the parameter isn't a positive integer
//     */
//    public Integer checkSugar ( final String sugar ) throws IllegalArgumentException {
//        Integer amtSugar = 0;
//        try {
//            amtSugar = Integer.parseInt( sugar );
//        }
//        catch ( final NumberFormatException e ) {
//            throw new IllegalArgumentException( "Units of sugar must be a positive integer" );
//        }
//        if ( amtSugar < 0 ) {
//            throw new IllegalArgumentException( "Units of sugar must be a positive integer" );
//        }
//
//        return amtSugar;
//    }

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
//			return true;
		} else {
			return false;
		}

		return true;

	}

//    /**
//     * Adds ingredients to the inventory
//     *
//     * @param coffee
//     *            amt of coffee
//     * @param milk
//     *            amt of milk
//     * @param sugar
//     *            amt of sugar
//     * @param chocolate
//     *            amt of chocolate
//     * @return true if successful, false if not
//     */
//    public boolean addIngredients ( final Integer coffee, final Integer milk, final Integer sugar,
//            final Integer chocolate ) {
//        if ( coffee < 0 || milk < 0 || sugar < 0 || chocolate < 0 ) {
//            throw new IllegalArgumentException( "Amount cannot be negative" );
//        }
//
//        setCoffee( this.coffee + coffee );
//        setMilk( this.milk + milk );
//        setSugar( this.sugar + sugar );
//        setChocolate( this.chocolate + chocolate );
//        return true;
//    }

//    /**
//     * Returns a string describing the current contents of the inventory.
//     *
//     * @return String
//     */
//    @Override
//    public String toString () {
//        final StringBuffer buf = new StringBuffer();
//        buf.append( "Coffee: " );
//        buf.append( getCoffee() );
//        buf.append( "\n" );
//        buf.append( "Milk: " );
//        buf.append( getMilk() );
//        buf.append( "\n" );
//        buf.append( "Sugar: " );
//        buf.append( getSugar() );
//        buf.append( "\n" );
//        buf.append( "Chocolate: " );
//        buf.append( getChocolate() );
//        buf.append( "\n" );
//        return buf.toString();
//    }

}