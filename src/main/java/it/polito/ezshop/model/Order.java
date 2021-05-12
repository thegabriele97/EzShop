package it.polito.ezshop.model;

import java.io.Serializable;
import java.util.Locale;

public class Order implements Serializable, it.polito.ezshop.data.Order, IDebit {
    private Integer orderId;
    private String supplier;
    private Double pricePerUnit;
    private Integer quantity;
    private ProductType product;
    private Integer balanceId;
    private EOrderStatus status;


    public Order(Integer orderId, Double pricePerUnit, Integer quantity, ProductType product, EOrderStatus status) {
        this.orderId = orderId;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.product = product;
        this.status = status;
    }

    @Override
    public Integer getBalanceId() {
        return this.balanceId;
    }

    @Override
    public void setBalanceId(Integer balanceId) {
        this.balanceId = balanceId;
    }

    @Override
    public String getProductCode() {
        return null;
    }

    @Override
    public void setProductCode(String productCode) {

    }

    @Override
    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

    @Override
    public void setStatus(String status) {
        this.status = EOrderStatus.valueOf(status.toUpperCase());
    }

    @Override
    public Integer getOrderId() {
        return this.orderId;
    }

    @Override
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public ProductType getRelatedProduct(){
        return this.product;
    }

    public void setAsPayed(){
        this.status = EOrderStatus.PAYED;
    }

    public void setAsCompleted(){
        this.status = EOrderStatus.COMPLETED;
    }

    @Override
    public Double getTotalValue() {
        return (this.quantity * this.pricePerUnit);
    }
}
