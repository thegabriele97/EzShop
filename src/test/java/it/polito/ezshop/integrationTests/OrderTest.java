 package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.*;

public class OrderTest {
    
    @Before
    @After
    public void cleanDatabase() {

        for (ProductType u : DataManager.getInstance().getProductTypes()) {
            DataManager.getInstance().deleteProductType(u);
        }

        for (Order u : DataManager.getInstance().getOrders()) {
            DataManager.getInstance().deleteOrder(u);
        }

        for (Position u : DataManager.getInstance().getPositions()) {
            DataManager.getInstance().deletePosition(u);
        }

    }

    @Test
    public void testOrderSetPayed() {

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 2.3, 8, p, EOrderStatus.ISSUED);

        o.setAsPayed();
        assertEquals(EOrderStatus.PAYED.toString(), o.getStatus());
    }

    @Test
    public void testOrderSetCompleted() {

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 2.3, 8, p, EOrderStatus.ISSUED);

        o.setAsCompleted();
        assertEquals(EOrderStatus.COMPLETED.toString(), o.getStatus());
    }

}
