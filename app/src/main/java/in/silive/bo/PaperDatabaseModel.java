package in.silive.bo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;





/**
 * Created by akriti on 5/8/16.
 */
@Entity
public class PaperDatabaseModel  {

    @PrimaryKey(autoGenerate = false)

    public int id;



    public Integer subjectCodeId;

    public Integer examTypeId;

    public String fileUrl;

    public Integer semester;

    public Integer sessionId;

    public int paperType;

    public Integer adminId;

    public String dwnldPath;
    public boolean downloaded;



    public PaperDatabaseModel(Integer id, Integer subjectCodeId, Integer examTypeId, String fileUrl,
                              Integer semester,Integer sessionId, int paperType, Integer adminId,boolean downloaded) {
        super();
        this.id = id;
        this.subjectCodeId = subjectCodeId;
        this.examTypeId = examTypeId;
        this.fileUrl = fileUrl;
        this.semester = semester;
        this.sessionId = sessionId;
        this.paperType = paperType;
        this.adminId = adminId;
        this.downloaded=downloaded;

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public Integer getSubjectCodeId() {
        return subjectCodeId;
    }

    public void setSubjectCodeId(Integer subjectCodeId) {
        this.subjectCodeId = subjectCodeId;
    }

    public String getDwnldPath() {
        return dwnldPath;
    }

    public void setDwnldPath(String dwnldPath) {
        this.dwnldPath = dwnldPath;
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

    public int getPaperType() {
        return paperType;
    }

    public void setPaperType(int paperType) {
        this.paperType = paperType;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }
}
