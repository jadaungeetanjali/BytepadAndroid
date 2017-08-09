package in.silive.bo;

import android.arch.persistence.room.Entity;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by akriti on 5/8/16.
 */
@Entity
public class PaperDatabaseModel extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;

    public String Title;

    public String ExamCategory;

    public String PaperCategory;

    public String Size;

    public String URL;


    public String RelativeURL;

    public boolean downloaded;

    public String dwnldPath;


    public PaperDatabaseModel(int id,String title,String ExamCategory,String PaperCategory,String Size,String URL,
                              String RelativeURL,boolean downloaded,String dwnldPath) {
        this.id = id;
        this.downloaded=downloaded;
        this.ExamCategory=ExamCategory;
        this.Title=title;
        this.PaperCategory=PaperCategory;
        this.Size=Size;
        this.URL=URL;
        this.RelativeURL=RelativeURL;
        this.dwnldPath=dwnldPath;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public int getId() {
        return id;
    }

    public String getSize() {
        return Size;
    }

    public String getTitle() {
        return Title;
    }
}
