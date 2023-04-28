package edu.ncsu.csc.CoffeeMaker.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;

@RestController
public class APIIngredientController extends APIController {
	
	private IngredientService service;
	
	/**
     * REST API method to provide GET access to all ingredients in the system
     *
     * @return 			JSON representation of all ingredients
     */
    @GetMapping ( BASE_PATH + "/ingredients" )
    public List<Ingredient> getRecipes () {
        return service.findAll();// need to make this method in IngredientService
    }
    
    /**
     * REST API method to provide GET access to a specific ingredient, as indicated
     * by the path variable provided (the name of the ingredient desired)
     *
     * @param name		ingredient name
     * @return 			response to the request
     */
	@GetMapping ( BASE_PATH + "/ingredients/{name}" )
	public ResponseEntity getIngredient ( @PathVariable final String name ) {
	    
		final Ingredient ingr = service.findByName( name ); // need to make this method in IngredientService

	    if ( null == ingr ) {
	        return new ResponseEntity( HttpStatus.NOT_FOUND );
	    }

	    return new ResponseEntity( ingr, HttpStatus.OK );
	}
	
	/**
     * REST API method to allow deleting a Ingredient from the CoffeeMaker's
     * Inventory, by making a DELETE request to the API endpoint and indicating
     * the recipe to delete (as a path variable)
     *
     * @param name		The name of the Ingredient to delete
     * @return 			Success if the recipe could be deleted; an error if the ingredient
     *        			does not exist
     */
    @DeleteMapping ( BASE_PATH + "/ingredients/{name}" )
    public ResponseEntity deleteInredient ( @PathVariable final String name ) {
        final Ingredient ingredient = service.findByName( name ); // need make this method in IngredientService
        if ( null == ingredient ) {
            return new ResponseEntity( errorResponse( "No ingredient found for name " + name ), HttpStatus.NOT_FOUND );
        }
        service.delete( ingredient );
        return new ResponseEntity( successResponse( name + " was deleted successfully" ), HttpStatus.OK );
    }
    
    /**
     * REST API method to provide POST access to the Ingredient model. This is used
     * to create a new Ingredient by automatically converting the JSON RequestBody
     * provided to a Ingredient object. Invalid JSON will fail.
     *
     * @param  ingredient 			The valid Ingredient to be saved.
     * @return ResponseEntity 	indicating success if the Ingredient could be saved to
     *         					the inventory, or an error if it could not be
     */
    @PostMapping ( BASE_PATH + "/recipes" )
    public ResponseEntity createIngredient ( @RequestBody final Ingredient ingredient) {
        if ( null != service.findByName( ingredient.getName() ) ) { //should ingredient take in a name or not?????
            return new ResponseEntity( errorResponse( "Recipe with the name " + ingredient.getName() + " already exists" ),
                    HttpStatus.CONFLICT );
        }
        	service.save(ingredient); //need to make this method
            return new ResponseEntity( successResponse( ingredient.getName() + " successfully created" ), HttpStatus.OK );
   
      }

  
	
}
