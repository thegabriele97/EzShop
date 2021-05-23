package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.CReturn;
import it.polito.ezshop.model.Sale;

public class CReturnTest {
    
    @Before
    @After
    public void cleanDatabase() {

        for (CReturn r : DataManager.getInstance().getReturns()){
            DataManager.getInstance().deleteReturn(r);
        }

        for (Sale s : DataManager.getInstance().getSales()) {
            DataManager.getInstance().deleteSale(s);
        }

    }

    @Test
    public void testValidCReturn(){
        Sale s = new Sale(1, 0.0, null);
        CReturn cr = new CReturn(1, s);
        cr.setBalanceId(2);
        assertEquals(cr.getReturnId().intValue(), 1);
        assertEquals(cr.getSaleTransaction(), s);
        assertEquals(cr.getBalanceId().intValue(), 2);
    }

    @Test
    public void testSetAsCommittedCReturn(){
        CReturn cr = new CReturn(1, new Sale(1, 0.0, null));
        cr.setAsCommitted();
        assertTrue(cr.isCommitted());
    }

    @Test
    public void testInvalidReturnIdCReturn(){
        assertThrows(IllegalArgumentException.class, () -> new CReturn(null, new Sale(1, 0.0, null)));
        assertThrows(IllegalArgumentException.class, () -> new CReturn(-10, new Sale(1, 0.0, null)));
    }

    @Test
    public void testInvalidSaleCReturn(){
        assertThrows(IllegalArgumentException.class, () -> new CReturn(1, null));
    }

    @Test
    public void testInvalidBalanceIdCReturn(){
        CReturn cr = new CReturn(1, new Sale(1, 0.0, null));
        assertThrows(IllegalArgumentException.class, () -> cr.setBalanceId(-3));
    }

    @Test
    public void testHashCode(){
        CReturn cr = new CReturn(1, new Sale(1, 0.0, null));
        assertEquals(1, cr.hashCode());
    }

    @Test
    public void testEquals(){
        CReturn cr1 = new CReturn(1, new Sale(1, 0.0, null)), cr2 = new CReturn(1, new Sale(1, 0.0, null));
        assertTrue(cr1.equals(cr2));
    }

    @Test
    public void testGetTotalValue(){
        CReturn cr = new CReturn(1, new Sale(1, 0.0, null));
        ProductType pt = new ProductType(1, "999999999993", "roba", 2.0, 7, 0.34, "no", null);
        cr.getSaleTransaction().addProduct(pt, 3);
        assertEquals(cr.getTotalValue(), 0.0, 0.001);
    }


    
}
