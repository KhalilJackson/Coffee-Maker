package edu.ncsu.csc.CoffeeMaker.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import org.springframework.test.web.servlet.ResultActions;

import edu.ncsu.csc.CoffeeMaker.common.TestUtils;
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith ( SpringExtension.class )
public class APIIngredientTest {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private InventoryService iService;
	
	@Autowired
	private IngredientService service;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		service.deleteAll();
		iService.deleteAll();
		recipeService.deleteAll();
	}

    @Test
    @Transactional
    public void ensureIngredient () throws Exception {
    	

        final Ingredient i = new Ingredient("Coffee", 5);
      

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i ) ) ).andExpect( status().isOk() );

    }
    
    
    @Test
    @Transactional
    public void testIngredientAPI () throws Exception {

 

        final Ingredient i = new Ingredient("Coffee", 5);
        
        i.setName( "Delicious Not-Coffee" );
        service.save(i);
   

        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i ) ) );

       
        Assertions.assertEquals( 1, (int) service.count() );
        
    }
    
    @Test
    @Transactional
    public void testAddIngredient2 () throws Exception {

        /* Tests a recipe with a duplicate name to make sure it's rejected */

        Assertions.assertEquals( 0, (int) service.findAll().size(), "There should be no ingredients in the CoffeeMaker" );
        
        final Ingredient i1 = new Ingredient("Coffee", 5);

        service.save( i1 );

        final Ingredient i2 = new Ingredient("Coffee", 5);
             
        mvc.perform( post( "/api/v1/ingredients" ).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString( i2 ) ) ).andExpect( status().is4xxClientError() );
        
        
        Assertions.assertEquals( 1, service.findAll().size(), "There should only one ingredient in the CoffeeMaker" );
    }
    
    @Test
    @Transactional
    public void testGetIngredient () throws Exception {

        /* Tests get from api end point */

        Assertions.assertEquals( 0, (int) service.findAll().size(), "There should be no ingredients in the CoffeeMaker" );
        
        final Ingredient i1 = new Ingredient("Coffee", 5);

        service.save( i1 );
        
        Assertions.assertEquals( 1, (int) service.findAll().size(), "There should be no ingredients in the CoffeeMaker" );
        
        final String name = i1.getName();
        
        ResultActions response = mvc.perform( post("/api/v1/ingredients/%", name));
        response.andExpect(status().isOk());
// 
//        mvc.perform( delete("/api/v1/deleteIngredients/{name}", name).andExpect(status().isOk()));
//        
//        service.delete(i1);
//             
//        mvc.perform( deleteIngredient( "/api/v1/deleteIngredients/", name ).contentType( MediaType.APPLICATION_JSON )
//                .content( TestUtils.asJsonString( i1.getName() ) ) ).andExpect( status().is4xxClientError() );
//        
        
        Assertions.assertEquals( 1, service.findAll().size(), "There should be no ingredient in the CoffeeMaker" );
    }
    
    @Test
    @Transactional
    public void testDeleteIngredient () throws Exception {

        /* Tests a recipe with a duplicate name to make sure it's rejected */

        Assertions.assertEquals( 0, (int) service.findAll().size(), "There should be no ingredients in the CoffeeMaker" );
        
        final Ingredient i1 = new Ingredient("Coffee", 5);

        service.save( i1 );
        
        Assertions.assertEquals( 1, (int) service.findAll().size(), "There should be no ingredients in the CoffeeMaker" );
        
        final String name = i1.getName();
        
//        mvc.perform( post( "/api/v1/ingredient/" ).contentType( MediaType.APPLICATION_JSON )
//                .content( TestUtils.asJsonString( i2 ) ) ).andExpect( status().is4xxClientError() );
// 
//        mvc.perform( delete("/api/v1/deleteIngredients/{name}", name).andExpect(status().isOk()));
//        
//        service.delete(i1);
//             
//        mvc.perform( deleteIngredient( "/api/v1/deleteIngredients/", name ).contentType( MediaType.APPLICATION_JSON )
//                .content( TestUtils.asJsonString( i1.getName() ) ) ).andExpect( status().is4xxClientError() );
//        
        
        Assertions.assertEquals( 1, service.findAll().size(), "There should be no ingredient in the CoffeeMaker" );
    }
    
    

}
