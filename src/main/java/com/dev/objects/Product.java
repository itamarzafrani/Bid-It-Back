package com.dev.objects;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String productName;

    @Column
    private String productImg;

    @Column
    private String productDescription;

    @Column
    private int productStartingPrice;

    @Column
    private Date createDate;

    @Column
    private boolean isOpen;

    @ManyToOne
    private User publisher;

    public Product(String productName, String productImg, String productDescription,
                   int productStartingPrice, User publisher) {

        this.productName = productName;
        this.productImg = productImg;
        this.productDescription = productDescription;
        this.productStartingPrice = productStartingPrice;
        this.isOpen = true;
        this.createDate = new Date();
        this.publisher = publisher;
    }

    public Product() {

    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }



}