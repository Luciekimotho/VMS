package com.stallar.vms.Views;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.microblink.activity.BaseScanActivity;
import com.microblink.activity.ScanCard;
import com.microblink.activity.SegmentScanActivity;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognizerSettings;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;
import com.stallar.vms.R;
import com.stallar.vms.ResultView.NationalIDResult;
import com.stallar.vms.model.NationalIID;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NationalIDScan extends AppCompatActivity {
    int MY_REQUEST_CODE = 300;
    private BaseRecognitionResult mData = null;
    String paramString;
    Intent i;
    String TAG = "Blink id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_national_idscan);

        Intent intent = new Intent(this, ScanCard.class);
        /** Set the license key */
        intent.putExtra(BaseScanActivity.EXTRAS_LICENSE_KEY,
                "7KESVHBO-2SWIURYN-LWFDSJJF-DGBJAUYS-UF5LUUFK-JEEIFECT-CKQXUGXD-2CSPZKSN");

        RecognitionSettings settings = new RecognitionSettings();
        // setup array of recognition settings (described in chapter "Recognition
        // settings and results")
        settings.setRecognizerSettingsArray(setupSettingsArray());
        intent.putExtra(ScanCard.EXTRAS_RECOGNITION_SETTINGS, settings);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Activity
        startActivityForResult(intent, MY_REQUEST_CODE);
        //finish(); // call this to finish the current activity
    }

    private RecognizerSettings[] setupSettingsArray() {
        MRTDRecognizerSettings sett = new MRTDRecognizerSettings();
        sett.setAllowUnparsedResults(true);
        // now add sett to recognizer settings array that is used to configure
        // recognition
        return new RecognizerSettings[] { sett };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode == SegmentScanActivity.RESULT_OK && data != null) {
                Log.d(TAG, "yes data");

                paramString = ((RecognitionResults)data.getParcelableExtra("EXTRAS_RECOGNITION_RESULTS"))
                        .getRecognitionResults()[0].getStringElement("MRTDRaw");

                Log.d(TAG, paramString);
                populateListViewNationalID(paramString);

                data.setComponent(new ComponentName(this, NationalIDResult.class));
                startActivity(data);

            }
        }
    }

    private void populateListViewNationalID(String paramString) {
        ArrayList localArrayList = new ArrayList();
        Object localObject = TextUtils.split(paramString, "\n");
        Log.d(TAG, "raw(ID)=\n" + paramString);

        Pattern pattern = Pattern.compile("IDKYA([0-9]{9})[0-9][<]{2}[0-9A-Z]+(?=<)[<]+(?=[0-9A-Z])[0-9A-Z]{3,4}");
        Matcher matcher = pattern.matcher(paramString);
        if (matcher.find()){
            Log.d(TAG,"Found value: " + matcher.group(0) );
            Log.d(TAG,"Found value: " + matcher.group(1) );
//            Log.d(TAG,"Found value: " + matcher.group(2) );
        }else {
            Log.d(TAG, "no match");
        }
        //paramString = Pattern.compile("IDKYA([0-9]{9})[0-9][<]{2}[0-9A-Z]+(?=<)[<]+(?=[0-9A-Z])[0-9A-Z]{3,4}").matcher(localObject[0]);
    }
}
