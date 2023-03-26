package com.dev.SseEvent;

public class NewOfferSseEvent extends SseBasicEvent{
    private String offerUsername;

    public NewOfferSseEvent(String dataType, String offerUsername) {
        super(dataType);
        this.offerUsername = offerUsername;
    }

    public String getOfferUsername() {
        return offerUsername;
    }

    public void setOfferUsername(String offerUsername) {
        this.offerUsername = offerUsername;
    }
}
