package it.polito.ezshop.model;

import it.polito.ezshop.data.DataManager;
import it.polito.ezshop.data.ProductType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ProductList implements Serializable {

    protected Map<ProductType, Integer> products;

    protected ProductList() {
        products = new HashMap<>();
    }

    public List<ProductType> getProductsList(){
        return products.keySet().stream().collect(Collectors.toList());
    }

    public Integer getQuantityByProduct(ProductType product){
        return products.get(product);
    }

    public void addProduct(ProductType product, Integer quantity){
        addProduct(product, quantity, false);
    }

    public void addProduct(ProductType product, Integer quantity, boolean replace){

        if (product == null || (products.containsKey(product) && products.get(product) + quantity < 0)) {
            throw new IllegalArgumentException();
        }

        if (!replace && products.containsKey(product))
            products.replace(product, products.get(product)+quantity);
        else
            products.put(product, quantity);

        if (this instanceof Sale) {
            DataManager.getInstance().updateSale((Sale)this);
        } else if (this instanceof CReturn) {
            DataManager.getInstance().updateReturn((CReturn)this);
        }
    }

}
