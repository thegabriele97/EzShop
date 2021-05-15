package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import it.polito.ezshop.data.CreditCardSystem;
import org.junit.*;

public class CreditCardSystemTest {
    
    @Test
    public void testValidCreditCardWithCharacters() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber("123s23xa223"));
    }

    @Test
    public void testValidCreditCardWithAll9s() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber("99999999999"));
    }

    @Test
    public void testValidCreditCardWithAll0s() {
        assertTrue(CreditCardSystem.getInstance().isValidNumber("000000000000"));
    }

    @Test
    public void testValidCreditCardWithValidNumber() {
        assertTrue(CreditCardSystem.getInstance().isValidNumber("123456789015"));
    }

    @Test
    public void testValidCreditCardWithEmptyString() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber(""));
    }

    @Test
    public void testValidCreditCardWithNullString() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber(null));
    }

    @Test
    public void testCardRegisteredWValidCreditCard() {
        assertTrue(CreditCardSystem.getInstance().isRegistered("9254347527611304"));
    }

    @Test
    public void testCardRegisteredWValidCreditCardNotRegistered() {
        assertFalse(CreditCardSystem.getInstance().isRegistered("123456789015"));
    }

    @Test
    public void testCardRegisteredWNoValidCreditCard() {
        assertFalse(CreditCardSystem.getInstance().isRegistered("999999999999"));
    }

}
