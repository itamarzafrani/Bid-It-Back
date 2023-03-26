package com.dev.SseEvent;

public class CloseProductSseEvent extends SseBasicEvent{
    private String sellerUsername;


    public CloseProductSseEvent(String dataType, String sellerUsername) {
        super(dataType);
        this.sellerUsername = sellerUsername;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }
}
