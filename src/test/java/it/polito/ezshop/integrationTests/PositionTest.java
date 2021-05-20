package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.Position;
import it.polito.ezshop.model.ProductType;

public class PositionTest {

    @Before
    @After
    public void cleanDatabase() {

    	 for (ProductType p : DataManager.getInstance().getProductTypes()) {
             DataManager.getInstance().deleteProductType(p);
         }
        for (Position p : DataManager.getInstance().getPositions()) {
            DataManager.getInstance().deletePosition(p);
        }

    }
    
    @Test
    public void testPositionConstructorValid() {
        new Position(36, "1232", 2, new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1"));
    }
    

    @Test
    public void testProductTypeGetters() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
    	Position p = new Position(36, "1232", 2, pr);
        
        assertEquals(Integer.valueOf(36), p.getAisleID());
        assertEquals("1232", p.getRackID());
        assertEquals(Integer.valueOf(2), p.getLevelID());
        assertEquals(pr, p.getAssignedProduct());
     
    }

    @Test
    public void testPositionInvalidAisleId0() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(0,"1232",2,  pr);
        });
    }

    @Test
    public void testPositionInvalidAisleIdNegative() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(-1,"1232",2,  pr);
        });
    }
    
    @Test
    public void testPositionInvalidAisleIdnull() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(null,"1232",2,  pr);
        });
    }
    

    @Test
    public void testPositionInvalidRackIdnull() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(36,null,2, pr);
        });
    }
    @Test
    public void testPositionInvalidRackIdEmpty() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(36,"",2, pr);
        });
    }
    
    @Test
    public void testPositionInvalidLevelId0() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(36,"1232",0, pr);
        });
    }

    @Test
    public void testPositionInvalidLevelIdNegative() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(36,"1232",-2, pr);
        });
    }
    
    @Test
    public void testPositionInvalidLevelIdnull() {
    	ProductType pr = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        assertThrows(IllegalArgumentException.class, () -> {
            new Position(36,"1232",null,  pr);
        });
    }
   
}