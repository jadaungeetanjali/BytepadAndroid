package in.silive.bo.util;

/**
 * Created by root on 8/11/17.
 */
import android.content.Context;

import java.util.HashMap;

/**
 * Created by root on 22/8/17.
 */

public class MappingPapeType{

    private Context context;
    HashMap<Integer, String> myMap;

    public MappingPapeType() {

    }

    public void map() {
        String[] exam_type = {"UT", "PUT", "ST-1", "ST-2"};
        int[] examtype_codes = {1, 2, 3, 4};



        myMap = new HashMap<Integer, String>();
        for (int i = 0; i < exam_type.length; i++) {
            myMap.put(examtype_codes[i], exam_type[i]);
          //  myMap.put(session_codes[i], session[i]);
        }
    }
    public String getvalues(int key)
    {
        map();
        return  myMap.get(key);
    }
}