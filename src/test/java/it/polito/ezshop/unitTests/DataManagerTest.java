package it.polito.ezshop.unitTests;
import org.junit.runner.RunWith;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.model.User;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

public class DataManagerTest {

    @Before
    @After
    public void cleanDatabase() {
        
        for (User u : DataManager.getInstance().getUsers()) {
            DataManager.getInstance().deleteUser(u);
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
    }

}
