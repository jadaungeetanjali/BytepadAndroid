package in.silive.bo.Network;

import in.silive.bo.Models.PaperModel;
import in.silive.bo.Models.SubjectModel;
import in.silive.bo.SubjectDatabaseModel;
import retrofit.http.GET;

/**
 * Created by akriti on 2/8/16.
 */
public interface BytePad {
    @GET("/getallpapers")
    PaperModel.PapersList papersList();
    @GET("/subject_detail")
    SubjectModel.SubjectList subjectList();
}