package com.stallar.vms.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.stallar.vms.R;
import com.stallar.vms.adapters.VisitsAdapter;
import com.stallar.vms.model.Visits;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class VisitorListFragment extends Fragment {
    String TAG = "Visitor list";
    ProgressBar progressBar;

    String apiUrl = "http://demo-vms.herokuapp.com/api/visits";
    String visitUrl = "http://demo-vms.herokuapp.com/api/visit/";
    String username = "admin";
    String password = "Merciful1";
    ArrayList<Visits> results = new ArrayList();
    JSONArray resultsArr;

    VisitsAdapter adapter;
    private RecyclerView recyclerView;


    public static VisitorListFragment newInstance(){
        VisitorListFragment fragement = new VisitorListFragment();
        return fragement;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_visitors_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.visitor_list_recycler);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar2);
        //progressBar.setVisibility(View.VISIBLE);

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

                try {
                    resultsArr = new JSONArray(new String(responseBody));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.d(TAG, "As a string"+ new String(responseBody));
                //Log.d(TAG, "Json arr"+ resultsArr);

                results = Visits.fromJson(resultsArr);
                Log.d(TAG, "List arr"+ resultsArr);
                adapter = new VisitsAdapter(results);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.INVISIBLE);

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

        return v;
    }

}
