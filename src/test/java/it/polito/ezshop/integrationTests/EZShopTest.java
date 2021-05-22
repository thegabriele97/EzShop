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
        
        Position pos = new Position(1,"a",2,p);
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
        ProductType p = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.4, "", "1-a-1");
        
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
        
        CreditTransaction ct = new CreditTransaction(1,s);
        
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

}
