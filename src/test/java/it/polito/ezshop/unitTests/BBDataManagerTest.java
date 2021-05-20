package it.polito.ezshop.unitTests;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

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

    ////Deletes

    //deleteUser()

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

    //deleteProductType()

    @Test
    public void testDeleteNotExistingProductType() {
        it.polito.ezshop.model.ProductType u = new it.polito.ezshop.model.ProductType(1, "1231231231232", "Dark Souls, only love reactions", 1.0, 1, 0.0, "", "");
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteProductType(u));
    }

    @Test
    public void testDeleteNullProductType() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteProductType(null));
    }

    @Test
    public void testDeleteExistingProductType() {

        it.polito.ezshop.model.ProductType u = new it.polito.ezshop.model.ProductType(1, "1231231231232", "homo erectus", 1.0, 1, 0.0, "", "2-a-2");
        if (!DataManager.getInstance().insertProductType(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().deleteProductType(u));
        assertFalse(DataManager.getInstance().getProductTypes().contains(u));
    }

    //deletePosition()

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

    //deleteOrder()

    @Test
    public void testDeleteNotExistingOrder() {
        it.polito.ezshop.model.Order u = new it.polito.ezshop.model.Order(1, 1.0, 1, new ProductType(1, "1231231231232", "austrolopiteco", 0.1, 1, 0.0, "", null), EOrderStatus.ISSUED);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteOrder(u));
    }

    @Test
    public void testDeleteNullOrder() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteOrder(null));
    }

    @Test
    public void testDeleteExistingOrder() {

        it.polito.ezshop.model.Order u = new it.polito.ezshop.model.Order(1, 1.0, 1, new ProductType(1, "1231231231232", "homo poco sapiens", 0.1, 1, 0.0, "", null), EOrderStatus.ISSUED);
        if (!DataManager.getInstance().insertOrder(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getOrderId() + "; true expected", DataManager.getInstance().deleteOrder(u));
        assertFalse(DataManager.getInstance().getOrders().contains(u));
    }

    //deleteCustomer()

    @Test
    public void testDeleteNotExistingCustomer() {
        it.polito.ezshop.model.Customer u = new it.polito.ezshop.model.Customer(1, "pippiniellu", null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteCustomer(u));
    }

    @Test
    public void testDeleteNullCustomer() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteCustomer(null));
    }

    @Test
    public void testDeleteExistingCustomer() {

        it.polito.ezshop.model.Customer u = new it.polito.ezshop.model.Customer(1, "Salvatore D'aquì", null);
        if (!DataManager.getInstance().insertCustomer(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().deleteCustomer(u));
        assertFalse(DataManager.getInstance().getCustomers().contains(u));
    }

    //deleteLoyaltyCard()

    @Test
    public void testDeleteNotExistingLoyaltyCard() {
        LoyaltyCard u = new LoyaltyCard("0000000001", 0, null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteLoyaltyCard(u));
    }

    @Test
    public void testDeleteNullLoyaltyCard() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteLoyaltyCard(null));
    }

    @Test
    public void testDeleteExistingLoyaltyCard() {

        LoyaltyCard u = new LoyaltyCard("0000000001", 0, null);
        if (!DataManager.getInstance().insertLoyaltyCard(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getID() + "; true expected", DataManager.getInstance().deleteLoyaltyCard(u));
        assertFalse(DataManager.getInstance().getLoyaltyCards().contains(u));
    }

    //deleteSale()

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

    //deleteReturn()

    @Test
    public void testDeleteNotExistingReturn() {
        CReturn u = new CReturn(1, new Sale(1, 0.23, null));
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().deleteReturn(u));
    }

    @Test
    public void testDeleteNullReturn() {
        assertFalse("Item null, false expected", DataManager.getInstance().deleteReturn(null));
    }

    @Test
    public void testDeleteExistingReturn() {

        CReturn u = new CReturn(1, new Sale(1, 0.0, null));
        if (!DataManager.getInstance().insertReturn(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getReturnId() + "; true expected", DataManager.getInstance().deleteReturn(u));
        assertFalse(DataManager.getInstance().getReturns().contains(u));
    }

    //deleteDummyCredit()

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

    //deleteDummyDebit()

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

    //deleteBalanceTransaction()

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

    ////Inserts

    //insertUser()

    @Test
    public void testInsertExistingUser() {
        User u1 = new User(1, "u1", "a", "Administrator");
        DataManager.getInstance().insertUser(u1);
        assertFalse("User already exists, false expected", DataManager.getInstance().insertUser(u1));
    }

    @Test
    public void testInsertUser() {
        User u1 = new User(1, "u1", "a", "Administrator");
        assertTrue("User inserted successfully", DataManager.getInstance().insertUser(u1));
    }

    @Test
    public void testInsertNullUser() {
        assertFalse("User null, false expected", DataManager.getInstance().insertUser(null));
    }

    //insertProductType()

    @Test
    public void testInsertExistingProductType() {
        ProductType p1 = new ProductType(1, "0000000000000", "p1", 1.0, 1, 0.1, "", "1-a-1");
        DataManager.getInstance().insertProductType(p1);
        assertFalse("ProductType already exists, false expected", DataManager.getInstance().insertProductType(p1));
    }

    @Test
    public void testInsertProductType() {
        ProductType p1 = new ProductType(1, "0000000000000", "p1", 1.0, 1, 0.1, "", "1-a-1");
        assertTrue("ProductType inserted successfully", DataManager.getInstance().insertProductType(p1));
    }

    @Test
    public void testInsertNullProductType() {
        assertFalse("ProductType null, false expected", DataManager.getInstance().insertProductType(null));
    }

    //insertPosition()

    @Test
    public void testInsertExistingPosition() {
        Position p1 = new Position(1, "a", 1, null);
        DataManager.getInstance().insertPosition(p1);
        assertFalse("Position already exists, false expected", DataManager.getInstance().insertPosition(p1));
    }

    @Test
    public void testInsertPosition() {
        Position p1 = new Position(1, "a", 1, null);
        assertTrue("Position inserted successfully", DataManager.getInstance().insertPosition(p1));
    }

    @Test
    public void testInsertNullPosition() {
        assertFalse("Position null, false expected", DataManager.getInstance().insertPosition(null));
    }

    //insertOrder()

    @Test
    public void testInsertExistingOrder() {
        Order o1 = new Order(1, 1.0, 1, new ProductType(1, "0000000000000", "p1", 1.0, 1, 0.1, "", "1-a-1"), EOrderStatus.ISSUED);
        DataManager.getInstance().insertOrder(o1);
        assertFalse("Order already exists, false expected", DataManager.getInstance().insertOrder(o1));
    }

    @Test
    public void testInsertOrder() {
        Order o1 = new Order(1, 1.0, 1, new ProductType(1, "0000000000000", "p1", 1.0, 1, 0.1, "", "1-a-1"), EOrderStatus.ISSUED);
        assertTrue("Order inserted successfully", DataManager.getInstance().insertOrder(o1));
    }

    @Test
    public void testInsertNullOrder() {
        assertFalse("Order null, false expected", DataManager.getInstance().insertOrder(null));
    }

    //insertCustomer()

    @Test
    public void testInsertExistingCustomer() {
        Customer c1 = new Customer(1, "c1", null);
        DataManager.getInstance().insertCustomer(c1);
        assertFalse("Customer already exists, false expected", DataManager.getInstance().insertCustomer(c1));
    }

    @Test
    public void testInsertCustomer() {
        Customer c1 = new Customer(1, "c1", null);
        assertTrue("Customer inserted successfully", DataManager.getInstance().insertCustomer(c1));
    }

    @Test
    public void testInsertNullCustomer() {
        assertFalse("Customer null, false expected", DataManager.getInstance().insertCustomer(null));
    }

    //insertLoyaltyCard()

    @Test
    public void testInsertExistingLoyaltyCard() {
        LoyaltyCard lc1 = new LoyaltyCard("1000000000", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lc1);
        assertFalse("LoyaltyCard already exists, false expected", DataManager.getInstance().insertLoyaltyCard(lc1));
    }

    @Test
    public void testInsertLoyaltyCard() {
        LoyaltyCard lc1 = new LoyaltyCard("0000000001", 1, null);
        assertTrue("LoyaltyCard inserted successfully", DataManager.getInstance().insertLoyaltyCard(lc1));
    }

    @Test
    public void testInsertNullLoyaltyCard() {
        assertFalse("LoyaltyCard null, false expected", DataManager.getInstance().insertLoyaltyCard(null));
    }

    //insertSale()

    @Test
    public void testInsertExistingSale() {
        Sale s1 = new Sale(1, 0.1, null);
        DataManager.getInstance().insertSale(s1);
        assertFalse("Sale already exists, false expected", DataManager.getInstance().insertSale(s1));
    }

    @Test
    public void testInsertSale() {
        Sale s1 = new Sale(1, 0.1, null);
        assertTrue("Sale inserted successfully", DataManager.getInstance().insertSale(s1));
    }

    @Test
    public void testInsertNullSale() {
        assertFalse("Sale null, false expected", DataManager.getInstance().insertSale(null));
    }

    //insertReturn()

    @Test
    public void testInsertExistingReturn() {
        CReturn r1 = new CReturn(1, new Sale(1, 0.0, null));
        DataManager.getInstance().insertReturn(r1);
        assertFalse("Return already exists, false expected", DataManager.getInstance().insertReturn(r1));
    }

    @Test
    public void testInsertReturn() {
        CReturn r1 = new CReturn(1, new Sale(1, 0.0, null));
        assertTrue("Return inserted successfully", DataManager.getInstance().insertReturn(r1));
    }

    @Test
    public void testInsertNullReturn() {
        assertFalse("Return null, false expected", DataManager.getInstance().insertReturn(null));
    }

    //insertDummyCredit()

    @Test
    public void testInsertExistingDummyCredit() {
        DummyCredit dc1 = new DummyCredit(1, 1.0);
        DataManager.getInstance().insertDummyCredit(dc1);
        assertFalse("DummyCredit already exists, false expected", DataManager.getInstance().insertDummyCredit(dc1));
    }

    @Test
    public void testInsertDummyCredit() {
        DummyCredit dc1 = new DummyCredit(1, 1.0);
        assertTrue("DummyCredit inserted successfully", DataManager.getInstance().insertDummyCredit(dc1));
    }

    @Test
    public void testInsertNullDummyCredit() {
        assertFalse("DummyCredit null, false expected", DataManager.getInstance().insertDummyCredit(null));
    }

    //insertDummyDebit()

    @Test
    public void testInsertExistingDummyDebit() {
        DummyDebit dd1 = new DummyDebit(1, 1.0);
        DataManager.getInstance().insertDummyDebit(dd1);
        assertFalse("DummyDebit already exists, false expected", DataManager.getInstance().insertDummyDebit(dd1));
    }

    @Test
    public void testInsertDummyDebit() {
        DummyDebit dd1 = new DummyDebit(1, 1.0);
        assertTrue("DummyDebit inserted successfully", DataManager.getInstance().insertDummyDebit(dd1));
    }

    @Test
    public void testInsertNullDummyDebit() {
        assertFalse("DummyDebit null, false expected", DataManager.getInstance().insertDummyDebit(null));
    }

    //insertBalanceTransaction()

    @Test
    public void testInsertExistingBalanceTransaction() {
        CreditTransaction bt1 = new CreditTransaction(1, new Sale(1, 0.1, null));
        DataManager.getInstance().insertBalanceTransaction(bt1);
        assertFalse("BalanceTransaction already exists, false expected", DataManager.getInstance().insertBalanceTransaction(bt1));
    }

    @Test
    public void testInsertBalanceTransaction() {
        CreditTransaction bt1 = new CreditTransaction(1, new Sale(1, 0.1, null));
        assertTrue("BalanceTransaction inserted successfully", DataManager.getInstance().insertBalanceTransaction(bt1));
    }

    @Test
    public void testNullInsertBalanceTransaction() {
        assertFalse("BalanceTransaction null, false expected", DataManager.getInstance().insertDummyDebit(null));
    }

    ////Getters

    //getUsers()

    @Test
    public void testEmptyGetUsers(){
        assertEquals(DataManager.getInstance().getUsers().size(), 0);
    }

    @Test
    public void testFilledGetUsers(){
        User user = new User(1, "Gianni", "14adfea356e*", "Cashier");
        DataManager.getInstance().insertUser(user);
        assertTrue("User present, true expected", DataManager.getInstance().getUsers().contains(user));
    }

    //getProductTypes()

    @Test
    public void testEmptyGetProductTypes(){
        assertEquals(DataManager.getInstance().getProductTypes().size(), 0);
    }

    @Test
    public void testFilledGetProductTypes(){
        ProductType pt = new ProductType(1, "1231231231232", "fancy thing", 12.0, 7, 0.25, "nothingtosay", null);
        DataManager.getInstance().insertProductType(pt);
        assertTrue("ProductType present, true expected", DataManager.getInstance().getProductTypes().contains(pt));
    }

    //getPositions()

    @Test
    public void testEmptyGetPositions(){
        assertEquals(DataManager.getInstance().getPositions().size(), 0);
    }

    @Test
    public void testFilledGetPositions(){
        Position pos = new Position(1, "a", 1, null);
        DataManager.getInstance().insertPosition(pos);
        assertTrue("Position present, true expected", DataManager.getInstance().getPositions().contains(pos));
    }

    //getOrders()

    @Test
    public void testEmptyGetOrders(){
        assertEquals(DataManager.getInstance().getOrders().size(), 0);
    }

    @Test
    public void testFilledGetOrders(){
        Order ord = new Order(1, 1.0, 1, new ProductType(1, "0000000000000", "p1", 1.0, 1, 0.1, "", "1-a-1"), EOrderStatus.ISSUED);
        DataManager.getInstance().insertOrder(ord);
        assertTrue("Order present, true expected", DataManager.getInstance().getOrders().contains(ord));
    }

    //getCustomers()

    @Test
    public void testEmptyGetCustomers(){
        assertEquals(DataManager.getInstance().getCustomers().size(), 0);
    }

    @Test
    public void testFilledGetCustomers(){
        Customer c = new Customer(1, "c1", null);
        DataManager.getInstance().insertCustomer(c);
        assertTrue("Customer present, true expected", DataManager.getInstance().getCustomers().contains(c));
    }

    //getLoyaltyCards()

    @Test
    public void testEmptyGetLoyaltyCards(){
        assertEquals(DataManager.getInstance().getLoyaltyCards().size(), 0);
    }

    @Test
    public void testFilledGetLoyaltyCards(){
        LoyaltyCard lt = new LoyaltyCard("0000000010", 0, null);
        DataManager.getInstance().insertLoyaltyCard(lt);
        assertTrue("LoyaltyCard present, true expected", DataManager.getInstance().getLoyaltyCards().contains(lt));
    }

    //getReturns()

    @Test
    public void testEmptyGetReturns(){
        assertEquals(DataManager.getInstance().getReturns().size(), 0);
    }

    @Test
    public void testFilledGetReturns(){
        CReturn ret = new CReturn(1, new Sale(1, 0.0, null));
        DataManager.getInstance().insertReturn(ret);
        assertTrue("Return present, true expected", DataManager.getInstance().getReturns().contains(ret));
    }

    //getDummyCredits()

    @Test
    public void testEmptyGetDummyCredits(){
        assertEquals(DataManager.getInstance().getDummyCredits().size(), 0);
    }

    @Test
    public void testFilledGetDummyCredits(){
        DummyCredit dc = new DummyCredit(1, 1.0);
        DataManager.getInstance().insertDummyCredit(dc);
        assertTrue("DummyCredit present, true expected", DataManager.getInstance().getDummyCredits().contains(dc));
    }

    //getDummyDebits()

    @Test
    public void testEmptyGetDummyDebits(){
        assertEquals(DataManager.getInstance().getDummyDebits().size(), 0);
    }

    @Test
    public void testFilledGetDummyDebits(){
        DummyDebit dd = new DummyDebit(1, 1.0);
        DataManager.getInstance().insertDummyDebit(dd);
        assertTrue("DummyDebit present, true expected", DataManager.getInstance().getDummyDebits().contains(dd));
    }

    //getBalanceTransactions()

    @Test
    public void testEmptyGetBalanceTransactions(){
        assertEquals(DataManager.getInstance().getBalanceTransactions().size(), 0);
    }

    @Test
    public void testFilledGetBalanceTransactions(){
        CreditTransaction bt1 = new CreditTransaction(1, new Sale(1, 0.1, null));
        DataManager.getInstance().insertBalanceTransaction(bt1);
        assertTrue("BalanceTransaction present, true expected", DataManager.getInstance().getBalanceTransactions().contains(bt1));
    }
    
    ////Updates

    //updateUser()

    @Test
    public void testUpdateNotExistingUser() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateUser(u));
    }

    @Test
    public void testUpdateNullUser() {
        assertFalse("User null, false expected", DataManager.getInstance().updateUser(null));
    }

    @Test
    public void testUpdateExistingUser() {

        User u = new User(1, "we", "mbare", "Administrator");
        if (!DataManager.getInstance().insertUser(u)) {
            throw new RuntimeException();
        }
        u.setUsername("user");
        assertTrue("Databased contains " + u.getId() + " : " + u.getUsername() + "; true expected", DataManager.getInstance().updateUser(u));
        int i = DataManager.getInstance().getUsers().indexOf(u);
        User us = DataManager.getInstance().getUsers().get(i);
        assertEquals(us.getUsername(),"user");
    }
    
    //updateProductType()

    @Test
    public void testUpdateNotExistingProductType() {
        it.polito.ezshop.model.ProductType u = new it.polito.ezshop.model.ProductType(1, "1231231231232", "des", 1.25, 1, 0.0, null, "1-a-1");
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateProductType(u));
    }

    @Test
    public void testUpdateNullProductType() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateProductType(null));
    }

    @Test
    public void testUpdateExistingProductType() {
        
        it.polito.ezshop.model.ProductType u = new it.polito.ezshop.model.ProductType(1, "1231231231232", "description", 1.0, 1, 0.0, "", "");
        if (!DataManager.getInstance().insertProductType(u)) {
            throw new RuntimeException();
        }
        
        u.setBarCode("0000000000000");
        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().updateProductType(u));
        int i = DataManager.getInstance().getProductTypes().indexOf(u);
        ProductType prod = DataManager.getInstance().getProductTypes().get(i);
        assertEquals(prod.getBarCode(),"0000000000000");
        
    }

    //updatePosition()

    @Test
    public void testUpdateNotExistingPosition() {
        Position u = new Position(3, "a", 2, null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updatePosition(u));
    }

    @Test
    public void testUpdateNullPosition() {
        assertFalse("Item null, false expected", DataManager.getInstance().updatePosition(null));
    }

    @Test
    public void testUpdateExistingPosition() {

        Position u = new Position(3, "a", 2, null);
        if (!DataManager.getInstance().insertPosition(u)) {
            throw new RuntimeException();
        }

        u.setAisleID(6);
        assertTrue("Databased contains " + u.toString() + "; true expected", DataManager.getInstance().updatePosition(u));
        int i = DataManager.getInstance().getPositions().indexOf(u);
        Position pos = DataManager.getInstance().getPositions().get(i);
        Integer j =  6;
        assertEquals(pos.getAisleID(),j);
    }

    //updateOrder()

    @Test
    public void testUpdateNotExistingOrder() {
        it.polito.ezshop.model.Order u = new it.polito.ezshop.model.Order(1, 1.0, 1, new ProductType(1, "1231231231232", "this is a description, as you can see", 0.1, 1, 0.0, "", null), EOrderStatus.ISSUED);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateOrder(u));
    }

    @Test
    public void testUpdateNullOrder() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateOrder(null));
    }

    @Test
    public void testUpdateExistingOrder() {

        it.polito.ezshop.model.Order u = new it.polito.ezshop.model.Order(1, 1.0, 1, new ProductType(1, "1231231231232", "this is a description, as you can see", 0.1, 1, 0.0, "", null), EOrderStatus.ISSUED);
        if (!DataManager.getInstance().insertOrder(u)) {
            throw new RuntimeException();
        }

        u.setQuantity(4);
        assertTrue("Databased contains " + u.getOrderId() + "; true expected", DataManager.getInstance().updateOrder(u));
        int i = DataManager.getInstance().getOrders().indexOf(u);
        Order ord = DataManager.getInstance().getOrders().get(i);
        assertEquals(ord.getQuantity(), 4);
    }

    //updateCustomer()

    @Test
    public void testUpdateNotExistingCustomer() {
        it.polito.ezshop.model.Customer u = new it.polito.ezshop.model.Customer(1, "antonino", null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateCustomer(u));
    }

    @Test
    public void testUpdateNullCustomer() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateCustomer(null));
    }

    @Test
    public void testUpdateExistingCustomer() {

        it.polito.ezshop.model.Customer u = new it.polito.ezshop.model.Customer(1, "canavacciuolo", null);
        if (!DataManager.getInstance().insertCustomer(u)) {
            throw new RuntimeException();
        }

        u.setCustomerName("gianni");
        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().updateCustomer(u));
        int i = DataManager.getInstance().getCustomers().indexOf(u);
        Customer cust = DataManager.getInstance().getCustomers().get(i);
        assertEquals(cust.getCustomerName(),"gianni");
    }

    //updateLoyaltyCard()

    @Test 
    public void testUpdateNotExistingLoyaltyCard() {
        LoyaltyCard u = new LoyaltyCard("0000000001", 0, null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateLoyaltyCard(u));
    }

    @Test
    public void testUpdateNullLoyaltyCard() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateLoyaltyCard(null));
    }

    @Test
    public void testUpdateExistingLoyaltyCard() {

        LoyaltyCard u = new LoyaltyCard("0000000001", 0, null);
        if (!DataManager.getInstance().insertLoyaltyCard(u)) {
            throw new RuntimeException();
        }

        assertTrue("Databased contains " + u.getID() + "; true expected", DataManager.getInstance().updateLoyaltyCard(u));
      
    }

    //updateSale()

    @Test
    public void testUpdateNotExistingSale() {
        it.polito.ezshop.model.Sale u = new it.polito.ezshop.model.Sale(1, 0.0, null);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateSale(u));
    }

    @Test
    public void testUpdateNullSale() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateSale(null));
    }

    @Test
    public void testUpdateExistingSale() {

        it.polito.ezshop.model.Sale u = new it.polito.ezshop.model.Sale(1, 0.0, null);
        if (!DataManager.getInstance().insertSale(u)) {
            throw new RuntimeException();
        }

        u.setAsCommitted();
        assertTrue("Databased contains " + u.getTicketNumber() + "; true expected", DataManager.getInstance().updateSale(u));
   
        int i = DataManager.getInstance().getSales().indexOf(u);
        Sale sale = DataManager.getInstance().getSales().get(i);
        assertEquals(sale.isCommitted(),true);
       
    }

    //updateReturn()

    @Test
    public void testUpdateNotExistingReturn() {
        CReturn u = new CReturn(1, new Sale(1, 0.0, null));
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateReturn(u));
    }

    @Test
    public void testUpdateNullReturn() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateReturn(null));
    }

    @Test
    public void testUpdateExistingReturn() {

        CReturn u = new CReturn(1, new Sale(1, 0.0, null));
        if (!DataManager.getInstance().insertReturn(u)) {
            throw new RuntimeException();
        }
        
        u.setAsCommitted();
        assertTrue("Databased contains " + u.getReturnId() + "; true expected", DataManager.getInstance().updateReturn(u));
     
        int i = DataManager.getInstance().getReturns().indexOf(u);
        CReturn cret = DataManager.getInstance().getReturns().get(i);
        assertEquals(cret.isCommitted(),true);
    }

    //updateDummyCredit()

    @Test
    public void testUpdateNotExistingDummyCredit() {
        DummyCredit u = new DummyCredit(1, 23.9);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateDummyCredit(u));
    }

    @Test
    public void testUpdateNullDummyCredit() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateDummyCredit(null));
    }

    @Test
    public void testUpdateExistingDummyCredit() {

        DummyCredit u = new DummyCredit(1, 23.9);
        if (!DataManager.getInstance().insertDummyCredit(u)) {
            throw new RuntimeException();
        }

        u.setValue(2.3);
        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().updateDummyCredit(u));
     
        int i = DataManager.getInstance().getDummyCredits().indexOf(u);
        DummyCredit dc = DataManager.getInstance().getDummyCredits().get(i);
        assertEquals(dc.getValue(),0,2.3);
    }

    //updateDummyDebit()

    @Test
    public void testUpdateNotExistingDummyDebit() {
        DummyDebit u = new DummyDebit(1, 23.9);
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateDummyDebit(u));
    }

    @Test
    public void testUpdateNullDummyDebit() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateDummyDebit(null));
    }

    @Test
    public void testUpdateExistingDummyDebit() {

        DummyDebit u = new DummyDebit(1, 23.9);
        if (!DataManager.getInstance().insertDummyDebit(u)) {
            throw new RuntimeException();
        }

        u.setValue(2.3);
        assertTrue("Databased contains " + u.getId() + "; true expected", DataManager.getInstance().updateDummyDebit(u));
     
        int i = DataManager.getInstance().getDummyDebits().indexOf(u);
        DummyDebit dd = DataManager.getInstance().getDummyDebits().get(i);
        assertEquals(dd.getValue(),0,2.3);
    }

    //updateBalanceTransaction()

    @Test
    public void testUpdateNotExistingBalanceTransaction() {
        BalanceTransaction u = new CreditTransaction(1, new DummyCredit(2, 3.0));
        assertFalse("Item doesn't exist, false expected", DataManager.getInstance().updateBalanceTransaction(u));
    }

    @Test
    public void testUpdateNullBalanceTransaction() {
        assertFalse("Item null, false expected", DataManager.getInstance().updateBalanceTransaction(null));
    }

    @Test
    public void testUpdateExistingBalanceTransaction() {

        BalanceTransaction u = new CreditTransaction(1, new DummyCredit(2, 3.0));
        if (!DataManager.getInstance().insertBalanceTransaction(u)) {
            throw new RuntimeException();
        }
        u.setDescription("prova");
        assertTrue("Databased contains " + u.getBalanceId() + "; true expected", DataManager.getInstance().updateBalanceTransaction(u));
        int i = DataManager.getInstance().getBalanceTransactions().indexOf(u);
        BalanceTransaction bt = DataManager.getInstance().getBalanceTransactions().get(i);
        assertEquals(bt.getDescription(),"prova");
    }

}
