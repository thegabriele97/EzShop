package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.model.LoyaltyCard;
import it.polito.ezshop.model.Position;
import it.polito.ezshop.model.ProductType;
import it.polito.ezshop.model.Sale;
import it.polito.ezshop.model.CReturn;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class SaleTest {

    @Before
    @After
    public void cleanDatabase() {

        for (Sale s : DataManager.getInstance().getSales()) {
            DataManager.getInstance().deleteSale(s);
        }

        for (LoyaltyCard lc : DataManager.getInstance().getLoyaltyCards()) {
            DataManager.getInstance().deleteLoyaltyCard(lc);
        }
        
       for(ProductType p : DataManager.getInstance().getProductTypes()) {
    	   DataManager.getInstance().deleteProductType(p);
       }
       for (Position p : DataManager.getInstance().getPositions()) {
           DataManager.getInstance().deletePosition(p);
       }

    }

    @Test
    public void testValidSale() {
        Sale s = new Sale(1, 0.0, null);
        assertEquals(Integer.valueOf(1), s.getTicketNumber());
        assertEquals(0.0, s.getDiscountRate(), 0.005);
        assertNull(s.getAttachedLoyaltyCard());
        assertFalse(s.isCommitted());
        assertEquals(0.0, s.getOriginalSalePrice(), 0.005);
        assertEquals(0.0, s.getTotalValue(), 0.005);
    }

    @Test
    public void testSetInvalidTicketNumber() {
        Sale s = new Sale(1, 0.0, null);
        assertThrows(IllegalArgumentException.class, () -> s.setTicketNumber(0));
    }

    @Test
    public void testSetInvalidDiscountRate() {
        Sale s = new Sale(1, 0.0, null);
        assertThrows(IllegalArgumentException.class, () -> s.setDiscountRate(1.1));
    }

    @Test
    public void testSetPriceInvalid() {
        Sale s = new Sale(1, 0.0, null);
        assertThrows(IllegalArgumentException.class, () -> s.setPrice(-0.01));
    }

    @Test
    public void testSetPriceValid() {
        
        Sale s = new Sale(1, 0.0, null);
        
        ProductType p = new ProductType(36, "1231231231232", "test", 1.0, 2, 0.1, "", "1-a-1");
        s.addProduct(p, 1);

        assertEquals(1.0, s.getPrice(), 0.01);
        
        s.setPrice(3.0);
        assertEquals(3.0, s.getPrice(), 0.01);
    }

    @Test
    public void testGetPriceWith1Loop() {
        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(36, "1231231231232", "test", 1.0, 2, 0.1, "", "1-a-1");
        s.addProduct(p, 2);
        assertEquals(2.0, s.getOriginalSalePrice(), 0.005);
    }

    @Test
    public void testSetAsCommitted() {
        Sale s = new Sale(1, 0.0, null);
        assertFalse(s.isCommitted());
        s.setAsCommitted();
        assertTrue(s.isCommitted());
    }

    @Test 
    public void testAddReturnTransactionWithNull() {
    	Sale s = new Sale(1, 0.0, null);
    	assertThrows(IllegalArgumentException.class, () -> s.addReturnTransaction(null));
    	
    	new CReturn(1,s);
    }
    
    @Test 
    public void testApplyDiscountRateToProductGroup() {
        Sale s = new Sale(1, 0.0, null);
        ProductType p = new ProductType(340, "1231231231232", "test", 1.4, 1, 0.0, "", "1-a-1");
        
        assertThrows(IllegalArgumentException.class, () -> s.applyDiscountRateToProductGroup(p,-2.0));
        assertThrows(IllegalArgumentException.class, () -> s.applyDiscountRateToProductGroup(p, 2.0));
        assertThrows(IllegalArgumentException.class, () -> s.applyDiscountRateToProductGroup(p,Double.NaN));
        assertThrows(IllegalArgumentException.class, () -> s.applyDiscountRateToProductGroup(p,Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> s.applyDiscountRateToProductGroup(p,Double.NEGATIVE_INFINITY));
        
        s.applyDiscountRateToProductGroup(p, 0.5);
    }
    
    @Test 
    public void testGetDiscountRateForProductGroup() {
       ProductType p1 = new ProductType(36, "1231231231232", "test", 1.4, 1, 0.0, "", "1-c-1");
       ProductType p2 = new ProductType(37, "1231231231232", "test1", 2.4, 2, 0.0, "", "2-d-2");
       DataManager.getInstance().insertProductType(p1);
       DataManager.getInstance().insertProductType(p2);
       
       Sale s = new Sale(1, 0.0, null);
       
       s.applyDiscountRateToProductGroup(p1, 0.3);
       assertEquals(Double.valueOf(0.0),Double.valueOf(s.getDiscountRateForProductGroup(p2)));
       assertEquals(Double.valueOf(0.3),Double.valueOf(s.getDiscountRateForProductGroup(p1)));
       s.applyDiscountRateToProductGroup(p1, 0.5);
       assertEquals(Double.valueOf(0.5),Double.valueOf(s.getDiscountRateForProductGroup(p1)));
    }

    @Test
    public void testEquals() {
        Sale s = new Sale(2, 0.0, null);
        Sale s1 = new Sale(2, 0.0,null);
        
        assertTrue(s.equals(s1));
        Sale s2 = new Sale(1, 0.0,null);
        assertFalse(s.equals(s2));    
    }

    @Test
    public void testSetEntriesAndTotalPrice() {

        Sale s = new Sale(2, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p0 = new ProductType(1, "1231231231232", "test", 2.5, 4, 0.0, "", "1-a-1");
        ProductType p1 = new ProductType(2, "0123456789012", "test", 3.5, 4, 0.0, "", "1-a-2");

        DataManager.getInstance().insertProductType(p0);
        DataManager.getInstance().insertProductType(p1);

        List<TicketEntry> entries = new ArrayList<>();
        entries.add(mapProduct2TicketEntry(p0, 2, 0.0));
        entries.add(mapProduct2TicketEntry(p1, 3, 0.0));

        s.setEntries(entries);

        assertEquals(Integer.valueOf(2), s.getQuantityByProduct(p0));
        assertEquals(Integer.valueOf(3), s.getQuantityByProduct(p1));
        assertEquals(2*2.5 + 3*3.5, s.getOriginalSalePrice(), 0.01);
    }

    @Test
    public void testGetEntries() {

        Sale s = new Sale(2, 0.0, null);
        DataManager.getInstance().insertSale(s);

        ProductType p0 = new ProductType(1, "1231231231232", "test", 2.5, 4, 0.0, "", "1-a-1");
        ProductType p1 = new ProductType(2, "0123456789012", "test", 3.5, 4, 0.0, "", "1-a-2");

        DataManager.getInstance().insertProductType(p0);
        DataManager.getInstance().insertProductType(p1);

        s.addProduct(p0, 1);
        s.addProduct(p0, 1);
        s.addProduct(p1, 3);

        List<TicketEntry> entries = s.getEntries();

        assertEquals(2, entries.size());
        assertTrue(entries.stream().anyMatch(e -> e.getBarCode() == p0.getBarCode()));
        assertTrue(entries.stream().anyMatch(e -> e.getBarCode() == p0.getBarCode() && e.getAmount() == 2));
        assertTrue(entries.stream().anyMatch(e -> e.getBarCode() == p1.getBarCode()));
        assertTrue(entries.stream().anyMatch(e -> e.getBarCode() == p1.getBarCode() && e.getAmount() == 3));

    }

    private TicketEntry mapProduct2TicketEntry(ProductType p, int soldQuantity, double discountRate) {
        return new TicketEntry(){

            @Override
            public int getAmount() {
                return soldQuantity;
            }

            @Override
            public String getBarCode() {
                return p.getBarCode();
            }

            @Override
            public double getDiscountRate() {
                return discountRate;
            }

            @Override
            public double getPricePerUnit() {
                return it.polito.ezshop.data.EZShop.getRightDoublePrecision(p.getPricePerUnit());
            }

            @Override
            public String getProductDescription() {
                return p.getProductDescription();
            }

            @Override
            public void setAmount(int amount) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void setBarCode(String barCode) {
                p.setBarCode(barCode);
            }

            @Override
            public void setDiscountRate(double discountRate) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void setPricePerUnit(double pricePerUnit) {
                p.setPricePerUnit(pricePerUnit);
            }

            @Override
            public void setProductDescription(String productDescription) {
                p.setProductDescription(productDescription);
            }
        };
    }


}