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
    
//    @Test
//    @Transactional
//    public void testConsumeInventory () {
//        final Inventory i = inventoryService.getInventory();
//
//        final Recipe recipe = new Recipe();
//        recipe.setName( "Delicious Not-Coffee" );
//
//        recipe.setPrice( 5 );
//
//        i.useIngredients( recipe );
//
//        /*
//         * Make sure that all of the inventory fields are now properly updated
//         */
//
//    }
//
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

//    @Test
//    @Transactional
//    public void testAddInventory2 () {
//        final Inventory ivt = inventoryService.getInventory();
//
//        try {
//            ivt.addIngredient(new  );
//        }
//        catch ( final IllegalArgumentException iae ) {
//            Assertions.assertEquals( 500, (int) ivt.getCoffee(),
//                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- coffee" );
//            Assertions.assertEquals( 500, (int) ivt.getMilk(),
//                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- milk" );
//            Assertions.assertEquals( 500, (int) ivt.getSugar(),
//                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- sugar" );
//            Assertions.assertEquals( 500, (int) ivt.getChocolate(),
//                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- chocolate" );
//        }
//    }
//
//    @Test
//    @Transactional
//    public void testAddInventory3 () {
//        final Inventory ivt = inventoryService.getInventory();
//
//        try {
//            ivt.addIngredients( 5, -3, 7, 2 );
//        }
//        catch ( final IllegalArgumentException iae ) {
//            Assertions.assertEquals( 500, (int) ivt.getCoffee(),
//                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- coffee" );
//            Assertions.assertEquals( 500, (int) ivt.getMilk(),
//                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- milk" );
//            Assertions.assertEquals( 500, (int) ivt.getSugar(),
//                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- sugar" );
//            Assertions.assertEquals( 500, (int) ivt.getChocolate(),
//                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- chocolate" );
//
//        }
//
//    }
//
//    @Test
//    @Transactional
//    public void testAddInventory4 () {
//        final Inventory ivt = inventoryService.getInventory();
//
//        try {
//            ivt.addIngredients( 5, 3, -7, 2 );
//        }
//        catch ( final IllegalArgumentException iae ) {
//            Assertions.assertEquals( 500, (int) ivt.getCoffee(),
//                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- coffee" );
//            Assertions.assertEquals( 500, (int) ivt.getMilk(),
//                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- milk" );
//            Assertions.assertEquals( 500, (int) ivt.getSugar(),
//                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- sugar" );
//            Assertions.assertEquals( 500, (int) ivt.getChocolate(),
//                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- chocolate" );
//
//        }
//
//    }
//
//    @Test
//    @Transactional
//    public void testAddInventory5 () {
//        final Inventory ivt = inventoryService.getInventory();
//
//        try {
//            ivt.addIngredients( 5, 3, 7, -2 );
//        }
//        catch ( final IllegalArgumentException iae ) {
//            Assertions.assertEquals( 500, (int) ivt.getCoffee(),
//                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- coffee" );
//            Assertions.assertEquals( 500, (int) ivt.getMilk(),
//                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- milk" );
//            Assertions.assertEquals( 500, (int) ivt.getSugar(),
//                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- sugar" );
//            Assertions.assertEquals( 500, (int) ivt.getChocolate(),
//                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- chocolate" );
//
//        }
//
//    }

}
