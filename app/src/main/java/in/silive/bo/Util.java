package in.silive.bo;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.Delete;

import java.io.File;
import java.io.FileNotFoundException;

import in.silive.bo.Activities.MainActivity;
import in.silive.bo.Application.BytepadApplication;
import in.silive.bo.Network.CheckConnectivity;
import in.silive.bo.database.RoomDb;
import in.silive.bo.util.PaperDetails;

/**
 * Created by AKG002 on 08-08-2016.
 */
public class Util {
    private static RoomDb appDatabase;

    public static boolean isDownloadComplete(Activity context, long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor c= downloadManager.query(new DownloadManager.Query().setFilterById(downloadId));

        if(c.moveToFirst()){
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            if(status == DownloadManager.STATUS_SUCCESSFUL){
                return true;
            }else{
                int reason = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON));
                Log.d("Bytepad", "Download not correct, status [" + status + "] reason [" + reason + "]");
                return false;
            }
        }
        return false;
    }

    public static void openDocument(Activity context ,String name) {
        BytepadApplication application = (BytepadApplication)context.getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        File file = new File(name);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
            showSnackBar(context,"No document viewer found.");
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("View Paper")
                    .setAction("opening")
                    .setLabel("Viewer not found")
                    .build());
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimetype);
            context.startActivity(Intent.createChooser(intent, "Choose an Application:"));
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("View Paper")
                    .setAction("opening")
                    .setLabel("Viewer found")
                    .build());
        }

    }

    public static void downloadPaper(final Activity context, final PaperDetails paper) {
        BytepadApplication application = (BytepadApplication)context.getApplication();
        final Tracker mTracker = application.getDefaultTracker();
        PrefManager prefManager = new PrefManager(context);
        if (!CheckConnectivity.isNetConnected(context)){
            Snackbar
                    .make(((MainActivity)context).coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadPaper(context,paper);
                        }
                    }).show();
        }else {
            final DownloadManager downloadManager;
            String file_url = paper.fileUrl;
            file_url = ("http://testapi.silive.in/PaperFileUpload/PUT/"+file_url).trim();
            file_url =file_url.replace(" ","%20");
            Log.d("debugg",file_url);
            final long downloadReference;
            BroadcastReceiver recieveDownloadComplete, notificationClicked;
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(file_url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle("xyz");
            request.setDescription("Bytepad Paper Download");
            final Uri uri1 = Uri.parse("file://" + prefManager.getDownloadPath() + "/" + "xyz");
            request.setDestinationUri(uri1);
            request.setVisibleInDownloadsUi(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            downloadReference = downloadManager.enqueue(request);

            DownloadQueue queueItem = new DownloadQueue();
            queueItem.dwnldPath = uri1.getPath();
            queueItem.reference = downloadReference;
            queueItem.paperId = paper.id;
            appDatabase = RoomDb.getDatabase(context.getApplication());
            appDatabase.itemAndPersonModel().addQueue(queueItem);
            showSnackBar(context,"Download Started : " + "xyz");
            IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED);
            notificationClicked = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String id = DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS;
                    long[] references = intent.getLongArrayExtra(id);
                    for (long reference : references) {
                        if (reference == downloadReference) {
                            try {
                                downloadManager.openDownloadedFile(downloadReference);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            };
            context.registerReceiver(notificationClicked, intentFilter);
            IntentFilter intentFilterDownload = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            recieveDownloadComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (downloadReference == reference) {
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(reference);
                        Cursor cursor = downloadManager.query(query);
                        cursor.moveToFirst();
                        int colmIndx = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        int status = cursor.getInt(colmIndx);
                        int fileNameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                        switch (status) {
                            case DownloadManager.STATUS_SUCCESSFUL:
                                showSnackBar(context,paper.subjectName + " downloaded");
                                paper.downloaded = true;
                                paper.dwnldPath = uri1.getPath();
                                //paper.update();
                                /*mTracker.send(new HitBuilders.EventBuilder()
                                        .setCategory("Download")
                                        .setAction("Paper download")
                                        .set("Result","Success")
                                        .build());*/
                                //FlowContentObserver.setShouldForceNotify(true);
                         //       new Delete().from(DownloadQueue.class).where(DownloadQueue_Table.paperId.eq(paper.id)).query();
                                break;
                            case DownloadManager.STATUS_FAILED:
                                /*mTracker.send(new HitBuilders.EventBuilder()
                                        .setCategory("Download")
                                        .setAction("Paper download")
                                        .set("Result","failed")
                                        .build());*/
                                showSnackBar(context,"Download Unsuccessful");
                                break;
                            case DownloadManager.STATUS_PAUSED:
                                Toast.makeText(context, "Download Paused", Toast.LENGTH_SHORT).show();
                                break;
                            case DownloadManager.STATUS_RUNNING:
                                Toast.makeText(context, "Downloading", Toast.LENGTH_SHORT).show();
                                break;
                            case DownloadManager.STATUS_PENDING:
                                Toast.makeText(context, "Download Pending", Toast.LENGTH_SHORT).show();
                                break;
                        }

                    }
                }
            };
            context.registerReceiver(recieveDownloadComplete, intentFilterDownload);
        }
    }
    public static void showSnackBar(Context context,String s){
        Snackbar.make(((SnackBarListener)context).getCoordinatorLayout() ,s,Snackbar.LENGTH_SHORT).show();
    }

}
