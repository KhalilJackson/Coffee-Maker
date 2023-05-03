package edu.ncsu.csc.CoffeeMaker.unit;

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
import edu.ncsu.csc.CoffeeMaker.services.InventoryService;

@ExtendWith ( SpringExtension.class )
@EnableAutoConfiguration
@SpringBootTest ( classes = TestConfig.class )
public class InventoryTest {

    @Autowired
    private InventoryService inventoryService;

    @BeforeEach
    public void setup () {
    	
        final Inventory ivt = inventoryService.getInventory();
        
        
        ivt.addIngredient(new Ingredient("Blackie", 500));
        ivt.addIngredient(new Ingredient("Work", 500));
        ivt.addIngredient(new Ingredient("Coffee", 500));
        


        inventoryService.save( ivt );
    }

    
    @Test
    @Transactional
    public void testAddInventory1 () {
        Inventory ivt = inventoryService.getInventory();

        Ingredient one = new Ingredient("Choco", 5);
        Ingredient two = new Ingredient("Milk", 5);
        Ingredient three = new Ingredient("Coffee", 5);
        Ingredient four = new Ingredient("Love", 5);
        		
        ivt.addIngredient(one);
        ivt.addIngredient(two);
        ivt.addIngredient(three);
        ivt.addIngredient(four);

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        Assertions.assertEquals( 5, (int) ivt.getIngredient(one),
                "Adding to the inventory should result in correctly-updated values for coffee" );
        Assertions.assertEquals( 5, (int) ivt.getIngredient(two),
                "Adding to the inventory should result in correctly-updated values for milk" );
        Assertions.assertEquals( 5, (int) ivt.getIngredient(three),
                "Adding to the inventory should result in correctly-updated values sugar" );

    }
    


}
