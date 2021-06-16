package com.dtl.parks.data;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dtl.parks.MapsActivity;
import com.dtl.parks.Utils.LoadingDialog;
import com.dtl.parks.Utils.UTIL;
import com.dtl.parks.controller.appController;
import com.dtl.parks.model.Images;
import com.dtl.parks.model.Park;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class repository {
    static List<Park> Parks = new ArrayList<>();
        public static void getParks(final AsyncResponse callback,String stateCode){
            String URL =UTIL.getParksUrl(stateCode);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,null, response -> {
            try {
                JSONArray dataArray = response.getJSONArray("data");
                for (int i = 0; i <dataArray.length() ; i++) {
                    Park park = new Park();
                    JSONObject parkObject = dataArray.getJSONObject(i);
                    park.setId(parkObject.getString("id"));
                    park.setFullName(parkObject.getString("fullName"));
                    park.setLatitude(parkObject.getString("latitude"));
                    park.setLongitude(parkObject.getString("longitude"));
                    park.setParkCode(parkObject.getString("parkCode"));
                    park.setDescription(parkObject.getString("description"));
                    park.setStates(parkObject.getString("states"));
                    park.setDesignation(parkObject.getString("designation"));
                    JSONArray imageList = parkObject.getJSONArray("images");
                    List<Images> listOfImages = new ArrayList<>();
                    for (int j = 0; j < imageList.length(); j++) {
                        Images images = new Images();
                        JSONObject image = imageList.getJSONObject(j);
                        images.setUrl(image.getString("url"));
                        images.setTitle(image.getString("title"));
                        listOfImages.add(images);
                    }
                    park.setImages(listOfImages);
                    Parks.add(park);
                }
                if(callback!=null){callback.processPark(Parks);}
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::fillInStackTrace);

        appController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
