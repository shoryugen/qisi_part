package com.cs4261.androidclient;

import android.content.Context;

import com.cs4261.androidclient.model.Places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jibweb on 21/02/15.
 */
public class PlacesParser {

    final private static String TAG = "PlacesParser";

    public static String NAME_NODE = "name";
    public static String RATING_NODE = "rating";
    public static String LOCATION_NODE = "location";
    public static String PHONE_NODE = "phone";
    public static String IMAGE_URL_NODE = "image_url";


    public static List<Places> parseFeed (JSONObject json, Context context) {

        List<Places> placesList = new ArrayList<>();

        try {
            JSONArray ar = json.getJSONArray("businesses");

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Places place = new Places();

                place.setName(obj.getString(NAME_NODE));
                place.setRating(obj.getInt(RATING_NODE));
                JSONObject tmp_obj = obj.getJSONObject(LOCATION_NODE);
                JSONArray loc_ar = tmp_obj.getJSONArray("display_address");
                String loc = "";
                for(int j = 0; j< loc_ar.length(); j++) {
                    if (j != 0) {
                        loc += "\n";
                    }
                    loc += loc_ar.getString(j);
                }
                place.setLocation(loc);

                String phoneString = obj.has(PHONE_NODE) ? obj.getString(PHONE_NODE) : context.getString(R.string.no_phone_number);
                place.setPhone(phoneString);

                String imageString = obj.has(IMAGE_URL_NODE) ? obj.getString(IMAGE_URL_NODE) : context.getString(R.string.no_image_url);
                place.setImage_url(imageString);


                placesList.add(place);
            }

            return placesList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
