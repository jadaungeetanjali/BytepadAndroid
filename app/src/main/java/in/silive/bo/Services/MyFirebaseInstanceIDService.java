package in.silive.bo.Services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.silive.bo.Application.BytepadApplication;
import in.silive.bo.Config;
import in.silive.bo.Network.CheckConnectivity;
import in.silive.bo.Network.FetchData;
import in.silive.bo.dialog.AlertDialog;
import in.silive.bo.listeners.FetchDataListener;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    //    parkonmineparking static final String Authorization_Token = "Authorization_Token";
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    SharedPreferences pref;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        System.out.println("refreshedToken" + refreshedToken);
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {



        if (CheckConnectivity.isNetConnected(getApplicationContext())) {
            // sending fcm token to server
            FetchData fetchData = new FetchData(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    pref = BytepadApplication.getInstance().sharedPrefs;
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("FirebaseIdSendToServer", "1");//1 means firebase id is registered
                    editor.apply();

                }
            }, getApplicationContext());
            String post_data = "";
            try {
                post_data = URLEncoder.encode("fcm_token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
                Log.d("fcm", post_data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            fetchData.setArgs(Config.FIREBASE_TOKEN_UPDATE, post_data);
            fetchData.execute();

            Log.e(TAG, "sendRegistrationToServer: " + token);
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(getApplicationContext());
        }
    }

    private void storeRegIdInPref(String token) {
        pref = BytepadApplication.getInstance().sharedPrefs;
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.putString("FirebaseIdSendToServer", "0");//0 means firebase id is not updated to server
        editor.apply();
    }
}