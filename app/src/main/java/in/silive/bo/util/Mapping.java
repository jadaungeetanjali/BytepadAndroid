package in.silive.bo.util;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by root on 22/8/17.
 */

public class Mapping {

    private Context context;
    HashMap<Integer, String> myMap;

    public Mapping() {

    }

    public void map() {
        String[] exam_type = {"UT", "PUT", "ST-1", "ST-2"};
        int[] examtype_codes = {1, 2, 3, 4};
        String[] session = {"2010-2011", "2011-2012", "2012-2013", "2013-2014", "2014-2015", "2015-2016", "2016-2017"};
        int[] session_codes = {1, 2, 3, 4, 5, 6, 7};


    myMap = new HashMap<Integer, String>();
        for (int i = 0; i < exam_type.length; i++) {
            myMap.put(examtype_codes[i], exam_type[i]);
            myMap.put(session_codes[i], session[i]);
        }
    }
    public String getvalues(int key)
    {
        map();
        return  myMap.get(key);
    }
}
