package edu.ncsu.csc.CoffeeMaker.unit;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import edu.ncsu.csc.CoffeeMaker.TestConfig;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;



//import javax.transaction.Transactional;

import edu.ncsu.csc.CoffeeMaker.services.RecipeService;
import edu.ncsu.csc.CoffeeMaker.services.Service;


@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class TestDatabaseInteraction {
	
	@Autowired
	private RecipeService recipeService;
	
	@Test
	@Transactional
	public void testRecipes() {
		/* We'll fill this out in a bit */		
		recipeService.deleteAll();
		
		Recipe r = new Recipe();
		
		recipeService.save(r);
		
		
		 List<Recipe> dbRecipes = (List<Recipe>) recipeService.findAll();

		 
		 
		   Assertions.assertEquals(1, dbRecipes.size());

		   Recipe dbRecipe = dbRecipes.get(0);

		   Assertions.assertEquals(r.getName(), dbRecipe.getName());
		/* Other fields would get tested one at a time here too */
		   
		   
		   dbRecipe.setPrice(10);
		   recipeService.save(dbRecipe);
		   
		   
		   final List<Recipe> dbRecipesList = (List<Recipe>) recipeService.findAll();
		   
		   assertEquals(1, dbRecipesList.size());
		   
		
	}


}
