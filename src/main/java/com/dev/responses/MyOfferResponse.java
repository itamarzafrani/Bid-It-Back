package com.dev.responses;

import com.dev.modelss.MyOffersModel;
import com.dev.objects.Offer;

import java.util.ArrayList;
import java.util.List;

public class MyOfferResponse extends BasicResponse {
    private List<MyOffersModel> myOffersOnProduct;

    public MyOfferResponse(List<MyOffersModel> myOffersOnProduct) {
        this.myOffersOnProduct = myOffersOnProduct;
    }

    public MyOfferResponse(boolean success, Integer errorCode, List<Offer> myOffersOnProduct) {
        super(success, errorCode);
        this.myOffersOnProduct = new ArrayList<>();
        for (Offer offer:myOffersOnProduct) {
            this.myOffersOnProduct.add(new MyOffersModel(offer));
        }

    }

    public List<MyOffersModel> getMyOffersOnProduct() {
        return myOffersOnProduct;
    }

    public void setMyOffersOnProduct(List<MyOffersModel> myOffersOnProduct) {
        this.myOffersOnProduct = myOffersOnProduct;
    }
}
