package in.silive.bo.Network;

import android.util.Log;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

/**
 * Created by root on 9/11/17.
 */

public class TimeStampRequest extends RetrofitSpiceRequest<String,BytePad> {
    public TimeStampRequest()
    {
        super(String.class,BytePad.class);

    }
    @Override
    public String loadDataFromNetwork() throws Exception {
        Log.d("time", getService().timeStamp());
        return getService().timeStamp();
    }
}
