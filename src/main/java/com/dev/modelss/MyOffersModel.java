package com.dev.modelss;

import com.dev.objects.Offer;

import java.text.SimpleDateFormat;

public class MyOffersModel {
    private String offerDate;
    private double offerAmount;

    public MyOffersModel() {
    }

    public MyOffersModel(Offer offer) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.offerDate = simpleDateFormat.format(offer.getCreateDate());
        this.offerAmount = offer.getOfferAmount();
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public double getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(double offerAmount) {
        this.offerAmount = offerAmount;
    }
}
