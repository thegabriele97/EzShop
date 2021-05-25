package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CreditTransactionTest {

    @Before
    @After
    public void cleanDatabase() {

        for (BalanceTransaction b : DataManager.getInstance().getBalanceTransactions()) {
            DataManager.getInstance().deleteBalanceTransaction(b);
        }

        for (Sale s : DataManager.getInstance().getSales()) {
            DataManager.getInstance().deleteSale(s);
        }

        for (LoyaltyCard lc : DataManager.getInstance().getLoyaltyCards()) {
            DataManager.getInstance().deleteLoyaltyCard(lc);
        }

        for(ProductType p : DataManager.getInstance().getProductTypes()) {
            DataManager.getInstance().deleteProductType(p);
        }

        for (Position p : DataManager.getInstance().getPositions()) {
            DataManager.getInstance().deletePosition(p);
        }

    }

    @Test
    public void testValidCreditTransaction() {
        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        s.addProduct(p, 1);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertEquals(1, ct.getBalanceId());
        assertEquals(s, ct.getRelatedCreditOperation());
        assertEquals(1.4, ct.getMoney(), 0.005);
    }

    @Test
    public void testInvalidCreditTransaction() {
        Sale s = new Sale(1, 0.0, null);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertThrows(IllegalArgumentException.class, () -> ct.setRelatedCreditOperation(null));
    }

    @Test
    public void testInvalidBalanceId() {
        Sale s = new Sale(1, 0.0, null);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertThrows(IllegalArgumentException.class, () -> ct.setBalanceId(0));
    }

    @Test
    public void testSetAndGetDescription() {
        Sale s = new Sale(1, 0.0, null);
        CreditTransaction ct = new CreditTransaction(1, s);
        ct.setDescription("test");
        assertEquals("test", ct.getDescription());
    }

    @Test
    public void testSetInvalidDescription() {
        Sale s = new Sale(1, 0.0, null);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertThrows(IllegalArgumentException.class, () -> ct.setDescription(""));
    }

    @Test
    public void testSetInvalidValue() {
        Sale s = new Sale(1, 0.0, null);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertThrows(IllegalArgumentException.class, () -> ct.setValue(-0.1));
    }

    @Test
    public void testGetDate() {
        Sale s = new Sale(1, 0.0, null);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertEquals(LocalDate.now(), ct.getDate());
    }

    @Test
    public void testSetMoney() {
        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        s.addProduct(p, 1);
        CreditTransaction ct = new CreditTransaction(1, s);
        ct.setMoney(1.0);
        assertEquals(1.0, ct.getMoney(), 0.005);
    }

    @Test
    public void testSetInvalidMoney() {
        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        s.addProduct(p, 1);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertThrows(IllegalArgumentException.class, () -> ct.setMoney(-0.1));
        assertThrows(IllegalArgumentException.class, () -> ct.setMoney(Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> ct.setMoney(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> ct.setMoney(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void testGetType() {
        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        s.addProduct(p, 1);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertEquals("CREDIT", ct.getType());
    }

    @Test
    public void testSetInvalidType() {
        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        s.addProduct(p, 1);
        CreditTransaction ct = new CreditTransaction(1, s);
        assertThrows(IllegalArgumentException.class, () -> ct.setType("test"));
    }

}