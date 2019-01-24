package in.silive.bo.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 17/10/17.
 */

public class PaperDetails {
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("subjectId")
    @Expose
    public Integer subjectCodeId;
    @SerializedName("examTypeId")
    @Expose
    public Integer examTypeId;
    @SerializedName("fileUrl")
    @Expose
    public String fileUrl;
    @SerializedName("semesterType")
    @Expose
    public Integer semester;
    @SerializedName("sessionId")
    @Expose
    public Integer sessionId;
    @SerializedName("paperType")
    @Expose
    public String paperType;
    @SerializedName("adminId")
    @Expose
    public Integer adminId;
    public String dwnldPath;
    public boolean downloaded;

    @SerializedName("id")
    @Expose

    public Integer id1;


    //@SerializedName("SubjectCode")
    //@Expose
    //public String subjectCode;
    @SerializedName("subjectName")
    @Expose
    public String subjectName;

    public PaperDetails(Integer id1, Integer subjectCodeId, Integer examTypeId, String fileUrl, Integer semester, Integer sessionId, String paperType, Integer adminId, Integer id, String subjectName) {
        this.id = id1;
        this.subjectCodeId = subjectCodeId;
        this.examTypeId = examTypeId;
        this.fileUrl = fileUrl;
        this.semester = semester;
        this.sessionId = sessionId;
        this.paperType = paperType;
        this.adminId = adminId;
        this.id = id;
        //this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }
}
