package com.dev.controllers;

import com.dev.objects.Offer;
import com.dev.objects.User;
import com.dev.responses.AllOffersResponse;
import com.dev.responses.AllUsersResponse;
import com.dev.responses.BasicResponse;
import com.dev.responses.LoginResponse;
import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.dev.utils.Constants.ADMIN_ID;
import static com.dev.utils.Errors.*;

@RestController
public class LoginController {

    @Autowired
    private Utils utils;

    @Autowired
    private Persist persist;

    @RequestMapping(value = "sign-up")
    public BasicResponse signUp (String username , String password) {
        BasicResponse basicResponse = new BasicResponse();
        boolean success = false;
        Integer errorCode = null;
        if (username != null ) {
            if (password != null) {
                if (utils.isStrongPassword(password)) {
                    User fromDb = persist.getUserByUsername(username);
                    if (fromDb == null) {
                        User toAdd = new User(username,utils.createHash(username,password));
                        persist.saveUser(toAdd);
                        success = true;
                    } else {
                        errorCode = ERROR_USERNAME_ALREADY_EXISTS;
                    }
                } else {
                    errorCode = ERROR_WEAK_PASSWORD;
                }

            } else {
                errorCode = ERROR_MISSING_PASSWORD;
            }
        } else {
            errorCode = ERROR_MISSING_USERNAME;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }

    @RequestMapping(value = "login")
    public BasicResponse login (String username , String password) {
        BasicResponse basicResponse = new BasicResponse();
        boolean success = false;
        Integer errorCode = null;
        if (username != null ) {
            if (password != null) {
                String token = utils.createHash(username, password);
                User fromDb = persist.getUserByUsernameAndToken(username,token);
                if (fromDb != null) {
                    if (fromDb.getId() != ADMIN_ID && !fromDb.isAdmin()) {
                        success = true;
                        basicResponse = new LoginResponse(token);
                    } else {
                        errorCode = ERROR_SYSTEM_ADMIN_CANT_LOGIN;
                    }
                } else {
                    errorCode = ERROR_WRONG_LOGIN_CREDS;
                }
            } else {
                errorCode = ERROR_MISSING_PASSWORD;
            }
        } else {
            errorCode = ERROR_MISSING_USERNAME;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }

    @RequestMapping(value = "login-as-admin")
    public BasicResponse loginAsAdmin (String username , String password) {
        BasicResponse basicResponse = new BasicResponse();
        boolean success = false;
        Integer errorCode = null;
        if (username != null ) {
            if (password != null) {
                String token = utils.createHash(username, password);
                User fromDb = persist.getUserByUsernameAndToken(username,token);
                if (fromDb != null) {
                    if (fromDb.getId() == ADMIN_ID && fromDb.isAdmin()) {
                        success = true;
                        basicResponse = new LoginResponse(token);
                    } else {
                        errorCode = ERROR_NOT_ADMIN;
                    }
                } else {
                    errorCode = ERROR_WRONG_LOGIN_CREDS;
                }
            } else {
                errorCode = ERROR_MISSING_PASSWORD;
            }
        } else {
            errorCode = ERROR_MISSING_USERNAME;
        }
        basicResponse.setSuccess(success);
        basicResponse.setErrorCode(errorCode);
        return basicResponse;
    }

    @RequestMapping(value = "get-all-users-" , method = RequestMethod.GET)
    public BasicResponse getAllUsers () {
        List<User> users = persist.getAllUsers();
        int usersAmount = users.size();
        AllUsersResponse allUsersResponse = new AllUsersResponse(true,null,users);
        return allUsersResponse;
    }

    @RequestMapping(value = "get-all-offers" , method = RequestMethod.GET)
    public BasicResponse getAllOffers() {
        BasicResponse basicResponse = new BasicResponse(false , null);
        List<Offer> offers = persist.getAllOffers();
        if (offers != null) {
            basicResponse = new AllOffersResponse(true ,null , offers);
        } else {
            basicResponse.setErrorCode(ERROR_NO_OFFERS_IN_THE_SYSTEM);
        }
        return basicResponse;
    }
}
