package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import it.polito.ezshop.data.CreditCardSystem;
import org.junit.*;

public class BBCreditCardSystemTest {
    
    //isValidNumber()

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

    //isRegistered()

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

    @Test
    public void testCardRegisteredWithCharacters() {
        assertFalse(CreditCardSystem.getInstance().isRegistered("123s23xa223"));
    }

    @Test
    public void testCardRegisteredWEmptyString() {
        assertFalse(CreditCardSystem.getInstance().isRegistered(""));
    }

    @Test
    public void testCardRegisteredWNullString() {
        assertFalse(CreditCardSystem.getInstance().isRegistered(null));
    }

    //hasEnoughBalance()

    @Test
    public void testHasBalanceWValidCreditCard(){
        assertTrue(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", 10.0));
    }

    @Test
    public void testHasBalanceWInvalidCreditCard(){
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("sas", 10.0));
    }

    @Test
    public void testHasBalanceWUnregisteredCreditCard(){
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("123456789015", 10.0));
    }

    @Test
    public void testHasBalanceWNotEnoughBalance(){
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", 100.0));
    }

    @Test
    public void testHasBalanceWNegativeToRemove(){
        assertTrue(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", -10.0)); 
        //Ma è ok che sia true? (no controlli sul toRemove)
    }

    @Test
    public void testHasBalanceWZeroToRemove(){
        assertTrue(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", 0.0));
    }

    @Test
    public void testHasBalanceWNaNToRemove(){
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", Double.NaN));
    }

    @Test
    public void testHasBalanceWPositiveInfinityToRemove(){
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", Double.POSITIVE_INFINITY));
    }

    @Test
    public void testHasBalanceWNegativeInfinityToRemove(){
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", Double.POSITIVE_INFINITY));
    }

    //updateBalance()

    

}
