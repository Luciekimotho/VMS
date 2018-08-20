package com.stallar.vms.ResultRecognition;

import android.content.Context;

import com.microblink.recognizers.BaseRecognitionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucie on 11/14/2017.
 */

public class NationalIDRecognition implements IBaseRecognitionResultExtractor {
    protected RecognitionResultEntry.Builder mBuilder;
    protected List<RecognitionResultEntry> mExtractedData;

    public NationalIDRecognition(Context context) {
        mBuilder = new RecognitionResultEntry.Builder(context);
        mExtractedData = new ArrayList<>();
    }
    @Override
    public List<RecognitionResultEntry> extractData(BaseRecognitionResult result) {

        if (result == null) {
            return mExtractedData;
        }

        return mExtractedData;
    }


}
