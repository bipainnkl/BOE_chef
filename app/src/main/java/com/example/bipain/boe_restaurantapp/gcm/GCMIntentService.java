package com.example.bipain.boe_restaurantapp.gcm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.example.bipain.boe_restaurantapp.model.TestObject;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

/**
 * Created by hoang on 24/05/2017.
 */

public class GCMIntentService extends GcmListenerService {
    public static final String MESSAGE_TO_DINER = "MESSAGE_TO_DINER";
    public static final String MESSAGE_TO_CHEF_ORDER = "MESSAGE_TO_CHEF_ORDER";
    public static final String MESSAGE_TO_CHEF_DISH = "MESSAGE_TO_CHEF_DISH";
    public static final String MESSAGE_TO_WAITER = "MESSAGE_TO_WAITER";

    @Override
    public void onMessageReceived(String string, Bundle data) {
        String to = data.getString("to");
        String body = data.getString("body");

        if ("diner".equals(to)) {
            sendMessageToDiner(body);
        } else if ("chef".equals(to)) {
            String term = data.getString("term");
            if ("order".equals(term)) {
                sendMessageToChefOrder(body);
            } else if ("dish".equals(term)) {
                sendMessageToChefDish(body);
            }
        } else if ("waiter".equals(to)) {
            sendMessageToWaiter(body);
        }
    }

    private void sendMessageToDiner(String message) {
        Intent i = new Intent(MESSAGE_TO_DINER);
        i.putExtra("body", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    private void sendMessageToChefOrder(String message) {
        Intent i = new Intent(MESSAGE_TO_CHEF_ORDER);
        i.putExtra("body", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    private void sendMessageToChefDish(String message) {
        Intent i = new Intent(MESSAGE_TO_CHEF_DISH);
        i.putExtra("body", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    private void sendMessageToWaiter(String message) {
        Intent i = new Intent(MESSAGE_TO_WAITER);
        i.putExtra("body", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }
}
