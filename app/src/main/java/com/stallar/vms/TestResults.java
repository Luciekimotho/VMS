package com.stallar.vms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.stallar.vms.RestAPI.LoginService;
import com.stallar.vms.RestAPI.ServiceGenerator;
import com.stallar.vms.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestResults extends AppCompatActivity {
    Button btnlogin;
    String name, idno;
    String TAG = "Results";
    String username = "admin";
    String password = "Merciful1";
    String apiUrl = "http://demo-vms.herokuapp.com/api/visits";
    String visitUrl = "http://demo-vms.herokuapp.com/api/visit/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);


        final AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(username,password);
        client.get(apiUrl, new AsyncHttpResponseHandler(){

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG,"Get On success"+ responseBody.toString());
                //Log.d(TAG, new String(responseBody));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
               Log.d(TAG, "On failure"+ error.toString());
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
            }
        });

//        RequestParams params = new RequestParams();
//        params.put("visitor", 1);
//        params.put("business", 1);
//        params.put("device", 1);
//        params.put("purpose", "Looking for a job");

       /* Visits not working*/
        JSONObject params = new JSONObject();

        try {
            params.put("id_no", "32179386");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("name", "Samson Rapando");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            params.put("phone", "+254720735");
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
            params.put( "purpose", "This is a weird purpose");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringEntity entity = null;
        try {
            entity = new StringEntity(params.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client.post(this, visitUrl, entity, "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d(TAG,"Post on success"+ responseBody.toString());
                        Log.d(TAG, new String(responseBody));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d(TAG, "Post On failure"+ error.toString());
                    }
                });


    }


}
