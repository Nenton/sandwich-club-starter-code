package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    private static final String SANDWICH_NAME = "name";
    private static final String SANDWICH_MAIN_NAME = "mainName";
    private static final String SANDWICH_ALSO_KNOW_AS = "alsoKnownAs";
    private static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String SANDWICH_DESCRIPTION = "description";
    private static final String SANDWICH_IMAGE = "image";
    private static final String SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich;

        try {
            JSONObject parent = new JSONObject(json);

            ArrayList<String> alsoKnownAs = new ArrayList<>();

            for (int i = 0; i < parent.getJSONObject(SANDWICH_NAME).getJSONArray(SANDWICH_ALSO_KNOW_AS).length(); i++) {
                alsoKnownAs.add(parent.getJSONObject(SANDWICH_NAME).getJSONArray(SANDWICH_ALSO_KNOW_AS).getString(i));
            }

            ArrayList<String> ingredients = new ArrayList<>();

            for (int i = 0; i < parent.getJSONArray(SANDWICH_INGREDIENTS).length(); i++) {
                ingredients.add(parent.getJSONArray(SANDWICH_INGREDIENTS).getString(i));
            }

            sandwich = new Sandwich(parent.getJSONObject(SANDWICH_NAME).getString(SANDWICH_MAIN_NAME),
                    alsoKnownAs,
                    parent.getString(SANDWICH_PLACE_OF_ORIGIN),
                    parent.getString(SANDWICH_DESCRIPTION),
                    parent.getString(SANDWICH_IMAGE),
                    ingredients);

        } catch (JSONException e) {
            return null;
        }

        return sandwich;
    }
}
