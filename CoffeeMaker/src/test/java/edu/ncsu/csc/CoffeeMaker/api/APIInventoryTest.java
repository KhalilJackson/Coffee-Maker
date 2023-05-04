package edu.ncsu.csc.CoffeeMaker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import edu.ncsu.csc.CoffeeMaker.models.Ingredient;
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
import edu.ncsu.csc.CoffeeMaker.services.IngredientService;
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;
import edu.ncsu.csc.CoffeeMaker.services.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class APIInventoryTest {

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
		
		
        final Inventory ivt = iService.getInventory();
        
        
        ivt.addIngredient(new Ingredient("Dark Roast", 500));
        ivt.addIngredient(new Ingredient("Work", 500));
        ivt.addIngredient(new Ingredient("Coffee", 500));
        
        iService.save( ivt );
	}
	
	
	
	@Test
	@Transactional
	public void updateInventoryTest() throws Exception{
		
		final Inventory iv = new Inventory();
		
		iv.addIngredient(new Ingredient("Dark Roast", 50));
        iv.addIngredient(new Ingredient("Work", 50));
        iv.addIngredient(new Ingredient("Coffee", 50));
        
        
        assertEquals(3, iv.getInventoryIngredients().size());
        
        
        
        mvc.perform( put( "/api/v1/inventory", iv).contentType( MediaType.APPLICATION_JSON )
                .content( TestUtils.asJsonString(iv) )).andExpect( status().isOk());
        
        
        
	}
}