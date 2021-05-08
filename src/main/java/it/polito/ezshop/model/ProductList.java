package it.polito.ezshop.model;

import it.polito.ezshop.data.TicketEntry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ProductList {

    protected Map<ProductType, Integer> products;

    public List<TicketEntry> getProductsList(){
        return this.products.keySet().stream().collect(Collectors.toList());
    }

    public Integer getQuantityByProduct(ProductType product){
        return products.get(product);
    }

    public void addProduct(ProductType product, Integer quantity){
        if (products.containsKey(product))
            products.replace(product, products.get(product)+quantity);
        else
            products.put(product, quantity);
    }

}
