package in.silive.bo.Models;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


import in.silive.bo.PaperDatabaseModel;







public class PaperModel implements Parcelable {

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
    public Integer paperType;
    @SerializedName("admin_id")
    @Expose
    public Integer adminId;

    /**
     * No args constructor for use in serialization
     *
     */
    public PaperModel() {
    }


    public PaperModel(Integer id, Integer subjectCodeId, Integer examTypeId, String fileUrl, Integer semester, Integer sessionId, Integer paperType, Integer adminId) {
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

    protected PaperModel(Parcel in) {
        id=in.readInt();
        subjectCodeId=in.readInt();
        examTypeId=in.readInt();
        semester=in.readInt();
        sessionId=in.readInt();
        paperType=in.readInt();
        adminId=in.readInt();
        fileUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileUrl);
        dest.writeInt(id);
        dest.writeInt(examTypeId);
        dest.writeInt(semester);
        dest.writeInt(sessionId);
        dest.writeInt(paperType);
        dest.writeInt(adminId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaperModel> CREATOR = new Creator<PaperModel>() {
        @Override
        public PaperModel createFromParcel(Parcel in) {
            return new PaperModel(in);
        }

        @Override
        public PaperModel[] newArray(int size) {
            return new PaperModel[size];
        }
    };


    @SuppressWarnings("serial")
    public static class PapersList extends ArrayList<PaperModel> {
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
