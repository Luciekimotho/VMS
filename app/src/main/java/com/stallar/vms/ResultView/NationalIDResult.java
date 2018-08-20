package com.stallar.vms.ResultView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.microblink.activity.ScanActivity;
import com.microblink.recognizers.RecognitionResults;
import com.stallar.vms.R;
import com.stallar.vms.Views.VisitorsList;
import com.stallar.vms.model.NationalIID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class NationalIDResult extends AppCompatActivity {

    String idRaw = "";
    private List<NationalIID> idList;
    EditText et_names, et_idno, et_phone, et_purpose, et_whereto;
    String names, idno, phone, purpose, whereTo;
    String ver_names, ver_idno;

    ProgressBar progressBar;
    Button btnSave;

    String TAG = "Results";
    String username = "admin";
    String password = "Merciful1";
    String apiUrl = "http://demo-vms.herokuapp.com/api/visits";
    String visitUrl = "http://demo-vms.herokuapp.com/api/visit/";

    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_visit);

        et_names = (EditText) findViewById(R.id.et_name);
        et_idno = (EditText) findViewById(R.id.et_idno);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_purpose = (EditText) findViewById(R.id.et_purpose);
        et_whereto = (EditText) findViewById(R.id.et_whereto);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSave = (Button) findViewById(R.id.btn_save);

        RecognitionResults mResults = getIntent().getExtras().getParcelable(
                ScanActivity.EXTRAS_RECOGNITION_RESULTS);
        idRaw = mResults.getRecognitionResults()[0].getStringElement("MRTDRaw");
        Log.d("blinkid", "Raw id is: "+idRaw);


//        serialNo = getSerialNo(idRaw);
//        dateOfBirth = getDob(idRaw);
//        sex = getSex(idRaw);
        getDateOfIssue(idRaw);
        idno = getIDNo(idRaw);
        names = getName(idRaw);

        et_names.setText(names);
        et_idno.setText(idno);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                ver_names = et_names.getText().toString();
                ver_idno = et_idno.getText().toString();
                phone = et_phone.getText().toString();
                purpose = et_purpose.getText().toString();
                whereTo = et_whereto.getText().toString();

                final AsyncHttpClient client = new AsyncHttpClient();
                client.setBasicAuth(username,password);

                JSONObject params = new JSONObject();

                try {
                    params.put("id_no", ver_idno);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    params.put("name", ver_names);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    params.put("phone", phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    params.put("device", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    params.put("business", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    params.put( "purpose", purpose);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                StringEntity entity = null;
                try {
                    entity = new StringEntity(params.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                client.post(getApplicationContext(), visitUrl, entity, "application/json",
                        new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                Log.d(TAG,"Post on success"+ responseBody.toString());
                                Log.d(TAG, new String(responseBody));
                                progressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(NationalIDResult.this, VisitorsList.class));
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Log.d(TAG, "Post On failure"+ error.toString());
                            }
                        });
            }
        });
    }

    private String parseDate(String paramString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String str = paramString;
        try {
            if (paramString.contains("0000")) {
                str = paramString;
                if (!paramString.startsWith("00")) {
                    str = paramString.replace("0000", "0101");
                }
            }
            paramString = String.valueOf(sdf.parse(str));
            return paramString;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String getSerialNo(String idRawString){
        String[] idSplit = idRawString.split("\n");
        Pattern serialPattern = Pattern.compile("IDKYA([0-9]{9})[0-9][<]{2}[0-9A-Z]+(?=<)[<]+(?=[0-9A-Z])[0-9A-Z]{3,4}");
        Matcher serialMatcher = serialPattern.matcher(idRawString);
        if (serialMatcher.find()){
            Log.d("blinkid","Serial Number: " + serialMatcher.group(1) );
            return serialMatcher.group(1);
        }else {
            Log.d("blinkid", "Serial Number not found!");
            return "Serial Number not found!";
        }
    }
    private String getDob(String idRawString){
        String[] idSplit = idRawString.split("\n");
        Matcher secondLineMatcher = Pattern.compile("([0-9O]{6})[0-9]([MF])([0-9]{6})[0-9][<]B([0-9]{9})[A-Z][<]{2}[0-9]").matcher(idSplit[1]);
        if (secondLineMatcher.matches()) {
            Log.d("blinkid","Date of birth: " + (secondLineMatcher.group(1).replace("O","0")));
            return (secondLineMatcher.group(1).replace("O","0"));
        }
        return "Date of Birth not found!";
    }
    private String getSex(String idRawString){
        String[] idSplit = idRawString.split("\n");
        Matcher secondLineMatcher = Pattern.compile("([0-9O]{6})[0-9]([MF])([0-9]{6})[0-9][<]B([0-9]{9})[A-Z][<]{2}[0-9]").matcher(idSplit[1]);
        if (secondLineMatcher.matches()) {
            Log.d("blinkid","Sex: " + secondLineMatcher.group(2) );
            return secondLineMatcher.group(2);
        }
        return "Sex not found!";
    }
    private String getDateOfIssue(String idRawString){
        String[] idSplit = idRawString.split("\n");
        Matcher secondLineMatcher = Pattern.compile("([0-9O]{6})[0-9]([MF])([0-9]{6})[0-9][<]B([0-9]{9})[A-Z][<]{2}[0-9]").matcher(idSplit[1]);
        if (secondLineMatcher.matches()) {
            Log.d("blinkid","Date of Issue: " + secondLineMatcher.group(3) );
            return secondLineMatcher.group(3);
        }
        return "Date of Issue not found!";
    }
    private String getIDNo(String idRawString){
        String[] idSplit = idRawString.split("\n");
        Matcher secondLineMatcher = Pattern.compile("([0-9O]{6})[0-9]([MF])([0-9]{6})[0-9][<]B([0-9]{9})[A-Z][<]{2}[0-9]").matcher(idSplit[1]);
        if (secondLineMatcher.matches()) {
            Log.d("blinkid","ID Number: " + secondLineMatcher.group(4).replaceFirst("^0*", "") );
            return secondLineMatcher.group(4).replaceFirst("^0*", "");
        }
        return "ID Number not found!";
    }
    private String getName(String idRawString){
        String[] idSplit = idRawString.split("\n");
        Matcher lastLineMatcher = Pattern.compile("([A-Z]+)(?=<)<([A-Z]+)(?=<)<([A-Z]+)(?=<)[<]+").matcher(idSplit[2].trim().replace("0","O"));

        Log.d("blinkid", "names line"+idSplit[2]);
        if (lastLineMatcher.matches()){
            if (lastLineMatcher.groupCount() > 2) {
                Log.d("blinkid","Names: "+ (lastLineMatcher.group(1) + " " + lastLineMatcher.group(2) +" " + lastLineMatcher.group(3)));
                return lastLineMatcher.group(1) + " " + lastLineMatcher.group(2) +" " + lastLineMatcher.group(3);
            }else{
                Log.d("blinkid","Names: "+ (lastLineMatcher.group(1) + " " + lastLineMatcher.group(2)));
                return lastLineMatcher.group(1) + " " + lastLineMatcher.group(2);
            }
        }
        return "Names not found!";
    }


//    private void populateListViewNationalID(String paramString)
//    {
//        ArrayList localArrayList = new ArrayList();
//        Object localObject = TextUtils.split(paramString, "\n");
//        Log.d("blinkid", "raw(ID)=\n" + paramString);
//        paramString = Pattern.compile("IDKYA([0-9]{9})[0-9][<]{2}[0-9A-Z]+(?=<)[<]+(?=[0-9A-Z])[0-9A-Z]{3,4}").matcher(localObject[0]);
//        if (paramString.matches()) {
//            localArrayList.add("Serial Number: " + paramString.group(1));
//        }
//        Matcher localMatcher = Pattern.compile("([0-9O]{6})[0-9]([MF])([0-9]{6})[0-9][<]B([0-9]{9})[A-Z][<]{2}[0-9]").matcher(localObject[1]);
//        StringBuilder localStringBuilder;
//        if (localMatcher.matches())
//        {
//            //localArrayList.add("Date of Birth: " + betterRepresentDate(localMatcher.group(1).replace("O", "0")));
//            localStringBuilder = new StringBuilder().append("Gender: ");
//            if (!localMatcher.group(2).equals("M")) {
//               // break label411;
//            }
//        }
//        //label411:
//        for (paramString = "Male";; paramString = "Female")
//        {
//            localArrayList.add(paramString);
//            //localArrayList.add("Date of Issue: " + betterRepresentDate(localMatcher.group(3)));
//            localArrayList.add("ID Number: " + localMatcher.group(4).replaceFirst("^0*", ""));
//            localMatcher = Pattern.compile("([A-Z]+)(?=<)<([A-Z]+)(?=<)<([A-Z]+)(?=<)[<]+").matcher(localObject[2].trim());
//            if (localMatcher.matches())
//            {
//                localObject = localMatcher.group(1) + " " + localMatcher.group(2);
//                paramString = (String)localObject;
//                if (localMatcher.groupCount() > 2) {
//                    paramString = (String)localObject + " " + localMatcher.group(3);
//                }
//                localArrayList.add("Full Name: " + paramString);
//            }
//            //this.scanElementsListView.setAdapter(new ArrayAdapter(this, 17367043, localArrayList));
//            return;
//        }
//    }

}
