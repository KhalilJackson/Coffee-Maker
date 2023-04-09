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
import edu.ncsu.csc.CoffeeMaker.models.Inventory;
import edu.ncsu.csc.CoffeeMaker.models.Recipe;
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

        ivt.setChocolate( 500 );
        ivt.setCoffee( 500 );
        ivt.setMilk( 500 );
        ivt.setSugar( 500 );

        inventoryService.save( ivt );
    }
    
    @Test
    @Transactional
    public void testConsumeInventory () {
        final Inventory i = inventoryService.getInventory();

        final Recipe recipe = new Recipe();
        recipe.setName( "Delicious Not-Coffee" );
        recipe.setChocolate( 10 );
        recipe.setMilk( 20 );
        recipe.setSugar( 5 );
        recipe.setCoffee( 1 );

        recipe.setPrice( 5 );

        i.useIngredients( recipe );

        /*
         * Make sure that all of the inventory fields are now properly updated
         */

        Assertions.assertEquals( 490, (int) i.getChocolate() );
        Assertions.assertEquals( 480, (int) i.getMilk() );
        Assertions.assertEquals( 495, (int) i.getSugar() );
        Assertions.assertEquals( 499, (int) i.getCoffee() );
    }
    
    @Test
    @Transactional
    public void testAddIngredients () {

    	Inventory testInventory = new Inventory(1, 1, 1, 1);
    	
    	//Ensures that addIngredients can add valid values ingredients
    	Assertions.assertTrue(testInventory.addIngredients(1, 1, 1, 1));
    	
    	//Ensures that you cannot add negative coffee
    	try {
    		
    		Assertions.assertFalse(testInventory.addIngredients(-1, 1, 1, 1));
    		    		
    	} catch (IllegalArgumentException iae) {
    		
    	}
    	
    	//Ensures that you cannot add negative milk
    	try {
    		
    		Assertions.assertFalse(testInventory.addIngredients(1, -1, 1, 1));
    		    		
    	} catch (IllegalArgumentException iae) {
    		
    	}
    	
    	//Ensures that you cannot add negative sugar
    	try {

    		Assertions.assertFalse(testInventory.addIngredients(1, 1, -1, 1));
    		    		
    	} catch (IllegalArgumentException iae) {
    		
    	}
    	
    	//Ensures that you cannot add negative chocolate
    	try {

    		Assertions.assertFalse(testInventory.addIngredients(1, 1, 1, -1));
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}
    	
    }
    
    @Test
    @Transactional
    public void testCheckCoffee () {
    	
    	Inventory testInventory = new Inventory(1, 1, 1, 1);
    	
    	//Ensures method works as intended
    	Assertions.assertEquals(1,  testInventory.checkCoffee(testInventory.getCoffee().toString()));
    	
    	//Ensures method cannot take negative values
    	try {
    		
    		testInventory.checkCoffee("-1");
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}
    	
    	//Ensures method cannot take a value that cannot be converted into an integer
    	try {
    		
    		testInventory.checkCoffee("K");
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}

    }
    
    @Test
    @Transactional
    public void testCheckMilk () {
    	
    	Inventory testInventory = new Inventory(1, 1, 1, 1);
    	
    	//Ensures method works as intended
    	Assertions.assertEquals(1,  testInventory.checkMilk(testInventory.getMilk().toString()));
    	
    	//Ensures method cannot take negative values
    	try {
    		
    		testInventory.checkMilk("-1");
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}
    	
    	//Ensures method cannot take a value that cannot be converted into an integer
    	try {
    		
    		testInventory.checkMilk("K");
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}

    }
    
    @Test
    @Transactional
    public void testCheckSugar () {
    	
    	Inventory testInventory = new Inventory(1, 1, 1, 1);
    	
    	//Ensures method works as intended
    	Assertions.assertEquals(1,  testInventory.checkSugar(testInventory.getSugar().toString()));
    	
    	//Ensures method cannot take negative values
    	try {
    		
    		testInventory.checkSugar("-1");
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}
    	
    	//Ensures method cannot take a value that cannot be converted into an integer
    	try {
    		
    		testInventory.checkSugar("K");
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}

    }
    
    @Test
    @Transactional
    public void testCheckChocolate () {
    	
    	Inventory testInventory = new Inventory(1, 1, 1, 1);
    	
    	//Ensures method works as intended
    	Assertions.assertEquals(1,  testInventory.checkChocolate(testInventory.getChocolate().toString()));
    	
    	//Ensures method cannot take negative values
    	try {
    		
    		testInventory.checkChocolate("-1");
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}
    	
    	//Ensures method cannot take a value that cannot be converted into an integer
    	try {
    		
    		testInventory.checkChocolate("K");
    		
    	} catch (IllegalArgumentException iae) {
    		
    	}

    }
    

    @Test
    @Transactional
    public void testAddInventory1 () {
        Inventory ivt = inventoryService.getInventory();

        ivt.addIngredients( 5, 3, 7, 2 );

        /* Save and retrieve again to update with DB */
        inventoryService.save( ivt );

        ivt = inventoryService.getInventory();

        Assertions.assertEquals( 505, (int) ivt.getCoffee(),
                "Adding to the inventory should result in correctly-updated values for coffee" );
        Assertions.assertEquals( 503, (int) ivt.getMilk(),
                "Adding to the inventory should result in correctly-updated values for milk" );
        Assertions.assertEquals( 507, (int) ivt.getSugar(),
                "Adding to the inventory should result in correctly-updated values sugar" );
        Assertions.assertEquals( 502, (int) ivt.getChocolate(),
                "Adding to the inventory should result in correctly-updated values chocolate" );

    }

    @Test
    @Transactional
    public void testAddInventory2 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.addIngredients( -5, 3, 7, 2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getCoffee(),
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- coffee" );
            Assertions.assertEquals( 500, (int) ivt.getMilk(),
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- milk" );
            Assertions.assertEquals( 500, (int) ivt.getSugar(),
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- sugar" );
            Assertions.assertEquals( 500, (int) ivt.getChocolate(),
                    "Trying to update the Inventory with an invalid value for coffee should result in no changes -- chocolate" );
        }
    }

    @Test
    @Transactional
    public void testAddInventory3 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.addIngredients( 5, -3, 7, 2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getCoffee(),
                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- coffee" );
            Assertions.assertEquals( 500, (int) ivt.getMilk(),
                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- milk" );
            Assertions.assertEquals( 500, (int) ivt.getSugar(),
                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- sugar" );
            Assertions.assertEquals( 500, (int) ivt.getChocolate(),
                    "Trying to update the Inventory with an invalid value for milk should result in no changes -- chocolate" );

        }

    }

    @Test
    @Transactional
    public void testAddInventory4 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.addIngredients( 5, 3, -7, 2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getCoffee(),
                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- coffee" );
            Assertions.assertEquals( 500, (int) ivt.getMilk(),
                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- milk" );
            Assertions.assertEquals( 500, (int) ivt.getSugar(),
                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- sugar" );
            Assertions.assertEquals( 500, (int) ivt.getChocolate(),
                    "Trying to update the Inventory with an invalid value for sugar should result in no changes -- chocolate" );

        }

    }

    @Test
    @Transactional
    public void testAddInventory5 () {
        final Inventory ivt = inventoryService.getInventory();

        try {
            ivt.addIngredients( 5, 3, 7, -2 );
        }
        catch ( final IllegalArgumentException iae ) {
            Assertions.assertEquals( 500, (int) ivt.getCoffee(),
                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- coffee" );
            Assertions.assertEquals( 500, (int) ivt.getMilk(),
                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- milk" );
            Assertions.assertEquals( 500, (int) ivt.getSugar(),
                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- sugar" );
            Assertions.assertEquals( 500, (int) ivt.getChocolate(),
                    "Trying to update the Inventory with an invalid value for chocolate should result in no changes -- chocolate" );

        }
    }
    
    @Test
    @Transactional
    public void testEnoughIngredients1 () {
        final Inventory ivt = inventoryService.getInventory();

        final Recipe r1 = new Recipe();

        r1.setName( "Coffee" );
        r1.setPrice( 50 );
        r1.setCoffee( 50 ); //good luck not having a heart attack
        r1.setMilk( 600 );
        r1.setSugar( 1 );
        r1.setChocolate( 0 );
        
        Assertions.assertFalse(ivt.enoughIngredients(r1), "There isn't enough milk to make this recipe but was still made.");
        r1.setMilk(50 );
        r1.setSugar(501);
        
        Assertions.assertFalse(ivt.enoughIngredients(r1), "There isn't enough sugar to make this recipe but was still made.");
        r1.setSugar(50);
        r1.setChocolate(501);
        Assertions.assertFalse(ivt.enoughIngredients(r1), "There isn't enough chocolate to make this recipe but was still made.");
        
    }

}
