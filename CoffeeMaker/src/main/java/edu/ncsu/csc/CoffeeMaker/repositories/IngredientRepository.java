package edu.ncsu.csc.CoffeeMaker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;

/**
 * IngredientRepository that extends the JpaRepository
 */
public interface IngredientRepository extends JpaRepository <Ingredient, Long> {

	/**
	 * Finds our ingredient by name
	 * @param name of the ingredient
	 * @return finds our ingredient for us
	 */
	Ingredient findByName(String name);

}