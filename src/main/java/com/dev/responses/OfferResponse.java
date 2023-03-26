package com.dev.responses;

import com.dev.objects.Offer;

public class OfferResponse extends BasicResponse {
    private Offer offer;


    public OfferResponse(boolean success, Integer errorCode,Offer offer) {
        super(success, errorCode);
        this.offer = offer;
    }

    public OfferResponse(Offer offer) {

    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}
