package com.dev.controllers;

import com.dev.modelss.ProductModel;
import com.dev.objects.Offer;
import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.responses.*;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.dev.utils.Errors.*;
import static com.dev.utils.Errors.ERROR_NO_PRODUCT_DESCRIPTION;

@RestController
public class ProductController {

    @Autowired
    private Persist persist;

    @Autowired
    private LiveUpdatesController liveUpdatesController;

    @RequestMapping(value = "get-highest-offer", method = RequestMethod.GET)
    public BasicResponse getHighestOffer(int productId) {
        BasicResponse basicResponse = null;
        Offer highestOffer = persist.getHighestOffer(productId);
        if (highestOffer != null) {
            basicResponse = new OfferResponse(true, null, highestOffer);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_OFFERS_IN_THE_SYSTEM);
        }
        return basicResponse;
    }

    @RequestMapping(value = "get-amount-of-my-offers-on-product", method = RequestMethod.GET)
    public BasicResponse getAmountMyOffersOnProduct(String token,int productId) {
        BasicResponse basicResponse = null;
        User user = persist.getUserByToken(token);
        Product product = persist.getProductById(productId);
        List<Offer> myOffersOnProduct = persist.getOffersByUserAndProduct(user,product);
        basicResponse = new MyOfferResponse(true, null, myOffersOnProduct);
        return basicResponse;
    }

    @RequestMapping(value = "get-amount-of-offers-on-product", method = RequestMethod.GET)
    public BasicResponse getAmountOffersOnProduct(String token,int productId) {
        BasicResponse basicResponse = null;
        User user = persist.getUserByToken(token);
        Product product =persist.getProductById(productId);
        int myOffersOnProduct = persist.getAmountOfOffersUserOnProduct(product.getId()).size();
        basicResponse = new AmountOnProductResponse(true, null, myOffersOnProduct);
        return basicResponse;
    }

    @RequestMapping(value = "get-product", method = RequestMethod.GET)
    public BasicResponse getProductById(int productId) {
        BasicResponse basicResponse = null;
        ProductModel productById = new ProductModel(persist.getProductById(productId));
        if (productById != null) {
            basicResponse = new ProductResponse(true, null, productById);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_PRODUCT);
        }
        return basicResponse;
    }

    @RequestMapping(value = "get-all-open-products-by-id", method = RequestMethod.GET)
    public BasicResponse getAllOpenProductsById(int userId) {
        BasicResponse basicResponse = null;
        int userProductsAmount = persist.getAllOpenProductsById(userId);
        if (userId != 0) {
            if (userProductsAmount != 0) {
                basicResponse = new ProductAmountResponse(true, null, userProductsAmount);
            } else {
                basicResponse = new BasicResponse(false, ERROR_NO_PRODUCTS_LIST);
            }
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_USER);
        }
        return basicResponse;
    }

    @RequestMapping(value = "post-offer", method = RequestMethod.POST)
    public BasicResponse postOffer(String token, Integer productId, double offerPrice) {
        BasicResponse basicResponse;
        boolean success = false;
        Integer errorCode = 0;
        Offer newOffer = null;
        User offerUser = persist.getUserByToken(token);
        Product product = persist.getProductById(productId);
        Offer highestOffer = persist.getHighestOffer(productId);
        if (offerUser != null) {
            if (product!= null) {
                if (product.isOpen()) {
                    if (offerUser.getCredits() >= offerPrice) {
                        if (offerPrice > product.getProductStartingPrice()) {
                            if (highestOffer == null) {
                                newOffer = new Offer(offerPrice, product, offerUser);
                                persist.payCreditNewOffer( offerUser.getId(),product.getPublisher().getId(), offerPrice);
                                success = true;
                                persist.saveOffer(newOffer);
                                liveUpdatesController.sendNewOfferEvent(product.getPublisher().getId(),offerUser.getUsername());
                            } else if (highestOffer.getOfferAmount() < offerPrice) {
                                newOffer = new Offer(offerPrice, product, offerUser);
                                persist.payCreditNewOffer( offerUser.getId(),product.getPublisher().getId(), offerPrice);
                                persist.payBackLastHighestOffer(product.getPublisher().getId(),highestOffer.getOfferFrom().getId(),highestOffer.getOfferAmount());
                                success = true;
                                liveUpdatesController.sendNewOfferEvent(product.getPublisher().getId(),offerUser.getUsername());
                                persist.saveOffer(newOffer);
                            } else {
                                errorCode = ERROR_LOWER_OFFER_AMOUNT;
                            }
                        } else {
                            errorCode = ERROR_LOWER_THAN_STARTING_PRICE;
                        }
                    } else {
                        errorCode = ERROR_NO_ENOUGH_MONEY;
                    }
                } else {
                    errorCode = ERROR_PRODUCT_IS_NOT_FOR_SALE_ANYMORE;
                }
            } else {
                errorCode = ERROR_NO_SUCH_PRODUCT;
            }
        } else {
            errorCode = ERROR_NO_SUCH_USER;
        }
        basicResponse = new OfferResponse(success, errorCode, newOffer);
        return basicResponse;
    }

    //TODO:CLOOOOOSE PRODUCT!!!!

    @RequestMapping(value = "close-product", method = RequestMethod.POST)
    public BasicResponse closeProduct(String token, int productId) {
        Integer errorCode = null;
        boolean success = false;
        BasicResponse basicResponse;
        Offer highestOffer = persist.getHighestOffer(productId);
        Product product = persist.getProductById(productId);
        ProductModel productModel = new ProductModel(persist.getProductById(productId));
        int offersOnProduct = persist.getAmountOfOffersOnProduct(productId);
        List<Integer> userOffersIdOnProduct = persist.getUserOffersIdOnProduct(productId);
        User user = persist.getUserByToken(token);
        if (offersOnProduct >= 3) {
            if (token.equals(product.getPublisher().getToken())) {
                persist.closeProductPayCredit(user.getId(), highestOffer.getOfferAmount());
                success = true;
                persist.closeProduct(productId);
                persist.saveWinningBid(highestOffer.getId());
                liveUpdatesController.sendCloseProductEvent(product.getPublisher().getUsername(),userOffersIdOnProduct);
            }
        } else {
            errorCode = ERROR_NO_3_OFFERS;
        }
        basicResponse = new ProductResponse(success, errorCode, productModel);
        return basicResponse;
    }

    @RequestMapping(value = "get-user-by-id" , method = RequestMethod.GET)
    public BasicResponse getUserById(int userId) {
        Integer errorCode = null;
        boolean success = false;
        BasicResponse basicResponse;
        User user = persist.getUserById(userId);
        if (user != null) {
            if (user.isAdmin()) {
                success = true;
            } else {
                errorCode = ERROR_NOT_ADMIN;
            }
        } else {
            errorCode = ERROR_NO_SUCH_USER;
        }
        basicResponse = new UserResponse(success , errorCode , user);
        return basicResponse;

    }
}
