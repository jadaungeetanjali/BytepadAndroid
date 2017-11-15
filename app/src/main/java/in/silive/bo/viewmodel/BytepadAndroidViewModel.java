package in.silive.bo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.telecom.RemoteConnection;

import java.util.List;

import in.silive.bo.Application.BytepadApplication;

import in.silive.bo.PrefManager;
import in.silive.bo.database.RoomDb;
import in.silive.bo.util.PaperDetails;

/**
 * Created by root on 9/8/17.
 */

public class BytepadAndroidViewModel extends AndroidViewModel implements ViewModelProvider.Factory{
    private final LiveData<List<PaperDetails>> itemAndPersonList;
    //private final LiveData<List<SubjectDatabaseModel>> SubjectList;
    private RoomDb appDatabase;
    PrefManager prefManager;


    public BytepadAndroidViewModel(Application application) {
        super(application);
        prefManager = new PrefManager(this.getApplication());

        appDatabase = RoomDb.getDatabase(this.getApplication());

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

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return null;
    }
}


