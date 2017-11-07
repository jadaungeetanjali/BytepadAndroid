package in.silive.bo.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.PendingRequestListener;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.silive.bo.Application.BytepadApplication;
import in.silive.bo.DownloadQueue;

import in.silive.bo.Fragments.DialogFileDir;
import in.silive.bo.MarshMallowPermission;

import in.silive.bo.Models.PaperModel;
import in.silive.bo.Models.SubjectModel;
import in.silive.bo.Network.CheckConnectivity;
import in.silive.bo.Network.RoboRetroSpiceRequest;
import in.silive.bo.Network.RoboRetroSpiceRequestSubject;
import in.silive.bo.Network.RoboRetrofitService;

import in.silive.bo.PaperDatabaseModel;

import in.silive.bo.PrefManager;
import in.silive.bo.R;
//import in.silive.bo.Services.RegisterGCM;
import in.silive.bo.SubjectDatabaseModel;
import in.silive.bo.Util;
import in.silive.bo.database.RoomDb;
import in.silive.bo.viewmodel.BytepadAndroidViewModel;

public class SplashActivity extends AppCompatActivity implements RequestListener<PaperModel.PapersList> {
    public static PaperModel pm;
    RelativeLayout splash;
    SpiceManager spiceManager;
    RoboRetroSpiceRequest roboRetroSpiceRequest;
    RoboRetroSpiceRequestSubject roboRetroSpiceRequestSubject;
    PrefManager prefManager;
    Bundle paperModelBundle;
    TextView tvProgressInfo;
    Tracker mTracker;
    Bundle bundle;
    ArrayList<PaperModel> list;
    AnimateHorizontalProgressBar progressBar;
    private BytepadAndroidViewModel addAndroidViewModel;
    private RoomDb appDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bundle = new Bundle();
        prefManager = new PrefManager(this);
        BytepadApplication application = (BytepadApplication) getApplication();
        appDatabase = RoomDb.getDatabase(this.getApplication());

  //      mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("SplashActivity");
  //      mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    //    if (!prefManager.isGCMTokenSentToServer()) {
      //      Intent i = new Intent(this, RegisterGCM.class);
        //    startService(i);
      //  }
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
        checkPermissions();

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
                checkSubjectList();
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
