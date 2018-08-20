package com.stallar.vms.ResultRecognition;

import com.microblink.recognizers.BaseRecognitionResult;

import java.util.List;

/**
 * Created by lucie on 11/14/2017.
 */

public interface IBaseRecognitionResultExtractor {

    /**
     * Returns list with extracted entries.
     *
     * @param result BaseRecognitionResult
     * @return list with extracted entries
     */
    public List<RecognitionResultEntry> extractData(BaseRecognitionResult result);
}
