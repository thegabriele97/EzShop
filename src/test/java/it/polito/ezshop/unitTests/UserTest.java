package it.polito.ezshop.unitTests;

import it.polito.ezshop.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testSetAndGetPassword() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertEquals("mbare", u.getPassword());
    }

    @Test
    public void testSetAndGetNullPassword() {
        User u = new User(1, "we", null, "Administrator");
        assertEquals(null, u.getPassword());
    }


    @Test
    public void testSetAndGetUsername() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertEquals("we", u.getUsername());
    }

    @Test
    public void testSetAndGetNullUsername() {
        User u = new User(1, null, "mbare", "Administrator");
        assertEquals(null, u.getUsername());
    }

    @Test
    public void testSetAndGetId() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertEquals(new Integer(1), u.getId());
    }

    @Test
    public void testSetAndGetInvalidId() {
        User u = new User(1, "we", "mbare", "Administrator");
        u.setId(-1);
        assertEquals(new Integer(1), u.getId());
    }

    @Test
    public void testSetAndGetRole() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertEquals("Administrator", u.getRole());
    }

    @Test
    public void testSetAndGetInvalidRole() {
        User u = new User(1, "we", "mbare", "Mbare");
        assertEquals(null, u.getRole());
    }

    @Test
    public void testHashCode() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertEquals(1, u.hashCode());
    }

    @Test
    public void testEquals() {
        User u1 = new User(1, "we", "mbare", "Administrator");
        User u2 = new User(1, "we", "mbare", "Administrator");
        assertTrue(u1.equals(u2));
    }


}