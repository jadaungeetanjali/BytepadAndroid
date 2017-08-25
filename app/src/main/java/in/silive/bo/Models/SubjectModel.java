package in.silive.bo.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by root on 12/8/17.
 */



public class SubjectModel implements Parcelable{

    @SerializedName("Id")
    @Expose
    public Integer id;
    @SerializedName("SubjectCode")
    @Expose
    public String subjectCode;
    @SerializedName("SubjectName")
    @Expose
    public String subjectName;

    /**
     * No args constructor for use in serialization
     *
     */
    public SubjectModel() {
    }


    public SubjectModel(Integer id, String subjectCode, String subjectName) {
        super();
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }

    protected SubjectModel(Parcel in) {
        id=in.readInt();
        subjectCode = in.readString();
        subjectName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(subjectCode);
        dest.writeString(subjectName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubjectModel> CREATOR = new Creator<SubjectModel>() {
        @Override
        public SubjectModel createFromParcel(Parcel in) {
            return new SubjectModel(in);
        }

        @Override
        public SubjectModel[] newArray(int size) {
            return new SubjectModel[size];
        }
    };
    @SuppressWarnings("serial")
    public static class SubjectList extends ArrayList<SubjectModel> {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}
