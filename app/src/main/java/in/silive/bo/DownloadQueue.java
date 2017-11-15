package in.silive.bo;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by AKG002 on 08-08-2016.
 */
@Entity
public class DownloadQueue  {

    @PrimaryKey
    public long reference;

    public int paperId;

    public String dwnldPath;
}
