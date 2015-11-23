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
            put(R.id.pants_button,"Bottoms");
            put(R.id.formal_button,"Formal Bottoms");
            put(R.id.suit_button,"Formal Tops");
            put(R.id.tshirts_button,"Tops");
            put(R.id.shoes_button,"Shoes");
            put(R.id.glasses_button,"Glasses");

        }
    };
}
