package in.silive.bo.database;

/**
 * Created by root on 9/8/17.
 */
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import in.silive.bo.DownloadQueue;
import in.silive.bo.PaperDatabaseModel;
import in.silive.bo.SubjectDatabaseModel;
import in.silive.bo.dao.PaperDatabaseDao;

@Database(entities = {PaperDatabaseModel.class,SubjectDatabaseModel.class, DownloadQueue.class}, version = 1,exportSchema = false)

public abstract class RoomDb extends RoomDatabase {

    private static RoomDb INSTANCE;

    public static RoomDb getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), RoomDb.class, "paper_db").allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract PaperDatabaseDao itemAndPersonModel();



}
