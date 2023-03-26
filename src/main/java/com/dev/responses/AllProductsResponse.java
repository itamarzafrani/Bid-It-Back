package com.dev.responses;

import com.dev.modelss.ProductModel;
import com.dev.objects.Product;

import java.util.ArrayList;
import java.util.List;

public class AllProductsResponse extends BasicResponse {
    private List<ProductModel> ProductList=new ArrayList<>();

    public AllProductsResponse(boolean success, Integer errorCode, List<Product> products) {
        super(success, errorCode);
        if (products != null) {
            for (Product product : products) {
                this.ProductList.add(new ProductModel(product));
            }
        }
    }

    public List<ProductModel> getProduct() {
        return ProductList;
    }

    public void setProducts(List<ProductModel> products) {
        this.ProductList = products;
    }
}
