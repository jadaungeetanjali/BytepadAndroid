package in.silive.bo.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import in.silive.bo.Config;
import in.silive.bo.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by AKG002 on 06-08-2016.
 */
public class BytepadApplication extends Application {

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
//        AppDatabase.getDatabase(this);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */






}
