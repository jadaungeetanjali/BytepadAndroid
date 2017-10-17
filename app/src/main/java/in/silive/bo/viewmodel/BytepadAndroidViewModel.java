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
import in.silive.bo.PrefManager;
import in.silive.bo.SubjectDatabaseModel;
import in.silive.bo.database.AppDatabase;
import in.silive.bo.util.PaperDetails;

/**
 * Created by root on 9/8/17.
 */

public class BytepadAndroidViewModel extends AndroidViewModel{
    private final LiveData<List<PaperDetails>> itemAndPersonList;
    //private final LiveData<List<SubjectDatabaseModel>> SubjectList;
    private AppDatabase appDatabase;
    PrefManager prefManager;

    public BytepadAndroidViewModel(BytepadApplication application) {
        super(application);
        prefManager = new PrefManager(this.getApplication());

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        itemAndPersonList = appDatabase.itemAndPersonModel().getPapers();
        //SubjectList=appDatabase.itemAndPersonModel().getSubjects();
    }


    public LiveData<List<PaperDetails>> getAllBorrowedItems() {
        return itemAndPersonList ;
    }
   // public LiveData<List<SubjectDatabaseModel>> getSubjectList()
    {
        //return SubjectList;
    }

    }


