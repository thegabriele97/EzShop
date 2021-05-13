package it.polito.ezshop.model;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static it.polito.ezshop.data.EZShop.*;

public class Sale extends ProductList implements Serializable, SaleTransaction, ICredit {

    private Integer ticketNumber;
    private LocalDate date;
    private Double discountRate;
    private LoyaltyCard loyaltyCard;
    private boolean committed;
    private Map<ProductType, Double> productsDiscountRate;
    private List<CReturn> returnTransaction;
    private double price;

    public Sale(Integer ticketNumber, Double discountRate, LoyaltyCard loyaltyCard) {
        this.ticketNumber = ticketNumber;
        this.discountRate = discountRate;
        this.loyaltyCard = loyaltyCard;
        this.committed = false;
        this.productsDiscountRate = new HashMap<>();
        this.returnTransaction = new ArrayList<>();
        this.price = 0.0;

        this.date = LocalDate.now();
    }


    @Override
    public Integer getTicketNumber() {
        return this.ticketNumber;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
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
                    return getQuantityByProduct(p);
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
                    if (amount < 0) return;
                    products.replace(p, amount);

                    addProduct(p, amount, true);
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
        entries.stream()
            .collect(Collectors
                .toMap(t -> DataManager
                        .getInstance()
                        .getProductTypes()
                        .stream()
                        .filter(p -> p.getBarCode().equals(t.getBarCode())), 
                       t -> t.getAmount()));
    }

    @Override
    public double getDiscountRate() {
        return getRightDoublePrecision(this.discountRate);
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
        Update();
    }

    @Override
    public double getPrice() {
        
        this.price = 0.0;
        for (it.polito.ezshop.data.ProductType prod : getProductsList()) {
            it.polito.ezshop.model.ProductType xProd = (it.polito.ezshop.model.ProductType)prod;

            this.price += (xProd.getPricePerUnit() * getQuantityByProduct(xProd))*(1-getDiscountRateForProductGroup(xProd));
        }

        this.price *= (1-getDiscountRate());
        return getRightDoublePrecision(this.price);
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
        Update();
    }

    public double getDiscountRateForProductGroup(ProductType product){
        return getRightDoublePrecision(productsDiscountRate.containsKey(product) ? productsDiscountRate.get(product) : 0.0);
    }

    public void addReturnTransaction(CReturn returnT){
        this.returnTransaction.add(returnT);
        Update();
    }

    public void applyDiscountRateToProductGroup(ProductType product, double discountRate){
        this.productsDiscountRate.replace(product, discountRate);
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
        return this.getPrice();
    }

    private void Update() {
        DataManager.getInstance().updateSale(this);
    }


}
