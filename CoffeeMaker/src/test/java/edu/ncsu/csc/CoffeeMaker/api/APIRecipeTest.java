package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class APIRecipeTest {

	@Autowired
	private RecipeService service;
	
	@Autowired
	private InventoryService iService;
	
	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setup() {

		service.deleteAll();
		
		iService.deleteAll();
		ingredientService.deleteAll();
	}

	@Test
	@Transactional
	public void ensureRecipe() throws Exception {

		final Recipe r = new Recipe();

		r.setPrice(10);
		r.setName("Mocha");

		mvc.perform(post("/api/v1/recipes").contentType(MediaType.APPLICATION_JSON).content(TestUtils.asJsonString(r)))
				.andExpect(status().isOk());

	}

	@Test
	@Transactional
	public void testRecipeAPI() throws Exception {


		final Recipe recipe = new Recipe();
		recipe.setName("Delicious Not-Coffee");

		recipe.setPrice(5);

		mvc.perform(post("/api/v1/recipes").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtils.asJsonString(recipe)));

		Assertions.assertEquals(1, (int) service.count());

	}

	@Test
	@Transactional
	public void testAddRecipe2() throws Exception {

		/* Tests a recipe with a duplicate name to make sure it's rejected */

		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");
		final String name = "Coffee";
		final Recipe r1 = createRecipe(name, 50, 3, 1, 1, 0);

		service.save(r1);

		final Recipe r2 = createRecipe(name, 50, 3, 1, 1, 0);
		
		mvc.perform(post("/api/v1/recipes").contentType(MediaType.APPLICATION_JSON).content(TestUtils.asJsonString(r2)))
				.andExpect(status().is4xxClientError());

		Assertions.assertEquals(1, service.findAll().size(), "There should only one recipe in the CoffeeMaker");
	}

	@Test
	@Transactional
	public void testAddRecipe15() throws Exception {

		/* Tests to make sure that our cap of 3 recipes is enforced */

		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");

		final Recipe r1 = createRecipe("Coffee", 50, 3, 1, 1, 0);
		service.save(r1);
		final Recipe r2 = createRecipe("Mocha", 50, 3, 1, 1, 2);
		service.save(r2);
		final Recipe r3 = createRecipe("Latte", 60, 3, 2, 2, 0);
		service.save(r3);

		Assertions.assertEquals(3, service.count(),
				"Creating three recipes should result in three recipes in the database");

		final Recipe r4 = createRecipe("Hot Chocolate", 75, 0, 2, 1, 2);

		mvc.perform(post("/api/v1/recipes").contentType(MediaType.APPLICATION_JSON).content(TestUtils.asJsonString(r4)))
				.andExpect(status().isInsufficientStorage());

		Assertions.assertEquals(3, service.count(), "Creating a fourth recipe should not get saved");
	}
	
	@Test
	@Transactional
	public void testGetRecipe() throws Exception {
		
		/* Tests to make sure that our cap of 3 recipes is enforced */

		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");
		
		final Recipe r1 = createRecipe("Coffee", 50, 3, 1, 1, 0);
		service.save(r1);
		
        mvc.perform( MockMvcRequestBuilders.get( "/api/v1/recipes/{name}", "Coffee").contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r1.getName() ) ) ).andExpect( status().isOk() );
        
        
        Assertions.assertEquals( 1, service.findAll().size(), "There should be no ingredient in the CoffeeMaker" );
        
        mvc.perform( MockMvcRequestBuilders.get( "/api/v1/recipes/{name}", "Syrup").contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( "Syrup" ) ) ).andExpect( status().isNotFound());
		
		
	}
	
	@Test
	@Transactional
	public void testDeleteRecipe() throws Exception {
		
		/* Tests to make sure that our cap of 3 recipes is enforced */

		Assertions.assertEquals(0, service.findAll().size(), "There should be no Recipes in the CoffeeMaker");
		
		final Recipe r1 = createRecipe("Coffee", 50, 3, 1, 1, 0);
		service.save(r1);
		
        mvc.perform( delete( "/api/v1/recipes/{name}", "Coffee").contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( r1.getName() ) ) ).andExpect( status().isOk() );
        
        
        Assertions.assertEquals( 0, service.findAll().size(), "There should be no ingredient in the CoffeeMaker" );
        
        service.save(r1);
        
        mvc.perform(delete( "/api/v1/recipes/{name}", "Syrup").contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( "Syrup" ) ) ).andExpect( status().isNotFound());
		
		
	}


	private Recipe createRecipe(final String name, final Integer price, final Integer coffee, final Integer milk,
			final Integer sugar, final Integer chocolate) {
		final Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setPrice(price);

		return recipe;
	}

}
