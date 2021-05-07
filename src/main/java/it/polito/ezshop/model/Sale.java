package it.polito.ezshop.model;

import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;

import java.io.Serializable;
import java.util.*;

public class Sale extends ProductList implements Serializable, SaleTransaction {

    private Integer ticketNumber;
    private Date date;
    private Double discountRate;
    private LoyaltyCard loyaltyCard;
    private boolean committed;
    private Map<ProductType, Double> productsDiscountRate;
    private List<CReturn> returnTransaction;

    public Sale(Integer ticketNumber, Date date, Double discountRate, LoyaltyCard loyaltyCard) {
        this.ticketNumber = ticketNumber;
        this.date = date;
        this.discountRate = discountRate;
        this.loyaltyCard = loyaltyCard;
        this.committed = false;
        this.productsDiscountRate = new HashMap<>();
        this.returnTransaction = new ArrayList<>();
    }


    @Override
    public Integer getTicketNumber() {
        return this.ticketNumber;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @Override
    public List<TicketEntry> getEntries() {
        return this.getProductsList();
    }

    @Override
    public void setEntries(List<TicketEntry> entries) {
        entries.forEach(x -> products.containsKey(x) ? products.replace(x, products.get(x)+1) : products.put(x, 1));
    }

    @Override
    public double getDiscountRate() {
        return this.discountRate;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double getPrice() {

    }

    @Override
    public void setPrice(double price) {

    }

    public void addReturnTransaction(CReturn returnT){
        this.returnTransaction.add(returnT);
    }

    public void applyDiscountRateToProductGroup(ProductType product, double discountRate){
        this.productsDiscountRate.replace(product, discountRate);
    }

    public void attachLoyaltyCard(LoyaltyCard loyaltyCard){
        this.loyaltyCard = loyaltyCard;
    }

    public LoyaltyCard getAttachedLoyaltyCard(){
        return this.loyaltyCard;
    }

    public void setAsCommitted(){
        this.committed = true;
    }

    public boolean isCommitted(){
        return this.committed;
    }

}
