package com.example.ftechdevice.Common.CommonAdapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter implements JsonDeserializer<List<String>> {
    @Override
    public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        List<String> messages = new ArrayList<>();
        if (json != null) {
            if (json.isJsonArray()) {
                json.getAsJsonArray().forEach(element -> messages.add(element.getAsString()));
            } else if (json.isJsonPrimitive()) {
                messages.add(json.getAsString());
            }
        }
        return messages;
    }
}