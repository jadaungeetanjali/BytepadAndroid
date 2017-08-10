package in.silive.bo.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import in.silive.bo.Application.BytepadApplication;
import in.silive.bo.Config;
import in.silive.bo.DownloadQueue;
import in.silive.bo.DownloadQueue_Table;
import in.silive.bo.Fragments.DialogFileDir;
import in.silive.bo.MarshMallowPermission;
import in.silive.bo.Models.PaperModel;
import in.silive.bo.Network.CheckConnectivity;
import in.silive.bo.Network.FetchData;
import in.silive.bo.Network.RoboRetroSpiceRequest;
import in.silive.bo.Network.RoboRetrofitService;
import in.silive.bo.PaperDatabaseModel;
import in.silive.bo.PaperDatabaseModel_Table;
import in.silive.bo.PrefManager;
import in.silive.bo.R;

import in.silive.bo.Util;
import in.silive.bo.dialog.AlertDialog;
import in.silive.bo.listeners.FetchDataListener;

public class SplashActivity extends AppCompatActivity implements RequestListener<PaperModel.PapersList> {
    public static PaperModel pm;
    RelativeLayout splash;
    SpiceManager spiceManager;
    RoboRetroSpiceRequest roboRetroSpiceRequest;
    PrefManager prefManager;
    Bundle paperModelBundle;
    TextView tvProgressInfo;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    Tracker mTracker;
    Bundle bundle;
    ArrayList<PaperModel> list;
    AnimateHorizontalProgressBar progressBar;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bundle = new Bundle();
        prefManager = new PrefManager(this);
        BytepadApplication application = (BytepadApplication) getApplication();
        sharedpreferences = BytepadApplication.getInstance().sharedPrefs;
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("SplashActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        splash = (RelativeLayout) findViewById(R.id.splash);
        progressBar = (AnimateHorizontalProgressBar) findViewById(R.id.animate_progress_bar);
        tvProgressInfo = (TextView) findViewById(R.id.tvProgressInfo);
        Log.d("Bytepad", "SplashActivity created");
        spiceManager = new SpiceManager(RoboRetrofitService.class);
        Log.d("Bytepad", "Spice manager initialized");
        roboRetroSpiceRequest = new RoboRetroSpiceRequest();
        Log.d("Bytepad", "Spice request initialized");
        checkPermissions();

        if (CheckConnectivity.isNetConnected(getApplicationContext())) {
            String firebase_id_send_to_server_or_not = sharedpreferences.getString(Config.FIREBASE_ID_SENT, "");
            if (firebase_id_send_to_server_or_not.equals("0")) {
                String Firebase_token = sharedpreferences.getString("regId", "");
                FetchData fetchData = new FetchData(new FetchDataListener() {
                    @Override
                    public void processStart() {
                    }

                    @Override
                    public void processFinish(String output) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Config.FIREBASE_ID_SENT, "1");//1 means firebase id is registered
                        editor.commit();
                    }
                }, this);
                String post_data = "";
                try {
                    post_data = URLEncoder.encode(Config.fcm_token, "UTF-8") + "=" + URLEncoder.encode(Firebase_token, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                fetchData.setArgs(Config.FIREBASE_TOKEN_UPDATE, post_data);
                fetchData.execute();
            }
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.alertDialog(this);
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // fcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");
                    //ToasterUtils.toaster("Push notification: " + message);
                }
            }
        };
    }


    private void checkPermissions() {
        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
        if (!marshMallowPermission.checkPermissionForExternalStorage()) {
            marshMallowPermission.requestPermissionForExternalStorage();
        }
        if (!marshMallowPermission.checkPermissionForRead()) {
            marshMallowPermission.requestPermissionForRead();
        } else
            checkPapersList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPapersList();
            } else {
                Toast.makeText(this, "Permission denied!!!. App is exiting now", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    public void checkPapersList() {
        if (!prefManager.isPapersLoaded()) {
            downloadPaperList();
        } else {
            tvProgressInfo.setText("Organizing your bookshelf");
            checkDownloadDir();
        }
    }

    public void downloadPaperList() {
        tvProgressInfo.setText("Searching for signals");
        if (!CheckConnectivity.isNetConnected(this)) {
            Snackbar
                    .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadPaperList();
                        }
                    }).show();

        } else {
            tvProgressInfo.setText("Moving satellites into position");
            spiceManager.execute(roboRetroSpiceRequest, "in.silive.bo", DurationInMillis.ONE_MINUTE, this);
        }
    }

    public void checkDownloadDir() {
        if (TextUtils.isEmpty(prefManager.getDownloadPath())) {
            DialogFileDir dialogFileDir = new DialogFileDir();
            dialogFileDir.show(getSupportFragmentManager(), "File Dialog");
            dialogFileDir.setListener(new DialogFileDir.Listener() {
                @Override
                public void onDirSelected(String addr) {
                    checkDownloadDir();
                    Log.d("Bytepad", "Directory added " + addr);
                }
            });
        } else checkDownloadList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Bytepad", "On start called");
        spiceManager.start(this);
    }


    private void moveToNextActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
        Log.d("Bytepad", "onStop called");
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        spiceException.printStackTrace();
        Log.d("Bytepad", "Request failure");
        Snackbar
                .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        downloadPaperList();
                    }
                }).show();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Download")
                .setAction("Paper list download")
                .set("Result", "Failed")
                .build());
    }

    @Override
    public void onRequestSuccess(final PaperModel.PapersList result) {
        Log.d("Bytepad", "Request success");
        updatePapers(result);
        progressBar.setMax(result.size());
        progressBar.setProgress(0);

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Download")
                .setAction("Paper list download")
                .set("Result", "Success")
                .build());

    }

    public void updatePapers(final PaperModel.PapersList result) {
        Log.d("Bytepad", "Updating papers in DB");
        tvProgressInfo.setText("Filling up your bookshelf");
        new AsyncTask<Void, Integer, Void>() {
            PrefManager pref = prefManager;

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                progressBar.setProgress(values[0]);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                new Delete().from(PaperDatabaseModel.class).query();
                for (int i = 0; i < result.size(); i++) {
                    PaperModel paper = result.get(i);
                    PaperDatabaseModel paperDatabaseModel = new PaperDatabaseModel();
                    paperDatabaseModel.Title = paper.Title;
                    paperDatabaseModel.ExamCategory = paper.ExamCategory;
                    paperDatabaseModel.PaperCategory = paper.PaperCategory;
                    paperDatabaseModel.URL = paper.URL;
                    paperDatabaseModel.RelativeURL = paper.RelativeURL;
                    paperDatabaseModel.Size = paper.Size;
                    paperDatabaseModel.downloaded = false;
                    paperDatabaseModel.save();
                    publishProgress(i + 1);
                }
                pref.setPapersLoaded(true);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                checkDownloadDir();
            }
        }.execute();

    }

    public void checkUpdate() {
        MAHUpdaterController.init(this, "http://highsoft.az/mah-android-updater-sample.php");
        MAHUpdaterController.callUpdate();
    }

    public void checkDownloadList() {
        List<DownloadQueue> list = new Select().from(DownloadQueue.class).queryList();
        for (DownloadQueue item : list) {
            if (Util.isDownloadComplete(this, item.reference)) {
                PaperDatabaseModel paper = new Select().from(PaperDatabaseModel.class)
                        .where(PaperDatabaseModel_Table.id.eq(item.paperId)).querySingle();
                if (paper != null) {
                    paper.downloaded = true;
                    paper.dwnldPath = item.dwnldPath;
                    paper.update();
                }
                new Delete().from(DownloadQueue.class).where(DownloadQueue_Table.reference.eq(item.reference)).query();
            }
        }

        List<PaperDatabaseModel> downloadedPapers = new Select().from(PaperDatabaseModel.class).where(PaperDatabaseModel_Table
                .downloaded.eq(true)).queryList();
        for (PaperDatabaseModel paper : downloadedPapers) {
            File file = new File(paper.dwnldPath);
            if (!file.exists()) {
                paper.downloaded = false;
                paper.update();
            }
        }

        moveToNextActivity();
    }


}
