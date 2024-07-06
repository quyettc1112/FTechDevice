package com.example.ftechdevice.Until;

import com.example.ftechdevice.Model.CartModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CartParser {

    public static List<CartModel> parseCartResponse(String jsonResponse) {
        Gson gson = new Gson();
        Type cartListType = new TypeToken<List<CartModel>>() {}.getType();
        return gson.fromJson(jsonResponse, cartListType);
    }
}