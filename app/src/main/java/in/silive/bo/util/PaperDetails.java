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
    @SerializedName("subject_code_id")
    @Expose
    public Integer subjectCodeId;
    @SerializedName("exam_type_id")
    @Expose
    public Integer examTypeId;
    @SerializedName("file_url")
    @Expose
    public String fileUrl;
    @SerializedName("semester")
    @Expose
    public Integer semester;
    @SerializedName("session_id")
    @Expose
    public Integer sessionId;
    @SerializedName("paper_type")
    @Expose
    public int paperType;
    @SerializedName("admin_id")
    @Expose
    public Integer adminId;
    public String dwnldPath;
    public boolean downloaded;

    @SerializedName("Id")
    @Expose

    public Integer id1;


    //@SerializedName("SubjectCode")
    //@Expose
    //public String subjectCode;
    @SerializedName("SubjectName")
    @Expose
    public String subjectName;

    public PaperDetails(Integer id1, Integer subjectCodeId, Integer examTypeId, String fileUrl, Integer semester, Integer sessionId, int paperType, Integer adminId, Integer id, String subjectName) {
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
