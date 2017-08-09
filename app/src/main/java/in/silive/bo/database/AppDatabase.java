package in.silive.bo.database;

/**
 * Created by root on 9/8/17.
 */
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import in.silive.bo.PaperDatabaseModel;
import in.silive.bo.dao.PaperDatabaseDao;

@Database(entities = {PaperDatabaseModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "paper_db")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    public abstract PaperDatabaseDao itemAndPersonModel();

}
