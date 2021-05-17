package it.polito.ezshop.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import it.polito.ezshop.data.CreditCardSystem;
import org.junit.*;

public class BBCreditCardSystemTest {
    
    //isValidNumber()

    @Test
    public void testValidCreditCardWithCharacters() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber("123/23//2233635"));
        assertFalse(CreditCardSystem.getInstance().isValidNumber("123:23::2233635"));
    }

    @Test
    public void testValidCreditCardWithAll9s() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber("99999999999999"));
    }

    @Test
    public void testValidCreditCardWithAll0s() {
        assertTrue(CreditCardSystem.getInstance().isValidNumber("000000000000000"));
    }

    @Test
    public void testValidCreditCardWithValidNumber() {
        assertTrue(CreditCardSystem.getInstance().isValidNumber("5569755825672968"));
    }

    @Test
    public void testValidCreditCardWithShortString() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber("142"));
    }

    @Test
    public void testValidCreditCardWithLongString() {
        assertFalse(CreditCardSystem.getInstance().isValidNumber("1432545245185529354022"));
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
        assertFalse(CreditCardSystem.getInstance().isRegistered("5569755825672968"));
    }

    @Test
    public void testCardRegisteredWNoValidCreditCard() {
        assertFalse(CreditCardSystem.getInstance().isRegistered("999999999999"));
    }

    @Test
    public void testCardRegisteredWithCharacters() {
        assertFalse(CreditCardSystem.getInstance().isRegistered("123/23//2233635"));
        assertFalse(CreditCardSystem.getInstance().isRegistered("123:23::2233635"));
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
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("123:23::2233635", 10.0));
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("123/23//2233635", 10.0));
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("", 10.0));
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance(null, 10.0));
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("14753871549149136508156051", 10.0));
    }

    @Test
    public void testHasBalanceWUnregisteredCreditCard(){
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("5569755825672968", 10.0));
    }

    @Test
    public void testHasBalanceWNotEnoughBalance(){
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", 100.0));
    }

    @Test
    public void testHasBalanceWNegativeToRemove(){
        assertTrue(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", -10.0)); 
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
        assertFalse(CreditCardSystem.getInstance().hasEnoughBalance("9254347527611304", Double.NEGATIVE_INFINITY));
    }

    //updateBalance()

    @Test
    public void testUpdateBalanceWValidCreditCard(){
        assertTrue(CreditCardSystem.getInstance().updateBalance("9254347527611304", 10.0));
    }

    @Test
    public void testUpdateBalanceWInvalidCreditCard(){
        assertFalse(CreditCardSystem.getInstance().updateBalance("sas", 10.0));
    }

    @Test
    public void testUpdateBalanceWUnregisteredCreditCard(){
        assertFalse(CreditCardSystem.getInstance().updateBalance("5569755825672968", 10.0));
    }

    @Test
    public void testUpdateBalanceWNotEnoughBalance(){
        assertFalse(CreditCardSystem.getInstance().updateBalance("9254347527611304", 100.0));
    }

    @Test
    public void testUpdateBalanceWNegativeToRemove(){
        assertTrue(CreditCardSystem.getInstance().updateBalance("9254347527611304", -10.0)); 
    }

    @Test
    public void testUpdateBalanceWZeroToRemove(){
        assertTrue(CreditCardSystem.getInstance().updateBalance("9254347527611304", 0.0));
    }

    @Test
    public void testUpdateBalanceWNaNToRemove(){
        assertFalse(CreditCardSystem.getInstance().updateBalance("9254347527611304", Double.NaN));
    }

    @Test
    public void testUpdateBalanceWPositiveInfinityToRemove(){
        assertFalse(CreditCardSystem.getInstance().updateBalance("9254347527611304", Double.POSITIVE_INFINITY));
    }

    @Test
    public void testUpdateBalanceWNegativeInfinityToRemove(){
        assertFalse(CreditCardSystem.getInstance().updateBalance("9254347527611304", Double.NEGATIVE_INFINITY));
    }

}
