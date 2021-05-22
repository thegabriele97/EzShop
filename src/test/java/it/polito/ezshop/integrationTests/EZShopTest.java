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

    @Test 
    public void testReset(){
        User u = new User(1, "TenaciousD", "stepnyevadeilfisco", "ShopManager");
        DummyDebit dd = new DummyDebit(1, 7.0);
        DebitTransaction dt = new DebitTransaction(1, dd);
        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        Position p = new Position(1, "roba", 2, pt);
        Order o = new Order(1, 6.4, 10, pt, EOrderStatus.ISSUED);
        Customer c = new Customer(1, "Michele Misseri", null);
        LoyaltyCard lt = new LoyaltyCard("4547483383", 3, c);
        Sale s = new Sale(2, 0.2, lt);
        CReturn cr = new CReturn(1, s);
        DummyCredit dc = new DummyCredit(2, 11);
        CreditTransaction ct = new CreditTransaction(2, dc);

        DataManager.getInstance().insertUser(u);
        DataManager.getInstance().insertDummyDebit(dd);
        DataManager.getInstance().insertBalanceTransaction(dt);
        DataManager.getInstance().insertProductType(pt);
        DataManager.getInstance().insertPosition(p);
        DataManager.getInstance().insertOrder(o);
        DataManager.getInstance().insertCustomer(c);
        DataManager.getInstance().insertLoyaltyCard(lt);
        DataManager.getInstance().insertSale(s);
        DataManager.getInstance().insertReturn(cr);
        DataManager.getInstance().insertDummyCredit(dc);
        DataManager.getInstance().insertBalanceTransaction(ct);

        EZShopInterface ez = new EZShop();
        ez.reset();

        assertEquals(DataManager.getInstance().getBalanceTransactions().size(), 0);
        assertEquals(DataManager.getInstance().getUsers().size(), 0);
        assertEquals(DataManager.getInstance().getDummyDebits().size(), 0);
        assertEquals(DataManager.getInstance().getProductTypes().size(), 0);
        assertEquals(DataManager.getInstance().getPositions().size(), 0);
        assertEquals(DataManager.getInstance().getOrders().size(), 0);
        //assertEquals(DataManager.getInstance().getCustomers().size(), 0);
        //assertEquals(DataManager.getInstance().getLoyaltyCards().size(), 0);
        assertEquals(DataManager.getInstance().getReturns().size(), 0);
        assertEquals(DataManager.getInstance().getDummyCredits().size(), 0);
        assertEquals(DataManager.getInstance().getSales().size(), 0);
    }

}
