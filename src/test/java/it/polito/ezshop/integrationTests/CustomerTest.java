package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.Customer;
import it.polito.ezshop.model.LoyaltyCard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {

    @Before
    @After
    public void cleanDatabase() {

        for (Customer c : DataManager.getInstance().getCustomers()) {
            DataManager.getInstance().deleteCustomer(c);
        }

        for (LoyaltyCard lc : DataManager.getInstance().getLoyaltyCards()) {
            DataManager.getInstance().deleteLoyaltyCard(lc);
        }

    }

    @Test
    public void testValidCustomer() {
        Customer c = new Customer(1, "we", null);
        assertEquals(new Integer(1), c.getId());
        assertEquals("we", c.getCustomerName());
        assertEquals("", c.getCustomerCard());

    }

    @Test
    public void testNullCustomerName() {
        assertThrows(IllegalArgumentException.class, () -> new Customer(1, null, null));
    }

    @Test
    public void testSetCustomerCardWithEmptyString() {
        Customer c = new Customer(1, "we", new LoyaltyCard("0123456789", 0, null));
        c.setCustomerCard("");
        assertEquals("", c.getCustomerCard());
    }

    @Test
    public void testSetCustomerCardWithInvalidLength() {
        Customer c = new Customer(1, "we", new LoyaltyCard("0123456789", 0, null));
        assertThrows(IllegalArgumentException.class, () ->  c.setCustomerCard("012345678"));
    }

    @Test
    public void testSetCustomerCardNotExistent() {
        Customer c = new Customer(1, "we", new LoyaltyCard("0123456789", 0, null));
        assertThrows(IllegalArgumentException.class, () ->  c.setCustomerCard("0012345678"));
    }

    @Test
    public void testSetValidCustomerCard() {
        Customer c = new Customer(1, "we", new LoyaltyCard("0123456789", 0, null));
        DataManager.getInstance().insertLoyaltyCard(c.getLoyaltyCard());
        LoyaltyCard lc = new LoyaltyCard("9876543210", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lc);
        c.setCustomerCard(lc.getID());
        assertEquals(lc.getID(), c.getCustomerCard());
    }

    @Test
    public void testSetPointsWithNullCard() {
        Customer c = new Customer(1, "we", null);
        assertThrows(IllegalArgumentException.class, () -> c.setPoints(1));
    }

    @Test
    public void testSetIllegalNegativePoints() {
        Customer c = new Customer(1, "we", new LoyaltyCard("0123456789", 0, null));
        c.setPoints(-1);
        assertEquals(new Integer(0), c.getPoints());
    }

    @Test
    public void testSetPoints() {
        Customer c = new Customer(1, "we", new LoyaltyCard("0123456789", 0, null));
        c.setPoints(1);
        assertEquals(new Integer(1), c.getPoints());
    }

}