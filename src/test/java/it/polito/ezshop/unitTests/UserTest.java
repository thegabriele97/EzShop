package it.polito.ezshop.unitTests;

import it.polito.ezshop.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testSettersAndGettersOfUser() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertEquals("mbare", u.getPassword());
        assertEquals("we", u.getUsername());
        assertEquals(Integer.valueOf(1), u.getId());
        assertEquals("Administrator", u.getRole());
    }

    @Test
    public void testSetNullPassword() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertThrows(IllegalArgumentException.class, () -> { u.setPassword(null); });
    }

    @Test
    public void testSetNullUsername() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertThrows(IllegalArgumentException.class, () -> { u.setUsername(null); });
    }

    @Test
    public void testSetInvalidId() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertThrows(IllegalArgumentException.class, () -> { u.setId(-1); });

    }

    @Test
    public void testSetInvalidRole() {
        User u = new User(1, "we", "mbare", "Administrator");
        assertThrows(IllegalArgumentException.class, () -> { u.setRole("mbare"); });
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