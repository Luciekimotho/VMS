package com.stallar.vms.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.stallar.vms.R;
import com.stallar.vms.Views.AddVisitor;
import com.stallar.vms.Views.VisitorsList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class AddVisitorFragment extends Fragment {

    String TAG = "Add visitor";

    String apiUrl = "http://demo-vms.herokuapp.com/api/visits";
    String visitUrl = "http://demo-vms.herokuapp.com/api/visit/";
    String username = "admin";
    String password = "Merciful1";

    Button saveBtn;
    EditText et_names, et_idno, et_phone, et_purpose, et_whereto;

    String names, idno, phone, purpose, whereTo;
    String ver_names, ver_idno;

    ProgressBar progressBar;

    public static AddVisitorFragment newInstance(){
        AddVisitorFragment fragement = new AddVisitorFragment();
        return fragement;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_visit, container, false);
        et_names = (EditText) v.findViewById(R.id.et_name);
        et_idno = (EditText) v.findViewById(R.id.et_idno);
        et_phone = (EditText) v.findViewById(R.id.et_phone);
        et_purpose = (EditText) v.findViewById(R.id.et_purpose);
        et_whereto = (EditText) v.findViewById(R.id.et_whereto);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        saveBtn = (Button) v.findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                ver_names = et_names.getText().toString();
                ver_idno = et_idno.getText().toString();
                phone = et_phone.getText().toString();
                purpose = et_purpose.getText().toString();
                whereTo = et_whereto.getText().toString();

                saveVisitor(ver_idno, ver_names, phone, 1, 1, purpose);
            }
        });
        return v;
    }

    private void saveVisitor(String id, String name,String phone, Integer device, Integer business, String purpose ){
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(username,password);
         /* Visits now working*/
        JSONObject params = new JSONObject();

        try {
            params.put("id_no", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("device", device);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("business", business);
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
        client.post(getActivity(), visitUrl, entity, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d(TAG,"Post on success"+ responseBody.toString());
                        Log.d(TAG, new String(responseBody));
                        progressBar.setVisibility(View.INVISIBLE);
                        //startActivity(new Intent(AddVisitor.this,VisitorsList.class));
                        VisitorListFragment fragment = new VisitorListFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, fragment);
                        fragmentTransaction.commit();
                        // set the toolbar title
                        //getSupportActionBar().setTitle("Visitor List");

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d(TAG, "Post On failure"+ error.toString());
                    }
                });
    }

}
