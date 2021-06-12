package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.DataManager;

import static org.junit.Assert.*;
import org.junit.*;

import it.polito.ezshop.model.*;

public class ProductTest {

    @Before
    @After
    public void cleanDatabase() {

        for (Product u : DataManager.getInstance().getProducts()) {
            DataManager.getInstance().deleteProduct(u);
        }

        for (ProductType u : DataManager.getInstance().getProductTypes()) {
            DataManager.getInstance().deleteProductType(u);
        }

        for (Position u : DataManager.getInstance().getPositions()) {
            DataManager.getInstance().deletePosition(u);
        }

    }

    @Test
    public void testProductConstructorWithInvalidArgs() {

        ProductType pt = new ProductType(1, "1231231231232", "des", 0.4, 2, 0.0, "not", null);
        DataManager.getInstance().insertProductType(pt);

        Position p = new Position(1, "a", 3, null);
        DataManager.getInstance().insertPosition(p);

        pt.assingToPosition(p);
        DataManager.getInstance().updateProductType(pt);

        assertThrows(IllegalArgumentException.class, () -> new Product("00000000001", pt));
        assertThrows(IllegalArgumentException.class, () -> new Product("000000000s01", pt));
        assertThrows(IllegalArgumentException.class, () -> new Product("000000000s01", null));
    }

    @Test
    public void testProductGetters() {

        ProductType pt = new ProductType(1, "1231231231232", "des", 0.4, 2, 0.0, "not", null);
        DataManager.getInstance().insertProductType(pt);

        Position pos = new Position(1, "a", 3, null);
        DataManager.getInstance().insertPosition(pos);

        pt.assingToPosition(pos);
        DataManager.getInstance().updateProductType(pt);

        Product p = new Product("000000000001", pt);

        assertEquals("000000000001", p.getRFID());
        assertEquals(pt, p.getRelativeProductType());
        assertEquals(true, p.isAvailable());

        p.setAvailable(false);
        assertEquals(false, p.isAvailable());
    }

}
