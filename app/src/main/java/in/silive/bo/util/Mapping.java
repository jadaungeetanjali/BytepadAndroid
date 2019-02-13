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

        String[] session = {"2012-2013", "2013-2014", "2014-2015", "2015-2016", "2016-2017", "2017-2018", "2018-2019","2019-2020","2020-2021"};
        int[] session_codes = {1, 2, 3, 4, 5, 6, 7,8,9};


    myMap = new HashMap<Integer, String>();
        for (int i = 0; i < session.length; i++) {

            myMap.put(session_codes[i], session[i]);
        }
    }
    public String getvalues(int key)
    {
        map();
        return  myMap.get(key);
    }
}
