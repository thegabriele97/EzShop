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

    //payOrderFor
    @Test
    public void testPayOrderForWithNoLoggedUser(){
        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.payOrderFor("1231231231232", 1, 0.4));
    }

    @Test
    public void testPayOrderForWithCashierRights() throws InvalidQuantityException, UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.payOrderFor("1231231231232", 1, 0.4));
    }

    @Test
    public void testPayOrderForWithShopManagerRights() throws InvalidQuantityException, UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        int res = ez.payOrderFor("1231231231232", 1, 0.4);
        assertEquals(-1, res);
    }

    @Test
    public void testPayOrderForWithAdministratorRights() throws InvalidQuantityException, UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        int res = ez.payOrderFor("1231231231232", 1, 0.4);
        assertEquals(-1, res);
    }

    @Test
    public void testPayOrderForWithInvalidBarcode(){
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.payOrderFor("111111111111", 1, 0.4));
    }

    @Test
    public void testPayOrderForWithInvalidQuantity(){
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidQuantityException.class, () -> ez.payOrderFor("1231231231232", 0, 0.4));
    }

    @Test
    public void testPayOrderForWithInvalidPricePerUnit(){
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidPricePerUnitException.class, () -> ez.payOrderFor("1231231231232", 1, 0));
    }

    @Test
    public void testPayOrderForWithNonexistentProduct() throws InvalidQuantityException, UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        ez.recordBalanceUpdate(5);
        int res = ez.payOrderFor("000000000000", 1, 0.4);
        assertEquals(-1, res);
    }

    @Test
    public void testPayOrderForCompletingOrder() throws InvalidQuantityException, UnauthorizedException, InvalidPricePerUnitException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        ez.recordBalanceUpdate(5);
        int res = ez.payOrderFor("1231231231232", 10, 0.4);
        assertEquals(1, res);
    }

    //receiveCreditCardPayment
    @Test
    public void testReceiveCreditCardPaymentWithNoLoggedUser(){
        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.receiveCreditCardPayment(1, "1234567890318"));
    }

    @Test
    public void testReceiveCreditCardPaymentWithCashierRights() throws InvalidCreditCardException, InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertFalse(ez.receiveCreditCardPayment(1, "9254347527611304"));
    }

    @Test
    public void testReceiveCreditCardPaymentWithShopManagerRights() throws InvalidCreditCardException, InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertFalse(ez.receiveCreditCardPayment(1, "9254347527611304"));
    }

    @Test
    public void testReceiveCreditCardPaymentWithAdministratorRights() throws InvalidCreditCardException, InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertFalse(ez.receiveCreditCardPayment(1, "9254347527611304"));
    }


    @Test
    public void testReceiveCreditCardPaymentWithNullTransactionID() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.receiveCreditCardPayment(null, "9254347527611304"));
    }

    @Test
    public void testReceiveCreditCardPaymentWithInvalidTransactionID() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.receiveCreditCardPayment(0, "9254347527611304"));
    }

    @Test
    public void testReceiveCreditCardPaymentWithEmptyCreditCard() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCreditCardException.class, () -> ez.receiveCreditCardPayment(1, ""));
    }

    @Test
    public void testReceiveCreditCardPaymentWithNullCreditCard() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCreditCardException.class, () -> ez.receiveCreditCardPayment(1, null));
    }

    @Test
    public void testReceiveCreditCardPaymentWithInvalidCreditCard() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCreditCardException.class, () -> ez.receiveCreditCardPayment(1, "1111111111111"));
    }

    @Test
    public void testReceiveCreditCardPaymentWithNonexistentSale() throws InvalidCreditCardException, InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        s.addProduct(p,2);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.receiveCreditCardPayment(2, "9254347527611304"));
    }

    @Test
    public void testReceiveCreditCardPaymentWithNotEnoughMoneyOnTheCreditCard() throws InvalidCreditCardException, InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 11.6, 3, 0.0, "", "1-a-1");
        s.addProduct(p,2);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.receiveCreditCardPayment(1, "9254347527611304"));
    }

    @Test
    public void testReceiveCreditCardPaymentWithNotRegisteredCreditCard() throws InvalidCreditCardException, InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertFalse(ez.receiveCreditCardPayment(1, "1234567890318"));
    }

    @Test
    public void testReceiveCreditCardPaymentSuccessfully() throws InvalidCreditCardException, InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.5, 3, 0.0, "", "1-a-1");
        s.addProduct(p,1);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        ez.endSaleTransaction(1);
        assertTrue(ez.receiveCreditCardPayment(1, "4485370086510891"));
    }

}
