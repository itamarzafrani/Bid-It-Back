package com.dev.controllers;

import com.dev.modelss.ProductModel;
import com.dev.objects.Offer;
import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.responses.*;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

import static com.dev.utils.Errors.*;

@RestController
public class DashboardController {

    @Autowired
    private Persist persist;
    @Autowired
    private Utils utils;


    @Autowired
    private LiveUpdatesController liveUpdatesController;

    @PostConstruct
    public void init(){
        String userName = "Shai";
        String password = "123456";
        String hash = utils.createHash(userName,password);
        boolean isAdmin = true;
        int credits = 0;
        User userAdmin = new User(userName,hash,credits,isAdmin);
        List<User>allUsers = persist.getAllUsers();
        if (allUsers.size()==0) {
            persist.saveUser(userAdmin);
        }
    }

    @RequestMapping(value = "get-username", method = RequestMethod.GET)
    public BasicResponse getUsername(String token) {
        User user = persist.getUserByToken(token);
        BasicResponse basicResponse = null;
        if (user != null) {
            basicResponse = new UsernameResponse(true, null, user.getUsername());
        } else {
            basicResponse = new BasicResponse(false, ERROR_NO_SUCH_TOKEN);
        }
        return basicResponse;
    }


    @RequestMapping(value = "post-product", method = RequestMethod.POST)
    public BasicResponse postProduct(String token, String productName, String productDescription, int startingPrice, String productImg) {
        Integer errorCode = null;
        boolean success = false;
        BasicResponse basicResponse = new BasicResponse();
        User user = persist.getUserByToken(token);
        if (user != null) {
            if (!productDescription.equals("")) {
                if (!productImg.equals("")) {
                    if (!productName.equals("")) {
                        if (startingPrice > 0 ) {
                            Product product = new Product(productName, productImg, productDescription, startingPrice, user);
                            ProductModel productModel = new ProductModel(product);

                            success = true;
                            persist.saveProduct(product);
                            basicResponse = new ProductResponse(productModel);
                        } else {
                            errorCode = ERROR_NO_STARTING_PRICE_PRODUCT;
                        }
                    } else {
                        errorCode = ERROR_NO_PRODUCT_NAME;
                    }
                } else {
                    errorCode = ERROR_NO_PRODUCT_IMG;
                }
            } else {
                errorCode = ERROR_NO_PRODUCT_DESCRIPTION;
            }
        } else {
            errorCode = ERROR_NO_SUCH_USER;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }
}
