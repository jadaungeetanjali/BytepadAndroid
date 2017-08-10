package in.silive.bo.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import in.silive.bo.Config;
import in.silive.bo.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by AKG002 on 06-08-2016.
 */
public class BytepadApplication extends Application {
    private Tracker mTracker;
    private static BytepadApplication singleton = null;
    public SharedPreferences sharedPrefs;
    public static BytepadApplication getInstance() {
        return singleton;
    }
    @Override
    public void onCreate() {
        singleton = this;
        super.onCreate();
        sharedPrefs = getSharedPreferences(Config.SHARED_PREFS, Context.MODE_PRIVATE);
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG

        }
        return mTracker;
    }





}
