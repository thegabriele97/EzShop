 package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
        Order o = new Order(1, 0.6, 8, p, EOrderStatus.ISSUED);

        o.setAsPayed();
        assertEquals(EOrderStatus.PAYED.toString(), o.getStatus());
    }

    @Test
    public void testOrderSetCompleted() {

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 0.6, 8, p, EOrderStatus.ISSUED);

        o.setAsCompleted();
        assertEquals(EOrderStatus.COMPLETED.toString(), o.getStatus());
    }

    @Test
    public void testSetAndGetBalanceId(){
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 0.6, 8, p, EOrderStatus.ISSUED);
        o.setBalanceId(18);
        assertEquals(Integer.valueOf(18), o.getBalanceId());
    }
    
    @Test
    public void testSetInvalidBalanceId(){
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 0.6, 8, p, EOrderStatus.ISSUED);
        assertThrows(IllegalArgumentException.class, () -> o.setBalanceId(-1));
    }

    @Test
    public void testSetInvaliPricePerUnit(){
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 0.6, 8, p, EOrderStatus.ISSUED);
        assertThrows(IllegalArgumentException.class, () -> o.setPricePerUnit(-2.0));
        assertThrows(IllegalArgumentException.class, () -> o.setPricePerUnit(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> o.setPricePerUnit(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> o.setPricePerUnit(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void testSetStatus(){
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 0.6, 8, p, EOrderStatus.ISSUED);
        o.setStatus("payed");
        assertEquals("PAYED", o.getStatus());
        o.setStatus("completed");
        assertEquals("COMPLETED", o.getStatus());
        o.setStatus("issued");
        assertEquals("ISSUED", o.getStatus());
    }

    @Test
    public void testSetInvalidStatus(){
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 0.6, 8, p, EOrderStatus.ISSUED);
        assertThrows(IllegalArgumentException.class, () -> o.setStatus("inRitardo"));
    }

    @Test
    public void testGettersOfOrder(){
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        Order o = new Order(1, 0.6, 8, p, EOrderStatus.ISSUED);
        assertEquals(Integer.valueOf(1), o.getOrderId());
        assertEquals(0.6, o.getPricePerUnit(), 0.005);
        assertEquals(8, o.getQuantity());
        assertEquals(p, o.getRelatedProduct());
        assertEquals("1231231231232", o.getProductCode());
        assertEquals(4.8, o.getTotalValue(), 0.005);
    }

}
