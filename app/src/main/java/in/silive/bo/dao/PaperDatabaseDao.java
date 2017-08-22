package in.silive.bo.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import in.silive.bo.PaperDatabaseModel;
import in.silive.bo.SubjectDatabaseModel;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by root on 7/8/17.
 */
@Dao
public interface PaperDatabaseDao {

      @Query("select * from PaperDatabaseModel where id in :id")
      LiveData<List<PaperDatabaseModel>> getPapers(int[] id);

    @Query("select * from PaperDatabaseModel where id in :id")
    LiveData<List<SubjectDatabaseModel>> getSubjects(String sub);

        @Query("select * from PaperDatabaseModel where id = :id")
        PaperDatabaseDao getPaperbyId(String id);


       @Query("select * from PaperDatabaseModel where downloaded = :downloaded")
       Boolean downloaded(Boolean downloaded);

        @Insert(onConflict = REPLACE)
        void addPaper(PaperDatabaseModel borrowModel);

        @Delete
        void deletePaper(PaperDatabaseModel borrowModel);


}
