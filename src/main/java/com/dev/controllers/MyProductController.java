package com.dev.controllers;

import com.dev.objects.Product;
import com.dev.objects.User;
import com.dev.responses.AllProductsResponse;
import com.dev.responses.BasicResponse;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.dev.utils.Errors.ERROR_NO_PRODUCTS_LIST;
import static com.dev.utils.Errors.ERROR_NO_SUCH_TOKEN;

@RestController
public class MyProductController {

    @Autowired
    private Persist persist;

    @RequestMapping(value = "my-products" , method = RequestMethod.GET)
    public BasicResponse getAllMyProduct(String token) {
        BasicResponse basicResponse = null;
        int userId = persist.getUserIdByToken(token);
        List<Product> allProducts = persist.getAllProducts();
        List<Product> myProducts = new ArrayList<>();
        for (Product product: allProducts) {
            if (product.getPublisher().getId() == userId) {
                myProducts.add(product);
            }
        }
        if (myProducts != null) {
            basicResponse = new AllProductsResponse(true , null , myProducts);
        } else {
            basicResponse = new BasicResponse(false , ERROR_NO_PRODUCTS_LIST);
        }
        return basicResponse;
    }

}
