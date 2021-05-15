package it.polito.ezshop.unitTests;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class BBDataManagerTest {

    @Before
    @After
    public void cleanDatabase() {
        
        for (User u : DataManager.getInstance().getUsers()) {
            DataManager.getInstance().deleteUser(u);
        }

        for (ProductType u : DataManager.getInstance().getProductTypes()) {
            DataManager.getInstance().deleteProductType(u);
        }

        for (Position u : DataManager.getInstance().getPositions()) {
            DataManager.getInstance().deletePosition(u);
        }

        for (Position u : DataManager.getInstance().getPositions()) {
            DataManager.getInstance().deletePosition(u);
        }

        for (Order u : DataManager.getInstance().getOrders()) {
            DataManager.getInstance().deleteOrder(u);
        }

        for (Customer u : DataManager.getInstance().getCustomers()) {
            DataManager.getInstance().deleteCustomer(u);
        }

        for (LoyaltyCard u : DataManager.getInstance().getLoyaltyCards()) {
            DataManager.getInstance().deleteLoyaltyCard(u);
        }

        for (Sale u : DataManager.getInstance().getSales()) {
            DataManager.getInstance().deleteSale(u);
        }

        for (CReturn u : DataManager.getInstance().getReturns()) {
            DataManager.getInstance().deleteReturn(u);
        }

        for (DummyCredit u : DataManager.getInstance().getDummyCredits()) {
            DataManager.getInstance().deleteDummyCredit(u);
        }

        for (DummyDebit u : DataManager.getInstance().getDummyDebits()) {
            DataManager.getInstance().deleteDummyDebit(u);
        }

        for (BalanceTransaction u : DataManager.getInstance().getBalanceTransactions()) {
            DataManager.getInstance().deleteBalanceTransaction(u);
        }
        
    }

    @Test
    public void testDeleteNotExistingUser() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteUser(u));
    }

    @Test
    public void testDeleteNullUser() {
        assertFalse("User null, false expected", DataManager.getInstance().deleteUser(null));
    }

    @Test
    public void testDeleteExistingUser() {

        User u = new User(1, "we", "mbare", "Administrator");
        if (!DataManager.getInstance().insertUser(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getId() + " : " + u.getUsername() + "; true expected", DataManager.getInstance().deleteUser(u));
        assertFalse(DataManager.getInstance().getUsers().contains(u));
    }

    @Test
    public void testDeleteNotExistingProductType() {
        it.polito.ezshop.model.ProductType u = new it.polito.ezshop.model.ProductType(1, "1231231231232", "", 0.0, 1, 0.0, "", "");
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteProductType(u));
    }

    @Test
    public void testDeleteNullProductType() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteProductType(null));
    }

    @Test
    public void testDeleteExistingProductType() {

        it.polito.ezshop.model.ProductType u = new it.polito.ezshop.model.ProductType(1, "1231231231232", "", 0.0, 1, 0.0, "", "");
        if (!DataManager.getInstance().insertProductType(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().deleteProductType(u));
        assertFalse(DataManager.getInstance().getProductTypes().contains(u));
    }

    @Test
    public void testDeleteNotExistingPosition() {
        Position u = new Position(3, "a", 2, null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deletePosition(u));
    }

    @Test
    public void testDeleteNullPosition() {
        assertFalse("Item null, false expected", DataManager.getInstance().deletePosition(null));
    }

    @Test
    public void testDeleteExistingPosition() {

        Position u = new Position(3, "a", 2, null);
        if (!DataManager.getInstance().insertPosition(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.toString() + "; true expected", DataManager.getInstance().deletePosition(u));
        assertFalse(DataManager.getInstance().getPositions().contains(u));
    }

    @Test
    public void testDeleteNotExistingOrder() {
        it.polito.ezshop.model.Order u = new it.polito.ezshop.model.Order(1, 1.0, 1, new ProductType(1, "1231231232", "", 0.1, 1, 0.0, "", null), EOrderStatus.ISSUED);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteOrder(u));
    }

    @Test
    public void testDeleteNullOrder() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteOrder(null));
    }

    @Test
    public void testDeleteExistingOrder() {

        it.polito.ezshop.model.Order u = new it.polito.ezshop.model.Order(1, 1.0, 1, new ProductType(1, "1231231232", "", 0.1, 1, 0.0, "", null), EOrderStatus.ISSUED);
        if (!DataManager.getInstance().insertOrder(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getOrderId() + "; true expected", DataManager.getInstance().deleteOrder(u));
        assertFalse(DataManager.getInstance().getOrders().contains(u));
    }

    @Test
    public void testDeleteNotExistingCustomer() {
        it.polito.ezshop.model.Customer u = new it.polito.ezshop.model.Customer(1, "", null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteCustomer(u));
    }

    @Test
    public void testDeleteNullCustomer() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteCustomer(null));
    }

    @Test
    public void testDeleteExistingCustomer() {

        it.polito.ezshop.model.Customer u = new it.polito.ezshop.model.Customer(1, "", null);
        if (!DataManager.getInstance().insertCustomer(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().deleteCustomer(u));
        assertFalse(DataManager.getInstance().getCustomers().contains(u));
    }

    @Test
    public void testDeleteNotExistingLoyaltyCard() {
        LoyaltyCard u = new LoyaltyCard("0001", 0, null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteLoyaltyCard(u));
    }

    @Test
    public void testDeleteNullLoyaltyCard() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteLoyaltyCard(null));
    }

    @Test
    public void testDeleteExistingLoyaltyCard() {

        LoyaltyCard u = new LoyaltyCard("0001", 0, null);
        if (!DataManager.getInstance().insertLoyaltyCard(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getID() + "; true expected", DataManager.getInstance().deleteLoyaltyCard(u));
        assertFalse(DataManager.getInstance().getLoyaltyCards().contains(u));
    }

    @Test
    public void testDeleteNotExistingSale() {
        it.polito.ezshop.model.Sale u = new it.polito.ezshop.model.Sale(1, 0.0, null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteSale(u));
    }

    @Test
    public void testDeleteNullSale() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteSale(null));
    }

    @Test
    public void testDeleteExistingSale() {

        it.polito.ezshop.model.Sale u = new it.polito.ezshop.model.Sale(1, 0.0, null);
        if (!DataManager.getInstance().insertSale(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getTicketNumber() + "; true expected", DataManager.getInstance().deleteSale(u));
        assertFalse(DataManager.getInstance().getSales().contains(u));
    }

    @Test
    public void testDeleteNotExistingReturn() {
        CReturn u = new CReturn(1, null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteReturn(u));
    }

    @Test
    public void testDeleteNullReturn() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteReturn(null));
    }

    @Test
    public void testDeleteExistingReturn() {

        CReturn u = new CReturn(1, null);
        if (!DataManager.getInstance().insertReturn(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getReturnid() + "; true expected", DataManager.getInstance().deleteReturn(u));
        assertFalse(DataManager.getInstance().getReturns().contains(u));
    }

    @Test
    public void testDeleteNotExistingDummyCredit() {
        DummyCredit u = new DummyCredit(1, 23.9);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteDummyCredit(u));
    }

    @Test
    public void testDeleteNullDummyCredit() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteDummyCredit(null));
    }

    @Test
    public void testDeleteExistingDummyCredit() {

        DummyCredit u = new DummyCredit(1, 23.9);
        if (!DataManager.getInstance().insertDummyCredit(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().deleteDummyCredit(u));
        assertFalse(DataManager.getInstance().getDummyCredits().contains(u));
    }

    @Test
    public void testDeleteNotExistingDummyDebit() {
        DummyDebit u = new DummyDebit(1, 23.9);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteDummyDebit(u));
    }

    @Test
    public void testDeleteNullDummyDebit() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteDummyDebit(null));
    }

    @Test
    public void testDeleteExistingDummyDebit() {

        DummyDebit u = new DummyDebit(1, 23.9);
        if (!DataManager.getInstance().insertDummyDebit(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().deleteDummyDebit(u));
        assertFalse(DataManager.getInstance().getDummyDebits().contains(u));
    }

    @Test
    public void testDeleteNotExistingBalanceTransaction() {
        BalanceTransaction u = new CreditTransaction(1, new DummyCredit(2, 3.0));
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteBalanceTransaction(u));
    }

    @Test
    public void testDeleteNullBalanceTransaction() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteBalanceTransaction(null));
    }

    @Test
    public void testDeleteExistingBalanceTransaction() {

        BalanceTransaction u = new CreditTransaction(1, new DummyCredit(2, 3.0));
        if (!DataManager.getInstance().insertBalanceTransaction(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getBalanceId() + "; true expected", DataManager.getInstance().deleteBalanceTransaction(u));
        assertFalse(DataManager.getInstance().getBalanceTransactions().contains(u));
    }

}
