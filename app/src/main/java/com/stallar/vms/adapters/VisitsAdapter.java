package com.stallar.vms.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.stallar.vms.R;
import com.stallar.vms.ResultView.Results;
import com.stallar.vms.model.Visitor;
import com.stallar.vms.model.Visits;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lucie on 9/30/2017.
 */

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.MyViewHolder> {

    private List<Visits> visitList;
    private Context context;
    String TAG = "VisitsAdapter";
    String username = "admin";
    String password = "Merciful1";
    JSONObject resultsObj;


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView visitor_name, purpose, time_in, time_out;

        public MyViewHolder(View v) {
            super(v);
            visitor_name = (TextView) v.findViewById(R.id.tv_visitor_name);
            purpose = (TextView) v.findViewById(R.id.tv_purpose);
            time_in = (TextView) v.findViewById(R.id.tv_time_in);
            time_out = (TextView) v.findViewById(R.id.tv_time_out);
        }
    }

    public VisitsAdapter(List<Visits> visitList){
        this.visitList = visitList;
    }

    @Override
    public VisitsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitors_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final VisitsAdapter.MyViewHolder holder, final int position) {
        Visits visits = visitList.get(position);
        final String visitor_id = visits.getVisitor().toString();
        holder.purpose.setText(visits.getPurpose());
        holder.time_in.setText(visits.getTime_in().toString());
        holder.time_out.setText(visits.getTime_out().toString());

        String baseUrl = "http://demo-vms.herokuapp.com/api/visitors/";
        final String visitorUrl = baseUrl + visitor_id;
        Log.d(TAG, visitorUrl);
        final AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(username,password);
        client.get(visitorUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //Log.d(TAG,"Get On success"+ responseBody.toString());
                try {
                    resultsObj = new JSONObject(new String(responseBody));
                    Log.d(TAG,"Get On success"+ resultsObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    holder.visitor_name.setText(resultsObj.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "On failure"+ error.toString());
            }
        });
//
//        Date date= readings.getTimeOfDay();
//        String format = "EEE MMM dd HH:mm:ss zzz yyyy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
//        Log.d("Retrofit date",sdf.toString());
//
//        try {
//            Date newDate = sdf.parse(date.toString());
//            String format2 = "dd/MM/yyyy HH:mm";
//            SimpleDateFormat sdf2 = new SimpleDateFormat(format2, Locale.US);
//            holder.date.setText(sdf2.format(newDate));
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Log.e(TAG, ""+e);
//        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Visitor list size: "+visitList.size());
        return visitList == null ?0 : visitList.size();
    }
}




