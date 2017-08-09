package in.silive.bo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

import in.silive.bo.Application.BytepadApplication;
import in.silive.bo.PaperDatabase;
import in.silive.bo.PaperDatabaseModel;
import in.silive.bo.database.AppDatabase;

/**
 * Created by root on 9/8/17.
 */

public class BytepadAndroidViewModel extends AndroidViewModel{
    private final LiveData<List<PaperDatabaseModel>> itemAndPersonList;

    private AppDatabase appDatabase;

    public BytepadAndroidViewModel(BytepadApplication application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        itemAndPersonList = appDatabase.itemAndPersonModel().getAllPapers();
    }


    public LiveData<List<PaperDatabaseModel>> getAllBorrowedItems() {
        return itemAndPersonList ;
    }



    }


