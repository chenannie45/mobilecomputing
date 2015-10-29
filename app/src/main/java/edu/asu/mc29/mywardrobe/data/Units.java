package edu.asu.mc29.mywardrobe.data;

import org.json.JSONObject;

/**
 * Created by cchen211 on 10/28/2015.
 */
public class Units implements JSONPopulator {
    private String tempreture;

    public String getTempreture() {
        return tempreture;
    }

    @Override

    public void populate(JSONObject data) {
        tempreture = data.optString("temperature");
    }
}
