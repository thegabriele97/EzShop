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
    public void testValidNumberWithCorrectInput0Loops() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber("1"));
    }

    @Test
    public void testValidNumberWithCorrectInput1Loops() {
        assertTrue(CreditCardSystem.getInstance().isValidNumber("18"));
    }

    @Test
    public void testValidNumberWithCorrectInputALotOfLoops() {
        assertTrue(CreditCardSystem.getInstance().isValidNumber("9254347527611304"));
    }

    @Test
    public void testIsRegisteredWithNoValidInput() {
        assertFalse(CreditCardSystem.getInstance().isRegistered(null));
    }

    @Test
    public void testIsRegisteredWithValidInput() {
        assertTrue(CreditCardSystem.getInstance().isRegistered("9254347527611304"));
    }

}
