package it.polito.ezshop.unitTests;



import org.junit.Test;

import static it.polito.ezshop.data.EZShop.isValidBarcode;
import static org.junit.Assert.*;

public class BBisValidBarcodeTest {

    @Test
    public void testIsValidBarcode() {
        assertTrue("Barcode valid, true expected", isValidBarcode("000000000000"));
        assertTrue("Barcode valid, true expected", isValidBarcode("00000000000000"));
        assertTrue("Barcode valid, true expected", isValidBarcode("999999999993"));
        assertTrue("Barcode valid, true expected", isValidBarcode("99999999999997"));
    }

    @Test
    public void testIsValidNullBarcode() {
        assertFalse("Barcode null, false expected", isValidBarcode(null));
    }

    @Test
    public void testIsValidShorterBarcode() {
        assertFalse("Barcode shorter than accepted, false expected", isValidBarcode("00000000000"));
    }

    @Test
    public void testIsValidLongerBarcode() {
        assertFalse("BBarcode longer than accepted, false expected", isValidBarcode("000000000000000"));
    }

    @Test
    public void testIsValidBarcodeWithNotNumericChar() {
        assertFalse("Barcode with not numeric char, false expected", isValidBarcode("000000000000/"));
        assertFalse("Barcode with not numeric char, false expected", isValidBarcode("000000000000:"));
    }

}