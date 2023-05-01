package edu.ncsu.csc.CoffeeMaker.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.intThat;

import java.util.List;

import javax.validation.ConstraintViolationException;

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
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@SpringBootTest(classes = TestConfig.class)
public class RecipeTest {

	@Autowired
	private RecipeService service;

	@Autowired
	private InventoryService iService;

	@Autowired
	private IngredientService ingredientService;

	@BeforeEach
	public void setup() {
		service.deleteAll();
		iService.deleteAll();
		ingredientService.deleteAll();
	}

	@Test
	@Transactional
	public void testAddRecipe() {

		final Recipe r1 = new Recipe();
		r1.setName("Black Coffee");
		r1.setPrice(1);

		service.save(r1);

		final Recipe r2 = new Recipe();
		r2.setName("Mocha");
		r2.setPrice(1);
		service.save(r2);

		final List<Recipe> recipes = service.findAll();
		Assertions.assertEquals(2, recipes.size(), "Creating two recipes should result in two recipes in the database");

		Assertions.assertEquals(r1, recipes.get(0), "The retrieved recipe should match the created one");
	}

	@Test
	@Transactional
	public void testAddRecipe1() {

		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");
		final String name = "Coffee";
		final Recipe r1 = createRecipe(name, 5);

		service.save(r1);

		Assertions.assertEquals(1, service.findAll().size(), "There should only one recipe in the CoffeeMaker");
		Assertions.assertNotNull(service.findByName(name));

	}

	/* Test2 is done via the API for different validation */

	@Test
	@Transactional
	public void testAddRecipe7() {
		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");
		final String name = "Coffee";

		try {
			final Recipe r1 = createRecipe(name, -1);
			service.save(r1);
			Assertions.assertNull(service.findByName(name),
					"A recipe was able to be created with a negative amount of chocolate");
		} catch (IllegalArgumentException e) {
			// expected
		}

	}
	
	
	

	/**
	 * Test if setRecipe and checkRecipe works in the recipe class 
	 */
	@Test
	@Transactional
	public void testSetIngredient() {
		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");

		final Recipe r1 = createRecipe("Coffee", 10);

		r1.addIngredient(new Ingredient("lemons", 10));

		service.save(r1);

		r1.setAmount(new Ingredient("lemons", 5));

		service.save(r1);

		int ingedientAmount = r1.getIngredients().get(0).getAmount();

		assertEquals(5, ingedientAmount, "Ingredient was suppose to be 5 but was " + ingedientAmount);
		assertFalse(r1.checkRecipe());

		r1.setAmount(new Ingredient("lemons", 0));

		service.save(r1);
		
		assertTrue(r1.checkRecipe());
	}
	
	
	
	/**
	 * Test if toString works in the recipe class 
	 * 
	 */
	@Test
	@Transactional
	public void testEqualsMethosd() {
		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");

		final Recipe r1 = createRecipe("Coffee", 10);
		
		final Ingredient testIngre = new Ingredient("grass", 19);

		r1.addIngredient(new Ingredient("lemons", 10));

		service.save(r1);

		
		assertFalse(r1.equals(null));
		assertFalse(testIngre.getName().equals(r1.getName()));
		assertFalse(r1.getClass().equals(testIngre.getClass()));
		assertTrue(r1.getName().equals("Coffee"));
		assertFalse(r1.getName().equals(null));
		assertTrue(!r1.getName().equals("Coffe"));
	}
	
	

	@Test
	@Transactional
	public void testDeleteRecipe2() {
		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");

		final Recipe r1 = createRecipe("Coffee", 10);
		service.save(r1);
		final Recipe r2 = createRecipe("Mocha", 30);
		service.save(r2);
		final Recipe r3 = createRecipe("Latte", 60);
		service.save(r3);

		Assertions.assertEquals(3, service.count(), "There should be three recipes in the database");

		service.deleteAll();

		Assertions.assertEquals(0, service.count(), "`service.deleteAll()` should remove everything");

	}

	@Test
	@Transactional
	public void testEditRecipe1() {
		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");

		final Recipe r1 = createRecipe("Coffee", 50);
		service.save(r1);

		r1.setPrice(70);

		service.save(r1);

		final Recipe retrieved = service.findByName("Coffee");

		Assertions.assertEquals(70, (int) retrieved.getPrice());

		Assertions.assertEquals(1, service.count(), "Editing a recipe shouldn't duplicate it");

	}

	private Recipe createRecipe(final String name, final Integer price) {
		final Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setPrice(price);

		return recipe;
	}

}
