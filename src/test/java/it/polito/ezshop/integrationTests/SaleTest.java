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

    }

    @Test
    public void testGetTicketNumber() {
    }

    @Test
    public void testSetTicketNumber() {
    }

    @Test
    public void testGetEntries() {
    }

    @Test
    public void testSetEntries() {
    }

    @Test
    public void testGetDiscountRate() {
    }

    @Test
    public void testSetDiscountRate() {
    }

    @Test
    public void testGetPrice() {
    }

    @Test
    public void testSetPrice() {
    }

    @Test
    public void testGetDiscountRateForProductGroup() {
    }

    @Test
    public void testSetAsCommitted() {
    }

    @Test
    public void testGetTotalValue() {
    }
}