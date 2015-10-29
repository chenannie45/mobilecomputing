package edu.asu.mc29.mywardrobe.data;

import org.json.JSONObject;

/**
 * Created by cchen211 on 10/28/2015.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
