package in.silive.bo.Activities;


import android.arch.lifecycle.ViewModelProviders;

import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.ahp.AnimateHorizontalProgressBar;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import in.silive.bo.Application.BytepadApplication;
import in.silive.bo.Config;
import in.silive.bo.DownloadQueue;

import in.silive.bo.Fragments.DialogFileDir;
import in.silive.bo.Manifest;
import in.silive.bo.MarshMallowPermission;

import in.silive.bo.Models.PaperModel;
import in.silive.bo.Models.SubjectModel;
import in.silive.bo.Network.CheckConnectivity;
import in.silive.bo.Network.FetchData;
import in.silive.bo.Network.RoboRetroSpiceRequest;
import in.silive.bo.Network.RoboRetroSpiceRequestSubject;
import in.silive.bo.Network.RoboRetrofitService;

import in.silive.bo.Network.TimeStampRequest;
import in.silive.bo.PaperDatabaseModel;

import in.silive.bo.PrefManager;
import in.silive.bo.R;

//import in.silive.bo.Services.RegisterGCM;
import in.silive.bo.SubjectDatabaseModel;
import in.silive.bo.Util;
import in.silive.bo.database.RoomDb;
import in.silive.bo.viewmodel.BytepadAndroidViewModel;


import in.silive.bo.Util;
import in.silive.bo.dialog.AlertDialog;
import in.silive.bo.listeners.FetchDataListener;


public class SplashActivity extends AppCompatActivity implements RequestListener<PaperModel.PapersList> {
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static PaperModel pm;
    RelativeLayout splash;
    SpiceManager spiceManager;
    RoboRetroSpiceRequest roboRetroSpiceRequest;
    RoboRetroSpiceRequestSubject roboRetroSpiceRequestSubject;
    TimeStampRequest timeStampRequest;
    PrefManager prefManager;
    Bundle paperModelBundle;
    TextView tvProgressInfo;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    String timeStamp;
    Bundle bundle;
    ArrayList<PaperModel> list;
    AnimateHorizontalProgressBar progressBar;

    private BytepadAndroidViewModel addAndroidViewModel;
    private RoomDb appDatabase;
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;


    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }
        bundle = new Bundle();
        prefManager = new PrefManager(this);
        BytepadApplication application = (BytepadApplication) getApplication();

        appDatabase = RoomDb.getDatabase(this.getApplication());
        sharedpreferences = BytepadApplication.getInstance().sharedPrefs;
  //      mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("SplashActivity");
  //      mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    //    if (!prefManager.isGCMTokenSentToServer()) {
      //      Intent i = new Intent(this, RegisterGCM.class);
        //    startService(i);

        splash = (RelativeLayout) findViewById(R.id.splash);
        addAndroidViewModel = ViewModelProviders.of(this).get(BytepadAndroidViewModel.class);
        progressBar = (AnimateHorizontalProgressBar) findViewById(R.id.animate_progress_bar);
        tvProgressInfo = (TextView) findViewById(R.id.tvProgressInfo);
        Log.d("Bytepad", "SplashActivity created");
        spiceManager = new SpiceManager(RoboRetrofitService.class);
        Log.d("Bytepad", "Spice manager initialized");
        roboRetroSpiceRequest = new RoboRetroSpiceRequest();
        Log.d("Bytepad", "Spice request initialized");
        roboRetroSpiceRequestSubject = new RoboRetroSpiceRequestSubject();
        timeStampRequest=new TimeStampRequest();
        checkTimeStamp();
       // checkPermissions();

     /*   if (CheckConnectivity.isNetConnected(getApplicationContext())) {
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
                        editor.putString(Config.FIREBASE_ID_SENT, "1");
                        editor.commit();
                    }
                }, this);
                String post_data = "";
                try {
                    post_data = URLEncoder.encode("gcm_id", "UTF-8") + "=" + URLEncoder.encode(Firebase_token, "UTF-8");
                    post_data += "&" + URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(Firebase_token, "UTF-8");
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
        };*/
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
              //  Log.v(TAG,"Permission is granted");
                checkPapersList();
                return true;
            } else {

                //Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
           // Log.v(TAG,"Permission is granted");
            checkPapersList();
            return true;
        }
    }


    private void checkPermissions() {

        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);
        if (!(marshMallowPermission.checkPermissionForExternalStorage()&&marshMallowPermission.checkPermissionForRead())) {
            marshMallowPermission.requestPermissionForExternalStorage();
            marshMallowPermission.requestPermissionForRead();
        }
        else {
            checkPapersList();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                    checkPapersList();
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                    Toast.makeText(getApplicationContext(),"App is exiting now",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

   // @Override
 /*   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkPapersList();

            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);

            }
        }
    }*/


    public void checkPapersList() {
        if (!prefManager.isPapersLoaded()) {
            downloadPaperList();
        } else {
            tvProgressInfo.setText("Organizing your bookshelf");
            checkDownloadDir();
        }
    }

    public void checkSubjectList() {
        if (!prefManager.isSubjectLoaded()) {
            downloadSubjectList();
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
    public void checkTimeStamp()
    {
        //tvProgressInfo.setText("Searching for signals");
        if (!CheckConnectivity.isNetConnected(this)) {
            Snackbar
                    .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkTimeStamp();                        }
                    }).show();

        } else {
            //tvProgressInfo.setText("Moving satellites into position");
            spiceManager.execute(timeStampRequest, "in.silive.bo", DurationInMillis.ONE_MINUTE, new PendingRequestListener<String>() {
                @Override
                public void onRequestNotFound() {


                }

                @Override
                public void onRequestFailure(SpiceException spiceException) {
                    Log.d("debugg",spiceException.getCause().toString());
                    //Toast.makeText(getApplicationContext(),"failed Timestamp",Toast.LENGTH_LONG).show();
                    Snackbar
                            .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    checkTimeStamp();
                                }
                            }).show();

                }



                @Override
                public void onRequestSuccess(String time) {
                    Log.d("Bytepad", "Request success");
                  timeStamp=time;
                  //Toast.makeText(getApplicationContext(),timeStamp,Toast.LENGTH_LONG).show();
                  if(sharedpreferences.getString(Config.TIMESTAMP,"").equals("")) {
                      SharedPreferences.Editor editor = sharedpreferences.edit();
                      editor.putString(Config.TIMESTAMP, timeStamp);
                      editor.commit();

                      isStoragePermissionGranted();
                  }
                  else if(sharedpreferences.getString(Config.TIMESTAMP,"").equals(timeStamp))
                  {
                      isStoragePermissionGranted();
                  }
                  else
                  {  isStoragePermissionGranted();
                      prefManager.setPapersLoaded(false);
                      prefManager.setSubjectLoaded(false);
                      appDatabase.itemAndPersonModel().deletePaperDb();
                      appDatabase.itemAndPersonModel().deleteSubjectDb();

                  }



       /* mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Download")
                .setAction("Paper list download")
                .set("Result", "Success")
                .build());*/

                }
            });
        }
    }

    public void downloadSubjectList() {
        tvProgressInfo.setText("Searching for signals");
        if (!CheckConnectivity.isNetConnected(this)) {
            Snackbar
                    .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadSubjectList();
                        }
                    }).show();

        } else {
            tvProgressInfo.setText("Moving satellites into position");
            spiceManager.execute(roboRetroSpiceRequestSubject, "in.silive.bo", DurationInMillis.ONE_MINUTE, new PendingRequestListener<SubjectModel.SubjectList>() {
                @Override
                public void onRequestNotFound() {


                }

                @Override
                public void onRequestFailure(SpiceException spiceException) {
                    Snackbar
                            .make(splash, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    downloadSubjectList();
                                }
                            }).show();

                }

                @Override
                public void onRequestSuccess(SubjectModel.SubjectList subjectModels) {
                    Log.d("Bytepad", "Request success");
                    updateSubject(subjectModels);
                    progressBar.setMax(subjectModels.size());
                    progressBar.setProgress(0);

       /* mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Download")
                .setAction("Paper list download")
                .set("Result", "Success")
                .build());*/

                }
            });
        }
    }

    public void checkDownloadDir() {
        if (TextUtils.isEmpty(prefManager.getDownloadPath())) {
            DialogFileDir dialogFileDir = new DialogFileDir();
           // dialogFileDir.setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogTheme);
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
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
       /* mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Download")
                .setAction("Paper list download")
                .set("Result", "Failed")
                .build());*/
    }

    @Override
    public void onRequestSuccess(final PaperModel.PapersList result) {
        Log.d("Bytepad", "Request success");
        updatePapers(result);
        progressBar.setMax(result.size());
        progressBar.setProgress(0);

       /* mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Download")
                .setAction("Paper list download")
                .set("Result", "Success")
                .build());*/
       // downloadSubjectList();

    }


    public void updateSubject(final SubjectModel.SubjectList result)
    {
        Log.d("Bytepad", "Updating subject in DB");
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

                for (int i = 0; i < result.size(); i++) {
                    SubjectModel subject = result.get(i);
                    //SubjectDatabaseModel subjectDatabaseModel =new SubjectDatabaseModel();
                    appDatabase.itemAndPersonModel().addSubject(new SubjectDatabaseModel(subject.id,subject.subjectCode,subject.subjectName));


                    publishProgress(i + 1);
                }
                pref.setSubjectLoaded(true);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                checkDownloadDir();
            }
        }.execute();
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

                for (int i = 0; i < result.size(); i++) {
                    PaperModel paper = result.get(i);

                    PaperDatabaseModel paperDatabaseModel = new PaperDatabaseModel(paper.id,paper.subjectCodeId,paper.examTypeId,paper.fileUrl,paper.semester,paper.sessionId,paper.paperType,paper.adminId,false);
                   /* paperDatabaseModel.id=paper.id;

                    paperDatabaseModel.subjectCodeId = paper.subjectCodeId;
                    paperDatabaseModel.examTypeId = paper.examTypeId;
                    paperDatabaseModel.fileUrl = paper.fileUrl;
                    paperDatabaseModel.semester = paper.semester;
                    paperDatabaseModel.sessionId = paper.sessionId;
                    paperDatabaseModel.paperType = paper.paperType;

                    paperDatabaseModel.downloaded = false;*/
                    appDatabase.itemAndPersonModel().addPaper(new PaperDatabaseModel(paper.id, paper.subjectCodeId, paper.examTypeId, paper.fileUrl
                            , paper.semester, paper.sessionId, paper.paperType, paper.adminId, false));


     publishProgress(i + 1);
                }
                pref.setPapersLoaded(true);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                downloadSubjectList();
                //checkDownloadDir();
            }
        }.execute();

    }

    public void checkUpdate() {
        MAHUpdaterController.init(this, "http://highsoft.az/mah-android-updater-sample.php");
        MAHUpdaterController.callUpdate();
    }

    public void checkDownloadList() {
        List<DownloadQueue> list =appDatabase.itemAndPersonModel().getQueuelist();

        for (DownloadQueue item : list) {
            if (Util.isDownloadComplete(this, item.reference)) {
                PaperDatabaseModel paper =appDatabase.itemAndPersonModel().getdownloadedpaperlist(item.paperId);
                if (paper != null) {

                    appDatabase.itemAndPersonModel().update(true,item.dwnldPath,item.paperId);
                }
                appDatabase.itemAndPersonModel().delete(item.reference);

            }
        }

        List<PaperDatabaseModel> downloadedPapers = appDatabase.itemAndPersonModel().downloaded(true);
        for (PaperDatabaseModel paper : downloadedPapers) {
            File file = new File(paper.dwnldPath);
            if (!file.exists()) {
                paper.downloaded = false;
                appDatabase.itemAndPersonModel().updatepaper(false,paper.id);
            }
        }

        moveToNextActivity();
    }


}
