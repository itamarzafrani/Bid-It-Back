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

import java.util.List;

import static com.dev.utils.Errors.*;

@RestController
public class ManageController {

    @Autowired
    private Persist persist;


    @RequestMapping(value = "get-user-by-token", method = RequestMethod.GET)
    public BasicResponse getUserByToken(String token) {
        BasicResponse basicResponse = null;
        User userFound = persist.getUserByToken(token);
        if (userFound != null) {
            basicResponse = new UserResponse(true, null, userFound);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_USER);
        }
        return basicResponse;
    }

    @RequestMapping(value = "get-all-users", method = RequestMethod.GET)
    public BasicResponse getAllUsers() {
        BasicResponse basicResponse = null;
        List<User> users = persist.getAllUsers();
        if (users != null) {
            basicResponse = new AllUsersResponse(true, null, users);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_USER);

        }
        return basicResponse;

    }

    @RequestMapping(value = "get-all-open-products", method = RequestMethod.GET)
    public BasicResponse getAllOpenProducts() {
        BasicResponse basicResponse = null;

        List<Product> products = persist.getAllOpenProducts();
        if (products != null) {
            basicResponse = new AllProductsResponse(true, null, products);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_PRODUCT);
        }
        return basicResponse;
    }


    @RequestMapping(value = "get-all-products", method = RequestMethod.GET)
    public BasicResponse getAllProducts() {
        BasicResponse basicResponse = null;

        List<Product> products = persist.getAllProducts();

        if (products != null) {
            basicResponse = new AllProductsResponse(true, null, products);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_PRODUCT);
        }
        return basicResponse;
    }


//    @RequestMapping(value = "get-all-profit", method = RequestMethod.GET)
//    public BasicResponse getAllProfit() {
//        double profit = 0;
//        List<Product> products = persist.getAllProducts();
//        List<Offer> closedProducts = persist.getAllClosedProducts();
//        List<Offer> offers = persist.getAllOffers();
//        for (Offer offer : closedProducts) {
//            profit += offer.getOfferAmount();
//        }
//        profit = profit * 0.05;
//        profit += profit + (products.size() * 2) + (offers.size());
//        ProfitResponse profitResponse = new ProfitResponse(true, null, profit);
//        return profitResponse;
//    }

    @RequestMapping(value = "add-credit-to-user", method = RequestMethod.POST)
    public BasicResponse addCreditToUser(String token, double credits, int userIdForChangeCredits) {
        BasicResponse basicResponse = null;
        User userFound = persist.getUserByToken(token);
        if (userFound.getId() == 1) {
            persist.fillCredit(userIdForChangeCredits, credits);
            basicResponse = new BasicResponse(true, null);
        } else {
            basicResponse = new BasicResponse(false, ERROR_NOT_ADMIN);
        }
        return basicResponse;
    }

}
