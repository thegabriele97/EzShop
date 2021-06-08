package it.polito.ezshop.model;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static it.polito.ezshop.data.EZShop.*;
import static java.util.stream.Collectors.*;

public class Sale extends ProductList implements SaleTransaction, ICredit {

    private Integer ticketNumber;
    private LocalDate date;
    private Double discountRate;
    private LoyaltyCard loyaltyCard;
    private boolean committed;
    private Map<ProductType, Double> productsDiscountRate;
    private Set<CReturn> returnTransactions;
    private double price;
    private Set<Product> productRFIDs;

    public Sale(Integer ticketNumber, Double discountRate, LoyaltyCard loyaltyCard) {
        
        this.committed = false;
        this.productsDiscountRate = new HashMap<>();
        this.returnTransactions = new HashSet<>();

        this.setTicketNumber(ticketNumber);
        this.setDiscountRate(discountRate);
        this.attachLoyaltyCard(loyaltyCard);
        
        this.price = Double.NaN;
        this.date = LocalDate.now();
        this.productRFIDs = new HashSet<>();
    }


    @Override
    public Integer getTicketNumber() {
        return this.ticketNumber;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
        
        if (ticketNumber < 1) throw new IllegalArgumentException();

        this.ticketNumber = ticketNumber;
        Update();
    }

    @Override
    public List<TicketEntry> getEntries() {
        return this.products
            .keySet()
            .stream()
            .map(p -> new TicketEntry(){

                @Override
                public int getAmount() {
                    int returnedCount = 0;

                    returnedCount = (int)getProducRFIDs().stream()
                        .filter(prod -> prod.getRelativeProductType().equals(p))
                        .filter(prod -> isProductRFIDReturned(prod))
                        .count();

                    return getQuantityByProduct(p) - getReturnedQuantityByProduct((it.polito.ezshop.model.ProductType)p) - returnedCount;
                }

                @Override
                public String getBarCode() {
                    return p.getBarCode();
                }

                @Override
                public double getDiscountRate() {
                    return getDiscountRateForProductGroup((it.polito.ezshop.model.ProductType)p);
                }

                @Override
                public double getPricePerUnit() {
                    return getRightDoublePrecision(p.getPricePerUnit());
                }

                @Override
                public String getProductDescription() {
                    return p.getProductDescription();
                }

                @Override
                public void setAmount(int amount) {
                    
                    if (amount < 0) throw new IllegalArgumentException();
                    
                    addProduct(p, amount + getReturnedQuantityByProduct((it.polito.ezshop.model.ProductType)p), true);
                }

                @Override
                public void setBarCode(String barCode) {
                    p.setBarCode(barCode);
                }

                @Override
                public void setDiscountRate(double discountRate) {
                    applyDiscountRateToProductGroup((it.polito.ezshop.model.ProductType)p, discountRate);
                }

                @Override
                public void setPricePerUnit(double pricePerUnit) {
                    p.setPricePerUnit(pricePerUnit);
                }

                @Override
                public void setProductDescription(String productDescription) {
                    p.setProductDescription(productDescription);
                }

            })
            .collect(Collectors.toList());
    }

    @Override
    public void setEntries(List<TicketEntry> entries) {

        if (entries == null)
            throw new IllegalArgumentException();

        boolean isAnEntryWrong = entries.stream().anyMatch(te -> !DataManager.getInstance()
            .getProductTypes()
            .stream()
            .map(ProductType::getBarCode)
            .collect(toList())
            .contains(te.getBarCode()));

        if (isAnEntryWrong) throw new IllegalArgumentException();

        products = entries.stream()
            .collect(Collectors
                .toMap(t -> DataManager
                        .getInstance()
                        .getProductTypes()
                        .stream()
                        .filter(p -> p.getBarCode().equals(t.getBarCode()))
                        .findFirst()
                        .get(),
                    t -> t.getAmount()));

    }

    @Override
    public double getDiscountRate() {
        return getRightDoublePrecision(this.discountRate);
    }

    @Override
    public void setDiscountRate(double discountRate) {

        if (discountRate < 0.0 || discountRate >= 1.0 || Double.isNaN(discountRate) || Double.isInfinite(discountRate)) {
            throw new IllegalArgumentException();  
        } 

        this.discountRate = discountRate;
        Update();
    }

    @Override
    public double getPrice() {
        
        if (!Double.isNaN(this.price)) return this.price;

        double price = 0.0;
        for (it.polito.ezshop.data.ProductType prod : getProductsList()) {
            it.polito.ezshop.model.ProductType xProd = (it.polito.ezshop.model.ProductType)prod;

            price += (xProd.getPricePerUnit() * (getQuantityByProduct(xProd) - getReturnedQuantityByProduct(xProd))) * (1 - getDiscountRateForProductGroup(xProd));
        }

        for (Product prod : getProducRFIDs()) {
            it.polito.ezshop.model.ProductType relProdType = prod.getRelativeProductType();
            
            if (!isProductRFIDReturned(prod)) {
                price += relProdType.getPricePerUnit() * (1- getDiscountRateForProductGroup(relProdType));
            }
        }

        price *= (1-getDiscountRate());
        return getRightDoublePrecision(price);
    }

    @Override
    public void setPrice(double price) {
        
        if (price < 0.0)
            throw new IllegalArgumentException();

        this.price = price;
        Update();
    }

    public double getOriginalSalePrice() {
        
        double price = 0.0;
        for (it.polito.ezshop.data.ProductType prod : getProductsList()) {
            it.polito.ezshop.model.ProductType xProd = (it.polito.ezshop.model.ProductType)prod;

            price += (xProd.getPricePerUnit() * getQuantityByProduct(xProd))*(1 - getDiscountRateForProductGroup(xProd));
        }

        for (Product prod : getProducRFIDs()) {
            it.polito.ezshop.model.ProductType relProdType = prod.getRelativeProductType();
            price += relProdType.getPricePerUnit() * (1- getDiscountRateForProductGroup(relProdType));
        }

        price *= 1 - getDiscountRate();
        return getRightDoublePrecision(price);
    }

    public double getDiscountRateForProductGroup(ProductType product){
        return getRightDoublePrecision(productsDiscountRate.containsKey(product) ? productsDiscountRate.get(product) : 0.0);
    }

    public void addReturnTransaction(CReturn returnT) {

        if (returnT == null || returnTransactions.contains(returnT)) throw new IllegalArgumentException();

        returnTransactions.add(returnT);
        Update();
    }

    public void applyDiscountRateToProductGroup(ProductType product, double discountRate) {

        if (discountRate < 0.0 || discountRate > 1.0 || Double.isNaN(discountRate) || Double.isInfinite(discountRate)) {
            throw new IllegalArgumentException();  
        }
        
        if (productsDiscountRate.containsKey(product)) {
        	  this.productsDiscountRate.replace(product, discountRate);
        } else {
        	 this.productsDiscountRate.put(product, discountRate);
        }
        
        Update();
    }

    public void attachLoyaltyCard(LoyaltyCard loyaltyCard){
        this.loyaltyCard = loyaltyCard;
        Update();
    }

    public LoyaltyCard getAttachedLoyaltyCard(){
        return this.loyaltyCard;
    }

    public void setAsCommitted(){
        this.committed = true;
        Update();
    }

    public boolean isCommitted(){
        return this.committed;
    }
    
    @Override
    public Double getTotalValue() {
        return this.getOriginalSalePrice();
    }

    @Override
    public Integer getQuantityByProduct(it.polito.ezshop.data.ProductType product) {
        return super.getQuantityByProduct(product) + (int)getProducRFIDs().stream().filter(p -> p.getRelativeProductType().equals(product)).count();
    }
    
    public LocalDate getDate(){
        return this.date;
    }

    public boolean addProductRFID(Product prod) {
        return this.productRFIDs.add(prod);
    }

    public boolean deleteProductRFID(Product prod) {
        return this.productRFIDs.remove(prod);
    }

    public List<Product> getProducRFIDs() {
        return this.productRFIDs
            .stream()
            .collect(toList());
    }
    
    @Override
    public int hashCode() {
        return this.getTicketNumber();
    }
    
    @Override
    public boolean equals(Object obj) {
        return this.getTicketNumber() == ((Sale)obj).getTicketNumber();
    }
    
    private void Update() {
        DataManager.getInstance().updateSale(this);
    }
    
    public int getReturnedQuantityByProduct(ProductType xProd) {
        
        return returnTransactions.stream()
            .filter(ret -> ret.isCommitted())
            .filter(ret -> ret.getProductsList().contains(xProd))
            .mapToInt(ret -> ret.getQuantityByProduct(xProd))
            .sum();  
    }

    public boolean isProductRFIDReturned(Product prod) {

        return returnTransactions.stream()
            .filter(ret -> ret.isCommitted())
            .anyMatch(ret -> ret.getProducRFIDs().contains(prod));
    }

}
