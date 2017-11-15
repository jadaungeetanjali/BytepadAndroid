package in.silive.bo.Network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import in.silive.bo.Models.SubjectModel;

/**
 * Created by root on 24/8/17.
 */

public class RoboRetroSpiceRequestSubject extends RetrofitSpiceRequest<SubjectModel.SubjectList,BytePad> {
    public RoboRetroSpiceRequestSubject() {

        super(SubjectModel.SubjectList.class, BytePad.class);
    }

    @Override
    public SubjectModel.SubjectList loadDataFromNetwork() throws Exception {
        return getService().subjectList();
    }
}
