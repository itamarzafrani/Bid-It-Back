package com.dev.modelss;

import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.utils.Persist;

import java.text.SimpleDateFormat;

public class ProductModel {
    private int id;
    private String productName;
    private String productImg;
    private String creationDate;
    private String productDescription;
    private int productStartingPrice;
    private User publisher;

    private boolean open;
    public ProductModel() {
    }



    public ProductModel(Product product) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.creationDate = simpleDateFormat.format(product.getCreateDate());
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productImg = product.getProductImg();
        this.productDescription = product.getProductDescription();
        this.productStartingPrice = product.getProductStartingPrice();
        this.publisher = product.getPublisher();
        this.open = product.isOpen();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductStartingPrice() {
        return productStartingPrice;
    }

    public void setProductStartingPrice(int productStartingPrice) {
        this.productStartingPrice = productStartingPrice;
    }


}
