package it.polito.ezshop.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.polito.ezshop.model.User;
import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.data.LoginManager;

public class LoginManagerTest {
    
    @Before
    @After
    public void resetLoginStatus() {
        
        LoginManager.getInstance().tryLogout();
        if (LoginManager.getInstance().isUserLogged()) throw new RuntimeException();

        for (User u : DataManager.getInstance().getUsers()) {
            DataManager.getInstance().deleteUser(u);
        }

    }

    //TODO: check this, here we are trying to login with a different user without logout first.
    @Test
    public void testLoginManagerTryLoginWithLoggedUser() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        User u2 = new User(2, "ciao2", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);
        DataManager.getInstance().insertUser(u2);

        assertTrue(LoginManager.getInstance().tryLogin("ciao", "pwd"));
        assertTrue(LoginManager.getInstance().tryLogin("ciao2", "pwd"));
    }

    @Test
    public void testLoginManagerTryLoginWithNullUsername() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        assertFalse(LoginManager.getInstance().tryLogin(null, "pwd"));
    }

    @Test
    public void testLoginManagerTryLoginWithNullPassword() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        assertFalse(LoginManager.getInstance().tryLogin("ciao", null));
    }

    @Test
    public void testLoginManagerTryLoginWithNull() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        assertFalse(LoginManager.getInstance().tryLogin(null, null));
    }

    @Test
    public void testLoginManagerTryLogoutWithoutLogin() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        assertFalse(LoginManager.getInstance().tryLogout());
    }

    @Test
    public void testLoginManagerTryLogoutWithLogin() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        assertTrue(LoginManager.getInstance().tryLogin("ciao", "pwd"));
        assertTrue(LoginManager.getInstance().tryLogout());
        assertFalse(LoginManager.getInstance().tryLogout());
    }

    @Test
    public void testLoginManagerIsUserLoggedWithNoLogged() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        assertFalse(LoginManager.getInstance().isUserLogged());
        assertFalse(LoginManager.getInstance().isUserLogged(u));
    }

    @Test
    public void testLoginManagerIsUserLoggedNull() {
        assertFalse(LoginManager.getInstance().isUserLogged(null));
    }

    @Test
    public void testLoginManagerIsUserLoggedAfterLogin() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        assertTrue(LoginManager.getInstance().tryLogin("ciao", "pwd"));

        assertTrue(LoginManager.getInstance().isUserLogged());
        assertTrue(LoginManager.getInstance().isUserLogged(u));
    }

    @Test
    public void testLoginManagerIsUserLoggedWithDiffUserAfterLogin() {
        User u1 = new User(1, "ciao", "pwd", "Cashier");
        User u2 = new User(2, "ciao2", "pwd", "Cashier"); 
        DataManager.getInstance().insertUser(u1);
        DataManager.getInstance().insertUser(u2);

        assertTrue(LoginManager.getInstance().tryLogin("ciao", "pwd"));

        assertTrue(LoginManager.getInstance().isUserLogged());
        assertTrue(LoginManager.getInstance().isUserLogged(u1));
        assertFalse(LoginManager.getInstance().isUserLogged(u2));
    }

    @Test
    public void testLoginManagerGetLoggedUserWithNoLogged() {
        assertNull(LoginManager.getInstance().getLoggedUser());
    }

    @Test
    public void testLoginManagerGetLoggedUserWithLogged() {
        User u = new User(1, "ciao", "pwd", "Cashier");
        DataManager.getInstance().insertUser(u);

        assertTrue(LoginManager.getInstance().tryLogin("ciao", "pwd"));
        assertEquals(u, LoginManager.getInstance().getLoggedUser());
    }
    

}
