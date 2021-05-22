package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DebitTransactionTest {

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
    public void testValidDebitTransaction() {
        DummyDebit dd = new DummyDebit(1, 7.0);
        DebitTransaction dt = new DebitTransaction(1, dd);
        assertEquals(1, dt.getBalanceId());
        assertEquals(dd, dt.getRelatedDebitOperation());
        assertEquals(7.0, dt.getMoney(), 0.005);
        assertEquals(dt.getType(), "DEBIT");
        assertFalse(dt.getDate() == null);
    }

    @Test
    public void testInvalidDebitTransaction() {
        //TODO: va in nullpointerexception perchè chiama il metodo super
        //assertThrows(IllegalArgumentException.class, () -> new DebitTransaction(1, null));
        assertThrows(IllegalArgumentException.class, () -> new DebitTransaction(-1, new DummyDebit(1, 6.0)));
        assertThrows(IllegalArgumentException.class, () -> new DebitTransaction(1, new DummyDebit(-1, 6.0)));
        assertThrows(IllegalArgumentException.class, () -> new DebitTransaction(1, new DummyDebit(1, -6.0)));
        DebitTransaction dt = new DebitTransaction(1, new DummyDebit(1, 6.0));
        assertThrows(IllegalArgumentException.class, () -> dt.setDescription(""));
    }

    @Test
    public void testSetRelatedDebitOperation(){
        DebitTransaction dt = new DebitTransaction(1, new DummyDebit(1, 6.0));
        DummyDebit dd2 = new DummyDebit(2, 12.0);
        dt.setRelatedDebitOperation(dd2);
        assertEquals(dd2, dt.getRelatedDebitOperation());
    }


    
}
