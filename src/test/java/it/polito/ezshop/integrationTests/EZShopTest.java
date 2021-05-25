package it.polito.ezshop.integrationTests;

import static org.junit.Assert.*;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.stream.Collectors.*;

import org.junit.*;
import it.polito.ezshop.model.*;
import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.data.LoginManager;
import it.polito.ezshop.exceptions.*;

public class EZShopTest {
    
    private List<String> lines = new ArrayList<>();

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

    @Before 
    public void saveCreditCardsStatus() throws IOException {
        Stream<String> stream = Files.lines(Paths.get("src/main/java/it/polito/ezshop/utils/CreditCards.txt"));
        this.lines = stream.collect(toList());
        stream.close();
    }

    @After
    public void restoreCreditCardsStatus() throws IOException {
        FileWriter writer = new FileWriter("src/main/java/it/polito/ezshop/utils/CreditCards.txt");

        boolean write_carriageret = false;
        for (String line : lines) {
            
            if (write_carriageret) {
                writer.write("\n");
            }

            writer.write(line);
            write_carriageret = true;
        }

        writer.close();
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
    public void testReturnCreditCardPayamentWithNoLoggedUser() {

        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.returnCreditCardPayment(1, "abcdef"));
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndNoValidCreditCard() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCreditCardException.class, () -> ez.returnCreditCardPayment(1, "19"));
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndEmptyCreditCard() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCreditCardException.class, () -> ez.returnCreditCardPayment(1, ""));
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndNullCreditCard() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCreditCardException.class, () -> ez.returnCreditCardPayment(1, null));
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndId0() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.returnCreditCardPayment(0, "18"));
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndIdNegative() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.returnCreditCardPayment(-1, "18"));
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndNoRegisteredCard() throws UnauthorizedException, InvalidTransactionIdException, InvalidPaymentException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException, InvalidProductIdException, InvalidLocationException, InvalidCreditCardException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        Integer saleTrans = ez.startSaleTransaction();
        ez.addProductToSale(saleTrans, "1231231231232", 1);
        
        ez.endSaleTransaction(saleTrans);
        ez.receiveCashPayment(saleTrans, ez.getSaleTransaction(saleTrans).getPrice());
        Integer returnTrans = ez.startReturnTransaction(saleTrans);
        ez.endReturnTransaction(returnTrans, true);

        assertEquals(-1, ez.returnCreditCardPayment(returnTrans, "18"), 0.01);
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndNoEndedTransaction() throws UnauthorizedException, InvalidTransactionIdException, InvalidPaymentException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException, InvalidProductIdException, InvalidLocationException, InvalidCreditCardException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        Integer saleTrans = ez.startSaleTransaction();
        ez.addProductToSale(saleTrans, "1231231231232", 1);
        
        ez.endSaleTransaction(saleTrans);
        ez.receiveCashPayment(saleTrans, ez.getSaleTransaction(saleTrans).getPrice());
        
        Integer returnTrans = ez.startReturnTransaction(saleTrans);

        assertEquals(-1, ez.returnCreditCardPayment(returnTrans, "9254347527611304"), 0.01);
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndInvalidTransaction() throws UnauthorizedException, InvalidTransactionIdException, InvalidPaymentException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException, InvalidProductIdException, InvalidLocationException, InvalidCreditCardException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertEquals(-1, ez.returnCreditCardPayment(123, "9254347527611304"), 0.01);
    }

    @Test
    public void testReturnCreditCardPayamentWithRightsAndEverythingCorrect() throws UnauthorizedException, InvalidTransactionIdException, InvalidPaymentException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException, InvalidProductIdException, InvalidLocationException, InvalidCreditCardException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        Integer saleTrans = ez.startSaleTransaction();
        ez.addProductToSale(saleTrans, "1231231231232", 3);
        
        ez.endSaleTransaction(saleTrans);
        ez.receiveCashPayment(saleTrans, ez.getSaleTransaction(saleTrans).getPrice());
        
        Integer returnTrans = ez.startReturnTransaction(saleTrans);
        ez.returnProduct(returnTrans, "1231231231232", 2);
        ez.endReturnTransaction(returnTrans, true);

        assertEquals(4.0, ez.returnCreditCardPayment(returnTrans, "9254347527611304"), 0.01);

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 4.0)
            .filter(c -> c.getType() == "DEBIT")
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

    @Test
    public void testRecordOrderArrivalWithNoLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.recordOrderArrival(1));
    }

    @Test
    public void testRecordOrderArrivalWithNoRights() {
        
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.recordOrderArrival(1));
    }

    @Test
    public void testRecordOrderArrivalWithRightsAndId0() {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();
        assertThrows(InvalidOrderIdException.class, () -> ez.recordOrderArrival(0));
    }

    @Test
    public void testRecordOrderArrivalWithRightsAndIdNegative() {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();
        assertThrows(InvalidOrderIdException.class, () -> ez.recordOrderArrival(-1));
    }

    @Test
    public void testRecordOrderArrivalWithRightsAndIdNull() {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();
        assertThrows(InvalidOrderIdException.class, () -> ez.recordOrderArrival(null));
    }

    @Test
    public void testRecordOrderArrivalWithRightsAndNoProductLocationAssigned() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();

        ez.createProductType("test", "1231231231232", 2.0, "");
        Integer orderId = ez.issueOrder("1231231231232", 5, 4.0);

        assertThrows(InvalidLocationException.class, () -> ez.recordOrderArrival(orderId));
    }

    @Test
    public void testRecordOrderArrivalWithRightsAndOrderNotExisting() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException, InvalidOrderIdException, InvalidLocationException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();

        assertFalse(ez.recordOrderArrival(1));
    }

    @Test
    public void testRecordOrderArrivalWithRightsAndInvalidOrderStatus() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException, InvalidProductIdException, InvalidLocationException, InvalidOrderIdException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");

        Integer orderId = ez.issueOrder("1231231231232", 5, 4.0);

        assertFalse(ez.recordOrderArrival(orderId));
    }

    @Test
    public void testRecordOrderArrivalWithRightsAndRightStatus() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException, InvalidProductIdException, InvalidLocationException, InvalidOrderIdException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);  
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");

        ez.recordBalanceUpdate(100.0);

        Integer orderId = ez.issueOrder("1231231231232", 5, 4.0);
        ez.payOrder(orderId);

        assertTrue(ez.recordOrderArrival(orderId));
        assertEquals(Integer.valueOf(5), ez.getProductTypeByBarCode("1231231231232").getQuantity());
    }

    @Test
    public void testRecordOrderArrivalWithRightsAndRightStatusMultipleCalls() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidQuantityException, InvalidProductIdException, InvalidLocationException, InvalidOrderIdException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");

        ez.recordBalanceUpdate(100.0);

        Integer orderId = ez.issueOrder("1231231231232", 5, 4.0);
        ez.payOrder(orderId);

        assertTrue(ez.recordOrderArrival(orderId));
        assertEquals(Integer.valueOf(5), ez.getProductTypeByBarCode("1231231231232").getQuantity());

        assertFalse(ez.recordOrderArrival(orderId));
        assertEquals(Integer.valueOf(5), ez.getProductTypeByBarCode("1231231231232").getQuantity());
    }

    @Test
    public void testapplyDiscountRateToProductWithNoLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.applyDiscountRateToProduct(1, "1231231231232", 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndInvalidDiscountRateLess0() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidDiscountRateException.class, () -> ez.applyDiscountRateToProduct(1, "1231231231232", -0.1));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndInvalidDiscountRateEqual1() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidDiscountRateException.class, () -> ez.applyDiscountRateToProduct(1, "1231231231232", 1.0));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndInvalidDiscountRateGreater1() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidDiscountRateException.class, () -> ez.applyDiscountRateToProduct(1, "1231231231232", 1.1));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndNullProductCode() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.applyDiscountRateToProduct(1, null, 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndEmptyProductCode() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.applyDiscountRateToProduct(1, "", 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndInvalidProductCode() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.applyDiscountRateToProduct(1, "1231231231231", 0.5));
    }

    
    @Test
    public void testapplyDiscountRateToProductWithRightsAndNullTransactionId() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.applyDiscountRateToProduct(null, "1231231231232", 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndTransactionIdEqual0() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.applyDiscountRateToProduct(0, "1231231231232", 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndTransactionIdNegative() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.applyDiscountRateToProduct(-1, "1231231231232", 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndNotRegisteredTransactionId() throws UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidDiscountRateException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        ez.createProductType("test", "1231231231232", 2.0, "");

        assertFalse(ez.applyDiscountRateToProduct(1, "1231231231232", 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndValidTransactionInvalidBarcode() throws UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidDiscountRateException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer saleId = ez.startSaleTransaction();

        assertFalse(ez.applyDiscountRateToProduct(saleId, "1231231231232", 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndValidTransactionButWrongState() throws UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 2);

        Integer saleId = ez.startSaleTransaction();
        ez.addProductToSale(saleId, "1231231231232", 1);
        ez.endSaleTransaction(saleId);

        assertFalse(ez.applyDiscountRateToProduct(saleId, "1231231231232", 0.5));
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndValidSingleProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 2);

        Integer saleId = ez.startSaleTransaction();
        ez.addProductToSale(saleId, "1231231231232", 1);

        assertTrue(ez.applyDiscountRateToProduct(saleId, "1231231231232", 0.5));
        assertEquals(1.0, ez.getSaleTransaction(saleId).getPrice(), 0.01);
    }

    @Test
    public void testapplyDiscountRateToProductWithRightsAndValidMultipleProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidDiscountRateException, InvalidProductIdException, InvalidLocationException, InvalidQuantityException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 2);

        prodId = ez.createProductType("test", "0123456789012", 3.0, "");
        ez.updatePosition(prodId, "1-a-2");
        ez.updateQuantity(prodId, 3);

        Integer saleId = ez.startSaleTransaction();
        ez.addProductToSale(saleId, "1231231231232", 1);
        ez.addProductToSale(saleId, "0123456789012", 1);

        assertTrue(ez.applyDiscountRateToProduct(saleId, "1231231231232", 0.5));
        assertEquals(1.0 + 3.0, ez.getSaleTransaction(saleId).getPrice(), 0.01);
    }

    //updateProduct
    @Test
    public void testUpdateProductWithNoLoggedUser(){
        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.updateProduct(36, "test2", "0000000000000", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithCashierRights()  {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.updateProduct(36, "test2", "0000000000000", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithShopManagerRights() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductIdException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertTrue( ez.updateProduct(36, "test2", "0000000000000", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithAdministratorRights() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductIdException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.updateProduct(36, "test2", "0000000000000", 1.1, ""));
    }


    @Test
    public void testUpdateProductWithNullProductId(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductIdException.class, () -> ez.updateProduct(null, "test2", "0000000000000", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithInvalidProductId(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductIdException.class, () -> ez.updateProduct(0, "test2", "0000000000000", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithNullProductDescription(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductDescriptionException.class, () -> ez.updateProduct(36, null, "0000000000000", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithEmptyProductDescription(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductDescriptionException.class, () -> ez.updateProduct(36, "", "0000000000000", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithNullProductCode(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.updateProduct(36, "test2", null, 1.1, ""));
    }

    @Test
    public void testUpdateProductWithEmptyProductCode(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.updateProduct(36, "test2", "", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithNotNumericProductCode(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.updateProduct(36, "test2", "asdfghjklqwerty", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithInvalidProductCode(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.updateProduct(36, "test2", "1111111111111", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithInvalidPricePerUnit(){

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidPricePerUnitException.class, () -> ez.updateProduct(36, "test2", "0000000000000", 0, ""));
    }

    @Test
    public void testUpdateNotFoundedProduct() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductIdException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.updateProduct(1, "test2", "0000000000000", 1.1, ""));
    }

    @Test
    public void testUpdateProductWithAlreadyUsedBarcode() throws UnauthorizedException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductIdException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        ProductType p2 = new ProductType(1, "0000000000000", "test", 1.4, 1, 0.0, "", "2-a-1");
        DataManager.getInstance().insertProductType(p2);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.updateProduct(36, "test2", "0000000000000", 1.1, ""));
    }

    @Test
    public void testReturnCashPaymentWithNoLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.returnCashPayment(1));
    }

    @Test
    public void testReturnCashPaymentWithRightsAndTransIdEqual0() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.returnCashPayment(0));
    }

    @Test
    public void testReturnCashPaymentWithRightsAndTransIdNegative() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.returnCashPayment(-1));
    }

    @Test
    public void testReturnCashPaymentWithRightsAndNoCompletedTrans() throws InvalidProductIdException, InvalidLocationException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidPaymentException {
        
        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        Integer saleTrans = ez.startSaleTransaction();
        ez.addProductToSale(saleTrans, "1231231231232", 1);
        
        ez.endSaleTransaction(saleTrans);
        ez.receiveCashPayment(saleTrans, ez.getSaleTransaction(saleTrans).getPrice());
        
        Integer returnTrans = ez.startReturnTransaction(saleTrans);

        assertEquals(-1, ez.returnCashPayment(returnTrans), 0.01);
    }

    @Test
    public void testReturnCashPaymentWithRightsAndNoExistingTrans() throws InvalidProductIdException, InvalidLocationException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidPaymentException {
        
        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        Integer saleTrans = ez.startSaleTransaction();
        ez.addProductToSale(saleTrans, "1231231231232", 1);
        
        ez.endSaleTransaction(saleTrans);
        ez.receiveCashPayment(saleTrans, ez.getSaleTransaction(saleTrans).getPrice());

        assertEquals(-1, ez.returnCashPayment(1), 0.01);
    }

    @Test
    public void testReturnCashPaymentWithRightsAndValid() throws InvalidProductIdException, InvalidLocationException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidPaymentException, InvalidDiscountRateException {
        
        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        prodId = ez.createProductType("test", "0123456789012", 3.0, "");
        ez.updatePosition(prodId, "1-a-2");
        ez.updateQuantity(prodId, 3);

        Integer saleTrans = ez.startSaleTransaction();
        ez.addProductToSale(saleTrans, "1231231231232", 1);
        ez.applyDiscountRateToProduct(saleTrans, "1231231231232", 0.5);

        ez.addProductToSale(saleTrans, "0123456789012", 2);
        ez.applyDiscountRateToProduct(saleTrans, "0123456789012", 0.4);

        ez.endSaleTransaction(saleTrans);
        ez.receiveCashPayment(saleTrans, ez.getSaleTransaction(saleTrans).getPrice());

        Integer returnId = ez.startReturnTransaction(saleTrans);
        ez.returnProduct(returnId, "1231231231232", 1);
        ez.endReturnTransaction(returnId, true);

        assertEquals(1.0, ez.returnCashPayment(returnId), 0.01);

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 1.0)
            .filter(c -> c.getType() == "DEBIT")
            .count();

        assertEquals(1, cnt);
    }

    @Test
    public void testReturnCashPaymentWithRightsAndValidMultipleProducts() throws InvalidProductIdException, InvalidLocationException, UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidTransactionIdException, InvalidQuantityException, InvalidPaymentException, InvalidDiscountRateException {
        
        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 2.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        prodId = ez.createProductType("test", "0123456789012", 3.0, "");
        ez.updatePosition(prodId, "1-a-2");
        ez.updateQuantity(prodId, 3);

        Integer saleTrans = ez.startSaleTransaction();
        ez.addProductToSale(saleTrans, "1231231231232", 1);
        ez.applyDiscountRateToProduct(saleTrans, "1231231231232", 0.5);

        ez.addProductToSale(saleTrans, "0123456789012", 2);
        //ez.applyDiscountRateToProduct(saleTrans, "0123456789012", 0.4);

        ez.applyDiscountRateToSale(saleTrans, 0.5);
        ez.endSaleTransaction(saleTrans);
        ez.receiveCashPayment(saleTrans, ez.getSaleTransaction(saleTrans).getPrice());

        Integer returnId = ez.startReturnTransaction(saleTrans);
        ez.returnProduct(returnId, "1231231231232", 1);
        ez.returnProduct(returnId, "0123456789012", 2);
        ez.endReturnTransaction(returnId, true);

        assertEquals(3.5, ez.returnCashPayment(returnId), 0.01);

        long cnt = ez.getCreditsAndDebits(null, null)
            .stream()
            .filter(c -> c.getMoney() == 3.5)
            .filter(c -> c.getType() == "DEBIT")
            .count();

        assertEquals(1, cnt);
    }

    @Test
    public void testDefineCustomerWithNoLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.defineCustomer("peppe"));
    }

    @Test
    public void testDefineCustomerWithRightsAndEmptyArg() {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCustomerNameException.class, () -> ez.defineCustomer(""));
    }
    
    @Test
    public void testDefineCustomerWithRightsAndNullArg() {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCustomerNameException.class, () -> ez.defineCustomer(null));
    }

    @Test
    public void testDefineCustomerWithRightsAndCorrectInserting() throws InvalidCustomerNameException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertNotEquals(Integer.valueOf(-1), ez.defineCustomer("Peppe"));
    }

    @Test
    public void testDefineCustomerWithRightsAndDuplicatedCustomer() throws InvalidCustomerNameException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertNotEquals(Integer.valueOf(-1), ez.defineCustomer("Peppe"));
        assertEquals(Integer.valueOf(-1), ez.defineCustomer("Peppe"));
    }


    @Test
    public void testUpdatePositionCashier() {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        
        assertThrows(UnauthorizedException.class, () -> ez.updatePosition(36,"1-a-2"));        
    }
    
    @Test
    public void testUpdatePositionWithNotValidProductId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        

        EZShopInterface ez = new EZShop();
        
        assertThrows(InvalidProductIdException.class, () -> ez.updatePosition(-1,"1-a-2"));
        assertThrows(InvalidProductIdException.class, () -> ez.updatePosition(null,"1-a-2"));
        
    }
    
    @Test
    public void testUpdatePositionWithEmptyString() throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        

        EZShopInterface ez = new EZShop();
        
        assertTrue(ez.updatePosition(36,""));
        assertTrue(ez.updatePosition(36,null));
        
    }
    
    @Test 
    public void testUpdatePositionWithInvalidString() throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        

        EZShopInterface ez = new EZShop();
        
        assertThrows(InvalidLocationException.class, () -> ez.updatePosition(36,"1-3-2"));
    }
    
    @Test
    public void testUpdatePosition() throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.updatePosition(36,"1-a-2"));
        
        assertFalse(ez.updatePosition(36,"1-a-2"));
        
        Position pos1 = new Position(1,"a",5,null);
        
        DataManager.getInstance().insertPosition(pos1);
        
        assertTrue(ez.updatePosition(36,"1-a-5"));
    } 
    
    @Test
    public void testPayOrderCashier() {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        
        assertThrows(UnauthorizedException.class, () -> ez.payOrder(1));    
         
    }
    
    @Test
    public void testPayOrderWithNotValidOrderId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        
        assertThrows(InvalidOrderIdException.class, () -> ez.payOrder(-1));
        assertThrows(InvalidOrderIdException.class, () -> ez.payOrder(null));
    }

    @Test
    public void testPayOrderWithNotExisitingOrder() throws InvalidOrderIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        
        assertFalse(ez.payOrder(1));
         
    }
    
    @Test
    public void testPayOrderNotIssued() throws InvalidOrderIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-1");
		
        Order o = new Order(1, 0.6, 8, p , EOrderStatus.COMPLETED);
		DataManager.getInstance().insertOrder(o);
        
		assertFalse(ez.payOrder(1));
        
        Order o1 = new Order(2, 0.6, 8, p , EOrderStatus.PAYED);
        DataManager.getInstance().insertOrder(o1);
        
        assertFalse(ez.payOrder(2));
        
    }

    @Test
    public void testPayOrderTotVal() throws InvalidOrderIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-1");
		
        Order o = new Order(1, 0.6, 8, p , EOrderStatus.ISSUED);
		DataManager.getInstance().insertOrder(o);
        
		assertFalse(ez.payOrder(1));
		
		ez.recordBalanceUpdate(100.00);
        
		assertTrue(ez.payOrder(1));
    } 
    
    @Test
    public void testDeleteProductFromSaleWithoutUser() {
       
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.deleteProductFromSale(1,"p",1));    
         
    }
    
    @Test
    public void testDeleteProductFromSaleNotValidTransactionId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        
        assertThrows(InvalidTransactionIdException.class, () -> ez.deleteProductFromSale(-1,"1231231231232",1));
        assertThrows(InvalidTransactionIdException.class, () -> ez.deleteProductFromSale(null,"1231231231232",1));
        assertThrows(InvalidProductCodeException.class, () -> ez.deleteProductFromSale(1, "123", 0));
        assertThrows(InvalidQuantityException.class, () -> ez.deleteProductFromSale(1, "1231231231232", -1));
    }
    
    @Test
    public void testDeleteProductFromSaleNotExistingProduct() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        
        assertFalse(ez.deleteProductFromSale(1, "1231231231232", 1));
        
    }

    @Test
    public void testDeleteProductFromSaleNotExistingSale() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        
        assertFalse(ez.deleteProductFromSale(1, "1231231231232", 1));
        
    }
    
    @Test
    public void testDeleteProductFromSale() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 5, 0.4, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        
        
        Sale s = new Sale(1, 0.0, null);
        s.addProduct(p, 3);
        DataManager.getInstance().insertSale(s);
        ez.startSaleTransaction(); 
        
        assertTrue(ez.deleteProductFromSale(1, "1231231231232", 1));
        assertFalse(ez.deleteProductFromSale(3, "1231231231232", 1));
        
        s.setAsCommitted();
        assertFalse(ez.deleteProductFromSale(1, "1231231231232", 1));
        
    }
    
    @Test
    public void testDeleteSaleTransactionWithoutUser() {
       
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.deleteSaleTransaction(1));  
         
    }
    

    @Test
    public void testDeleteSaleTransaction() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        
        assertThrows(InvalidTransactionIdException.class, () -> ez.deleteSaleTransaction(-1));
        assertThrows(InvalidTransactionIdException.class, () -> ez.deleteSaleTransaction(null));
    }
    
    @Test
    public void testDeleteSaleTransactionSaleNotPresent() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        
        assertFalse(ez.deleteSaleTransaction(1));
        
        Sale s = new Sale(1,0.0,null);
        DataManager.getInstance().insertSale(s);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 5, 0.4, "", "1-a-1");
        s.addProduct(p, 2);
        DataManager.getInstance().updateSale(s);
        
        CreditTransaction ct = new CreditTransaction(1,s);
        DataManager.getInstance().insertBalanceTransaction(ct);
        
        assertFalse(ez.deleteSaleTransaction(1));
    }
    
    @Test
    public void testDeleteSaleTransactionSale() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
       
        EZShopInterface ez = new EZShop();
        
        Sale s = new Sale(1,0.0,null);
        DataManager.getInstance().insertSale(s);
        
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 5, 0.4, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        
        s.addProduct(p, 2);
        DataManager.getInstance().updateSale(s);
        
        assertTrue(ez.deleteSaleTransaction(1));
    }
    
    @Test
    public void testGetCreditsAndDebitsWithNoLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getCreditsAndDebits(null, null));
    }

    @Test
    public void testGetCreditsAndDebitsWithBothParameters() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        ez.recordBalanceUpdate(100);
        ez.recordBalanceUpdate(-50);

        BalanceTransaction bt = new CreditTransaction(3, new DummyCredit(3, 4));
        bt.setDate(LocalDate.now().minusDays(1));
        DataManager.getInstance().insertDummyCredit((DummyCredit)((CreditTransaction)bt).getRelatedCreditOperation());
        DataManager.getInstance().insertBalanceTransaction(bt);

        List<BalanceOperation> res = ez.getCreditsAndDebits(LocalDate.now(), LocalDate.now());
        assertEquals(2, res.size());

        assertEquals(1, (res.stream().filter(b -> b.getType() == "CREDIT" && b.getMoney() == 100).count()));
        assertEquals(1, (res.stream().filter(b -> b.getType() == "DEBIT" && b.getMoney() == 50).count()));
    }

    @Test
    public void testGetCreditsAndDebitsWithFromNull() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        ez.recordBalanceUpdate(100);
        ez.recordBalanceUpdate(-50);

        BalanceTransaction bt = new CreditTransaction(3, new DummyCredit(3, 4));
        bt.setDate(LocalDate.now().minusDays(1));
        DataManager.getInstance().insertDummyCredit((DummyCredit)((CreditTransaction)bt).getRelatedCreditOperation());
        DataManager.getInstance().insertBalanceTransaction(bt);

        List<BalanceOperation> res = ez.getCreditsAndDebits(null, LocalDate.now());
        assertEquals(3, res.size());

        assertEquals(1, (res.stream().filter(b -> b.getType() == "CREDIT" && b.getMoney() == 100).count()));
        assertEquals(1, (res.stream().filter(b -> b.getType() == "DEBIT" && b.getMoney() == 50).count()));
    }

    @Test
    public void testGetCreditsAndDebitsWithToNull() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        ez.recordBalanceUpdate(100);
        ez.recordBalanceUpdate(-50);

        BalanceTransaction bt = new CreditTransaction(3, new DummyCredit(3, 4));
        bt.setDate(LocalDate.now().minusDays(1));
        DataManager.getInstance().insertDummyCredit((DummyCredit)((CreditTransaction)bt).getRelatedCreditOperation());
        DataManager.getInstance().insertBalanceTransaction(bt);

        List<BalanceOperation> res = ez.getCreditsAndDebits(LocalDate.now(), null);
        assertEquals(2, res.size());

        assertEquals(1, (res.stream().filter(b -> b.getType() == "CREDIT" && b.getMoney() == 100).count()));
        assertEquals(1, (res.stream().filter(b -> b.getType() == "DEBIT" && b.getMoney() == 50).count()));
    }

    @Test
    public void testGetCreditsAndDebitsWithBothNull() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        ez.recordBalanceUpdate(100);
        ez.recordBalanceUpdate(-50);

        BalanceTransaction bt = new CreditTransaction(3, new DummyCredit(3, 4));
        bt.setDate(LocalDate.now().minusDays(1));
        DataManager.getInstance().insertDummyCredit((DummyCredit)((CreditTransaction)bt).getRelatedCreditOperation());
        DataManager.getInstance().insertBalanceTransaction(bt);

        List<BalanceOperation> res = ez.getCreditsAndDebits(null, null);
        assertEquals(3, res.size());

        assertEquals(1, (res.stream().filter(b -> b.getType() == "CREDIT" && b.getMoney() == 100).count()));
        assertEquals(1, (res.stream().filter(b -> b.getType() == "DEBIT" && b.getMoney() == 50).count()));
    }

    @Test
    public void testComputePointsForSaleWithoutLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.computePointsForSale(1));
    }

    @Test
    public void testComputePointsForSaleWithRightsAndNullTransId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.computePointsForSale(null));
    }

    @Test
    public void testComputePointsForSaleWithRightsAndZeroTransId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.computePointsForSale(0));
    }

    @Test
    public void testComputePointsForSaleWithRightsAndNegativeTransId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.computePointsForSale(-1));
    }

    @Test
    public void testComputePointsForSaleWithRightsAndSaleNotExisting() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidTransactionIdException, InvalidQuantityException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        assertEquals(-1, ez.computePointsForSale(1));
    }

    @Test
    public void testComputePointsForSaleWithRightsAndSaleProducts() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidTransactionIdException, InvalidQuantityException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 22.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        Integer saleTrans = ez.startSaleTransaction();
        ez.addProductToSale(saleTrans, "1231231231232", 2);

        assertEquals(4, ez.computePointsForSale(saleTrans));
    }

    @Test
    public void testComputePointsForSaleWithRightsAndNoProds() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidTransactionIdException, InvalidQuantityException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 22.0, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 5);

        Integer saleTrans = ez.startSaleTransaction();

        assertEquals(0, ez.computePointsForSale(saleTrans));
    }

    @Test
    public void testDeleteCustomerWithoutLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.deleteCustomer(1));
    }

    @Test
    public void testDeleteCustomerWithRightsAndNullId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCustomerIdException.class, () -> ez.deleteCustomer(null));
    }

    @Test
    public void testDeleteCustomerWithRightsAndZeroId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCustomerIdException.class, () -> ez.deleteCustomer(0));
    }

    @Test
    public void testDeleteCustomerWithRightsAndNegativeId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCustomerIdException.class, () -> ez.deleteCustomer(-1));
    }

    @Test
    public void testDeleteCustomerWithRightsAndNoExistingUser() throws InvalidCustomerIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertFalse(ez.deleteCustomer(1));
    }

    @Test
    public void testDeleteCustomerWithRightsAndExistingUser() throws InvalidCustomerIdException, UnauthorizedException, InvalidCustomerNameException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        Integer customerId = ez.defineCustomer("Peppe");

        assertEquals(1, ez.getAllCustomers().stream().filter(c -> c.getId() == customerId).count());
        assertTrue(ez.deleteCustomer(customerId));
        assertEquals(0, ez.getAllCustomers().stream().filter(c -> c.getId() == customerId).count());
    }

    @Test
    public void testDeleteCustomerWithRightsAndExistingUserMultiple() throws InvalidCustomerIdException, UnauthorizedException, InvalidCustomerNameException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        Integer customerId = ez.defineCustomer("Peppe");

        assertEquals(1, ez.getAllCustomers().stream().filter(c -> c.getId() == customerId).count());
        assertTrue(ez.deleteCustomer(customerId));
        assertEquals(0, ez.getAllCustomers().stream().filter(c -> c.getId() == customerId).count());

        assertFalse(ez.deleteCustomer(customerId));
    }

    @Test
    public void testGetCustomerWithoutLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getCustomer(1));
    }

    @Test
    public void testGetCustomerWithRightsAndNullId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCustomerIdException.class, () -> ez.getCustomer(null));
    }

    @Test
    public void testGetCustomerWithRightsAndZeroId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCustomerIdException.class, () -> ez.getCustomer(0));
    }

    @Test
    public void testGetCustomerWithRightsAndNegativeId() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidCustomerIdException.class, () -> ez.getCustomer(-1));
    }

    @Test
    public void testGetCustomerWithRightsAndExistingCustomer() throws InvalidCustomerIdException, UnauthorizedException, InvalidCustomerNameException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        Integer customerId = ez.defineCustomer("Peppe");
        ez.defineCustomer("Peppe2");

        assertEquals(customerId, ez.getCustomer(customerId).getId());
        assertEquals("Peppe", ez.getCustomer(customerId).getCustomerName());
    }

    @Test
    public void testGetCustomerWithRightsAndNoExistingCustomer() throws InvalidCustomerIdException, UnauthorizedException, InvalidCustomerNameException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        Integer customerId = 1;

        assertNull(ez.getCustomer(customerId));
    }

    @Test
    public void testGetProductTypeByDescriptionWithoutLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getProductTypesByDescription("budella"));
    }

    @Test
    public void testGetProductTypeByDescriptionWithNullArg() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId0 = ez.createProductType("test", "1231231231232", 2.0, "");
        Integer prodId1 = ez.createProductType("test", "0123456789012", 3.0, "");

        assertEquals(2, ez.getProductTypesByDescription(null).size());
        assertTrue(ez.getProductTypesByDescription(null).stream().anyMatch(p -> p.getId() == prodId0));
        assertTrue(ez.getProductTypesByDescription(null).stream().anyMatch(p -> p.getId() == prodId1));
    }

    @Test
    public void testGetProductTypeByDescriptionWithEmptyArg() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId0 = ez.createProductType("test", "1231231231232", 2.0, "");
        Integer prodId1 = ez.createProductType("test", "0123456789012", 3.0, "");

        assertEquals(2, ez.getProductTypesByDescription("").size());
        assertTrue(ez.getProductTypesByDescription("").stream().anyMatch(p -> p.getId() == prodId0));
        assertTrue(ez.getProductTypesByDescription("").stream().anyMatch(p -> p.getId() == prodId1));
    }

    @Test
    public void testGetProductTypeByDescriptionWithFullArg() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId0 = ez.createProductType("test ba", "1231231231232", 2.0, "");
        Integer prodId1 = ez.createProductType("test be", "0123456789012", 3.0, "");

        assertEquals(1, ez.getProductTypesByDescription("a").size());
        assertTrue(ez.getProductTypesByDescription("a").stream().anyMatch(p -> p.getId() == prodId0));
        assertFalse(ez.getProductTypesByDescription("a").stream().anyMatch(p -> p.getId() == prodId1));
    }

    @Test
    public void testGetProductTypeByDescriptionWithFullArgExpected0() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId0 = ez.createProductType("test ba", "1231231231232", 2.0, "");
        Integer prodId1 = ez.createProductType("test be", "0123456789012", 3.0, "");

        assertEquals(0, ez.getProductTypesByDescription("z").size());
        assertFalse(ez.getProductTypesByDescription("z").stream().anyMatch(p -> p.getId() == prodId0));
        assertFalse(ez.getProductTypesByDescription("z").stream().anyMatch(p -> p.getId() == prodId1));
    }

    @Test
    public void testGetAllProductTypesWithoutLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getAllProductTypes());
    }

    @Test
    public void testGetAllProductTypesWithRightsAndNoProducts() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        assertEquals(0, ez.getAllProductTypes().size());
    }

    @Test
    public void testGetAllProductTypesWithRightsAndProducts() throws UnauthorizedException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId0 = ez.createProductType("test ba", "1231231231232", 2.0, "");
        Integer prodId1 = ez.createProductType("test be", "0123456789012", 3.0, "");

        LoginManager.getInstance().tryLogout();

        u = new User(2, "ciao2", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao2", "pwd");

        assertEquals(2, ez.getAllProductTypes().size());
        assertTrue(ez.getAllProductTypes().stream().anyMatch(p -> p.getId() == prodId0));
        assertTrue(ez.getAllProductTypes().stream().anyMatch(p -> p.getId() == prodId1));
    }

    @Test
    public void testLogoutWithoutLoggedUser() {
        EZShopInterface ez = new EZShop();
        assertFalse(ez.logout());
    }

    @Test
    public void testLogoutWithLoggedUser() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertTrue(ez.logout());
    }


    //returnProduct
    @Test
    public void testReturnProductWithNoLoggedUser(){
        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.returnProduct(1, "1231231231232", 1));
    }

    @Test
    public void testReturnProductWithCashierRights() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.returnProduct(1, "1231231231232", 1));
    }

    @Test
    public void testReturnProductWithShopManagerRights() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.returnProduct(1, "1231231231232", 1));
    }

    @Test
    public void testReturnProductWithAdministratorRights() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.returnProduct(1, "1231231231232", 1));
    }

    @Test
    public void testReturnProductWithMultipleCalls() throws InvalidQuantityException, InvalidTransactionIdException, UnauthorizedException, InvalidProductCodeException, InvalidProductDescriptionException, InvalidPricePerUnitException, InvalidProductIdException, InvalidLocationException, InvalidPaymentException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 1.4, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 3);

        Integer transId = ez.startSaleTransaction();
        ez.addProductToSale(transId, "1231231231232", 2);
        ez.endSaleTransaction(transId);

        assertEquals(Integer.valueOf(1), ez.getProductTypeByBarCode("1231231231232").getQuantity());

        assertEquals(0.0, ez.receiveCashPayment(transId, ez.getSaleTransaction(transId).getPrice()), 0.01);

        Integer retId = ez.startReturnTransaction(transId);

        assertTrue(ez.returnProduct(retId, "1231231231232", 1));
        assertTrue(ez.returnProduct(retId, "1231231231232", 1));
        assertFalse(ez.returnProduct(retId, "1231231231232", 1));
        assertFalse(ez.returnProduct(retId, "1231231231232", 1));

        ez.endReturnTransaction(retId, true);

        assertEquals(Integer.valueOf(3), ez.getProductTypeByBarCode("1231231231232").getQuantity());
    }

    @Test
    public void testReturnProductWithNullReturnId() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.returnProduct(null, "1231231231232", 1));
    }

    @Test
    public void testReturnProductWithInvalidReturnId() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.returnProduct(0, "1231231231232", 1));
    }

    @Test
    public void testReturnProductWithNullProductCode() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.returnProduct(1, null, 1));
    }

    @Test
    public void testReturnProductWithEmptyProductCode() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.returnProduct(1, "", 1));
    }

    @Test
    public void testReturnProductWithInvalidProductCode() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductCodeException.class, () -> ez.returnProduct(1, "1111111111111", 1));
    }

    @Test
    public void testReturnProductWithInvalidQuantity() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);


        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidQuantityException.class, () -> ez.returnProduct(1, "1231231231232", 0));
    }

    //startReturnTransaction
    @Test
    public void testStartReturnTransactionWithNoLoggedUser(){
        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.startReturnTransaction(1));
    }

    @Test
    public void testStartReturnTransactionWithCashierRights() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        EZShopInterface ez = new EZShop();
        assertEquals(Integer.valueOf(1), ez.startReturnTransaction(1));
    }

    @Test
    public void testStartReturnTransactionWithShopManagerRights() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        EZShopInterface ez = new EZShop();
        assertEquals(Integer.valueOf(1), ez.startReturnTransaction(1));
    }

    @Test
    public void testStartReturnTransactionWithAdministratorRights() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        EZShopInterface ez = new EZShop();
        assertEquals(Integer.valueOf(1), ez.startReturnTransaction(1));
    }

    @Test
    public void testStartReturnTransactionWithNullTransactionId() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.startReturnTransaction(null));
    }

    @Test
    public void testStartReturnTransactionWithInvalidTransactionId() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.startReturnTransaction(0));
    }

    @Test
    public void testStartReturnTransactionWithTransactionNotAvailable() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        EZShopInterface ez = new EZShop();
        assertEquals(Integer.valueOf(-1), ez.startReturnTransaction(2));
    }

    @Test
    public void testStartReturnTransactionWithSaleNotCommitted() throws InvalidTransactionIdException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 1.4, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 3);

        Integer transId = ez.startSaleTransaction();
        ez.addProductToSale(transId, "1231231231232", 2);

        assertEquals(Integer.valueOf(-1), ez.startReturnTransaction(transId));
    }

    @Test
    public void testStartReturnTransactionWithSaleCommittedNoPaid() throws InvalidTransactionIdException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 1.4, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 3);

        Integer transId = ez.startSaleTransaction();
        ez.addProductToSale(transId, "1231231231232", 2);
        ez.endSaleTransaction(transId);

        assertEquals(Integer.valueOf(-1), ez.startReturnTransaction(transId));
    }

    @Test
    public void testStartReturnTransactionWithSaleCommittedPaid() throws InvalidTransactionIdException, UnauthorizedException, InvalidProductIdException, InvalidLocationException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, InvalidQuantityException, InvalidPaymentException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer prodId = ez.createProductType("test", "1231231231232", 1.4, "");
        ez.updatePosition(prodId, "1-a-1");
        ez.updateQuantity(prodId, 3);

        Integer transId = ez.startSaleTransaction();
        ez.addProductToSale(transId, "1231231231232", 2);
        ez.endSaleTransaction(transId);
        ez.receiveCashPayment(transId, ez.getSaleTransaction(transId).getPrice());

        assertFalse(ez.startReturnTransaction(transId) <= 0);
    }

    //createUser
    @Test
    public void testCreateUserWithNullUsername(){

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidUsernameException.class, () -> ez.createUser(null, "pwd", "Cashier"));
    }

    @Test
    public void testCreateUserWithEmptyUsername(){

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidUsernameException.class, () -> ez.createUser("", "pwd", "Cashier"));
    }

    @Test
    public void testCreateUserWithNullPassword(){

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidPasswordException.class, () -> ez.createUser("ciao", null, "Cashier"));
    }

    @Test
    public void testCreateUserWithEmptyPassword(){

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidPasswordException.class, () -> ez.createUser("ciao", "", "Cashier"));
    }

    @Test
    public void testCreateUserWithNullRole(){

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidRoleException.class, () -> ez.createUser("ciao", "pwd", null));
    }

    @Test
    public void testCreateUserWithEmptyRole(){

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidRoleException.class, () -> ez.createUser("ciao", "pwd", ""));
    }

    @Test
    public void testCreateUserAsCashier() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {

        EZShopInterface ez = new EZShop();
        assertEquals(Integer.valueOf(1), ez.createUser("ciao", "pwd", "Cashier"));
    }

    @Test
    public void testCreateUserAsShopManager() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {

        EZShopInterface ez = new EZShop();
        assertEquals(Integer.valueOf(1), ez.createUser("ciao", "pwd", "ShopManager"));
    }

    @Test
    public void testCreateUserAsAdministrator() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {

        EZShopInterface ez = new EZShop();
        assertEquals(Integer.valueOf(1), ez.createUser("ciao", "pwd", "Administrator"));
    }

    @Test
    public void testCreateUserWithAlreadyUsedUsername() throws InvalidPasswordException, InvalidRoleException, InvalidUsernameException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        EZShopInterface ez = new EZShop();
        assertEquals(Integer.valueOf(-1), ez.createUser("ciao", "pwd", "Administrator"));
    }

    //updateQuantity
    @Test
    public void testUpdateQuantityWithNoLoggedUser() {

        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.updateQuantity(36, 1));
    }

    @Test
    public void testUpdateQuantityWithCashierRights(){

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.updateQuantity(36, 1));
    }

    @Test
    public void testUpdateQuantityWithShopManagerRights() throws UnauthorizedException, InvalidProductIdException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.updateQuantity(36,  1));
    }

    @Test
    public void testUpdateQuantityWithAdministratorRights() throws UnauthorizedException, InvalidProductIdException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.updateQuantity(36,  1));
    }

    @Test
    public void testUpdateQuantityWithNullProductId(){

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductIdException.class, () -> ez.updateQuantity(null, 1));
    }

    @Test
    public void testUpdateQuantityWithInvalidProductId(){

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidProductIdException.class, () -> ez.updateQuantity(0, 1));
    }

    @Test
    public void testUpdateQuantityOfNonexistentProduct() throws UnauthorizedException, InvalidProductIdException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.updateQuantity(1,  1));
    }

    @Test
    public void testUpdateQuantityWithNegativeToBeAdded() throws UnauthorizedException, InvalidProductIdException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.updateQuantity(36,  -1));
    }

    @Test
    public void testUpdateQuantityWithNegativeResultingAmount() throws UnauthorizedException, InvalidProductIdException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.updateQuantity(36,  -4));
    }

    @Test
    public void testUpdateQuantityOfProductWithoutLocation() throws UnauthorizedException, InvalidProductIdException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "");
        DataManager.getInstance().insertProductType(p);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.updateQuantity(36,  1));
    }

    @Test
    public void testModifyPointsOnCardWithoutUser() {

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.modifyPointsOnCard("",1));

    }



    @Test
    public void testModifyPointsOnCardWithInvalidCard()  {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidCustomerCardException.class, () -> ez.modifyPointsOnCard(null,1));
        assertThrows(InvalidCustomerCardException.class, () -> ez.modifyPointsOnCard("123",1));
        assertThrows(InvalidCustomerCardException.class, () -> ez.modifyPointsOnCard("",1));

    }

    @Test
    public void testModifyPointsOnCardWithCardNotPresent() throws InvalidCustomerCardException, UnauthorizedException  {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        /*         Customer c = new Customer(1, "Michele Misseri", null);
        LoyaltyCard lt = new LoyaltyCard("4547483383", 3, c);*/

        assertFalse(ez.modifyPointsOnCard("0123456789",10));

    }

    @Test
    public void testModifyPointsOnCard() throws InvalidCustomerCardException, UnauthorizedException  {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        String code = ez.createCard();
        assertEquals("0000000001",code);

        assertTrue(ez.modifyPointsOnCard(code,3));
        assertFalse(ez.modifyPointsOnCard(code,-10));

    }

    @Test
    public void testCreateCardWithoutUser() throws UnauthorizedException {

    	 EZShopInterface ez = new EZShop();
         assertThrows(UnauthorizedException.class, () -> ez.createCard());
    }


    @Test
    public void testGetAllOrders() throws UnauthorizedException {

    	 EZShopInterface ez = new EZShop();
         assertThrows(UnauthorizedException.class, () -> ez.getAllOrders());

         User u = new User(1, "ciao", "pwd", "ShopManager");
         DataManager.getInstance().insertUser(u);
         LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
		Order o1 = new Order(1, 6.4, 10, pt, EOrderStatus.ISSUED);
		List<Order> orders = new ArrayList<Order>();
		orders.add(o1);
		DataManager.getInstance().insertOrder(o1);

		assertEquals(orders.stream().collect(toList()),ez.getAllOrders());

    }

    @Test
    public void testLogin() {


        EZShopInterface ez = new EZShop();

        assertThrows(InvalidUsernameException.class, () -> ez.login(null,"pwd"));
        assertThrows(InvalidUsernameException.class, () -> ez.login("","pwd"));
        assertThrows(InvalidPasswordException.class, () -> ez.login("user",""));
        assertThrows(InvalidPasswordException.class, () -> ez.login("user",null));

    }

    @Test
    public void testLoginNoUser() throws InvalidUsernameException, InvalidPasswordException {

        EZShopInterface ez = new EZShop();

        assertEquals(null,ez.login("user","pwd"));

    }

    @Test
    public void testLoginValidUser() throws InvalidUsernameException, InvalidPasswordException {

        User u = new User(1, "user", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);


        EZShopInterface ez = new EZShop();

        assertEquals(u,ez.login("user","pwd"));

    }

    @Test
    public void testCreateProductTypeCashier() {

    	 User u = new User(1, "ciao", "pwd", "Cashier");
         DataManager.getInstance().insertUser(u);
         LoginManager.getInstance().tryLogin("ciao", "pwd");

         //ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        EZShopInterface ez = new EZShop();

        assertThrows(UnauthorizedException.class, () -> ez.createProductType("test","999999999993",14.0,"nada"));

    }

    @Test
    public void testCreateProductType() {

   	 User u = new User(1, "ciao", "pwd", "ShopManager");
     DataManager.getInstance().insertUser(u);
     LoginManager.getInstance().tryLogin("ciao", "pwd");

     EZShopInterface ez = new EZShop();

     assertThrows(InvalidProductDescriptionException.class, () -> ez.createProductType(null,"999999999993",14.0,"nada"));
     assertThrows(InvalidProductDescriptionException.class, () -> ez.createProductType("","999999999993",14.0,"nada"));

     assertThrows(InvalidProductCodeException.class, () -> ez.createProductType("test","",14.0,"niente"));
     assertThrows(InvalidProductCodeException.class, () -> ez.createProductType("test",null,14.0,"nada"));
     assertThrows(InvalidProductCodeException.class, () -> ez.createProductType("test","1111",14.0,"nada"));

     assertThrows(InvalidPricePerUnitException.class, () -> ez.createProductType("test","999999999993",-1.0,"nada"));
     assertThrows(InvalidPricePerUnitException.class, () -> ez.createProductType("test","999999999993",0.0,"nada"));

    }

    @Test
    public void testCreateProductTypeWithExistingProd() throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {

   	 User u = new User(1, "ciao", "pwd", "ShopManager");
     DataManager.getInstance().insertUser(u);
     LoginManager.getInstance().tryLogin("ciao", "pwd");

     EZShopInterface ez = new EZShop();

     ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
     DataManager.getInstance().insertProductType(pt);
     assertEquals(Integer.valueOf(-1), ez.createProductType("robetta","999999999993",14.0,"nada"));

    }

    @Test
    public void testgetProductTypeByBarcodeCashier() {

    	 User u = new User(1, "ciao", "pwd", "Cashier");
         DataManager.getInstance().insertUser(u);
         LoginManager.getInstance().tryLogin("ciao", "pwd");

         //ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        EZShopInterface ez = new EZShop();

        assertThrows(UnauthorizedException.class, () -> ez.getProductTypeByBarCode("999999999993"));

    }

    @Test
    public void testgetProductTypeByBarcode() {

    	 User u = new User(1, "ciao", "pwd", "ShopManager");
         DataManager.getInstance().insertUser(u);
         LoginManager.getInstance().tryLogin("ciao", "pwd");

         //ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        EZShopInterface ez = new EZShop();

        assertThrows(InvalidProductCodeException.class, () -> ez.getProductTypeByBarCode("9999999993"));
        assertThrows(InvalidProductCodeException.class, () -> ez.getProductTypeByBarCode(""));
        assertThrows(InvalidProductCodeException.class, () -> ez.getProductTypeByBarCode(null));
    }

    @Test
    public void testEndSaleTransactionWithoutUSer() {
        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.endSaleTransaction(1));
    }

    @Test
    public void testEndSaleTransaction() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        //ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        EZShopInterface ez = new EZShop();

        assertThrows(InvalidTransactionIdException.class, () -> ez.endSaleTransaction(null));
        assertThrows(InvalidTransactionIdException.class, () -> ez.endSaleTransaction(-1));
        assertThrows(InvalidTransactionIdException.class, () -> ez.endSaleTransaction(0));
    }

    @Test
    public void testEndSaleTransactionWithMultipleCalls() throws UnauthorizedException, InvalidTransactionIdException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer transId = ez.startSaleTransaction();

        assertTrue(ez.endSaleTransaction(transId));
        assertFalse(ez.endSaleTransaction(transId));
    }

    @Test
    public void testEndSaleTransactionWithNoValidId() throws UnauthorizedException, InvalidTransactionIdException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        Integer transId = ez.startSaleTransaction();

        assertFalse(ez.endSaleTransaction(transId + 1));
    }

    @Test
    public void testComputeBalance() {


        //ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
       EZShopInterface ez = new EZShop();

       assertThrows(UnauthorizedException.class, () -> ez.computeBalance());

   }





    //modifyCustomer()
    @Test
    public void testValidModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException{

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.modifyCustomer(1, "Gianni Balestra", "9999992392"));

        Optional<Customer> customer = DataManager.getInstance()
            .getCustomers()
            .stream()
            .filter(s -> s.getId() == 1)
            .findFirst();
        assertTrue(customer.isPresent());
        assertEquals(customer.get().getCustomerName(), "Gianni Balestra");

        Optional<LoyaltyCard> card = DataManager.getInstance()
                    .getLoyaltyCards()
                    .stream()
                    .filter(d -> d.getID().equals("9999992392"))
                    .findFirst();
        assertTrue(card.isPresent());
        assertEquals(card.get(), customer.get().getLoyaltyCard());

    }

    @Test
    public void testUnauthorizedModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException{
        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();

        assertThrows(UnauthorizedException.class, ()->ez.modifyCustomer(1, "Gianni Balestra", "9999992392"));
        
    }

    @Test
    public void testWrongIdModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidCustomerIdException.class, ()->ez.modifyCustomer(-1, "Gianni Balestra", "9999992392"));
        assertThrows(InvalidCustomerIdException.class, ()->ez.modifyCustomer(null, "Gianni Balestra", "9999992392"));
        
    }

    @Test
    public void testWrongCustomerNameModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidCustomerNameException.class, ()->ez.modifyCustomer(1, null, "9999992392"));
        assertThrows(InvalidCustomerNameException.class, ()->ez.modifyCustomer(1, "", "9999992392"));
        
    }

    @Test
    public void testWrongCustomerCardModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerIdException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidCustomerCardException.class, () -> ez.modifyCustomer(1, "Gianni Balestra", "252"));
        assertThrows(InvalidCustomerCardException.class, () -> ez.modifyCustomer(1, "Gianni Balestra", "yzcfjdxjch"));
    }

    @Test
    public void testMissingCustomerModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException{

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertFalse(ez.modifyCustomer(1, "Gianni Balestra", "9999992392"));
    }

    @Test
    public void testNoCardModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException{

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.modifyCustomer(1, "Gianni Balestra", null));      
        assertTrue(ez.modifyCustomer(1, "Gianni Balestra", ""));

    }

    @Test 
    public void testMissingCardModifyCustomer() throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException{

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.modifyCustomer(1, "Gianni Balestra", "9999992392"));      

        Customer c1 = new Customer(2, "Giovanni Muciaccia", null);
        DataManager.getInstance().insertCustomer(c1);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, c1);
        DataManager.getInstance().insertLoyaltyCard(lt);

        c1.setCustomerCard("9999992392");
        assertFalse(ez.modifyCustomer(1, "Gianni Balestra", "9999992392"));
    }

    //addProductToSale

    @Test
    public void testValidAddProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException{
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.addProductToSale(1, "999999999993", 3));
    }

    @Test
    public void testUnauthorizedAddProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException{
        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, ()->ez.addProductToSale(1, "999999999993", 3));
    }

    @Test
    public void testWrongParamsAddProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException{
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, ()->ez.addProductToSale(null, "999999999993", 3));
        assertThrows(InvalidTransactionIdException.class, ()->ez.addProductToSale(-1, "999999999993", 3));
        assertThrows(InvalidProductCodeException.class, ()->ez.addProductToSale(1, "defve", 3));
        assertThrows(InvalidQuantityException.class, ()->ez.addProductToSale(1, "999999999993", -3));
    }

    @Test
    public void testMissingProductAddProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException{
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.addProductToSale(1, "999999999993", 3));

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 1, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);
        assertFalse(ez.addProductToSale(1, "999999999993", 3));
    }

    @Test
    public void testMissingSaleAddProductToSale() throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException{
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.addProductToSale(1, "999999999993", 3));
    }

    //receiveCashPayment

    @Test
    public void testValidReceiveCashPayment() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidProductCodeException, InvalidQuantityException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        ez.addProductToSale(1, "999999999993", 3);

        s.setAsCommitted();

        assertEquals(ez.receiveCashPayment(1, 33.6), 0.0, 0.01);

    }

    @Test
    public void testUnauthorizedReceiveCashPayment() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidProductCodeException, InvalidQuantityException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        ez.addProductToSale(1, "999999999993", 3);
        
        s.setAsCommitted();

        LoginManager.getInstance().tryLogout();

        assertThrows(UnauthorizedException.class,() -> ez.receiveCashPayment(1, 33.6));

    }

    @Test
    public void testWrongParamsReceiveCashPayment() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidProductCodeException, InvalidQuantityException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        ez.addProductToSale(1, "999999999993", 3);
        
        s.setAsCommitted();

        assertThrows(InvalidTransactionIdException.class,() -> ez.receiveCashPayment(null, 33.6));
        assertThrows(InvalidTransactionIdException.class,() -> ez.receiveCashPayment(-1, 33.6));
        assertThrows(InvalidPaymentException.class,() -> ez.receiveCashPayment(1, -33.6));

    }

    @Test
    public void testMissingSaleReceiveCashPayment() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidProductCodeException, InvalidQuantityException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        ez.addProductToSale(1, "999999999993", 3);

        s.setAsCommitted();
        DataManager.getInstance().deleteSale(s);

        assertEquals(ez.receiveCashPayment(1, 33.6), -1, 0.01);

    }

    @Test
    public void testNoMoneyReceiveCashPayment() throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException, InvalidProductCodeException, InvalidQuantityException {
        
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();
        ez.addProductToSale(1, "999999999993", 3);

        s.setAsCommitted();

        assertEquals(ez.receiveCashPayment(1, 10.0), -1, 0.01);

    }

    //receiveCashPayment
    
    @Test
    public void testValidIssueOrder() throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();

        assertEquals(ez.issueOrder("999999999993", 20, 3), 1, 0.0000000001);
    }

    @Test
    public void testUnauthorizedIssueOrder() throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException{
        
        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();

        assertThrows(UnauthorizedException.class, () -> ez.issueOrder("999999999993", 20, 3));

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        assertThrows(UnauthorizedException.class, () -> ez.issueOrder("999999999993", 20, 3));
    }

    @Test
    public void testWrongParamsIssueOrder() throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidProductCodeException.class, () -> ez.issueOrder(null, 20, 3));
        assertThrows(InvalidProductCodeException.class, () -> ez.issueOrder("", 20, 3));
        assertThrows(InvalidProductCodeException.class, () -> ez.issueOrder("fdsv", 20, 3));

        assertThrows(InvalidQuantityException.class, () -> ez.issueOrder("999999999993", -3, 3));

        assertThrows(InvalidPricePerUnitException.class, () -> ez.issueOrder("999999999993", 3, -3));
    }

    @Test
    public void testMissingProductIssueOrder() throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        assertEquals(ez.issueOrder("999999999993", 20, 3), -1, 0.0000000001);
    }

    //attachCardToCustomer

    @Test
    public void testValidAttachCardToCustomer() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();

        assertTrue(ez.attachCardToCustomer("9999992392", 1));
    }

    @Test
    public void testUnauthorizedAttachCardToCustomer() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();

        assertThrows(UnauthorizedException.class, () -> ez.attachCardToCustomer("9999992392", 1));
    }

    @Test
    public void testWrongParamsAttachCardToCustomer() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidCustomerIdException.class, () -> ez.attachCardToCustomer("9999992392", null));
        assertThrows(InvalidCustomerIdException.class, () -> ez.attachCardToCustomer("9999992392", -1));

        assertThrows(InvalidCustomerCardException.class, () -> ez.attachCardToCustomer("", 1));
        assertThrows(InvalidCustomerCardException.class, () -> ez.attachCardToCustomer(null, 1));
        assertThrows(InvalidCustomerCardException.class, () -> ez.attachCardToCustomer("132", 1));
        assertThrows(InvalidCustomerCardException.class, () -> ez.attachCardToCustomer("faviaickga", 1));
    }

    @Test
    public void testMissingCustomerCardToCustomer() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        LoyaltyCard lt = new LoyaltyCard("9999992392", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);

        EZShopInterface ez = new EZShop();

        assertFalse(ez.attachCardToCustomer("9999992392", 1));
    }

    @Test
    public void testMissingCardAttachCardToCustomer() throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Customer c = new Customer(1, "Paolo Brosio", null);
        DataManager.getInstance().insertCustomer(c);

        EZShopInterface ez = new EZShop();

        assertFalse(ez.attachCardToCustomer("9999992392", 1));
    }

    //applydiscountRateToSale

    @Test
    public void testValidApplydiscountRateToSale() throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();

        assertTrue(ez.applyDiscountRateToSale(1, 0.1));
    }

    @Test
    public void testUnauthorizedApplydiscountRateToSale() throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException{
        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();

        assertThrows(UnauthorizedException.class, () -> ez.applyDiscountRateToSale(1, 0.1));
    }

    @Test
    public void testWrongParamsApplydiscountRateToSale() throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidTransactionIdException.class, () -> ez.applyDiscountRateToSale(null, 0.1));
        assertThrows(InvalidTransactionIdException.class, () -> ez.applyDiscountRateToSale(-1, 0.1));

        assertThrows(InvalidDiscountRateException.class, () -> ez.applyDiscountRateToSale(1, -0.2));
        assertThrows(InvalidDiscountRateException.class, () -> ez.applyDiscountRateToSale(1, 1.4));
    }

    @Test
    public void testValidMissingSalediscountRateToSale() throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        assertFalse(ez.applyDiscountRateToSale(1, 0.1));
    }

    //updateUserRight

    @Test
    public void testValidUpdateUserRights() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        User u1 = new User(2, "giorgio", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u1);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.updateUserRights(2, "Administrator"));
    }

    @Test
    public void testUnauthorizedUpdateUserRights() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException{

        User u1 = new User(2, "giorgio", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u1);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.updateUserRights(2, "Administrator"));
    }

    @Test
    public void testWrongParamsUpdateUserRights() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        User u1 = new User(2, "giorgio", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u1);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidUserIdException.class, () -> ez.updateUserRights(null, "Administrator"));
        assertThrows(InvalidUserIdException.class, () -> ez.updateUserRights(-1, "Administrator"));

        assertThrows(InvalidRoleException.class, () -> ez.updateUserRights(2, ""));
        assertThrows(InvalidRoleException.class, () -> ez.updateUserRights(2, null));
        assertThrows(InvalidRoleException.class, () -> ez.updateUserRights(2, "asdaf"));
    }

    @Test
    public void testMissingUserUpdateUserRights() throws InvalidUserIdException, InvalidRoleException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertFalse(ez.updateUserRights(2, "Administrator"));
    }

    //deleteReturnTransaction

    @Test
    public void testValidDeleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();

        assertTrue(ez.deleteReturnTransaction(1));
    }

    @Test
    public void testUnauthorizedDeleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {
        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();

        assertThrows(UnauthorizedException.class, () -> ez.deleteReturnTransaction(1));
    }

    @Test
    public void testWrongParamsDeleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidTransactionIdException.class, () -> ez.deleteReturnTransaction(null));
        assertThrows(InvalidTransactionIdException.class, () -> ez.deleteReturnTransaction(-1));
    }

    @Test
    public void testMissingReturnDeleteReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        assertFalse(ez.deleteReturnTransaction(1));
    }

    //deleteProductType

    @Test
    public void testValidDeleteProductType() throws InvalidProductIdException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();

        assertTrue(ez.deleteProductType(1));
    }

    @Test
    public void testUnauthorizedDeleteProductType() throws InvalidProductIdException, UnauthorizedException {
        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();

        assertThrows(UnauthorizedException.class, () -> ez.deleteProductType(1));
    }

    @Test
    public void testWrongParamsDeleteProductType() throws InvalidProductIdException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        ProductType pt = new ProductType(1, "999999999993", "robetta", 14.0, 13, 0.1, "niente", null);
        DataManager.getInstance().insertProductType(pt);

        EZShopInterface ez = new EZShop();

        assertThrows(InvalidProductIdException.class, () -> ez.deleteProductType(null));
        assertThrows(InvalidProductIdException.class, () -> ez.deleteProductType(-1));
    }

    @Test
    public void testMissingReturnDeleteProductType() throws InvalidProductIdException, UnauthorizedException {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();

        assertFalse(ez.deleteProductType(1));
    }

    //getSaleTransaction

    @Test
    public void testValidGetSaleTransaction() throws InvalidTransactionIdException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        assertEquals(s, ez.getSaleTransaction(1));

    }

    @Test
    public void testUnauthorizedGetSaleTransaction() throws InvalidTransactionIdException, UnauthorizedException{
        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getSaleTransaction(1));

    }

    @Test
    public void testWrongParamsGetSaleTransaction() throws InvalidTransactionIdException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.getSaleTransaction(null));
        assertThrows(InvalidTransactionIdException.class, () -> ez.getSaleTransaction(-1));

    }

    @Test
    public void testMissingSaleGetSaleTransaction() throws InvalidTransactionIdException, UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertEquals(null, ez.getSaleTransaction(1));

    }

    //getAllUsers

    @Test
    public void testValidGetAllUsers() throws UnauthorizedException{
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        List<User> lu = new ArrayList<User>();
        lu.add(u);

        EZShopInterface ez = new EZShop();
        assertEquals(lu, ez.getAllUsers());

    }

    @Test
    public void testUnauthorizedGetAllUsers() throws UnauthorizedException{
        Sale s = new Sale(1, 0.2, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getAllUsers());

    }


    //endReturnTransaction
    @Test
    public void testEndReturnTransactionWithNoLoggedUser() throws InvalidTransactionIdException, UnauthorizedException {

        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.endReturnTransaction(1, true));
    }

    @Test
    public void testEndReturnTransactionWithCashierRights() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.endReturnTransaction(1, true));
    }

    @Test
    public void testEndReturnTransactionWithShopManagerRights() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.endReturnTransaction(1, true));    }

    @Test
    public void testEndReturnTransactionWithAdministratorRights() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.endReturnTransaction(1, true));
    }

    @Test
    public void testEndReturnTransactionWithNullReturnId() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidTransactionIdException.class, () -> ez.endReturnTransaction(null, true));
    }

    @Test
    public void testEndNonexistentReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertFalse(ez.endReturnTransaction(2, true));
    }

    @Test
    public void testEndAlreadyCommittedReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.endReturnTransaction(1, true));
        assertFalse(ez.endReturnTransaction(1, true));
        assertFalse(ez.endReturnTransaction(1, false));
    }

    @Test
    public void testEndAlreadyRolledBackReturnTransaction() throws InvalidTransactionIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 3, 0.0, "", "1-a-1");
        DataManager.getInstance().insertProductType(p);
        s.addProduct(p, 2);
        s.setAsCommitted();

        CreditTransaction ct = new CreditTransaction(1, s);
        DataManager.getInstance().insertBalanceTransaction(ct);

        CReturn cr = new CReturn(1, s);
        DataManager.getInstance().insertReturn(cr);

        EZShopInterface ez = new EZShop();
        assertTrue(ez.endReturnTransaction(1, false));
        assertFalse(ez.endReturnTransaction(1, false));
        assertFalse(ez.endReturnTransaction(1, true));
    }

    //startSaleTransaction
    @Test
    public void testStartSaleTransactionWithNoLoggedUser() {

        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.startSaleTransaction());
    }

    @Test
    public void testStartSaleTransactionWithCashierRights() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Sale s = new Sale(1, 0.0, null);
        DataManager.getInstance().insertSale(s);

        EZShopInterface ez = new EZShop();
        assertEquals(2, ez.startSaleTransaction().intValue());
    }

    @Test
    public void testStartSaleTransactionWithShopManagerRights() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertEquals(1, ez.startSaleTransaction().intValue());
    }

    @Test
    public void testStartSaleTransactionWithAdministratorRights() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertEquals(1, ez.startSaleTransaction().intValue());
    }

    //deleteUser
    @Test
    public void testDeleteUserWithNoLoggedUser() {

        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.deleteUser(1));
    }

    @Test
    public void testDeleteUserWithCashierRights() {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.deleteUser(1));
    }

    @Test
    public void testDeleteUserWithShopManagerRights() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.deleteUser(1));
    }

    @Test
    public void testDeleteUserWithAdministratorRights() throws InvalidUserIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertTrue(ez.deleteUser(1));
    }

    @Test
    public void testDeleteNonexistentUser() throws InvalidUserIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        EZShopInterface ez = new EZShop();
        assertFalse(ez.deleteUser(2));
    }

    //getUser
    @Test
    public void testgetUserWithNoLoggedUser() {

        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getUser(1));
    }

    @Test
    public void testGetUserWithCashierRights() {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        User u2 = new User(7, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u2);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getUser(1));
    }

    @Test
    public void testGetUserWithShopManagerRights() {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        User u2 = new User(7, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u2);

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getUser(7));
    }

    @Test
    public void testGetUserWithAdministratorRights() throws InvalidUserIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        User u2 = new User(7, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u2);

        EZShopInterface ez = new EZShop();
        assertEquals(u2, ez.getUser(7));
    }

    @Test
    public void testGetUserWithNullUserId() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        User u2 = new User(7, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u2);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidUserIdException.class, () -> ez.getUser(null));
    }

    @Test
    public void testGetUserWithInvalidUserId() {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        User u2 = new User(7, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u2);

        EZShopInterface ez = new EZShop();
        assertThrows(InvalidUserIdException.class, () -> ez.getUser(0));
    }

    @Test
    public void testGetNonexistentUser() throws InvalidUserIdException, UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        User u2 = new User(7, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u2);

        EZShopInterface ez = new EZShop();
        assertNull(ez.getUser(2));
    }

    //getAllCustomers
    @Test
    public void testGetAllCustomersWithNoLoggedUser() {

        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        EZShopInterface ez = new EZShop();
        assertThrows(UnauthorizedException.class, () -> ez.getAllCustomers());
    }

    @Test
    public void testGetAllCustomersWithCashierRights() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Customer c = new Customer(3, "mbare", null);
        DataManager.getInstance().insertCustomer(c);

        EZShopInterface ez = new EZShop();
        assertEquals(DataManager.getInstance().getCustomers(), ez.getAllCustomers());
    }

    @Test
    public void testGetAllCustomersWithShopManagerRights() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "ShopManager");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Customer c = new Customer(3, "mbare", null);
        DataManager.getInstance().insertCustomer(c);

        EZShopInterface ez = new EZShop();
        assertEquals(DataManager.getInstance().getCustomers(), ez.getAllCustomers());
    }

    @Test
    public void testgetAllCustomersWithAdministratorRights() throws UnauthorizedException {

        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");

        Customer c = new Customer(3, "mbare", null);
        DataManager.getInstance().insertCustomer(c);

        EZShopInterface ez = new EZShop();
        assertEquals(DataManager.getInstance().getCustomers(), ez.getAllCustomers());
    }


}
