package com.dev.responses;

import com.dev.modelss.ProductModel;
import com.dev.objects.Offer;
import com.dev.objects.Product;
import com.dev.objects.User;

import java.util.ArrayList;
import java.util.List;

public class AllOffersResponse extends BasicResponse {
    private List<Offer> offers;

    public AllOffersResponse(List<Offer> offers) {
        this.offers = offers;
    }

    public AllOffersResponse(boolean success, Integer errorCode, List<Offer> offers) {
        super(success, errorCode);
        this.offers = offers;
    }
    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}

