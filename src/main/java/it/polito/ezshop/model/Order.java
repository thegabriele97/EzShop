package it.polito.ezshop.model;

import java.io.Serializable;

import it.polito.ezshop.data.EZShop;

public class Order implements Serializable, it.polito.ezshop.data.Order, IDebit {
    private Integer orderId;
    private String supplier;
    private Double pricePerUnit;
    private Integer quantity;
    private ProductType product;
    private Integer balanceId;
    private EOrderStatus status;
    private String productCode;

    public Order(Integer orderId, Double pricePerUnit, Integer quantity, ProductType product, EOrderStatus status) {
        setOrderId(orderId);
        setPricePerUnit(pricePerUnit);
        setQuantity(this.quantity);
        this.product = product;
        this.status = status;
        setProductCode(product.getBarCode());
    }

    @Override
    public Integer getBalanceId() {
        return this.balanceId;
    }

    @Override
    public void setBalanceId(Integer balanceId) {
        if(balanceId < 1) return;
        this.balanceId = balanceId;
    }

    @Override
    public String getProductCode() {
        return this.productCode;
    }

    @Override
    public void setProductCode(String productCode) {
        if (!EZShop.isValidBarcode(productCode)) return;
        this.productCode = productCode;
    }

    @Override
    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        if (pricePerUnit < 0.0) return;
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        if (quantity < 0) return;
        this.quantity = quantity;
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

    @Override
    public void setStatus(String status) {
        if (!(status.toUpperCase().equals("ISSUED") || status.toUpperCase().equals("PAYED") || status.toUpperCase().equals("COMPLETED"))) return;
        this.status = EOrderStatus.valueOf(status.toUpperCase());
    }

    @Override
    public Integer getOrderId() {
        return this.orderId;
    }

    @Override
    public void setOrderId(Integer orderId) {
        if (orderId < 1) return;
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
