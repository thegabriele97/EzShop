package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.model.User;
import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.data.LoginManager;
import it.polito.ezshop.data.RightsManager;

public class RightsManagerTest {
    
    @Before
    @After
    public void resetLoginStatus() {
        
    	LoginManager.getInstance().tryLogout();
        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        for (User u : DataManager.getInstance().getUsers()) {
            DataManager.getInstance().deleteUser(u);
        }

    }

   
    @Test
    public void testCanManageUsers() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canManageUsers(u));
    }
    
    @Test
    public void testCanManageUsersNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canManageUsers(u));
    } 
    
    @Test
    public void testCanManageUsersNotAdmin() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertFalse(RightsManager.getInstance().canManageUsers(u));
    }
    
    @Test
    public void testCanManageProductsCatalogue() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canManageProductsCatalogue(u));
    }
    
    @Test
    public void testCanManageProductsCatalogueCashier() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertFalse(RightsManager.getInstance().canManageProductsCatalogue(u));
    }
    @Test
    public void testCanManageProductsCatalogueNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canManageProductsCatalogue(u));
    }
    
    @Test
    public void testCanListAllProductTypes() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canListAllProductTypes(u));
    }
    @Test
    public void testCanListAllProductTypesNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canListAllProductTypes(u));
    }
    
    @Test
    public void testCanManageInventory() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canManageInventory(u));
    }
    @Test
    public void testCanManageInventoryNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canManageInventory(u));
    }
    
    @Test
    public void testCanManageInventoryCashier() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertFalse(RightsManager.getInstance().canManageInventory(u));
    }
    
    @Test
    public void testCanManageCustomers() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canManageCustomers(u));
    }
    @Test
    public void testCanManageCustomersNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canManageCustomers(u));
    }
    @Test
    public void testCanManageSaleTransactions() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canManageSaleTransactions(u));
    }
    @Test
    public void testCanManageSaleTransactionsNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canManageSaleTransactions(u));
    }
    
    @Test
    public void testCanManagePayments() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canManagePayments(u));
    }
    @Test
    public void testCanManagePaymentsNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canManagePayments(u));
    }
    
    @Test
    public void testCanManageAccounting() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canManageAccounting(u));
    }
    
    @Test
    public void testCanManageAccountingNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canManageAccounting(u));
    }
    
    @Test
    public void testCanManageAccountingCashier() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertFalse(RightsManager.getInstance().canManageAccounting(u));
    }
    
    @Test
    public void testCanManageBalanceTransactions() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertTrue(RightsManager.getInstance().canManageBalanceTransactions(u));
    }
    
    @Test
    public void testCanManageBalanceTransactionsNotLogged() {
        User u = new User(1, "ciao", "pwd", "Administrator");
        DataManager.getInstance().insertUser(u);
        
        assertFalse(RightsManager.getInstance().canManageBalanceTransactions(u));
    }
    
    @Test
    public void testCanManageBalanceTransactionsCashier() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        LoginManager.getInstance().tryLogin("ciao", "pwd");
        
        assertFalse(RightsManager.getInstance().canManageBalanceTransactions(u));
    }
    
    
    
}
