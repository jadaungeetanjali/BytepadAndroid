package in.silive.bo.Models;

/**
 * Created by root on 12/8/17.
 */


        import android.os.Parcel;
        import android.os.Parcelable;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;


public class Model {


    public int id;

    public Integer subjectCodeId;

    private Integer examTypeId;

    private String fileUrl;

    private Integer semester;

    private Integer sessionId;

    private Integer paperType;

    private Integer adminId;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */


    public Model(Integer id, Integer subjectCodeId, Integer examTypeId, String fileUrl, Integer semester, Integer sessionId, Integer paperType, Integer adminId) {
        super();
        this.id = id;
        this.subjectCodeId = subjectCodeId;
        this.examTypeId = examTypeId;
        this.fileUrl = fileUrl;
        this.semester = semester;
        this.sessionId = sessionId;
        this.paperType = paperType;
        this.adminId = adminId;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectCodeId() {
        return subjectCodeId;
    }


    public void setSubjectCodeId(Integer subjectCodeId) {
        this.subjectCodeId = subjectCodeId;
    }


    public Integer getExamTypeId() {
        return examTypeId;
    }


    public void setExamTypeId(Integer examTypeId) {
        this.examTypeId = examTypeId;
    }


    public String getFileUrl() {
        return fileUrl;
    }


    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    public Integer getSemester() {
        return semester;
    }
 public void setSemester(Integer semester) {
        this.semester = semester;
    }


    public Integer getSessionId() {
        return sessionId;
    }


    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }


    public Integer getPaperType() {
        return paperType;
    }


    public void setPaperType(Integer paperType) {
        this.paperType = paperType;
    }


    public Integer getAdminId() {
        return adminId;
    }


    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }




}
