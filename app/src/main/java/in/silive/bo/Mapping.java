package in.silive.bo;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by root on 22/8/17.
 */

public class Mapping {

    private Context context;
    HashMap<String, Integer> myMap;

    public Mapping(Context current) {
        this.context = current;
    }

    public void map() {
        String[] exam_type = {"UT", "PUT", "ST-1", "ST-2"};
        int[] examtype_codes = {1, 2, 3, 4};
        String[] session = {"2010-2011", "2011-2012", "2012-2013", "2013-2014", "2014-2015", "2015-2016", "2016-2017"};
        int[] session_codes = {1, 2, 3, 4, 5, 6, 7};


    myMap = new HashMap<String, Integer>();
        for (int i = 0; i < exam_type.length; i++) {
            myMap.put(exam_type[i], examtype_codes[i]);
            myMap.put(session[i], session_codes[i]);
        }
    }
    public int getvalues(String query)
    {
        return  myMap.get("query");
    }
}
