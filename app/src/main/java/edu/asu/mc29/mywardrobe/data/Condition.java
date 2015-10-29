package edu.asu.mc29.mywardrobe.data;

import org.json.JSONObject;

/**
 * Created by cchen211 on 10/28/2015.
 */
public class Condition implements JSONPopulator {
    private  int code;
    private  int tempreture;

    public int getCode() {
        return code;
    }

    public int getTempreture() {
        return tempreture;
    }

    public String getDescription() {
        return description;
    }

    private  String description;

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        tempreture = data.optInt("temp");
        description = data.optString("text");
    }
}
