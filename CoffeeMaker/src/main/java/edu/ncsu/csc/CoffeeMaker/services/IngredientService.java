package edu.ncsu.csc.CoffeeMaker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.repositories.IngredientRepository;


@Component
@Transactional
public class IngredientService extends Service<Ingredient, Long> {
	
	/**
     * IngredientRepository, to be autowired in by Spring and provide CRUD
     * operations on Inventory model.
     */
	@Autowired
	private IngredientRepository ingredientRepository;

	@Override
	protected JpaRepository getRepository() {
		// TODO Auto-generated method stub
		return ingredientRepository;
	}

	public Ingredient findByName ( final String name ) {
        return ingredientRepository.findByName( name );// it would look something like this, findByName needs to be created
    }
}
