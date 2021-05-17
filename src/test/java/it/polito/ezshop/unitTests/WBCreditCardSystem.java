package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.*;

import it.polito.ezshop.data.CreditCardSystem;

public class WBCreditCardSystem {

    @Test
    public void testValidNumberWithNull() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber(null));
    }

    @Test
    public void testValidNumberWithCorrectInput13Loops() {
        assertTrue(CreditCardSystem.getInstance().isValidNumber("1234567890318"));
    }

    @Test
    public void testValidNumberWithCorrectInput16Loops() {
        assertTrue(CreditCardSystem.getInstance().isValidNumber("9254347527611304"));
    }


}
