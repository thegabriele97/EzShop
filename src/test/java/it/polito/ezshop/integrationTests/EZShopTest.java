package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;

import org.junit.*;
import it.polito.ezshop.model.*;
import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.data.LoginManager;
import it.polito.ezshop.exceptions.*;

public class EZShopTest {
    
    @Before
    @After
    public void cleanDatabase() {

        if (LoginManager.getInstance().isUserLogged()) {
            LoginManager.getInstance().tryLogout();
        }


        for (User u : DataManager.getInstance().getUsers()) {
            DataManager.getInstance().deleteUser(u);
        }

        for (ProductType u : DataManager.getInstance().getProductTypes()) {
            DataManager.getInstance().deleteProductType(u);
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
    public void testRecordBalanceUpdateWithNoLoggedUser() {

        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.recordBalanceUpdate(10.0));
    }

    @Test
    public void testRecordBalanceUpdateWithLoggedCashier() {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.recordBalanceUpdate(10.0));
    }

    @Test
    public void testRecordBalanceUpdateWithRightsAndPositiveValue() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertTrue(ez.recordBalanceUpdate(10.0));

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 10.0)
            .filter(c -> c.getType() == "CREDIT")
            .count();

        assertEquals(1, cnt);
    }

    @Test
    public void testRecordBalanceUpdateWithRightsAdministratorAndPositiveValue() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertTrue(ez.recordBalanceUpdate(10.0));

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 10.0)
            .filter(c -> c.getType() == "CREDIT")
            .count();

        assertEquals(1, cnt);
    }

    @Test
    public void testRecordBalanceUpdateWithRightsAndNegativeValuePositiveBalance() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertTrue(ez.recordBalanceUpdate(10.0));
        assertTrue(ez.recordBalanceUpdate(-5.0));

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 10.0)
            .filter(c -> c.getType() == "CREDIT")
            .count();

        assertEquals(1, cnt);

        cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 5.0)
            .filter(c -> c.getType() == "DEBIT")
            .count();

        assertEquals(1, cnt);
    }

    @Test
    public void testRecordBalanceUpdateWithRightsAndNegativeValueZeroBalance() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertTrue(ez.recordBalanceUpdate(10.0));
        assertTrue(ez.recordBalanceUpdate(-10.0));

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 10.0)
            .filter(c -> c.getType() == "CREDIT")
            .count();

        assertEquals(1, cnt);

        cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 10.0)
            .filter(c -> c.getType() == "DEBIT")
            .count();

        assertEquals(1, cnt);
    }

    @Test
    public void testRecordBalanceUpdateWithRightsAndNegativeBalance() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertTrue(ez.recordBalanceUpdate(10.0));
        assertFalse(ez.recordBalanceUpdate(-10.01));

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 10.0)
            .filter(c -> c.getType() == "CREDIT")
            .count();

        assertEquals(1, cnt);

        cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 10.1)
            .filter(c -> c.getType() == "DEBIT")
            .count();

        assertEquals(0, cnt);
    }

    @Test
    public void testRecordBalanceUpdateWithRightsAndZeroAsInput() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertTrue(ez.recordBalanceUpdate(0.0));

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 0.0)
            .filter(c -> c.getType() == "CREDIT")
            .count();

        assertEquals(1, cnt);
    }

}
