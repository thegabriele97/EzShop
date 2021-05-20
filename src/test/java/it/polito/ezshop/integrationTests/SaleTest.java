package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.Customer;
import it.polito.ezshop.model.LoyaltyCard;
import it.polito.ezshop.model.Sale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SaleTest {

    @Before
    @After
    public void cleanDatabase() {

        for (Sale s : DataManager.getInstance().getSales()) {
            DataManager.getInstance().deleteSale(s);
        }

        for (LoyaltyCard lc : DataManager.getInstance().getLoyaltyCards()) {
            DataManager.getInstance().deleteLoyaltyCard(lc);
        }

    }

    @Test
    public void testValidSale() {
        Sale s = new Sale(1, 0.0, null);
        assertEquals(new Integer(1), s.getTicketNumber());
        assertEquals(0.0, s.getDiscountRate(), 0.005);
        assertNull(s.getAttachedLoyaltyCard());
        assertFalse(s.isCommitted());
        assertEquals(0.0, s.getPrice(), 0.005);
        assertEquals(0.0, s.getTotalValue(), 0.005);
    }

    @Test
    public void testSetInvalidTicketNumber() {
        Sale s = new Sale(1, 0.0, null);
        assertThrows(IllegalArgumentException.class, () -> s.setTicketNumber(0));
    }

    @Test
    public void testSetInvalidDiscountRate() {
        Sale s = new Sale(1, 0.0, null);
        assertThrows(IllegalArgumentException.class, () -> s.setDiscountRate(1.1));
    }

    @Test
    public void testSetPrice() {
        Sale s = new Sale(1, 0.0, null);
        assertThrows(IllegalArgumentException.class, () -> s.setPrice(-0.01));
    }

    @Test
    public void testSetAsCommitted() {
        Sale s = new Sale(1, 0.0, null);
        assertFalse(s.isCommitted());
        s.setAsCommitted();
        assertTrue(s.isCommitted());
    }


}