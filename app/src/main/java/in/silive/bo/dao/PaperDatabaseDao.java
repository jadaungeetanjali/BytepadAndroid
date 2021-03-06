package in.silive.bo.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

import in.silive.bo.DownloadQueue;
import in.silive.bo.PaperDatabaseModel;
import in.silive.bo.SubjectDatabaseModel;
import in.silive.bo.util.PaperDetails;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by root on 7/8/17.
 */
@Dao
public interface PaperDatabaseDao {

      @Query("select * from PaperDatabaseModel,SubjectDatabaseModel where PaperDatabaseModel.subjectCodeId=SubjectDatabaseModel.id1")
      LiveData<List<PaperDetails>> getPapers();



        //@Query("select * from PaperDatabaseModel where id = :id")
        //PaperDatabaseDao getPaperbyId(String id);


       @Query("select * from PaperDatabaseModel where downloaded = :downloaded")
       List<PaperDatabaseModel> downloaded(Boolean downloaded);
       @Query("select * from DownloadQueue")
       List<DownloadQueue> getQueuelist();
       @Query("select * from PaperDatabaseModel where id = :itemid")
       PaperDatabaseModel getdownloadedpaperlist(int itemid);
       @Query("update PaperDatabaseModel set downloaded = :val, dwnldPath = :downloadpath where id = :itemid")
       void update(boolean val,String downloadpath,int itemid);
    @Query("update PaperDatabaseModel set downloaded = :val  where id = :itemid")
    void updatepaper(boolean val,int itemid);
    @Query("update PaperDatabaseModel set downloaded = :val,dwnldPath=:path  where id = :itemid")
    void updatepaperDownloaded(boolean val,String path,int itemid);
       @Query("delete from DownloadQueue where reference = :itemreference")
       void delete(long itemreference);
     //@Query("select * from PaperDatabaseModel,SubjectDatabaseModel  where downloaded = :val")
     //List<PaperDatabaseModel>  setval(boolean val);
    @Query("select * from PaperDatabaseModel,SubjectDatabaseModel where PaperDatabaseModel.subjectCodeId=SubjectDatabaseModel.id1 and  examTypeId in (:val) and subjectName LIKE '%' || :query || '%' and downloaded=:value ORDER BY sessionId DESC")
    List<PaperDetails> setPaperType(int val[],String query,boolean value);

        @Insert(onConflict = REPLACE)
        void addPaper(PaperDatabaseModel borrowModel);
        @Insert(onConflict = REPLACE)
        void addSubject(SubjectDatabaseModel subjectDatabaseModel);
        @Insert(onConflict = REPLACE)
        void addQueue(DownloadQueue downloadQueue);
       @Delete
        void deletePaper(PaperDatabaseModel borrowModel);
       @Query("delete from PaperDatabaseModel")
      void deletePaperDb();
    @Query("delete from SubjectDatabaseModel")
    void deleteSubjectDb();


}
