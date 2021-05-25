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
    private LocalDate date;  //no get?
    private Double discountRate;
    private LoyaltyCard loyaltyCard;
    private boolean committed;
    private Map<ProductType, Double> productsDiscountRate;
    private List<CReturn> returnTransaction;
    private double price;

    public Sale(Integer ticketNumber, Double discountRate, LoyaltyCard loyaltyCard) {
        this.setTicketNumber(ticketNumber);
        this.setDiscountRate(discountRate);
        this.attachLoyaltyCard(loyaltyCard);
        this.committed = false;
        this.productsDiscountRate = new HashMap<>();
        this.returnTransaction = new ArrayList<>();
        this.setPrice(0.0);

        this.date = LocalDate.now();
    }


    @Override
    public Integer getTicketNumber() {
        return this.ticketNumber;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
        if (ticketNumber < 1)
            throw new IllegalArgumentException();
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
                    if (amount < 0) throw new IllegalArgumentException();
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
        if (discountRate < 0.0 || discountRate > 1.0 || Double.isNaN(discountRate) || Double.isInfinite(discountRate)) {
            throw new IllegalArgumentException();  
        } 

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
        if (price < 0.0)
            throw new IllegalArgumentException();
        this.price = price;
        Update();
    }

    public double getDiscountRateForProductGroup(ProductType product){
        return getRightDoublePrecision(productsDiscountRate.containsKey(product) ? productsDiscountRate.get(product) : 0.0);
    }

    public void addReturnTransaction(CReturn returnT){

        if (returnT == null) throw new IllegalArgumentException();

        this.returnTransaction.add(returnT);
        Update();
    }

    public void applyDiscountRateToProductGroup(ProductType product, double discountRate){

        if (discountRate < 0.0 || discountRate > 1.0 || Double.isNaN(discountRate) || Double.isInfinite(discountRate)) {
            throw new IllegalArgumentException();  
        }
        
        if(productsDiscountRate.containsKey(product)) {
        	  this.productsDiscountRate.replace(product, discountRate);
        }
        else{
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
        return this.getPrice();
    }

    private void Update() {
        DataManager.getInstance().updateSale(this);
    }

    @Override
    public int hashCode() {
        return this.getTicketNumber();
    }

    @Override
    public boolean equals(Object obj) {
        return this.getTicketNumber() == ((Sale)obj).getTicketNumber();
    }

    public LocalDate getDate(){
        return this.date;
    }


}
