package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.Position;
import it.polito.ezshop.model.ProductType;

public class ProductTypeTest {

    @Before
    @After
    public void cleanDatabase() {

        for (ProductType u : DataManager.getInstance().getProductTypes()) {
            DataManager.getInstance().deleteProductType(u);
        }

        for (Position u : DataManager.getInstance().getPositions()) {
            DataManager.getInstance().deletePosition(u);
        }

    }
    
    @Test
    public void testProductTypeConstructorValid() {
        new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
    }

    @Test
    public void testProductTypeGetters() {
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        
        assertEquals(Integer.valueOf(36), p.getId());
        assertEquals("1231231231232", p.getBarCode());
        assertEquals("test", p.getProductDescription());
        assertEquals(Double.valueOf(1.4), p.getPricePerUnit());
        assertEquals(Integer.valueOf(1), p.getQuantity());
        assertEquals(Double.valueOf(0.0), p.getDiscountRate());
        assertEquals("", p.getNote());
        assertEquals("1-a-1", p.getLocation());

        Position expectedPos = DataManager.getInstance()
            .getPositions().stream()
            .filter(l -> l.toString().equals("1-a-1"))
            .findFirst()
            .orElse(null);

        assertEquals(expectedPos, p.getAssignedPosition());
    }

    @Test
    public void testProductTypeConstructorInvalidID0() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(0, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidIDNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(-1, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidBarcode() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "asd123", "test", 1.4, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorNullBarcode() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, null, "test", 1.4, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidEmptyDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "", 1.4, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidNullDescription() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", null, 1.4, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPriceNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", null, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPrice0() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 0.0, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPriceNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", -5.0, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPriceInfinite() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", Double.POSITIVE_INFINITY, 1, 0.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidQuantityNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, -1, 0.0, "", "1-a-1");
        });
    }
    
    @Test
    public void testProductTypeConstructorInvalidDiscountEq1() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, -1, 1.0, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidDiscountGt1() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 1.1, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidDiscountNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, null, "", "1-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorValidPositionLengthGt1() {
        new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "123-conga-456");
    }

    @Test
    public void testProductTypeConstructorValidPositionEmpty() {
        new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "");
    }

    @Test
    public void testProductTypeConstructorInvalidPositionOnlyLetters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "a-b-c");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPositionOnlyNumbers() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-1-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPositionWrongFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1+a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPositionLettersNumbers() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1a-a-1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPositionMissing() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1--1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPositionSpace() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1- -1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPositionRandomAscii() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1+-aa--1");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPosition0s() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "0-a-1");
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-2-1");
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-0");
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "0-0-0");
        });
    }

    @Test
    public void testProductTypeConstructorInvalidPositionAlreadyInUse() {

        new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");

        assertThrows(IllegalArgumentException.class, () -> {
            new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        });

    }

    @Test
    public void testProductTypeQuantityOffsetPositive() {

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");

        assertTrue(p.addQuantityOffset(5));
        assertEquals(Integer.valueOf(6), p.getQuantity());
    }

    @Test
    public void testProductTypeQuantityOffsetNegativeButEnough() {

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 10, 0.0, "", "1-a-1");

        assertTrue(p.addQuantityOffset(-3));
        assertEquals(Integer.valueOf(7), p.getQuantity());
    }

    @Test
    public void testProductTypeQuantityOffsetNegativeButNotEnough() {
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 10, 0.0, "", "1-a-1");
        
        assertFalse(p.addQuantityOffset(-11));
    }

    @Test
    public void testProductTypeQuantityOffsetNegativeTheSameAsQuantity() {
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 10, 0.0, "", "1-a-1");
        
        assertTrue(p.addQuantityOffset(-p.getQuantity()));
        assertEquals(Integer.valueOf(0), p.getQuantity());
    }

}
