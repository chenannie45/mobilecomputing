package edu.asu.mc29.mywardrobe.data;

import java.util.HashMap;
import java.util.Map;

import edu.asu.mc29.mywardrobe.R;

/**
 * Created by rrshah9 on 11/13/2015.
 */
public class Constants {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String IMG_FILE_PREFIX = "IMG_WARDROBE_MANAGER";
    public static final String IMG_FILE_SUFFIX = ".png";
    public static final Map<Integer, String>imgToFolderNameMap = new HashMap<Integer,String>(){
        {
            put(R.id.pants_button,"Pants");
            put(R.id.formal_button,"Formals");
            put(R.id.suit_button,"Suits");
            put(R.id.tshirts_button,"T-shirts");
            put(R.id.others_button,"Other");
        }
    };
}
