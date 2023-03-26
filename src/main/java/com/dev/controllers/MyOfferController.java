package com.dev.controllers;

import com.dev.objects.Offer;
import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.responses.AllOffersResponse;
import com.dev.responses.AllProductsResponse;
import com.dev.responses.BasicResponse;
import com.dev.responses.OfferResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.dev.utils.Errors.*;
import static com.dev.utils.Errors.ERROR_PRODUCT_IS_NOT_FOR_SALE_ANYMORE;

@RestController
public class MyOfferController {
    @Autowired
    private Persist persist;


    @RequestMapping(value = "my-offers" , method = RequestMethod.GET)
    public BasicResponse getAllMyOffer(String token) {
        BasicResponse basicResponse = null;
        int userId = persist.getUserIdByToken(token);
        List<Offer> allOffers = persist.getAllOffers();
        List<Offer> myOffers = new ArrayList<>();
        for (Offer offer: allOffers) {
            if (offer.getOfferFrom().getId() == userId) {
                myOffers.add(offer);
            }
        }
        if (myOffers != null) {
            basicResponse = new AllOffersResponse(true , null , myOffers);
        } else {
            basicResponse = new BasicResponse(false , ERROR_NO_PRODUCTS_LIST);
        }
        return basicResponse;
    }

    @RequestMapping(value = "get-is-won-on-sale" , method = RequestMethod.GET)
    public BasicResponse getIsWonTheSale(Offer offer ,int productId) {
        BasicResponse basicResponse = null;
        Offer highestOffer = persist.getHighestOffer(productId);
            if (!offer.getProduct().isOpen()) {
                if (offer.getId() == highestOffer.getId()) {
                    basicResponse = new BasicResponse(true , null);
                } else {
                    basicResponse = new BasicResponse(false , ERROR_OFFER_DONT_WON_THE_SALE);
                }
            } else {
                basicResponse = new BasicResponse(false , ERROR_PRODUCT_STILL_FOR_SALE);

        }
        return basicResponse;
    }



}
