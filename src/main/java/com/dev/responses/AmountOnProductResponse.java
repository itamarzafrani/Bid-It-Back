package com.dev.responses;

public class AmountOnProductResponse extends BasicResponse{
    private int amountOfUsersOnProduct;


    public AmountOnProductResponse(int amountOfUsersOnProduct) {
        this.amountOfUsersOnProduct = amountOfUsersOnProduct;
    }

    public AmountOnProductResponse(boolean success, Integer errorCode, int amountOfUsersOnProduct) {
        super(success, errorCode);
        this.amountOfUsersOnProduct = amountOfUsersOnProduct;
    }

    public int getAmountOfUsersOnProduct() {
        return amountOfUsersOnProduct;
    }

    public void setAmountOfUsersOnProduct(int amountOfUsersOnProduct) {
        this.amountOfUsersOnProduct = amountOfUsersOnProduct;
    }
}
