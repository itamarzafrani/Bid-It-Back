package com.dev.controllers;

import com.dev.SseEvent.CloseProductSseEvent;
import com.dev.SseEvent.NewOfferSseEvent;
import com.dev.objects.User;
import com.dev.utils.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dev.utils.Constants.*;

@Controller
public class LiveUpdatesController {

    @Autowired
    private Persist persist;

    private final Map<Integer, SseEmitter> mainPageEmitterMap = new HashMap<>();

    @RequestMapping(value = "/dashboard-sse-handler", method = RequestMethod.GET)
    public SseEmitter dashBoardHandler(String token) {
        User user = persist.getUserByToken(token);
        SseEmitter sseEmitter = null;
        if (user != null) {
            sseEmitter = new SseEmitter(15L * MINUTE);
            this.mainPageEmitterMap.put(user.getId(), sseEmitter);
        }
        return sseEmitter;
    }


    public void sendNewOfferEvent(int productOwnerId , String offerUsername) {
        NewOfferSseEvent newOfferEvent = new NewOfferSseEvent(EVENT_NEW_OFFER,offerUsername);
        SseEmitter conversationEmitter = this.mainPageEmitterMap.get(productOwnerId);
        if (conversationEmitter != null) {
            try {
                conversationEmitter.send(newOfferEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendCloseProductEvent(String productOwnerUsername,  List<Integer> offersId) {
        CloseProductSseEvent closeProductEvent = new CloseProductSseEvent(EVENT_CLOSE_PRODUCT,productOwnerUsername);
        for (Integer offerId : offersId){
            SseEmitter conversationEmitter = this.mainPageEmitterMap.get(offerId);
            if (conversationEmitter != null){
                try {
                    conversationEmitter.send(closeProductEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }




}
